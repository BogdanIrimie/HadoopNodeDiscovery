import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import dto.LiveNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Client {
    public static void main(String[] args) {

        String ips = "109.231.122.0-100";//"109.231.122.54";
        CommandExecutor commandExecutor = new CommandExecutor();
        String nmapOutput = commandExecutor.execute("nmap --open " + ips + " -p 50070 -oX -");

        XmlToJsonConverter converter = new XmlToJsonConverter();
        String jsonString = converter.process(nmapOutput);
        List<String> potentialNameNodes = getPotentialNameNodes(jsonString);

        for (String ip : potentialNameNodes) {
            String url = "http://" + ip + ":50070/jmx?qry=Hadoop:service=NameNode,name=NameNodeInfo";
            HdfsNodeExtractor hdfsNodeExtractor = new HdfsNodeExtractor(url);

            List<LiveNode> liveDataNodes = hdfsNodeExtractor.getLiveDataNodes();
            //hdfsNodeExtractor.prettyPrint();
            System.out.print(liveDataNodes);
        }

        //"nmap --open 109.231.122.240 109.231.122.54 -p 50070"
    }

    public static List<String> getPotentialNameNodes(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        List<String> potentialNameNodes = new ArrayList<String>();

        try {
            rootNode = mapper.readTree(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonNode hostsNode =  rootNode.findPath("host");

        if (hostsNode.getNodeType().equals(JsonNodeType.OBJECT)) {
            String addr = hostsNode.findPath("addr").toString().replace("\"", "");
            String portId = hostsNode.findPath("portid").toString();
            String state = hostsNode.findPath("state").findPath("state").toString();
            if (portId.equals("50070") && state.replace("\"", "").equals("open")) {
                potentialNameNodes.add(addr);
            }
            //System.out.println("addr: " + addr + "\nportid " + portId + "\nstate: " + state);
        }
        if (hostsNode.getNodeType().equals(JsonNodeType.ARRAY)) {
            for (JsonNode node : hostsNode) {
                String addr = node.findPath("addr").toString().replace("\"", "");
                String portId = node.findPath("portid").toString();
                String state = node.findPath("state").findPath("state").toString();
                if (portId.equals("50070") && state.trim().equals("open")) {
                    potentialNameNodes.add(addr);
                }
                //System.out.println("addr: " + addr + "\nportid " + portId + "\nstate: " + state);
            }
        }

        return potentialNameNodes;
    }
}

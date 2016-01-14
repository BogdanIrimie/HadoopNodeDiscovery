import converters.JsonConverter;
import dto.LiveNode;
import dto.LiveNodeMinimal;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {

        StringBuilder ipList = new StringBuilder();
        for (String arg : args) {
            ipList.append(arg + " ");
        }
        String ips = ipList.toString().trim();
        CommandExecutor commandExecutor = new CommandExecutor();
        String nmapOutput = commandExecutor.execute("nmap --open " + ips + " -p 50070 -oX -");

        XmlToJsonConverter converter = new XmlToJsonConverter();
        PotentialNameNodeDiscoverer pnd = new PotentialNameNodeDiscoverer();
        String jsonString = converter.process(nmapOutput);
        List<String> potentialNameNodes = pnd.getPotentialNameNodes(jsonString);

        for (String ip : potentialNameNodes) {
            String url = "http://" + ip + ":50070/jmx?qry=Hadoop:service=NameNode,name=NameNodeInfo";
            HdfsNodeExtractor hdfsNodeExtractor = new HdfsNodeExtractor(url);
            List<LiveNode> liveDataNodes = hdfsNodeExtractor.getLiveDataNodes();

            List<LiveNodeMinimal> liveNodesMinimal = new ArrayList<LiveNodeMinimal>();
            for (LiveNode ln : liveDataNodes) {
                String nodeName = ln.getNodeName();
                String nodeAddress = ln.getLiveNodeDetails().getInfoAddr();
                liveNodesMinimal.add(new LiveNodeMinimal(nodeName, nodeAddress));
            }
            JsonConverter.prettyPrint(liveNodesMinimal);
        }
    }
}

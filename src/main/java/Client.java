import converters.JsonConverter;
import dto.LiveNode;
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
            //hdfsNodeExtractor.prettyPrint();
            String liveDataNodesJsonString = JsonConverter.objectToJsonString(liveDataNodes);
            System.out.print(liveDataNodesJsonString);
        }
    }
}

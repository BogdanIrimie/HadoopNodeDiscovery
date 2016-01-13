
public class Client {
    public static void main(String[] args) {

        String ips = "109.231.122.240 109.231.122.54";
        CommandExecutor commandExecutor = new CommandExecutor();
        String nmapOutput = commandExecutor.execute("nmap --open " + ips + " -p 50070 -oX -");
        /*
        String url = "http://109.231.122.54:50070/jmx?qry=Hadoop:service=NameNode,name=NameNodeInfo";
        HdfsNodeExtractor hdfsNodeExtractor = new HdfsNodeExtractor(url);
        hdfsNodeExtractor.getLiveDataNodes();
        hdfsNodeExtractor.prettyPrint();
        */

        //"nmap --open 109.231.122.240 109.231.122.54 -p 50070"
    }
}

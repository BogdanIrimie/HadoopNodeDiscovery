
public class Client {
    public static void main(String[] args) {
        String url = "http://109.231.122.54:50070/jmx?qry=Hadoop:service=NameNode,name=NameNodeInfo";
        HdfsNodeExtractor hdfsNodeExtractor = new HdfsNodeExtractor(url);
        hdfsNodeExtractor.getLiveDataNodes();
        hdfsNodeExtractor.prettyPrint();
    }
}

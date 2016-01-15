import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class XmlTOJsonConverterTest {

    /**
     * Test conversion from XML to JSON.
     */
    @Test
    public void convertXmlToJson() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<nodes>\n" +
                "    <node>\n" +
                "        <name>hdfs_dataNode_1</name>\n" +
                "        <ip>192.168.56.120</ip>\n" +
                "    </node>\n" +
                "    <node>\n" +
                "        <name>hdfs_dataNode_2</name>\n" +
                "        <ip>192.168.56.121</ip>\n" +
                "    </node>\n" +
                "</nodes>";
        String json = "{\"nodes\":{\"node\":[{\"ip\":\"192.168.56.120\",\"name\":\"hdfs_dataNode_1\"}," +
                "{\"ip\":\"192.168.56.121\",\"name\":\"hdfs_dataNode_2\"}]}}";

        XmlToJsonConverter xmlConverter = new XmlToJsonConverter();
        String convertedJson = xmlConverter.convert(xml);

        assertTrue(convertedJson.equals(json));
    }
}

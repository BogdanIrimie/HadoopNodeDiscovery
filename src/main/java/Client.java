import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        String url = "http://109.231.122.54:50070/jmx?qry=Hadoop:service=NameNode,name=NameNodeInfo";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = restTemplate.getForEntity(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        String body = response.getBody().toString();//.replaceAll("\\\\","");
        JsonNode rootNode = null;

        try {
            rootNode = mapper.readTree(body);
            JsonNode liveNodes = rootNode.findPath("LiveNodes");

            String lnString = liveNodes.toString();
            lnString = lnString.replaceAll("\\\\", "");
            lnString = lnString.substring(1, lnString.length() - 1);
            JsonNode lNodes = mapper.readTree(lnString);

            ((ObjectNode)rootNode.findPath("beans").get(0)).replace("LiveNodes",lNodes);
            //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode));
            System.out.println(rootNode.findPath("dice.cdh5.w6.internal"));

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}

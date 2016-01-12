import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dao.LiveNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        String url = "http://109.231.122.54:50070/jmx?qry=Hadoop:service=NameNode,name=NameNodeInfo";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        correctJsonString(mapper, response.getBody().toString());
    }

    public static JsonNode correctJsonString(ObjectMapper mapper, String body) {
        JsonNode rootNode = null;

        try {
            rootNode = mapper.readTree(body);

            List<String> nodeTypes = Arrays.asList("LiveNodes", "DeadNodes", "DecomNodes");

            for (String nodeType : nodeTypes) {
                JsonNode liveNodes = rootNode.findPath(nodeType);
                String lnString = liveNodes.toString();
                lnString = lnString.replaceAll("\\\\", "");
                lnString = lnString.substring(1, lnString.length() - 1);
                JsonNode lNodes = mapper.readTree(lnString);
                ((ObjectNode) rootNode.findPath("beans").get(0)).replace(nodeType, lNodes);
            }
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode));


            JsonNode nodes =  rootNode.findPath("LiveNodes");

            Iterator<Map.Entry<String, JsonNode>> nodeIterator = nodes.fields();
            while(nodeIterator.hasNext()) {
                Map.Entry<String, JsonNode> jsonParam = nodeIterator.next();
                String nodeName = jsonParam.getKey();
                LiveNode liveNode = mapper.treeToValue(jsonParam.getValue(), LiveNode.class);
                System.out.println(nodeName + " " + liveNode.getInfoAddr());
            }
            
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return rootNode;
    }

}
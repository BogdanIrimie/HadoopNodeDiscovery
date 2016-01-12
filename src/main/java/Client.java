import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dao.LiveNode;
import dao.LiveNodeDetails;
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
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(response.getBody().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        correctJsonString(rootNode);
        getDataNodes(rootNode);

    }

    public static JsonNode correctJsonString(JsonNode rootNode) {
        List<String> nodeTypes = Arrays.asList("LiveNodes", "DeadNodes", "DecomNodes");
        ObjectMapper mapper = new ObjectMapper();

        for (String nodeType : nodeTypes) {
            JsonNode liveNodes = rootNode.findPath(nodeType);
            String lnString = liveNodes.toString();
            lnString = lnString.replaceAll("\\\\", "");
            lnString = lnString.substring(1, lnString.length() - 1);
            JsonNode lNodes = null;
            try {
                lNodes = mapper.readTree(lnString);
                ((ObjectNode) rootNode.findPath("beans").get(0)).replace(nodeType, lNodes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return rootNode;
    }

    public static List<LiveNode> getDataNodes(JsonNode rootNode) {
        List<LiveNode> liveNodes = new ArrayList();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode nodes =  rootNode.findPath("LiveNodes");

        Iterator<Map.Entry<String, JsonNode>> nodeIterator = nodes.fields();
        while(nodeIterator.hasNext()) {
            Map.Entry<String, JsonNode> jsonParam = nodeIterator.next();
            String nodeName = jsonParam.getKey();
            LiveNodeDetails liveNodeDetails = null;
            try {
                liveNodeDetails = mapper.treeToValue(jsonParam.getValue(), LiveNodeDetails.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            liveNodes.add(new LiveNode(nodeName, liveNodeDetails));
            //System.out.println(nodeName + " " + liveNodeDetails.getInfoAddr());
        }

        return liveNodes;
    }


}
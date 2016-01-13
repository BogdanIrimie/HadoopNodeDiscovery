import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.DeadNode;
import dto.DeadNodeDetails;
import dto.LiveNode;
import dto.LiveNodeDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

public class HdfsNodeExtractor {
    private JsonNode rootNode = null;

    public HdfsNodeExtractor(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            rootNode = mapper.readTree(response.getBody().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        correctJsonString();
    }

    private JsonNode correctJsonString() {
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

        return rootNode;
    }

    public List<LiveNode> getLiveDataNodes() {
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
        }

        return liveNodes;
    }

    public List<DeadNode> getDeadNodes() {
        List<DeadNode> deadNodes = new ArrayList();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode nodes =  rootNode.findPath("DeadNodes");

        Iterator<Map.Entry<String, JsonNode>> nodeIterator = nodes.fields();
        while(nodeIterator.hasNext()) {
            Map.Entry<String, JsonNode> jsonParam = nodeIterator.next();
            String nodeName = jsonParam.getKey();
            DeadNodeDetails deadNodeDetails = null;
            try {
                deadNodeDetails = mapper.treeToValue(jsonParam.getValue(), DeadNodeDetails.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            deadNodes.add(new DeadNode(nodeName, deadNodeDetails));
        }

        return deadNodes;
    }

    public void prettyPrint() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

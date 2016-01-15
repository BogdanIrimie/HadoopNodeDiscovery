import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Query REST api of potential NameNode to obtain data about DataNodes
 */
public class HdfsNodeExtractor {
    private Logger logger = LoggerFactory.getLogger(HdfsNodeExtractor.class);
    private JsonNode rootNode = null;

    /**
     * Make REST call and correct resulting json.
     *
     * @param url used to make the REST call
     */
    public HdfsNodeExtractor(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            rootNode = mapper.readTree(response.getBody().toString());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        correctJsonString();
    }

    /**
     * Json contains NodeName as a string, so we correct it and transform the string in
     * a valid JsonNode and integrate it in the json tree.
     *
     * @return root node of the Json
     */
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
                logger.error(e.getMessage(), e);
            }
        }

        return rootNode;
    }

    /**
     * Extract list of DataNodes.
     *
     * @return list of DataNodes
     */
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
                logger.error(e.getMessage(), e);
            }
            liveNodes.add(new LiveNode(nodeName, liveNodeDetails));
        }

        return liveNodes;
    }

    /**
     * Extract minimal information about DataNodes;
     *
     * @return list with DataNodesMinimal objects
     */
    public List<LiveNodeMinimal> getLiveDatNodesMinimal() {
        List<LiveNode> liveDataNodes =  getLiveDataNodes();

        List<LiveNodeMinimal> liveNodesMinimal = new ArrayList<LiveNodeMinimal>();
        for (LiveNode ln : liveDataNodes) {
            String nodeName = ln.getNodeName();
            String nodeAddress = ln.getLiveNodeDetails().getInfoAddr();
            liveNodesMinimal.add(new LiveNodeMinimal(nodeName, nodeAddress));
        }

        return liveNodesMinimal;
    }

    /**
     * Extract list of DeadNodes.
     *
     * @return list of DeadNodes
     */
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
                logger.error(e.getMessage(), e);
            }
            deadNodes.add(new DeadNode(nodeName, deadNodeDetails));
        }

        return deadNodes;
    }
}

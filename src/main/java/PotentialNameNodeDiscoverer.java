import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Extract information about potential NameNodes from Json obtained from Nmap
 */
public class PotentialNameNodeDiscoverer {
    private Logger logger = LoggerFactory.getLogger(PotentialNameNodeDiscoverer.class);

    /**
     * Parse the Json resulted from a Nmap scann to find potential
     * (nodes that have port 50070 open) Namenodes.
     *
     * @param jsonString contains the Nmap scann results
     * @return list with potential NameNodes
     */
    public List<String> getPotentialNameNodes(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        List<String> potentialNameNodes = new ArrayList<String>();

        try {
            rootNode = mapper.readTree(jsonString);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        JsonNode hostsNode =  rootNode.findPath("host");

        if (hostsNode.getNodeType().equals(JsonNodeType.OBJECT)) {
            String addr = hostsNode.findPath("addr").toString().replace("\"", "");
            String portId = hostsNode.findPath("portid").toString();
            String state = hostsNode.findPath("state").findPath("state").toString();
            if (portId.equals("50070") && state.replace("\"", "").equals("open")) {
                potentialNameNodes.add(addr);
            }
        }
        if (hostsNode.getNodeType().equals(JsonNodeType.ARRAY)) {
            for (JsonNode node : hostsNode) {
                String addr = node.findPath("addr").toString().replace("\"", "");
                String portId = node.findPath("portid").toString();
                String state = node.findPath("state").findPath("state").toString();
                if (portId.equals("50070") && state.trim().equals("open")) {
                    potentialNameNodes.add(addr);
                }
            }
        }

        return potentialNameNodes;
    }
}

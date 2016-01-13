import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PotentialNameNodeDiscoverer {

    public List<String> getPotentialNameNodes(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        List<String> potentialNameNodes = new ArrayList<String>();

        try {
            rootNode = mapper.readTree(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonNode hostsNode =  rootNode.findPath("host");

        if (hostsNode.getNodeType().equals(JsonNodeType.OBJECT)) {
            String addr = hostsNode.findPath("addr").toString().replace("\"", "");
            String portId = hostsNode.findPath("portid").toString();
            String state = hostsNode.findPath("state").findPath("state").toString();
            if (portId.equals("50070") && state.replace("\"", "").equals("open")) {
                potentialNameNodes.add(addr);
            }
            //System.out.println("addr: " + addr + "\nportid " + portId + "\nstate: " + state);
        }
        if (hostsNode.getNodeType().equals(JsonNodeType.ARRAY)) {
            for (JsonNode node : hostsNode) {
                String addr = node.findPath("addr").toString().replace("\"", "");
                String portId = node.findPath("portid").toString();
                String state = node.findPath("state").findPath("state").toString();
                if (portId.equals("50070") && state.trim().equals("open")) {
                    potentialNameNodes.add(addr);
                }
                //System.out.println("addr: " + addr + "\nportid " + portId + "\nstate: " + state);
            }
        }

        return potentialNameNodes;
    }

}

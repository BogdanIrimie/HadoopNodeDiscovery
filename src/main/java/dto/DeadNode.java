package dto;

/**
 * DeadNode with name and DeadNodeDetails.
 */
public class DeadNode {
    private String nodeName;
    private DeadNodeDetails deadNodeDetails;

    public DeadNode(String nodeName, DeadNodeDetails deadNodeDetails) {
        this.nodeName = nodeName;
        this.deadNodeDetails = deadNodeDetails;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public DeadNodeDetails getDeadNodeDetails() {
        return deadNodeDetails;
    }

    public void setDeadNodeDetails(DeadNodeDetails deadNodeDetails) {
        this.deadNodeDetails = deadNodeDetails;
    }
}

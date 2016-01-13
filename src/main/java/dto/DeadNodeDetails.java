package dto;

public class DeadNodeDetails {
    private Long lastContact;
    private Boolean decommissioned;
    private String xferaddr;

    public Long getLastContact() {
        return lastContact;
    }

    public void setLastContact(Long lastContact) {
        this.lastContact = lastContact;
    }

    public Boolean getDecommissioned() {
        return decommissioned;
    }

    public void setDecommissioned(Boolean decommissioned) {
        this.decommissioned = decommissioned;
    }

    public String getXferaddr() {
        return xferaddr;
    }

    public void setXferaddr(String xferaddr) {
        this.xferaddr = xferaddr;
    }
}

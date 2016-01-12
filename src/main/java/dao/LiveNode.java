package dao;

public class LiveNode {
    private String infoAddr;
    private String infoSecureAddr;
    private String xferaddr;
    private Long lastContact;
    private Long usedSpace;
    private String adminState;
    private Long nonDfsUsedSpace;
    private Long capacity;
    private Long numBlocks;
    private String version;
    private Long used;
    private Long remaining;
    private Long blockScheduled;
    private Long blockPoolUsed;
    private Double blockPoolUsedPercent;
    private Long volfails;

    public String getInfoAddr() {
        return infoAddr;
    }

    public void setInfoAddr(String infoAddr) {
        this.infoAddr = infoAddr;
    }

    public String getInfoSecureAddr() {
        return infoSecureAddr;
    }

    public void setInfoSecureAddr(String infoSecureAddr) {
        this.infoSecureAddr = infoSecureAddr;
    }

    public String getXferaddr() {
        return xferaddr;
    }

    public void setXferaddr(String xferaddr) {
        this.xferaddr = xferaddr;
    }

    public Long getLastContact() {
        return lastContact;
    }

    public void setLastContact(Long lastContact) {
        this.lastContact = lastContact;
    }

    public Long getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(Long usedSpace) {
        this.usedSpace = usedSpace;
    }

    public String getAdminState() {
        return adminState;
    }

    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }

    public Long getNonDfsUsedSpace() {
        return nonDfsUsedSpace;
    }

    public void setNonDfsUsedSpace(Long nonDfsUsedSpace) {
        this.nonDfsUsedSpace = nonDfsUsedSpace;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Long getNumBlocks() {
        return numBlocks;
    }

    public void setNumBlocks(Long numBlocks) {
        this.numBlocks = numBlocks;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getUsed() {
        return used;
    }

    public void setUsed(Long used) {
        this.used = used;
    }

    public Long getRemaining() {
        return remaining;
    }

    public void setRemaining(Long remaining) {
        this.remaining = remaining;
    }

    public Long getBlockScheduled() {
        return blockScheduled;
    }

    public void setBlockScheduled(Long blockScheduled) {
        this.blockScheduled = blockScheduled;
    }

    public Long getBlockPoolUsed() {
        return blockPoolUsed;
    }

    public void setBlockPoolUsed(Long blockPoolUsed) {
        this.blockPoolUsed = blockPoolUsed;
    }

    public Double getBlockPoolUsedPercent() {
        return blockPoolUsedPercent;
    }

    public void setBlockPoolUsedPercent(Double blockPoolUsedPercent) {
        this.blockPoolUsedPercent = blockPoolUsedPercent;
    }

    public Long getVolfails() {
        return volfails;
    }

    public void setVolfails(Long volfails) {
        this.volfails = volfails;
    }
}

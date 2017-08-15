public class UsrEntity extends Entity {
    protected String[] attributes = {"id", "name", "fans", "status"};
    private String id;
    private String name;
    private String fans;
    private String status;
    UsrEntity() {
        super.attributes = this.attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String val) {
        id = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String val) {
        name = val;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String val) {
        fans = val;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String val) {
        status = val;
    }

}

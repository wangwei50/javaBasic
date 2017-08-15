public class BillEntity extends Entity {
    protected String[] attributes = {"id", "uid", "pay_money"};
    private String id;
    private String uid;
    private String pay_money;
    BillEntity() {
        super.attributes = this.attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;

    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

}

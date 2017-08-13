public class BillEntity extends Entity{
    public String _primaryKey = "id";
    public String _tableName = "bill";

    public int id;
    public int uid;
    public int pay_money;

    public int getId(){
        return id;
    }

    public int getUid(){
        return uid;

    }

    public int getPay_money(){
        return pay_money;

    }
}

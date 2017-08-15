import java.util.HashMap;

/**
 * Created by wangwei50 on 2017/8/10.
 */
public class helloWorld {
    public static void main(String[] argus) {

        UsrDao ud = new UsrDao();
        HashMap<String, Object> cond = new HashMap<>();
        cond.put("name", "William");

        UsrEntity ue = (UsrEntity) ud.selectOne(cond);

        System.out.println(ue.getName());
        System.out.println(ue.getId());

        BillDao bd = new BillDao();
        BillEntity be = (BillEntity) bd.selectByPK(1);

        System.out.println(be.getPay_money());


    }


}



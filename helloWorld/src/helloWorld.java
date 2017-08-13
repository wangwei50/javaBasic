import sun.jvm.hotspot.runtime.ResultTypeFinder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;

/**
 * Created by wangwei50 on 2017/8/10.
 */
public class helloWorld {
    public static void main(String[] argus){

        UsrDao ud = new UsrDao();
        HashMap<String,Object> cond = new HashMap<>();
        cond.put("name", "William");

        Entity ue  = ud.select(cond);

        //Entity ue = ud.selectByPK(9);

        System.out.println(ue.get("id"));
        System.out.println(ue.get("name"));

        BillDao bd = new BillDao();
        Entity be = bd.selectByPK(1);
        System.out.println(be.get("pay_money"));


    }


}



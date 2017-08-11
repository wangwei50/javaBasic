import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * Created by wangwei50 on 2017/8/10.
 */
public class helloWorld {
    public static void main(String[] argus){
        CDao cdb = CDao.getInstance();

        ResultSet rs = cdb.select("William");


        try{
            ResultSetMetaData m = rs.getMetaData();
            System.out.println(m.getColumnCount());

            while(rs.next()){

            }

        }catch(Exception e){
            e.printStackTrace();

        }


    }


}



import sun.jvm.hotspot.runtime.ResultTypeFinder;

import javax.xml.transform.Result;
import java.sql.*;

public class CDao {
    protected static CDao cdao;
    protected static Connection dbConnection;

    protected static String DBDRIVER = "com.mysql.jdbc.Driver";
    protected static String DBURL = "jdbc:mysql://localhost:3306/java?useSSL=false";
    protected static String DBUSER = "root";
    protected static String DBPASS = "root";

    private CDao(){}

    public static synchronized CDao getInstance(){
        if(cdao == null){
            cdao = new CDao();
            try{
                Class.forName(DBDRIVER); //1、使用CLASS 类加载驱动程序
                dbConnection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return cdao;
    }

    public ResultSet select(String tableName, String sqlCondition){
        PreparedStatement statement = null;
        ResultSet rs = null;

        try{
            statement = dbConnection.prepareStatement("SELECT * FROM ? WHERE ?");
            statement.setString(1, tableName);
            statement.setString(2,sqlCondition);

            rs = statement.executeQuery();
            return rs;
        }catch(Exception e){
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet select(String tableName){
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            statement = dbConnection.prepareStatement("SELECT * FROM usr WHERE name = ?") ;
            statement.setString(1,tableName);
            rs = statement.executeQuery();
        }catch(Exception e){
            System.out.println("1");
            e.printStackTrace();
        }

        return rs;
    }






}

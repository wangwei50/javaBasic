import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;
import sun.jvm.hotspot.runtime.ResultTypeFinder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

abstract class Dao {
    protected static Connection dbConnection;

    protected static String DBDRIVER = "com.mysql.jdbc.Driver";
    protected static String DBURL = "jdbc:mysql://localhost:3306/java?useSSL=false";
    protected static String DBUSER = "root";
    protected static String DBPASS = "root";
    protected String _tableName;
    protected String _entityName;

    Dao() {
        synchronized (this) {
            if (dbConnection == null) {
                try {
                    Class.forName(DBDRIVER); //1、使用CLASS 类加载驱动程序
                    dbConnection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public Entity select(HashMap<String, Object> map) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        Entity entity = null;
        String whereClause = getWhereClauseFromCondition(map);
        String sql = "SELECT * FROM " + _tableName + whereClause;

        try {
            statement = dbConnection.prepareStatement(sql);
            bindParams(statement, map);
            rs = statement.executeQuery();
            return getEntityFromRS(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entity;
    }

    public Entity selectByPK(int pk) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", pk);
        return select(map);
    }

    protected Entity getEntityFromRS(ResultSet rs) {
        try {
            if (_entityName.equals("billEntity")) {
                BillEntity entity = new BillEntity();
                while(rs.next()){
                    entity.id = rs.getInt("id");
                    entity.pay_money = rs.getInt("pay_money");
                    entity.uid = rs.getInt("uid");
                }
                return entity;
            } else if (_entityName.equals("usrEntity")) {
                UsrEntity entity = new UsrEntity();
                while (rs.next()) {
                    entity.id = rs.getInt("id");
                    entity.name = rs.getString("name");
                    entity.fans = rs.getInt("fans");
                    entity.status = rs.getInt("status");

                }
                return entity;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;

    }


    protected String getWhereClauseFromCondition(HashMap<String, Object> map) {
        String whereClause = "";
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            if (whereClause.equals("")) {
                whereClause = " WHERE " + key.toString() + " = ?";//todo check is the name of columns
            } else {
                whereClause = whereClause + " AND " + key.toString() + " = ?";
            }

        }

        return whereClause;
    }

    protected void bindParams(PreparedStatement statement, HashMap<String, Object> map) {
        Iterator iter = map.entrySet().iterator();
        int index = 0;
        try {
            while (iter.hasNext()) {

                Map.Entry entry = (Map.Entry) iter.next();
                Object val = entry.getValue();
                index++;
                statement.setString(index, val.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

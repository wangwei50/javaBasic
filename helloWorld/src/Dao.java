import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

abstract class Dao {
    protected volatile static Connection dbConnection;

    protected static String DBDRIVER = "com.mysql.jdbc.Driver";
    protected static String DBURL = "jdbc:mysql://localhost:3306/java?useSSL=false";
    protected static String DBUSER = "root";
    protected static String DBPASS = "root";
    protected String _tableName;
    protected String _entityName;

    Dao() {
        if (dbConnection == null) {
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
    }


//    public void insert(Entity entity){
//        PreparedStatement statement = null;
//        ResultSet rs = null;
//        Entity entity = null;
//        String sql = "INSERT INTO  " + _tableName + "()" + "VALUES()";
//
//        try {
//            statement = dbConnection.prepareStatement(sql);
//            bindParams(statement, map);
//            rs = statement.executeQuery();
//            return getEntityFromRS(rs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void update(Entity entity) {
//
//        PreparedStatement statement = null;
//        ResultSet rs = null;
//        String whereClause = getWhereClauseFromCondition(map);
//        String sql = "SELECT * FROM " + _tableName + whereClause;
//
//        try {
//            statement = dbConnection.prepareStatement(sql);
//            bindParams(statement, map);
//            rs = statement.executeQuery();
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    public int delete(Entity entity){
        int isDeleted = 0;
        try{
            HashMap<String,String> map = new HashMap<>();
            map.put(entity.getPk(),entity.get(entity.getPk()));
            String whereClause = getWhereClauseFromCondition(map);

            String sql = "DELETE FROM " + _tableName + whereClause;
            PreparedStatement statement = dbConnection.prepareStatement(sql);
            bindParams(statement, map);

            isDeleted = statement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return isDeleted;
    }

    public boolean update(Entity entity){
        boolean isSuccess = false;
        try{
            ArrayList<String> values = new ArrayList<>();
            String setStr = "";
            String[] attributes =  entity.attributes;
            for(int i = 0;i<attributes.length;i++){
                if(attributes[i] == entity.getPk() || entity.get(attributes[i]) == null){
                    continue;
                }
                if(!setStr.equals("")){
                    setStr = setStr + ",";
                }
                setStr = setStr + attributes[i] + "=?";
                values.add(entity.get(attributes[i]));
            }
            values.add(entity.get(entity.getPk()));
            String sql = "UPDATE usr SET "+setStr+" WHERE "+entity.getPk() + "=?" ;
            PreparedStatement statement = dbConnection.prepareStatement(sql);
            bindParams(statement, values);
            int isInserted = statement.executeUpdate();
            if(isInserted == 1){
                isSuccess = true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

    public boolean insert(Entity entity){
        boolean isSuccess = false;
        try{
            ArrayList<String> values = new ArrayList<>();
            String columnStr = "";
            String commaStr = "";
            String[] attributes =  entity.attributes;
            for(int i = 0;i<attributes.length;i++){
                if(attributes[i] == entity.getPk() || entity.get(attributes[i]) == null){
                    continue;
                }
                if(!columnStr.equals("")){
                    columnStr = columnStr + ",";
                    commaStr = commaStr+",";
                }
                columnStr = columnStr + attributes[i];
                commaStr = commaStr + "?";
                values.add(entity.get(attributes[i]));
            }
            String sql = "INSERT INTO usr("+columnStr+") " + "VALUES("+commaStr+")";
            PreparedStatement statement = dbConnection.prepareStatement(sql);
            bindParams(statement, values);
            int isInserted = statement.executeUpdate();
            if(isInserted == 1){
                isSuccess = true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

    public ArrayList<Entity> select(HashMap<String, String> map) {
        PreparedStatement statement = null;
        ArrayList<Entity> entities = null;
        String whereClause = getWhereClauseFromCondition(map);
        String sql = "SELECT * FROM " + _tableName + whereClause;

        try {
            statement = dbConnection.prepareStatement(sql);
            bindParams(statement, map);
            ResultSet rs = statement.executeQuery();
            entities =  getEntityFromRS(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entities;
    }

    public Entity selectOne(HashMap<String, String> map) {
        ArrayList<Entity> entities = select(map);
        return entities.get(0);
    }

    public Entity selectByPK(String pk) {
        HashMap<String, String> map = new HashMap<>();

        map.put("id", pk);
        return selectOne(map);
    }

    protected ArrayList<Entity> getEntityFromRS(ResultSet rs) {
        ArrayList<Entity> entities = new ArrayList<>();
        try {
            Class c = Class.forName(_entityName);
            while (rs.next()) {
                Entity entity = (Entity) c.newInstance();
                String[] attributes = entity.getAttributes();

                for (int i = 0; i < attributes.length; i++) {
                    entity.set(attributes[i], rs.getString(attributes[i]));
                }
                entities.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entities;

    }


    protected String getWhereClauseFromCondition(HashMap<String, String> map) {
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

    protected void bindParams(PreparedStatement statement, HashMap<String, String> map) {
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

    protected void bindParams(PreparedStatement statement, ArrayList<String> al) {
        try {
            for(int i=0;i<al.size();i++){
                statement.setString(i+1, al.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

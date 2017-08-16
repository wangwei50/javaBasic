
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangwei50 on 2017/8/10.
 */
public class helloWorld {
    public static void main(String[] argus) {
        testSelect();

        //testInsert and Delete
        testInsert();

        //testUpdate
        testUpdate();



    }

    protected static void testSelect(){
        UsrDao ud = new UsrDao();
        HashMap<String, String> cond = new HashMap<>();
        cond.put("name", "William");

        UsrEntity ue = (UsrEntity) ud.selectOne(cond);
        System.out.println("This is select Query:");
        System.out.println("Result is :" + ue.getName());
    }

    protected static void testInsert(){
        UsrEntity ueToInsert =  new UsrEntity();
        ueToInsert.setName("TEST_NAME_SPECIFIC");
        ueToInsert.setFans("0");

        UsrDao ud = new UsrDao();
        ud.insert(ueToInsert);

        HashMap<String,String> cond = new HashMap<>();
        cond.put("name", "TEST_NAME_SPECIFIC");
        ArrayList<Entity> al = ud.select(cond);
        for(int i=0;i<al.size();i++){
            UsrEntity ue = (UsrEntity)al.get(i);
            System.out.println(ue.getId());
            ud.delete(ue);
        }

    }

    protected static void testUpdate(){
        UsrDao ud = new UsrDao();
        HashMap<String,String> cond = new HashMap<>();
        cond.put("name", "William");
        ArrayList<Entity> al = ud.select(cond);
        UsrEntity ue = (UsrEntity) al.get(0);
        System.out.println("Before update, fans_count is " + ue.getFans());

        int fans = Integer.parseInt(ue.getFans()) +1;
        ue.setFans(Integer.toString(fans));
        ud.update(ue);

        ud.select(cond);
        UsrEntity ue1 = (UsrEntity) al.get(0);
        System.out.println("After update, fans_count is " + ue1.getFans());



    }


}



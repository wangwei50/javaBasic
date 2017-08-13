import java.lang.reflect.Method;
import java.util.HashMap;

abstract class Entity {

    public Object get(String fieldName){
        try{
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = this.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(this, new Object[] {});
            return value;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}

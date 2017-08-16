import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;

import java.lang.reflect.Method;

abstract class Entity implements sget {
    protected String[] attributes;

    public String[] getAttributes() {
        return attributes;
    }

    public String getPk(){
        return "id";
    }

    public String get(String fieldName) {
        Object value = null;

        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method[] methods = this.getClass().getMethods();
            for(Method method : methods){
                if(method.getName().equals(getter)){
                    value = method.invoke(this,new Class[]{});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String)value;
    }

    public void set(String fieldName, String val) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setter = "set" + firstLetter + fieldName.substring(1);
            Method method = this.getClass().getMethod(setter, String.class);//一个参数，参数类型是String
            method.invoke(this, val);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

import java.lang.reflect.Method;

abstract class Entity implements sget {
    protected String[] attributes;

    public String[] getAttributes() {
        return attributes;
    }

    public Object get(String fieldName) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = this.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(this, new Object[]{});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

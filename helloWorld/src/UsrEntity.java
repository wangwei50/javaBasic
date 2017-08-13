import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;

import java.util.HashMap;

public class UsrEntity extends Entity{

    public int id;
    public String name;
    public int fans;
    public int status;

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getFans(){
        return fans;
    }

    public int getStatus(){
        return status;
    }

    public void setId(int val){
        id = val;
    }

    public void setName(String val){
        name = val;
    }

    public void setFans(int val){
        fans = val;
    }

    public void setStatus(int val){
        status = val;
    }




}

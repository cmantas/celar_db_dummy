/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.ntua.cslab.db_entities2;

import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author cmantas
 */
public class User extends DBIDEntity{
    String name;
    
    public User(){
        super();
    }
    
    public User(JSONObject jo){
        super(jo);
    }
    
    public User(Map<String, String> inmap){
        super(inmap);
    }
    
    public User(int id) throws DBException{
        super(id);
    }
    
    
    public User(String name){
        this.name=name;
    }

    @Override
    protected void fromMap(Map<String, String> fields) {
        if(fields.containsKey("id"))
            this.id=Integer.parseInt(fields.get("id"));
        else this.id = -1;
        this.name=fields.get("name");
    }

    @Override
    protected Map<String, String> toMap() {
        Map<String, String> m = new java.util.TreeMap();
        m.put("id", ""+id);
        m.put("name", name);
        return m;
    }

    @Override
    public String getTableName() {
        return "USER";
    }

    

    /**
     * gets the user with the specified name
     * 
     * @param name
     * @return 
     * @throws gr.ntua.cslab.database.entities2.DBException 
    */
    public static User getByName(String name) throws DBException{
        User dummy = new User();
        
        return (User)dummy.getByField("name", name).get(0);

    }
    
}

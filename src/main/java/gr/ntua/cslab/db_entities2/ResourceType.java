/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.ntua.cslab.db_entities2;

import java.util.List;
import java.util.Map;

/**
 *
 * @author cmantas
 */
public class ResourceType extends DBIDEntity{

    String type;
    
    public ResourceType(){
        super();
    }
    
    public ResourceType(String type){
        this.type=type;
    }
    
    public ResourceType(Map<String, String> inmap){
        super(inmap);
    }
    
    public ResourceType(int id) throws DBException{
        super(id);
    }

    @Override
    protected void fromMap(Map<String, String> fields) {
        if(fields.containsKey("id"))
            this.id=Integer.parseInt(fields.get("id"));
        this.type=fields.get("type");
    }

    @Override
    protected Map<String, String> toMap() {
        Map<String, String> m = new java.util.TreeMap();
        m.put("id", ""+id);
        m.put("type", type);
        return m;
    }

    @Override
    public String getTableName() {
        return "RESOURCE_TYPE";
    }
    
    public String getTypeName(){
        return this.type;
    }

    

    /**
     * gets the resource with the specified String type (assumed unique)
     * 
     * @param type
     * @return 
     * @throws gr.ntua.cslab.database.entities2.DBException 
    */
    public static ResourceType getByName(String type) throws DBException{
        ResourceType dummy = new ResourceType();
        List<ResourceType> list = dummy.getByField("type", type);
        if(list.size()==0){
            throw new DBException(DBException.NO_SUCH_ENTRY, "No resources found of type: "+type);
        }
        return list.get(0);

    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.cslab.database.entities2;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static org.apache.log4j.Level.*;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *Abstract DataBase Entity
 * @author cmantas
 */
public abstract class DBEntity {

	protected boolean modified=true; /** True if the current state of the 
					   * entity is not stored in the DB
	 				   */
        private static Logger LOG = Logger.getLogger(DBEntity.class);
        
	/**
	 * Default constructor.
	 * by default the 
	 */
	public DBEntity(){
		modified=true;
	}
        

	/**
	 * Creates an entity from the fields of the database entry.
	 * Calls then fromMap method which is implemented in the subclasses
	 * @param fields
	 */
	public DBEntity(Map <String,String> fields){
		fromMap(fields);
		modified=false;
	}
        
        public  DBEntity(JSONObject jo) {
            modified=true;
            fromJSON(jo);
        }


	/**
	 *Reads the entity-specific parameters from the Database fields and stores
	 * them in the appropriate class variables;
	 * @param fields
	 */
	abstract protected void fromMap(Map <String,String> fields);
        
        abstract protected Map<String, String> toMap();
        
        abstract public String getTableName();

	public boolean isModified(){
		return modified;
	}

	

	/**
	 * Stores the entity in the appropriate Database Table
	 * @return
     * @throws gr.ntua.cslab.database.entities2.DBException
	 */
	public void store() throws DBException{
           DBTools.insertData(this.getTableName(), this.toMap());
        }
        
    /**
     * Converts the Entity to a JSON representation
     * @return a String in JSON format
     */
    public JSONObject toJSONObject(){
        JSONObject jo = new JSONObject();
        Map<String,String> m = this.toMap();
        for(Map.Entry<String,String> entry: m.entrySet()){
            jo.put(entry.getKey(), entry.getValue());
        }
        return jo;
    }
    
    void fromJSON(JSONObject jo){
        Map<String,String> m = new java.util.TreeMap();
        for(Object i: jo.keySet()){
            String key = (String) i;
            m.put(key, jo.getString(key));
        }
        fromMap(m);
    }
    
        @Override
    public String toString(){
        String s ="";
        s+= this.getClass().getSimpleName() +"(";
        Map<String,String> m = this.toMap();
        boolean once = true;
        for(Map.Entry<String,String> entry: m.entrySet()){
            if(once) once=false;
            else s+=", ";
            s+=entry.getKey()+":"+entry.getValue();
        }
        s+=")";
        return s;
    }
    
    
    /**
     * Checks all fields of the two entities to see if they are equal
     * @param e
     * @return a String in JSON format
     */
    public boolean equals(DBEntity e){
        JSONObject jo = new JSONObject();
        Map<String,String> me = this.toMap();
        Map<String,String> other = e.toMap();
        for(Map.Entry<String,String> entry: me.entrySet()){
            String key = entry.getKey();
            String my_value = entry.getValue();
            if(! other.containsKey(key)) return false;
            if(! other.get(key).equals(my_value)) return false;
        }
        return true;
    }
   
    public static <T extends DBEntity> List<T> getByField(Class myClass, String field, String value){
        List<T> results = new java.util.LinkedList();
        try {
            T dummy = (T) myClass.newInstance();
            List<Map<String, String>> mapsFromDB = DBTools.doSelectByField(dummy.getTableName(), field, value);

            for (Map m : mapsFromDB) {
                    T e = (T) myClass.newInstance();
                    e.fromMap(m);
                    e.modified = false;
                    results.add(e);
                }
        } catch (DBException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }   catch (InstantiationException ex) {
                System.err.println("Instatiation exception, have you defined a default constructor for "+myClass.getSimpleName()+" ?");
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
               ex.printStackTrace();
            }
        return results;
    }
    
    
    public  <T extends DBEntity> List<T> getByField( String field, String value) {
        return getByField(this.getClass(), field, value);
    }
    
    
    public void delete() throws DBException{
       DBTools.doDelete(this.getTableName(), this.toMap());
    }
    

	
}

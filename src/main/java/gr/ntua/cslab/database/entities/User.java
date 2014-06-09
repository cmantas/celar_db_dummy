package gr.ntua.cslab.database.entities;

import static gr.ntua.cslab.database.Tables.usertable;
import gr.ntua.cslab.database.UserTable;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author cmantas
 */
public class User extends DBIDEntity{
	
	String name;

	/**
	 * Creates an unstored user from a name and the user table
	 * @param name
	 * @param table 
	 */
	public User(String name) {
		super(usertable);
		this.name=name;
	}

	
	/**
	 * Creates a previously stored user as retrieved from the fields of the database
	 * @param fields
	 */
	public User(Map <String,String> fields){
		super(fields, usertable);
		this.name=fields.get("name");
	}
	
	/**
	 * Creates an previously stored user directly from  the database
	 * @param id
	 * @param table 
	 */
	public User(int id){
		super(id, usertable);
	}
        
         /**
         * creates an unstored User from a json object
         * @param jo
         */
        public User(JSONObject jo) {
            super(jo, usertable);
        }
	

	/**
	 * Stores the user in the database and retrieves the id he was assigned
	 * @return true if successful false if not 
	 */
	@Override
	public boolean store() {
		UserTable t=(UserTable) table;
		this.id=t.insertUser(name);
		if(id!=0){
			this.modified=false;
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		return "User: "+name+" , id:"+id;
	}


	@Override
	protected void fromMap(Map <String,String> fields) {
		this.id=Integer.parseInt(fields.get("id"));
		this.name=fields.get("name");
	}

    @Override
    public JSONObject toJSONObject() {
        JSONObject json= new JSONObject();
        json.put("id", id);
        json.put("name", name);       
        return json;
    }

    @Override
    void fromJSON(JSONObject jo)  {
        this.name=jo.getString("name");
    }
    
    
    /**
     * gets the user with the specified name
     * 
     * @param name
     * @return 
    */
    public static User getByName(String name){
        List<ResourceType> prs=new java.util.LinkedList();
        List<Integer> pr_ids= usertable.getIdsForSelection("name", ""+name);
        for(Integer id: pr_ids){
            return new User(id);
        }
        return null;
    }




	
}

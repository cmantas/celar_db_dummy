package gr.ntua.cslab.database;

import gr.ntua.cslab.database.entities.User;
import java.util.List;
import java.util.Map;

public class UserTable extends IDTable {

	public UserTable() {super();}
	
	public UserTable(boolean connect) {super(connect);}

	@Override
	protected String getTableName() {
		return "USER";
	}
	
	
	/**
	 * Inserts a user with the specified id and name
	 * @param id
	 * @param name
     * @return true if everything went smoothly, else it returns false
	 */
	public boolean insertUser(int id, String name) throws DBException{
		Map<String, String> data = new java.util.TreeMap<String, String>();
		data.put("id", Integer.toString(id));
		data.put("name",name);
		return	this.insertData(data);
	}

	public User getUser(int id) throws DBException{
		return new User(id);	
	}
	

	
	/**
	 * Inserts a user with the specified name;
	 * @param name
	 * @return the given id if successful, -1 if not. 
	 */
	public int insertUser(String name) throws DBException{
		int id=this.getNextId();
		if(insertUser(id,name))
			return id;
		else return -1;
	}

    /**
     * @return a list of all users in the table
     */
    public List<User> getAllUsers() throws DBException {
        List<User> results = new java.util.LinkedList();
        List<String> IDs = doSelect("id", "TRUE").get("id");
        //for each of the ids create the user
        for (String id : IDs) {
            results.add(new User(Integer.parseInt(id)));
        }
        return results;
    }
	

}
package database;

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
	 * It returns true if everything went smoothly, else it returns false.
	 * @param id
	 * @param name
	 */
	public boolean insertUser(int id, String name){
		Map<String, String> data = new java.util.TreeMap<String, String>();
		data.put("id", Integer.toString(id));
		data.put("name",name);
		return	this.insertData(data);
	}
	

	
	/**
	 * Inserts a user with the specified name;
	 * @param name
	 * @return the given id if successful, -1 if not. 
	 */
	public int insertUser(String name){
		int id=this.getNextId();
		if(insertUser(id,name))
			return id;
		else return -1;
	}

	

	
	
	
	public static void main(String[] args) {
		UserTable table = new UserTable();
		table.openConnection();
		int id=table.insertUser("christaras" );
		System.out.println(table.exists(id));
		
		table.delete(id);
//		table.insertUser("ggian", null, null, null);
		System.out.println(table.exists(id));
		
		table.closeConnection();
		
	}

}
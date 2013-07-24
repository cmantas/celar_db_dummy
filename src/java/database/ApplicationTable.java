package database;

import java.sql.Date;
import java.util.Map;

public class ApplicationTable extends IDTable {
	
	public ApplicationTable() {super();}
	
	
	public ApplicationTable(boolean connect) {super(connect);}


	

	@Override
	protected String getTableName() {
		return "APPLICATION";
	}
	
	
	/**
	 * Inserts an application  with the specified data. 
	 * It returns true if everything went smoothly, else it returns false.
	 * @param id
	 * @param description the application description
	 * @param submitted the time submitted
	 * @param USER_id the foreign key of the owner
	 */
	public boolean insertApplication(int id, String description, Date submitted, int USER_id){
		Map<String, String> data = new java.util.TreeMap<String, String>();
		data.put("id", Integer.toString(id));
		data.put("description",description);
		data.put("submitted",submitted.toString());
		data.put("USER_id",Integer.toString(USER_id));
		return	this.insertData(data);
	}
	

	
	/**
	 * Inserts an application  with the specified data next available id 
	 * @param description the application description 
	 * @param submitted the time submitted
	 * @param USER_id the foreign key of the owner
	 * @return the given id if successful, -1 if not. 
	 */
	public int insertApplication(String description, Date submitted, int USER_id){
		int id=this.getNextId();
		if(insertApplication(id,description, submitted, USER_id))
			return id;
		else return -1;
	}

	/**
	 * Inserts an application  with the specified data next available id, with current timestamp 
	 * @param description the application description
	 * @param USER_id the foreign key of the owner. 
	 */
	public int insertApplication(String description, int USER_id){
		int id=this.getNextId();
		if(insertApplication(id,description, new Date(System.currentTimeMillis()), USER_id))
			return id;
		else return -1;
	}


}
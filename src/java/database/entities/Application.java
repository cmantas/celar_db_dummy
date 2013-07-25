package database.entities;

import database.ApplicationTable;
import java.sql.Timestamp;
import java.util.Map;

/**
 *
 * @author cmantas
 */
public class Application extends DBIDEntity{
	
	String description;
	Timestamp submitted;
	int userId;

	/**
	 * Creates an unstored application from a description, submitted and the application table
	 * @param name
	 * @param table 
	 */
	public Application(String description, Timestamp submitted, User user, ApplicationTable table) throws NotInDBaseException {
		super(table);
		this.description=description;
		this.submitted=submitted;
		if(user.id==0||user.modified)
			throw new NotInDBaseException("the User must be stored "
				+ "in Database before the application is created");
		this.userId=user.id;
	}

	
	
	/**
	 * Creates an previously stored application directly from  the database
	 * @param id
	 * @param table 
	 */
	public Application(int id, ApplicationTable table){
		super(id, table);
	}
	


	/**
	 * Stores the application in the database and retrieves the id he was assigned
	 * @return true if successful false if not 
	 */
	@Override
	public boolean store() {

		ApplicationTable t=(ApplicationTable) table;
		this.id=t.insertApplication(description,submitted, userId);
		if(id!=0){
			this.modified=false;
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		return "Application: "+description+", id:"+id+" submitted:"+submitted+", userId:"+userId;
	}

	


	@Override
	protected void fromMap(Map<String, String> fields) {
		this.id=Integer.parseInt(fields.get("id"));
		this.description=fields.get("description");
		this.submitted=Timestamp.valueOf(fields.get("submitted"));
		this.userId=Integer.parseInt(fields.get("USER_id"));
	}


	
}

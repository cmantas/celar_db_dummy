package database.entities;

import database.DeploymentTable;
import java.sql.Timestamp;
import java.util.Map;

/**
 *
 * @author cmantas
 */
public class Deployment extends DBIDEntity{
	
	
	int applicationId;
	Timestamp startTime, endTime;

	/**
	 * Creates an unstored deployment from a name, submitted and the deployment table
	 * @param application an application object that this deployment is from
	 * @param start_time the time this deployment was created
	 * @param end_time the time this deployment was destroyed
	 */
	public Deployment(Application application, Timestamp start_time, 
		Timestamp end_time, DeploymentTable table)
				throws NotInDBaseException {
		super(table);
		//check if the app is not strored in the database (for consistency reasons)
		if(application.id==0||application.modified)
			throw new NotInDBaseException("the User must be stored "
				+ "in Database before the deployment is created");
		this.applicationId=application.id;
		this.startTime=start_time;
		this.endTime=end_time;
	}

	
	
	/**
	 * Creates an previously stored deployment directly from  the database
	 * @param id
	 * @param table 
	 */
	public Deployment(int id, DeploymentTable table){
		super(id, table);
	}
	


	/**
	 * Stores the deployment in the database and retrieves the id he was assigned
	 * @return true if successful false if not 
	 */
	@Override
	public boolean store() {
		DeploymentTable t=(DeploymentTable) table;
		this.id=t.insertDeployment(applicationId, startTime,endTime);
		if(id!=0){
			this.modified=false;
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		return "Deployment,  id:"+id+" appId:"+applicationId 
		+ "start time="+startTime+" end time="+endTime;
		
	}


	@Override
	protected void fromMap(Map<String, String> fields) {
		this.id=Integer.parseInt(fields.get("id"));
		this.applicationId=Integer.parseInt(fields.get("APPLICATION_id"));
		this.startTime=Timestamp.valueOf(fields.get("start_time"));
		String end=fields.get("end_time");
		this.endTime= end==null?null:Timestamp.valueOf(fields.get("end_time"));
	
	}


	
}

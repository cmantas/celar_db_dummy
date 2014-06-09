package gr.ntua.cslab.database.entities;

import gr.ntua.cslab.database.ApplicationTable;
import gr.ntua.cslab.database.Tables;
import java.sql.Timestamp;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author cmantas
 */
public class Application extends DBEntity{
	
	String description, id="-1";
	Timestamp submitted;
	int userId, uniqueId, majorVersion, minorVersion;

	/**
	 * Creates an unstored application from a description, submitted and the application table
     * @param uniqueId
     * @param majorVersion
     * @param description
     * @param minorVersion
     * @param submitted
     * @param user
     * @throws gr.ntua.cslab.database.entities.NotInDBaseException
	 */
	public Application(int uniqueId, int majorVersion, int minorVersion, String description, Timestamp submitted, User user) throws NotInDBaseException {
		super(Tables.appTable);
		this.description=description;
		this.submitted=submitted;
                this.uniqueId = uniqueId;
                this.majorVersion = majorVersion;
                this.minorVersion = minorVersion;
		if(user.id==0||user.modified)
			throw new NotInDBaseException("the User must be stored "
				+ "in Database before the application is created");
		this.userId=user.id;
	}
        
        public Application(String description, Timestamp submitted, User user) throws NotInDBaseException {
		super(Tables.appTable);
		this.description=description;
		this.submitted=submitted;
                this.uniqueId = 0;
                this.majorVersion = 0;
                this.minorVersion = 0;
		if(user.id==0||user.modified)
			throw new NotInDBaseException("the User must be stored "
				+ "in Database before the application is created");
		this.userId=user.id;
	}

	
	
	/**
	 * Creates an previously stored application directly from  the database
	 * @param id
	 */
	public Application(String id){
                super(Tables.appTable);
		Map <String,String> fields=((ApplicationTable)table).getById(id);
		fromMap(fields);
		modified=false;
	}
	
        
        
         /**
         * creates an unstored Application from a json object
         * @param jo
         */
        public Application(JSONObject jo){
            super(jo, Tables.appTable);
        }
	


	/**
	 * Stores the application in the database and retrieves the id he was assigned
	 * @return true if successful false if not 
	 */
	@Override
	public boolean store() {

		ApplicationTable t=(ApplicationTable) table;
		this.id=t.insertApplication(uniqueId, majorVersion, minorVersion, description,userId);
		if(!id.equals("-1")){
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
        this.id = fields.get("id");
        this.uniqueId = Integer.parseInt(fields.get("unique_id"));
        this.majorVersion = Integer.parseInt(fields.get("major_version"));
        this.minorVersion = Integer.parseInt(fields.get("minor_version"));
        this.description = fields.get("description");
        this.submitted = Timestamp.valueOf(fields.get("submitted"));
        this.userId = Integer.parseInt(fields.get("USER_id"));
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("unique_id",uniqueId);
        json.put("major_version", majorVersion);
        json.put("minor_version",minorVersion);
        json.put("description", description);
        json.put("submitted", submitted.toString());    
        json.put("USER_id", userId);        
        return json;
    }

   
        @Override
        void fromJSON(JSONObject jo)  {
        String appDescription = (String) jo.get("description");
        int appUserId = jo.getInt("USER_id");
        this.userId=appUserId;
        if(jo.has("unique_id")){
            this.uniqueId = jo.getInt("unique_id");
            this.majorVersion = jo.getInt("major_version");
            this.minorVersion = jo.getInt("minor_version");
        }
        else{
            this.uniqueId=0;
            this.majorVersion=0;
            this.minorVersion=0;
        }

        String appSubmittedString = jo.has("submitted")?(String) jo.get("submitted"):"now";
        Timestamp appSubmittedTs;
        if (appSubmittedString.equalsIgnoreCase("now")) {
            appSubmittedTs = new Timestamp(System.currentTimeMillis());
        } else {
            appSubmittedTs = Timestamp.valueOf(appSubmittedString);
        }
        this.description=appDescription;
        this.submitted=appSubmittedTs;
        
    }

   public String getId(){
       return this.id;
   }
   
   public void delete(){
		((ApplicationTable)this.table).delete(id);
		this.id="-1";
		this.modified=true;
		
	}

	
}

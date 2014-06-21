/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.ntua.cslab.database.entities2;

import java.sql.Timestamp;
import java.util.Map;
import org.apache.log4j.Logger;
import static org.apache.log4j.Level.*;
import org.json.JSONObject;

/**
 *
 * @author cmantas
 */
public class Application extends DBEntity {
    String description, id;
    Timestamp submitted;
    int userId, uniqueId, majorVersion, minorVersion;
    
    static Logger LOG = Logger.getLogger(Application.class);
    
    static{
        LOG.setLevel(DEBUG);
    }

    public Application() {
        super();
        id="0 ";
    }
    
    public Application(JSONObject jo){
        super(jo);
    }

    public Application(int uniqueId, int majorVersion, int minorVersion, String description, Timestamp submitted, User user) {
        this.description = description;
        this.submitted = submitted;
        this.userId = user.id;
        this.uniqueId = uniqueId;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        
        if(this.uniqueId==0){
             String prevMax = DBTools.maxValue(this.getTableName(), "unique_id");
             this.uniqueId= prevMax!=null? Integer.parseInt(prevMax)+1:1;
             this.id = makeStringID(this.uniqueId, majorVersion, minorVersion);
        }
    }
    
   
    public Application(String description, Timestamp submitted, User user) {
        this(0,0,0, description, submitted, user);
    }
    
    public Application(String description, User user) {
        this(0,0,0, description, new Timestamp(System.currentTimeMillis()), user);
    }

    @Override
    protected void fromMap(Map<String, String> fields) {
        this.id = fields.get("id");
        LOG.debug("parsed id = "+this.id);
        this.uniqueId = Integer.parseInt(fields.get("unique_id"));
        this.majorVersion = Integer.parseInt(fields.get("major_version"));
        this.minorVersion = Integer.parseInt(fields.get("minor_version"));
        this.description = fields.get("description");
        this.submitted = Timestamp.valueOf(fields.get("submitted"));
        this.userId = Integer.parseInt(fields.get("USER_id"));
    }

    @Override
    protected Map<String, String> toMap() {
        Map<String, String> map = new java.util.TreeMap();
        map.put("id",id);
        map.put("unique_id",""+uniqueId);
        map.put("major_version", ""+majorVersion);
        map.put("minor_version", ""+minorVersion);
        map.put("description", ""+description);
        map.put("submitted", submitted.toString());    
        map.put("USER_id", ""+userId);
        return map;
    }

    @Override
    public String getTableName() {
        return "APPLICATION";
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public int getUserId() {
        return userId;
    }
    
     /**
     * Constructs the String "id" for the application table
     * @param uniqueId the unique application identifier
     * @param majorVersion the major version of the application
     * @param minorVersion the minor version of the application
     * @return an 18-digit String (uniqueId.majorVersion.minorVersion)based on the given parameters
     */
    
    public static String makeStringID(int uniqueId, int majorVersion, int minorVersion){
       String rv = String.format("%010d", uniqueId) +"."+
               String.format("%03d", majorVersion)+"."+
               String.format("%03d", minorVersion);
        return rv;
    }
    
    
}

package gr.ntua.cslab.database;

import static gr.ntua.cslab.database.Table.decode;
import gr.ntua.cslab.database.entities.Application;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class ApplicationTable extends Table {

    public ApplicationTable() {
        super();
    }

    public ApplicationTable(boolean connect) {
        super(connect);
    }

    @Override
    protected String getTableName() {
        return "APPLICATION";
    }
    
    

    /**
     * Inserts an application with the specified data. It returns true if
     * everything went smoothly, else it returns false.
     *
     * @param id 
     * @param uniqueId the unique application identifier
     * @param majorVersion the major version of the application
     * @param description the application description
     * @param minorVersion the minor version of the application
     * @param submitted the time submitted
     * @param USER_id the foreign key of the owner
     * @return success or not
     */
    public boolean insertApplication(String id,  int uniqueId, int majorVersion,
            int minorVersion, String description,Timestamp submitted, int USER_id) {
        Map<String, String> data = new java.util.TreeMap<String, String>();
        data.put("id", id);
        data.put("unique_id", Integer.toString(uniqueId));
        data.put("major_version", Integer.toString(majorVersion));
        data.put("minor_version", Integer.toString(minorVersion));
        data.put("description", description);
        data.put("submitted", submitted.toString());
        data.put("USER_id", Integer.toString(USER_id));
        return this.insertData(data);
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

    /**
     * Inserts an application with the specified data
     * You have to know a unique id (previously existing or not for the application inserted
     *
     * @param uniqueId
     * @param majorVersion
     * @param minorVersion
     * @param description the application description
     * @param submitted the time submitted
     * @param USER_id the foreign key of the owner
     * @return the given id if successful, -1 if not.
     */
    public String insertApplication(int uniqueId, int majorVersion, int minorVersion,
                                    String description, int USER_id) {
        if(uniqueId==0){
             String prevMax = this.maxValue("unique_id");
             uniqueId= prevMax!=null? Integer.parseInt(prevMax)+1:1;
        }
        String id = makeStringID(uniqueId, majorVersion, minorVersion);
        if (insertApplication(id, uniqueId, majorVersion, minorVersion,description, new Timestamp(System.currentTimeMillis()), USER_id)) {
            return id;
        } else {
            return "-1";
        }
    }


    public Application getApplication(String id) {
        return new Application(id);
    }

    /**
     * Retrieves all the Applications for the user with the given id
     *
     * @param userId
     * @return
     */
    public List<Application> getUserApplications(int userId) {
        List<Application> results = new java.util.LinkedList();
        String field = "id";
        String testField = "USER_id";
        List<String> IDs = doSelectEquals(field, testField, ""+userId).get(field);
        //for each of the ids create the module
        for (String id : IDs) {
            results.add(new Application(id));
        }
        return results;
    }
    
    	/**
	 * Deletes a row from the application table.
	 * @param id
	 * @return
	 */
	public boolean delete(String id){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute(this.deleteSQL("id","'"+id+"'"));
			statement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
        
        	/**
	 * Checks if the entry with the specified id exists 
	 * @param Id
	 * @return 
	 */
	public boolean exists(String Id){
            try {
                        ResultSet set=executeQuery(this.selectSQL("id='"+Id+"'"));
                        boolean result=set.next();
                        set.close();
                        return result;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
	}
        
        
        	/**
	 * Retrieves the entry stored under the specified id
	 * @param id
	 * @return a Map of ColumnName->Value for this entry
	 */
	public Map<String, String> getById(String id) {
            String query = this.selectSQL("id='" +id+"'");
		try {
			Map<String, String> result = new java.util.TreeMap();
			Statement statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			rs.next();
			for (int i = 1; i < columnCount + 1; i++) {
				String name = rsmd.getColumnName(i);
				String value = decode(rs.getString(name));
				result.put(name, value);
			}

			return result;
		} catch (SQLException ex) {
                        System.err.println("Query failed: \n" + query);
			ex.printStackTrace();
		}
		return null;
	}

}

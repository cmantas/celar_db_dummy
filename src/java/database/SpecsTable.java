package database;

import java.util.Map;

public class SpecsTable extends IDTable {
	
	public SpecsTable() {super();}
	
	
	public SpecsTable(boolean connect) {super(connect);}


	@Override
	protected String getTableName() {
		return "SPECS";
	}
	
	
	/**
	 * Inserts a spec with the specified data. 
	 * It returns true if everything went smoothly, else it returns false.
	 * @param id
	 * @param PROVIDED_RESOURCE_id the provided resource this is a spec of: 
	 * @param description the description of this resource
	 * @return true if success false if not
	 */
	public boolean insertSpecs(int id, int PROVIDED_RESOURCE_id, String description){
		Map<String, String> data = new java.util.TreeMap<String, String>();
		data.put("id", Integer.toString(id));
		data.put("PROVIDED_RESOURCE_id",Integer.toString(PROVIDED_RESOURCE_id));
		data.put("description",description);
		return	this.insertData(data);
	}
	

	
	/**
	 * Inserts a spec with the specified data at the next available id 
	 * @param PROVIDED_RESOURCE_id the provided resource this is a spec of
	 * @param description the description of this resource 
	 * @return the given id if successful, -1 if not. 
	 */
	public int insertSpecs( int PROVIDED_RESOURCE_id, String description){
		int id=this.getNextId();
		if(insertSpecs(id, PROVIDED_RESOURCE_id, description))
			return id;
		else return -1;
	}

}
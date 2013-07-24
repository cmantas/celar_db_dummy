package database;

import java.util.Map;

public class ComponentTable extends IDTable {
	
	public ComponentTable() {super();}
	
	
	public ComponentTable(boolean connect) {super(connect);}


	@Override
	protected String getTableName() {
		return "COMPONENT";
	}
	
	
	/**
	 * Inserts an component  with the specified data. 
	 * It returns true if everything went smoothly, else it returns false.
	 * @param id
	 * @param MODULE_id the component id that this component is from
	 * @param PROVIDED_RESOURCE_id the provided resource this component is an istance of
	 * @return true if success false if not
	 */
	public boolean insertComponent(int id, int MODULE_id, int PROVIDED_RESOURCE_id){
		Map<String, String> data = new java.util.TreeMap<String, String>();
		data.put("id", Integer.toString(id));
		data.put("MODULE_id",Integer.toString(MODULE_id));
		data.put("PROVIDED_RESOURCE_id",Integer.toString(PROVIDED_RESOURCE_id));
		return	this.insertData(data);
	}
	

	
	/**
	 * Inserts an component  with the specified data next available id 
	 * @param APPLICATION_id the component id that this component is from
	 * @param name the name of the component
	 * @return the given id if successful, -1 if not. 
	 */
	public int insertComponent(int MODULE_id, int PROVIDED_RESOURCE_id){
		int id=this.getNextId();
		if(insertComponent(id,	MODULE_id,  PROVIDED_RESOURCE_id))
			return id;
		else return -1;
	}

}
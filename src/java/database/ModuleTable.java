package database;

import database.entities.Module;
import java.sql.Date;
import java.util.Map;

public class ModuleTable extends IDTable {
	
	public ModuleTable() {super();}
	
	
	public ModuleTable(boolean connect) {super(connect);}


	@Override
	protected String getTableName() {
		return "MODULE";
	}
	
	
	/**
	 * Inserts an module  with the specified data. 
	 * It returns true if everything went smoothly, else it returns false.
	 * @param id
	 * @param APPLICATION_id the module id that this module is from
	 * @param name the name of the module
	 */
	public boolean insertModule(int id, int APPLICATION_id, String name){
		Map<String, String> data = new java.util.TreeMap<String, String>();
		data.put("id", Integer.toString(id));
		data.put("APPLICATION_id",Integer.toString(APPLICATION_id));
		data.put("name",name);
		return	this.insertData(data);
	}
	

	
	/**
	 * Inserts an module  with the specified data next available id 
	 * @param APPLICATION_id the module id that this module is from
	 * @param name the name of the module
	 * @return the given id if successful, -1 if not. 
	 */
	public int insertModule(int APPLICATION_id, String name){
		int id=this.getNextId();
		if(insertModule(id,APPLICATION_id, name))
			return id;
		else return -1;
	}


	public Module getModule(int id){
		return new Module(id, this);	
	}

}
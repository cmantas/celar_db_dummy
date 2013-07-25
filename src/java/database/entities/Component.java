package database.entities;

import database.ComponentTable;
import java.util.Map;

/**
 *
 * @author cmantas
 */
public class Component extends DBIDEntity{
	
	int moduleId, providedResourceId;

	/**
	 * Creates an unstored component from a module, provided resource and the component table
	 * @param module the module this component belongs to
	 * @param pr the provided resource this made from physically
	 * @param table a component table
	 */
	public Component(Module module, ProvidedResource pr, ComponentTable table) throws NotInDBaseException {
		super(table);
		//check if the app is not strored in the database (for consistency reasons)
		if(module.id==0||module.modified)
			throw new NotInDBaseException("the module must be stored "
				+ "in Database before the component is created");
		if(pr.id==0||pr.modified)
			throw new NotInDBaseException("the provided resource must be stored "
				+ "in Database before the component is created");
		this.moduleId=module.id;
		this.providedResourceId=pr.id;
	}

	
	
	/**
	 * Creates an previously stored component directly from  the database
	 * @param id
	 * @param table 
	 */
	public Component(int id, ComponentTable table){
		super(id, table);
	}
	


	/**
	 * Stores the component in the database and retrieves the id he was assigned
	 * @return true if successful false if not 
	 */
	@Override
	public boolean store() {
		ComponentTable t=(ComponentTable) table;
		this.id=t.insertComponent(moduleId, providedResourceId);
		if(id!=0){
			this.modified=false;
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		return "Component, id:"+id+" ModuleId:"+moduleId+" prov.ResourceId:"+providedResourceId;
	}


	@Override
	protected void fromMap(Map<String, String> fields) {
		this.id=Integer.parseInt(fields.get("id"));
		this.moduleId=Integer.parseInt(fields.get("MODULE_id"));
		this.providedResourceId=Integer.parseInt(fields.get("PROVIDED_RESOURCE_id"));
	}


	
}

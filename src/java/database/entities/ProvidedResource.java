package database.entities;

import database.ProvidedResourceTable;
import java.util.Map;

/**
 *
 * @author cmantas
 */
public class ProvidedResource extends DBIDEntity{
	
	String type, description;

	/**
	 * Creates an unstored providedResource from a type, submitted and the providedResource table
	 * @param type
	 * @param description
	 * @param table a providedResource table
	 */
	public ProvidedResource(String type, String description, ProvidedResourceTable table) throws NotInDBaseException {
		super(table);
		this.type=type;
		this.description=description;
	}

	
	
	/**
	 * Creates an previously stored providedResource directly from  the database
	 * @param id
	 * @param table 
	 */
	public ProvidedResource(int id, ProvidedResourceTable table){
		super(id, table);
	}
	


	/**
	 * Stores the providedResource in the database and retrieves the id he was assigned
	 * @return true if successful false if not 
	 */
	@Override
	public boolean store() {
		ProvidedResourceTable t=(ProvidedResourceTable) table;
		this.id=t.insertProvidedResource(type,description);
		if(id!=0){
			this.modified=false;
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		return "ProvidedResource: "+type+", id:"+id+" description:"+description;
	}


	@Override
	protected void fromMap(Map<String, String> fields) {
		this.id=Integer.parseInt(fields.get("id"));
		this.type=fields.get("type");
		this.description=fields.get("description");
	}


	
}

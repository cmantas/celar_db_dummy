package gr.ntua.cslab.database;

import gr.ntua.cslab.database.entities.User;
import java.util.List;
import java.util.Map;

public class ResourceTypeTable extends IDTable {

	public ResourceTypeTable() {super();}
	
	public ResourceTypeTable(boolean connect) {super(connect);}

	@Override
	protected String getTableName() {
		return "RESOURCE_TYPE";
	}
	
	
	/**
	 * Inserts a ProvidedResource with the specified id and type
	 * @param id
	 * @param type
     * @return true if everything went smoothly, else it returns false
	 */
	public boolean insertProvidedResource(int id, String type){
		Map<String, String> data = new java.util.TreeMap<String, String>();
		data.put("id", Integer.toString(id));
		data.put("type",type);
		return	this.insertData(data);
	}

	

	
	/**
	 * Inserts a ProvidedResource with the specified type;
	 * @param type
	 * @return the given id if successful, -1 if not. 
	 */
	public int insertResourceType(String type){
		int id=this.getNextId();
		if(ResourceTypeTable.this.insertProvidedResource(id,type))
			return id;
		else return -1;
	}
        
        
        /**
         * Returns the id of the given type name
         * @param name
         * @return type id
         */        
        public int getTypeid(String type){
            String field="id";
            String testField="type";
            List<String> IDs=doSelectEquals(field, testField, type).get(field);
            return Integer.parseInt(IDs.remove(0));
        }

	

}
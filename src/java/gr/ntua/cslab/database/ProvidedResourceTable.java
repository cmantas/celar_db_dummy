package gr.ntua.cslab.database;

import gr.ntua.cslab.database.entities.ProvidedResource;
import java.util.List;
import java.util.Map;

public class ProvidedResourceTable extends IDTable {
	
	public ProvidedResourceTable() {super();}
	
	
	public ProvidedResourceTable(boolean connect) {super(connect);}


	@Override
	protected String getTableName() {
		return "PROVIDED_RESOURCE";
	}
	
	
	/**
	 * Inserts a provided resource  with the specified data. 
	 * It returns true if everything went smoothly, else it returns false.
	 * @param id
	 * @param name the type of the provided resource 
	 * @param description the description of this resource
	 * @return true if success false if not
	 */
	public boolean insertProvidedResource(int id, int resourceTypeId, String name){
		Map<String, String> data = new java.util.TreeMap<String, String>();
		data.put("id", Integer.toString(id));
		data.put("name",name);
                data.put("RESOURCE_TYPE_id",Integer.toString(resourceTypeId));
		return	this.insertData(data);
	}
	

	
    /**
     * Inserts a provided resource with the specified data at the next available
     * id
     *
     * @param resourceTypeId
     * @param name the name of the component
     * @return the given id if successful, -1 if not.
     */
	public int insertProvidedResource( int resourceTypeId, String name){
		int id=this.getNextId();
		if(insertProvidedResource(id, resourceTypeId,  name))
			return id;
		else return -1;
	}

	public ProvidedResource getProvidedResource(int id){
		return new ProvidedResource(id);
	}
        
        /**
         *Returns a list of all provided resources in the table
         * 
     * @return 
         */
        public List<ProvidedResource> getAllProvidedResources(){
             List<ProvidedResource> results=new java.util.LinkedList();
            List<String> IDs=doSelect("id","TRUE").get("id");
            //for each of the ids create the module
            for(String id : IDs){
                results.add(new ProvidedResource(Integer.parseInt(id)));
            }
            return  results;
        }


}

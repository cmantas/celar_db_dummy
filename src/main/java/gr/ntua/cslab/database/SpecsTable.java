package gr.ntua.cslab.database;

import gr.ntua.cslab.database.entities.ProvidedResource;
import gr.ntua.cslab.database.entities.Spec;
import java.util.List;
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
	 * @param property the spec property name
     * @param value the spec property value
	 * @return true if success false if not
	 */
	public boolean insertSpecs(int id, int PROVIDED_RESOURCE_id, String property, String value) throws DBException{
		Map<String, String> data = new java.util.TreeMap<String, String>();
		data.put("id", Integer.toString(id));
		data.put("PROVIDED_RESOURCE_id",Integer.toString(PROVIDED_RESOURCE_id));
		data.put("property",property);
                data.put("value",value);
		return	this.insertData(data);
	}
	

	
	/**
	 * Inserts a spec with the specified data at the next available id 
	 * @param PROVIDED_RESOURCE_id the provided resource this is a spec of
	 * @param property the spec property name
     * @param value the spec property value
	 * @return the given id if successful, -1 if not. 
	 */
	public int insertSpecs( int PROVIDED_RESOURCE_id, String property, String value) throws DBException{
		int id=this.getNextId();
		if(insertSpecs(id, PROVIDED_RESOURCE_id, property, value))
			return id;
		else return -1;
	}

        
        public  List<Spec>getProvidedResourceSpecs(ProvidedResource pr) throws DBException{
            List<Spec> results=new java.util.LinkedList();
            String field="id";
            String testField="PROVIDED_RESOURCE_id";            
            List<String> IDs=doSelectEquals(field, testField, ""+pr.getId()).get(field);
            //for each of the ids create the component
            for(String id : IDs){
                results.add(new Spec(Integer.parseInt(id)));
            }
            
            return  results;
        }

}

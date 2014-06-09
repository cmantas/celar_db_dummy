package gr.ntua.cslab.database.entities;

import gr.ntua.cslab.database.ResourceTypeTable;
import static gr.ntua.cslab.database.Tables.resTypeTable;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author cmantas
 */
public class ResourceType extends DBIDEntity {
	
	String name;

    /**
     * Creates an unstored ResourceType from a type string
     * @param type
     * @throws gr.ntua.cslab.database.entities.NotInDBaseException
     */
	public ResourceType(String type) throws NotInDBaseException {
		super(resTypeTable);
		this.name=type;
	}

	
	
	/**
	 * Creates an previously stored ResourceType from its id
	 * @param id 
	 */
	public ResourceType(int id){
		super(id, resTypeTable);
	}
	

         /**
         * creates an unstored ResourceType from a JSON object
         * @param jo
         */
        public ResourceType(JSONObject jo) {
            super(jo, resTypeTable);
        }
	

	/**
	 * Stores the ResourceType in the database and retrieves the id it was assigned
	 * @return true if successful false if not 
	 */
	@Override
	public boolean store() {
		ResourceTypeTable t=(ResourceTypeTable) table;
		this.id=t.insertResourceType(name);
		if(id!=0){
			this.modified=false;
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		return "ResourceType: "+" id:"+id+" type:"+name ;
	}


	@Override
	protected void fromMap(Map<String, String> fields) {
		this.id=Integer.parseInt(fields.get("id"));
		this.name=fields.get("type");
	}

    @Override
    public JSONObject toJSONObject() {
       JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("name", ""+name);
        return json;
    }

    @Override
    void fromJSON(JSONObject jo) {
        this.name=jo.getString("name");
    }

    /**
     * Searches the database for the resource type with the given Resource Type String
     * @param typeName
     * @return 
    */
    public static ResourceType getByName(String typeName){
        List<ResourceType> prs=new java.util.LinkedList();
        List<Integer> pr_ids= resTypeTable.getIdsForSelection("type", ""+typeName);
        for(Integer id: pr_ids){
            return new ResourceType(id);
        }
        return null;
    }
	
}

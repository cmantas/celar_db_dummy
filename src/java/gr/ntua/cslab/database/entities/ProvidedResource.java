package gr.ntua.cslab.database.entities;

import gr.ntua.cslab.database.ProvidedResourceTable;
import gr.ntua.cslab.database.Tables;
import static gr.ntua.cslab.database.Tables.provResTable;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author cmantas
 */
public class ProvidedResource extends DBIDEntity {

	String name;
        int resourceTypeId;

    /**
     * Creates an unstored providedResource from a type, submitted and the
     * providedResource table
     *
     * @param name
     * @param resourceTypeId
     * @throws gr.ntua.cslab.database.entities.NotInDBaseException
     */
	public ProvidedResource( int resourceTypeId, String name) throws NotInDBaseException {
		super(provResTable);
		this.resourceTypeId=resourceTypeId;
		this.name=name;
	}

	
	
	/**
	 * Creates an previously stored providedResource directly from  the database
	 * @param id
	 * @param table 
	 */
	public ProvidedResource(int id){
		super(id, provResTable);
	}
	

         /**
         * creates an unstored Proved Resource from a json object
         * @param jo
         */
        public ProvidedResource(JSONObject jo) {
            super(jo, provResTable);
        }
	

	/**
	 * Stores the providedResource in the database and retrieves the id he was assigned
	 * @return true if successful false if not 
	 */
	@Override
	public boolean store() {
		ProvidedResourceTable t=(ProvidedResourceTable) table;
		this.id=t.insertProvidedResource(resourceTypeId, name);
		if(id!=0){
			this.modified=false;
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		return "ProvidedResource: "+" id:"+id+" name:"+name+", resource type id: "+this.resourceTypeId;
	}


	@Override
	protected void fromMap(Map<String, String> fields) {
		this.id=Integer.parseInt(fields.get("id"));
		this.resourceTypeId=Integer.parseInt( fields.get("RESOURCE_TYPE_id") );
		this.name=fields.get("name");
	}

    @Override
    public JSONObject toJSONObject() {
       JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("RESOURCE_TYPE_id", this.resourceTypeId);
        json.put("name", ""+name);
        return json;
    }

    @Override
    void fromJSON(JSONObject jo) {
        this.name=jo.getString("name");
        this.resourceTypeId=jo.getInt("RESOURCE_TYPE_id");
    }

    /**
     * gets all the provided resources of the given type
     * 
    */
    static List<ProvidedResource> getByType(String resourceTypeName){
        List<ProvidedResource> prs=new java.util.LinkedList();
        int typeId = Tables.resTypeTable.getTypeid(resourceTypeName);
        List<Integer> pr_ids= provResTable.getIdsForSelection("RESOURCE_TYPE_id", ""+typeId);
        for(Integer id: pr_ids){
            prs.add(new ProvidedResource(id));
        }
        return prs;
    }
    
	
}

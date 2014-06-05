package gr.ntua.cslab.database.entities;

import gr.ntua.cslab.database.SpecsTable;
import gr.ntua.cslab.database.Tables;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author cmantas
 */
public class Spec extends DBIDEntity{
	
	int providedResourceId;
	String  property, value;
        
    /**
     * Creates an unstored spec from a provided resource, description and the
     * spec table
     *
     * @param pr
     * @param property
     * @param value
     * @throws gr.ntua.cslab.database.entities.NotInDBaseException
     */
	public Spec(ProvidedResource pr, String property, String value) throws NotInDBaseException {
		super(Tables.specsTable);
		if(pr.id==0||pr.modified)
			throw new NotInDBaseException("the provided resource must be stored "
				+ "in Database before the spec is created");
		this.providedResourceId=pr.id;
		this.property=property;
                this.value = value;
	}

	
	
	/**
	 * Creates an previously stored spec directly from  the database
	 * @param id
	 */
	public Spec(int id){
		super(id, Tables.specsTable);
	}
	
        
        /**
         * creates an unstored Spec from a json object
         * @param jo
         */
        public Spec(JSONObject jo) {
            super(jo, Tables.specsTable);
        }


	/**
	 * Stores the spec in the database and retrieves the id he was assigned
	 * @return true if successful false if not 
	 */
	@Override
	public boolean store() {
		SpecsTable t=(SpecsTable) table;
		this.id=t.insertSpecs(providedResourceId, property, value);
		if(id!=0){
			this.modified=false;
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		return "Spec, prov.Resource: "+providedResourceId+", id:"+id+" property:"+property+ " value: "+value;
	}


	@Override
	protected void fromMap(Map<String, String> fields) {
		this.id=Integer.parseInt(fields.get("id"));
		this.providedResourceId=Integer.parseInt(fields.get("PROVIDED_RESOURCE_id"));
		this.property=fields.get("property");
                this.value=fields.get("value");
	}

    @Override
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("PROVIDED_RESOURCE_id",providedResourceId);
        json.put("property", ""+property);
        json.put("value", ""+value);
        return json;
    }

    @Override
    void fromJSON(JSONObject jo) {
        this.property=jo.getString("property");
        this.property=jo.getString("value");
        this.providedResourceId=jo.getInt("PROVIDED_RESOURCE_id");
    }


	
}

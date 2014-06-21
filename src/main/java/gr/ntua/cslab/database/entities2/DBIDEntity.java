/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.cslab.database.entities2;

import java.util.Map;

/**
 *
 * @author cmantas
 */
public abstract class DBIDEntity extends DBEntity {

    int id;
    
     public DBIDEntity(){
         super();
         this.id=0;
     }
     
     public DBIDEntity(int id) throws DBException{
         Map fields = DBTools.doSelectByID(this.getTableName(), id);
         this.fromMap(fields);
     }
    
    public DBIDEntity(Map<String, String> fields) {
        super(fields);
    }

    /**
     * Stores the entity in the appropriate Database Table
     *
     * @return
     * @throws gr.ntua.cslab.database.entities2.DBException
     */
    public void store() throws DBException {
        Map m = this.toMap();
        this.id = DBTools.insertIDData(this.getTableName(), m);
        
    }
    
    /**
     * Deletes the entity from the appropriate table based on its ID;
     * @throws gr.ntua.cslab.database.entities2.DBException
     */
    public void delete() throws DBException{
        DBTools.doDeleteID(this.getTableName(), id);
    }
}

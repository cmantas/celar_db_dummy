/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.entities;

import database.Table;
import java.util.Map;

/**
 *
 * @author cmantas
 */
public abstract class DBEntity {
	Table table;
	protected boolean modified=true;

	public DBEntity(){
		modified=true;
	}

	public DBEntity(Map <String,String> fields, Table table){
		this.table=table;
		fromMap(fields);
		modified=false;
	}



	abstract protected void fromMap(Map <String,String> fields);

	public boolean isModified(){
		return modified;
	}

	

	abstract public boolean store();

	
}

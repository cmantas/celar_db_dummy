/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.cslab.database;

import gr.ntua.cslab.database.entities.*;
import java.sql.SQLException;

/**
 *
 * @author cmantas
 */
public class DBException extends SQLException{
    
    byte type = 0;
    
    //define types
    public final static byte FOREIGN_KEY=1;
    public final static byte NO_SUCH_ENTRY=2;
    

	DBException(String msg){
		super(msg);
                if (is_foreign_key())
                    type = FOREIGN_KEY;
	}

    public DBException(byte type, String detail) {
        super("Detail: "+detail);
        this.type=type;

    }
        
        
        public boolean is_foreign_key(){
        String msg = this.getMessage();
        if(msg.indexOf("foreign key constraint")!=-1)
            return true;
        else return false;
    }
        
    
     public String getDetail(){
        String msg = this.getMessage();
        int index=msg.indexOf("Detail:");
        index += 8;
        return msg.substring(index);
    }
     
     public String getType(){
         switch(type){
             case FOREIGN_KEY:
                 return "Foreign Key Violation";
             case NO_SUCH_ENTRY:
                 return "Entry not in the DB";
             default:
                 return "SQL Error";
         }
     }

    @Override
     public String toString(){
         return "Type: "+this.getType()+"\n\tDetails: "+this.getDetail();
     }
	
}

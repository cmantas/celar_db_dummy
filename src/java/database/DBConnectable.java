/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author cmantas
 */
public interface DBConnectable {
	final static  String USER="root";
	final static String PASSWORD="";
	final String DB_NAME="celar_db";
	final String DRIVER="com.mysql.jdbc.Driver";


	public void openConnection();
	public void closeConnection();	
	
}

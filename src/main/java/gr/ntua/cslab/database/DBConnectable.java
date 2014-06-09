/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.cslab.database;

import static gr.ntua.cslab.database.Table.DEBUG;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cmantas
 */
public abstract class DBConnectable {
    	final static  String USER="celaruser";
	final static String PASSWORD="celar-user";
	final static String DB_NAME="celardb";
        final static String BACKEND = "postgresql";
        final static String HOST = "localhost";
        final static String PORT = "5432";
        
        protected Connection connection;

	/**
	 * Opens the connection with the database.
	 * 
	 * <b>Note:</b> SLOW procedure, use with care!
	 */
    public void openConnection() {
       connection= getConnection();
        if (DEBUG && connection!=null) {
            System.out.println("Connection created");
        }
    }

	/**
	 * Closes the connection with the database.
	 */
	public void closeConnection(){
		try {
			connection.close();
			if(DEBUG)	System.out.println("Connection closed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
        public static Connection getConnection(){
            try {
            String DRIVER = "org." + BACKEND + ".Driver";
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String url = "jdbc:" + BACKEND + ":" + "//" + HOST + ":" + PORT + "/" + DB_NAME;
            Connection rv = DriverManager.getConnection(url, USER, PASSWORD);
            return rv;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        }
}

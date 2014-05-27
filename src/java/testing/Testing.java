package testing;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
 
public class Testing {
    
    	final static  String USER="celaruser";
	final static String PASSWORD="celar-user";
	final static String DB_NAME="celardb";
        final static String BACKEND = "postgresql";
        final static String HOST = "localhost";
        final static String PORT = "5432";
 
	public static void main(String[] argv) {
 
		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");
 
		try {
                        String DRIVER="org."+BACKEND+".Driver";
 
			Class.forName(DRIVER);
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
 
		}
 
		System.out.println("PostgreSQL JDBC Driver Registered!");
 
		Connection connection = null;
 
		try {
                   
                   String url = "jdbc:"+BACKEND+":"+"//"+HOST+":"+PORT+"/"+DB_NAME;
                    System.out.println(url);
 
			connection = DriverManager.getConnection(url, USER, PASSWORD);
 
		} catch (SQLException e) {
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
 
		}
 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}
 
}
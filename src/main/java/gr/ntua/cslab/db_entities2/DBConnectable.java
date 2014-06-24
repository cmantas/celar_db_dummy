/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.cslab.db_entities2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
import static org.apache.log4j.Level.*;

/**
 *
 * @author cmantas
 */
public abstract class DBConnectable {

    protected static Connection connection;

    final static Logger LOG = Logger.getLogger(DBConnectable.class.getName());

    //the prop file location
    final static String PROPERTIES_FILE = "src/main/resources/db_entities2.properties";

    //the properties required for the DB connection
    static String BACKEND, HOST, PORT, USER, PASSWORD, DB_NAME;

    /**
     * The static initializer loads the properties
     */
    static {
        //LOG.setLevel(DEBUG);
        loadProperties();
    }

    /**
     * Loads the properties about the database connection from the properties
     * file
     */
    public static void loadProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(PROPERTIES_FILE);
            // load a properties file
            prop.load(input);

            // get the property value and print it out
            BACKEND = prop.getProperty("backend");
            HOST = prop.getProperty(BACKEND + ".host");
            PORT = prop.getProperty(BACKEND + ".port");
            USER = prop.getProperty(BACKEND + ".username");
            PASSWORD = prop.getProperty(BACKEND + ".password");
            DB_NAME = prop.getProperty(BACKEND + ".db_name");

        } catch (IOException ex) {
            LOG.error("Could not load the properties for the DB Connection");
            BACKEND ="postgresql";
            HOST = "localhost";
            PORT = ""+5432;
            USER = "celaruser";
            PASSWORD = "celar-user";
            DB_NAME = "celardb";
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Opens the connection with the database.
     *
     * <b>Note:</b> SLOW procedure, use with care!
     */
    public void openConnection() {
        connection = getConnection();
        System.out.println("connection created");
        LOG.info("connection created info");
        LOG.debug("Connection created");
    }

    /**
     * Closes the connection with the database.
     */
    public void closeConnection() {
        try {
            connection.close();
            LOG.debug("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
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

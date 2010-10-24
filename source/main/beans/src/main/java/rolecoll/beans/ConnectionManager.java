/*
 * ConnectionManager.java
 *
 * Created on December 9, 2006, 12:42 PM
 */

package rolecoll.beans;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class creates a static connection to the database
 */
public class ConnectionManager {    
    /**
     * Static connection to the database
     */
    private static Connection connection = null;
    
    private static final String DB_HOST = "rolecoll.database.host";
    private static final String DB_PORT = "rolecoll.database.port";
    private static final String DB_NAME = "rolecoll.database.name";
    private static final String DB_USER = "rolecoll.database.user";
    private static final String DB_PASSWORD = "rolecoll.database.password";
    private static final String DB_TYPE = "rolecoll.database.type";
    private static final String DB_DRIVER = "rolecoll.database.driver";
    
    private static final String PROP_FILE = "database.properties";
    
    private Properties properties = new Properties();
    
    /** Creates a new instance of ConnectionManager */
    public ConnectionManager() throws SQLException, IOException, ClassNotFoundException {
        // read properties file
        properties.load(this.getClass().getClassLoader().getResourceAsStream(PROP_FILE));                
        
        // instanciate the driver class
        Class.forName(properties.getProperty(DB_DRIVER));
        
        // Connect to dabase
        String host = properties.getProperty(DB_HOST);
        String port = properties.getProperty(DB_PORT);
        String dbName = properties.getProperty(DB_NAME);
        String user = properties.getProperty(DB_USER);
        String password = properties.getProperty(DB_PASSWORD);        
        String type = properties.getProperty(DB_TYPE);
        
        String url = "jdbc:" + type + "://" + host + ":" + port + "/" + dbName;
        
        connection = DriverManager.getConnection(url, user, password);
    }
    
    /**
     * This method destroys the connection
     */
    public static void destroy() throws SQLException {
        if(connection != null){
            connection.close();
        }
    }
    
    /**
     * This method returns the status of the connection
     * @return boolean
     */
    public boolean isConnected() throws SQLException {
        return !connection.isClosed();
    }
    
    /**
     * This method returns the static connection
     * @return Connection
     */
    public static Connection getConnection(){
        return connection;
    }        
}

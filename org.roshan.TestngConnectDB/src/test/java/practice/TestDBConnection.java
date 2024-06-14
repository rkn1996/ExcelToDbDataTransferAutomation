package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import utilities.PropertiesFileUtility;

public class TestDBConnection {

	public static void main(String[] args) throws Exception
	{
		
		//Register driver class w.r.t RDBMS technology
		String pfpath="src\\test\\resources\\properties\\dbconfig.properties";
 	    Class.forName(PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"JDBC_DRIVER")); //for MySQL
	    //1. connect to DB
	    Connection con=DriverManager.getConnection(PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"JDBC_URL"),
	    		                  PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"USERNAME"),
	    		                  PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"PASSWORD"));
        try {
            // Check if connection is successful
            if (con != null) {
                System.out.println("Connection established successfully.");
            } else {
                System.out.println("Failed to establish connection.");
            }
        } 
        finally {
            // Close the connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
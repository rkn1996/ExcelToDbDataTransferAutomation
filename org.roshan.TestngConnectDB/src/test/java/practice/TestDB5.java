package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import utilities.PropertiesFileUtility;

public class TestDB5 {

	public static void main(String[] args) throws Exception
	{
		//enter table name
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter table name to search:");
		String tableName=sc.nextLine();
		sc.close();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		//Register driver class w.r.t RDBMS technology
		String pfpath="src\\test\\resources\\properties\\dbconfig.properties";
 	    Class.forName(PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"JDBC_DRIVER")); //for MySQL
	    //1. connect to DB
	    Connection con=DriverManager.getConnection(PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"JDBC_URL"),
	    		                  PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"USERNAME"),
	    		                  PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"PASSWORD"));
	    //2. Perform operations
		try {
            // SQL query to check if the table exists
            //String tableName = "EMPLOYEES"; // Replace with the table name you want to check
            String schemaName = "SYS";
            String sql = "SELECT table_name FROM all_tables WHERE table_name = ? AND owner = ?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, tableName.toUpperCase());
            preparedStatement.setString(2, schemaName.toUpperCase());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Table '" + tableName + "' exists.");
            } else {
                System.out.println("Table '" + tableName + "' does not exist.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection or operation failed.");
            e.printStackTrace();
        } finally {
            // Close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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

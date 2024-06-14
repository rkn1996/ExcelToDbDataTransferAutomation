package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import utilities.PropertiesFileUtility;
 
public class TestDB1 {

	public static void main(String[] args) throws Exception
	{
		
	    //Register driver class w.r.t RDBMS technology
		String pfpath="src\\test\\resources\\properties\\dbconfig.properties";
 	    Class.forName(PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"JDBC_DRIVER")); //for MySQL
	    //1. connect to DB
	    Connection con=DriverManager.getConnection(PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"JDBC_URL"),
	    		                  PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"USERNAME"),
	    		                  PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"PASSWORD"));
	    //2. Perform operations
	    try
	    {
	    	//Insert 1 rows into tab validate response
	    	String q="INSERT INTO SYS.EMPLOYEES (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, SALARY)"
	    	                              +"VALUES(?, ?, ?, ?, ?)";
	    	PreparedStatement preparedStatement = con.prepareStatement(q);
	    	// Set the parameters
            preparedStatement.setInt(1, 2); // Example rollno
            preparedStatement.setString(2, "Johnnn"); // Example name
            preparedStatement.setString(3, "Doeeee"); // Example course
            preparedStatement.setString(4, "Johnnn.Doeee12@gmail.com"); // Example grade
            preparedStatement.setString(5, "70000.00");
            
            // Execute the insert
            int r = preparedStatement.executeUpdate();
	    	System.out.println(r);
	    	if(r>0)
	    	{
	    	System.out.println("Insertion test passed");
	    	}
	    	else
	    	{
	    	System.out.println("Insertion test failed");
	    	}
	    }
	    catch(Exception ex)
	    {
	            System.out.println("DB test failed to insert data "+ex.getMessage());
	    }
	    //4. disconnect from DB.
	    con.close();
	}
}

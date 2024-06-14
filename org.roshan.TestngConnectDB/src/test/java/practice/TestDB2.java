package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import utilities.PropertiesFileUtility;
 
public class TestDB2 {

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
	    Statement st=con.createStatement();
	    try
	    {
	    	String q="SELECT * FROM SYS.EMP60";
	    	ResultSet rs=st.executeQuery(q);
	    	while(rs.next()) {
	    		System.out.println(rs.getInt(1)+" "+rs.getString(2));
	    	}
	    }
	    catch(Exception ex)
	    {
	            System.out.println("DB test failed due to "+ex.getMessage());
	    }
	    //4. disconnect from DB.	    
	    con.close();
	}
}

package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import utilities.PropertiesFileUtility;
 
public class TestDB3 {

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
		   //3. execute a SQL query
		   String q="create table SYS.STUDENTSSS(rollno NUMBER PRIMARY KEY,"
				   + "name VARCHAR2(100) NOT NULL,"
				   + "course VARCHAR2(100) NOT NULL,"
				   + "grade VARCHAR2(1) NOT NULL);";
		   int r=st.executeUpdate(q); //Create a new table
		   System.out.println(r);
		   if(r>=0)
		   {
		      System.out.println("Table creation test passed");
		   }
		}
		catch(Exception ex)
		{
		   System.out.println("Table creation test failed due to "+ex.getMessage());
		}
		//4. disconnect from DB
		con.close();
	}
}

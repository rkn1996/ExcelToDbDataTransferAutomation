package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import utilities.ExcelFileUtility;
import utilities.PropertiesFileUtility;

public class ExcelToDbDataTransfer {
	
	//Declare global variables and objects
	String efpath;
	ExcelFileUtility eu;
	int nour;
	int nouc;
	Workbook book;
	Sheet sh;
	SoftAssert sf;
	Connection con;
	PreparedStatement preparedStatement;
	String pfpath;
		
	@BeforeClass
	public void setup() throws Exception
	{
	    //Database SetUP
		//Register driver class w.r.t RDBMS technology
		pfpath="src\\test\\resources\\properties\\dbconfig.properties";
 	    Class.forName(PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"JDBC_DRIVER")); //for MySQL
	    //1. connect to DB
	    con=DriverManager.getConnection(PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"JDBC_URL"),
	    		                  PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"USERNAME"),
	    		                  PropertiesFileUtility.getValueFromPropertiesFile(pfpath,"PASSWORD"));
	    
	    //Excel SetUp
		//Initialize global variables
		efpath="src\\test\\resources\\Excels\\EmployeeDetails.xlsx";
		//Define objects
		eu=new ExcelFileUtility();
		sf=new SoftAssert();
		//Open Excel file
		book=eu.openExcelFile(efpath);
		sh=eu.openSheet(book, "Sheet1");
		nour=eu.getRowsCount(sh);
		nouc=eu.getCellscount(sh, 0);
	}
		
	@Test
	public void transferDatatoDB() throws Exception
	{
		try {
		     // Prepare the SQL insert statement
             String sql = "INSERT INTO sys.EMPLOYEES_DETAILS (employee_id, first_name, last_name, email,  salary) VALUES (?, ?, ?, ?, ?)";
             preparedStatement = con.prepareStatement(sql);
        
		     //Data Driven from 2nd row(index=1)
		     for(int i=1;i<nour;i++) //1st row(index=0) has names of columns
		     {
			     String temp1=eu.getCellValue(sh, i, 0);
			     String temp2=eu.getCellValue(sh, i, 1);
			     String temp3=eu.getCellValue(sh, i, 2);
			     String temp4=eu.getCellValue(sh, i, 3);
			     String temp5=eu.getCellValue(sh, i, 4);
			     preparedStatement.setString(1, temp1);
			     preparedStatement.setString(2, temp2);
			     preparedStatement.setString(3, temp3);
			     preparedStatement.setString(4, temp4);
			     preparedStatement.setString(5, temp5);
			
			     // Execute the insert statement
                 preparedStatement.executeUpdate();
                 System.out.println("Data inserted successfully!!");
		     }
		     System.out.println("All Data inserted successfully from the Excel sheet1");
	       }
	       catch(Exception ex)
	       {
            System.out.println("DB test failed to insert data "+ex.getMessage());
           }
	}
		
	@AfterClass
	public void tearDown() throws Exception
	{
		//save and close excel file
		eu.saveAndCloseExcel(book, efpath);
		sf.assertAll();
		//4. disconnect from DB.
	    con.close();
	}
}

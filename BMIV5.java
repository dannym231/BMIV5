package bmi;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.lang.Math;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BMIV5 {
    //main method for function calls
    public static final DecimalFormat df = new DecimalFormat("#.00");
    
   
   
    // helper function to calculate BMI given height and weight
    public static double getBodyMassIndex(double height, double weight){
       
        double BMI = 0;
   
        BMI = weight / Math.pow(height, 2) * 703;
        
        return BMI;

    }
    
    // helper function to calculate weightClass based on the BMI.
    public static String weightClass(double BMI){
        String classWeight;
        if(BMI < 18.5){
            classWeight = "underweight";
        }
        else if(BMI < 24.9 && BMI> 18.5){
            classWeight = "normal";
        }
        else if(BMI < 29.9 && BMI> 24.9){
            classWeight = "overweight";
        }
        else{
            classWeight = "obese";
        }
        return classWeight;
    }
    

	
	
    public static void main(String[] args) {
        // 1. connect to the database
    	
    	Connection db_connection = null;
		ResultSet result_set = null;
		try {

			// Step 1: Get the connection object for the database
			String url = "jdbc:mysql://localhost/bmiv5";
			String user = "root";
			String password = "";
			db_connection = DriverManager.getConnection(url, user, password);
			System.out.println("Success: Connection established");
			
			
			// Step 2: Create a statement object
			Statement statement_object = db_connection.createStatement();
			
			// Step 3: Execute SQL query to fetch the height and weight
			// Set the query string you want to run on the database
			// If this query is not running in PhpMyAdmin, then it will not run here
		//	result_set = statement_object.executeQuery(sqlQueryStr);

			

		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		} // end catch

    	// 2. SELECT (get/query) the information from the table bodymetrics (height and weight)
    	
    	// 3. call our helper function to calculate BMI
    	
    	// 4. call our helpr function to calculate the weightClass
    	
    	// 5. UPDATE (push) the BMI and weightClass for each row of the table into the database.
    	
    	// 6. Once the update is successful, print a confirmation message.
    	System.out.println("BMI and weightClass are updated for all 600 people");
    }

}
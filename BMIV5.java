package bmi;
import java.text.DecimalFormat;
import java.lang.Math;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * This class established the connection to MYSQL data base. 
 * It selects Id, height, and weight from the body metrics table
 * Based on the height and weight it calculates the BMI, classification using the java methods.
 * Updates the data base with the calculated BMI and classification values
 */
public class BMIV5 {
   
	/**
	 * Given height and weight this method calculates BMI
	 * @param height
	 * @param weight
	 * @return
	 */

    public static double getBodyMassIndex(double height, double weight) {
        double BMI = weight / Math.pow(height, 2) * 703;
        return BMI;
    }
    
    /**
     * Given BMI this method returns the weight class
     * @param BMI
     * @return
     */

    public static String weightClass(double BMI) {
        String classWeight;
        if (BMI < 18.5) {
            classWeight = "underweight";
        } else if (BMI < 24.9 && BMI > 18.5) {
            classWeight = "normal";
        } else if (BMI < 29.9 && BMI > 24.9) {
            classWeight = "overweight";
        } else {
            classWeight = "obese";
        }
        return classWeight;
    }

    
    /**
     * This method is doing three things
     * 1. Establish the conneciton to the database
     * 2. Get the height and weight from the database
     * 3. For each height and weight, find the BMI and BMI classificaiton.
     * 4. Update the database with BMI and classificaiton.
     * @param args
     */
    public static void main(String[] args) {
        try (
        	 Connection db_connection = DriverManager.getConnection("jdbc:mysql://localhost/bmiv5", "root", "");
             Statement statement_object = db_connection.createStatement();
        	 // all the rows returned by SELECT query are now in the Result Set.
             ResultSet result_set = statement_object.executeQuery("SELECT id, height, weight FROM bodymetrics");
            ) 
        {
            // Loop through the Result Set
            while (result_set.next()) {
                int id = result_set.getInt("id");
                double height = result_set.getDouble("height");
                double weight = result_set.getDouble("weight");
                System.out.println("id: " + id + " height: " + height + " weight: " + weight);
                
                // find the bmi
                double bmi = getBodyMassIndex(height, weight);
                
                // find the bmi classification
                String bmi_classification = weightClass(bmi);
                
                // prepare the SQL statement for updating it
                String update_query = "UPDATE bodymetrics SET BMI=?, classification=? WHERE id=?";
                
                // substituting the bmi and classification and id in the update query string
                // and running the query.
                try (PreparedStatement prepared_statement = db_connection.prepareStatement(update_query)) {
                    prepared_statement.setDouble(1, bmi);
                    prepared_statement.setString(2, bmi_classification);
                    prepared_statement.setInt(3, id);
                    int update_result = prepared_statement.executeUpdate();
                    
                    if (update_result == 0) {
                        System.out.println("Error: Something has gone wrong! Check your database connection");
                    }
                }
            }
            
            System.out.println("Success: All records are updated with BMI and Classification Category:");
        } // end try block
        
        catch (Exception ex) {
            ex.printStackTrace();
        } 
    }
}

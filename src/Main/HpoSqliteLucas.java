package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HpoSqliteLucas {

	
	     /**
	     * Connect to a sample database
	     */
	    public static ArrayList<String> connect(String id) {
	    	System.out.println(id);
	        Connection conn = null;
	        ArrayList<String> res =new ArrayList<String>();
	        try {
	            // db parameters
	            String url = "jdbc:sqlite:C:/Users/lulu/Desktop/Projet/Données/hpo/hpo_annotations.sqlite";
	            Statement statement = null;
	            ResultSet result = null;
	            // create a connection to the database
	            conn = DriverManager.getConnection(url);
	            res.add(0,"");
	            
	            System.out.println("Connection to SQLite has been established.");
	            String query = "Select distinct disease_id,disease_label from phenotype_annotation where disease_db = \"OMIM\" and sign_id = "+id;
	            System.out.println("ici");
			    statement = conn.createStatement();
			    
			    result = statement.executeQuery(query);
			    int i = 1;
			    while(result.next())
			    {
			    System.out.println(result.getString(2));
			    res.add(i,result.getString(2));
			    
			    i++;
			    }
			    return res;
			    
	            
	            
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        } finally {
	            try {
	                if (conn != null) {
	                    conn.close();
	                }
	            } catch (SQLException ex) {
	                System.out.println(ex.getMessage());
	            }
	            
	        }
			return res;
	    }
	    /**
	     * @param args the command line arguments
	     */
	    public static void main(String[] args) {
	        connect("\"HP:0000008\"");
	    
	}
	
}

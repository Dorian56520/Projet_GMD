package Search;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Hpo {

	public Hpo(String[] args)
	{
		Date start = new Date();
		String url = "jdbc:mysql://neptune.telecomnancy.univ-lorraine.fr:3306/gmd";
		String user = "gmd-read";
		String password = "esial";
		Connection connexion = null;
		Statement statement = null;
		ResultSet result = null;
		ArrayList<String[]> stitch_ids = new ArrayList<String[]>();
		try {
		    Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
			System.out.println(e.getMessage());
		    return;
		}
		try {
		    connexion = DriverManager.getConnection( url, user, password );
		    String where = args[0].replace("*", ".*");
		    
		    /*for(String arg : args)
		    {
		    	
		    }*/
		    String query = "SELECT stitch_compound_id1,stitch_compound_id2 FROM meddra_all_se WHERE side_effect_name REGEXP (\"" + where + "\");";
		    statement = connexion.createStatement();
		    
		    result = statement.executeQuery(query);
		    while(result.next())
		    {
		    	stitch_ids.add(new String[] {result.getString(1), result.getString(2)});
		    }
		      Date end = new Date();
		      System.out.println(end.getTime() - start.getTime() + " total milliseconds");
	    	System.out.println("Nb results : " + stitch_ids.size());
		} catch ( SQLException e ) {
			System.out.println(e.getMessage());
		} finally {
		    if ( result != null ) {
		        try {
		        	result.close();
		        } catch ( SQLException ignore ) {
		        }
		    }
		    if ( statement != null ) {
		        try {
		            statement.close();
		        } catch ( SQLException ignore ) {
		        }
		    }
		    if ( connexion != null ) {
		        try {
		            connexion.close();
		        } catch ( SQLException ignore ) {
		        }
		    }
		}
	}
}

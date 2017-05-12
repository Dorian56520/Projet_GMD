package Search;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import index.PretraitementOmin;

public class Sider {

	public static ArrayList<String[]> GetCUIfromDisease(String[] args)
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
		}
		try {
		    connexion = DriverManager.getConnection( url, user, password );
		    String where = args[0].replace("*", "%");
		    
		    /*for(String arg : args)
		    {
		    	
		    }*/
		    String query = "SELECT DISTINCT stitch_compound_id1,stitch_compound_id2 FROM meddra_all_se WHERE side_effect_name LIKE \"" + where + "\"";
		    statement = connexion.createStatement();
		    
		    result = statement.executeQuery(query);
		    while(result.next())
		    {
		    	stitch_ids.add(new String[] {result.getString(1).replaceFirst("1", "m"), result.getString(2).replaceFirst("0", "s")});
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
		return stitch_ids;
	}
	public static ArrayList<String[]> GetSiderData(String[] args)
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
		}
		try {
		    connexion = DriverManager.getConnection( url, user, password );
		    String where = args[0].replace("*", "%");
		    
		    /*for(String arg : args)
		    {
		    	
		    }*/
		    String query = "SELECT DISTINCT stitch_compound_id1,stitch_compound_id2 FROM meddra_all_se WHERE side_effect_name LIKE \"" + where + "\"";
		    statement = connexion.createStatement();
		    
		    result = statement.executeQuery(query);
		    while(result.next())
		    {
		    	stitch_ids.add(new String[] {result.getString(1).replaceFirst("1", "m"), result.getString(2).replaceFirst("0", "s")});
		    }
		      Date end = new Date();
		      System.out.println("---------------------------");
		      System.out.println(end.getTime() - start.getTime() + " Sider milliseconds");
	    	System.out.println("Sider match : " + stitch_ids.size());
		      System.out.println("---------------------------");
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
		return stitch_ids;
	}
	public static void main(String[] args) {
		Date start = new Date();
		ArrayList<String[]> data = Sider.GetSiderData(new String[] {"*pain*"});
		ArrayList<String> ATC = SearchStitch.SearchStitch(data);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);
		Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " total milliseconds");
	}
}

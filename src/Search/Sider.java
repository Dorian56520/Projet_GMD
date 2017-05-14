package Search;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Sider {

	public static ArrayList<ArrayList<String>> GetStitchIDfromCUI(ArrayList<ArrayList<String>> args)
	{
		if(args.size() == 0)
			return new ArrayList<ArrayList<String>>();
		Date start = new Date();
		String url = "jdbc:mysql://neptune.telecomnancy.univ-lorraine.fr:3306/gmd";
		String user = "gmd-read";
		String password = "esial";
		Connection connexion = null;
		Statement statement = null;
		ResultSet result = null;
		ArrayList<ArrayList<String>> Allstitch_ids = new ArrayList<ArrayList<String>>();
		try {
		    Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
			System.out.println(e.getMessage());
		}
		try {
		    connexion = DriverManager.getConnection( url, user, password );
			for(ArrayList<String> tmp : args)
			{
				/*if(Integer.parseInt(tmp.get(2)) < 2)
					break;*/
				ArrayList<String> stitch_ids = new ArrayList<String>();
				stitch_ids.add(tmp.get(0));
				stitch_ids.add(tmp.get(1));
				stitch_ids.add(tmp.get(2));
				
				if(tmp.size() <= 3)
				{
					Allstitch_ids.add(stitch_ids);
					break;
				}
			    String where = "";
			    for(int i=3;i<tmp.size() - 1;i++)
			    	where += "\"" + tmp.get(i).trim() + "\",";
			    where += "\"" + tmp.get(tmp.size() - 1).trim() + "\"";
			    String query = "SELECT stitch_compound_id FROM meddra_all_indications WHERE cui_of_meddra_term IN ( " + where + ")";
			    statement = connexion.createStatement();
			    
			    result = statement.executeQuery(query);
			    while(result.next())
			    {
		    		stitch_ids.add(result.getString(1).replaceFirst("1", "m"));
			    }
			    //if(stitch_ids.size() > 3)
			    	Allstitch_ids.add(stitch_ids);
			}
		    Date end = new Date();
		    System.out.println("---------------------------");
		    System.out.println(end.getTime() - start.getTime() + " Sider milliseconds");
	    	System.out.println("Sider match : " + (Allstitch_ids.size() == 0 ? "0" : Allstitch_ids.get(0).size()));
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
		return Allstitch_ids;
	}
	
	public static ArrayList<String> GetSiderDrugData(String[] args)
	{
		Date start = new Date();
		String url = "jdbc:mysql://neptune.telecomnancy.univ-lorraine.fr:3306/gmd";
		String user = "gmd-read";
		String password = "esial";
		Connection connexion = null;
		Statement statement = null;
		ResultSet result = null;
		ArrayList<ArrayList<String>> stitch_ids = new ArrayList<ArrayList<String>>();
		ArrayList<String> results = new ArrayList<String>();
		try {
		    Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
			System.out.println(e.getMessage());
		}
		try {
		    connexion = DriverManager.getConnection( url, user, password );
		    
		    for(String arg : args)
		    {
		    	ArrayList<String> tmp_ids = new ArrayList<String>();
			    String where = arg.replace("*", "%");
			    String query = "SELECT DISTINCT stitch_compound_id1 FROM meddra_all_se WHERE side_effect_name LIKE \"" + where + "\"";
			    statement = connexion.createStatement();
			    
			    result = statement.executeQuery(query);
			    while(result.next())
			    {
			    	tmp_ids.add(result.getString(1).replaceFirst("1", "m"));
			    }
			    stitch_ids.add(tmp_ids);
		    }
		    if(stitch_ids.size() > 1)
		    {
			    int index = GetsmallerSet(stitch_ids);
			    for(int i =0;i<stitch_ids.get(index).size();i++)
			    {
				    int cpt = 0;
				    boolean contains = true;
			    	String stitch = stitch_ids.get(index).get(i);
			    	while(cpt < stitch_ids.size())
			    	{
			    		if(cpt != index)
			    		{
				    		if(!stitch_ids.get(cpt).contains(stitch))
				    		{
				    			contains = false;
				    			break;
				    		}
			    		}
			    		cpt++;
			    	}
			    	if(contains)
		    			results.add(stitch);
			    }
		    }
		    else
		    	results = stitch_ids.get(0);
		    Date end = new Date();
		    System.out.println("---------------------------");
		    System.out.println(end.getTime() - start.getTime() + " Sider milliseconds");
	    	System.out.println("Sider match : " + results.size());
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
		return results;
	}
	
public static int GetsmallerSet(ArrayList<ArrayList<String>> set)
	{
		int minSize = set.get(0).size();
		int index = 0;
		for(int i=0;i<set.size();i++)
		{
			if(set.get(i).size() < minSize)
			{
				minSize = set.get(i).size();
				index = i;
			}
		}
		return index;
	}


	public static void main(String[] args) {
		/*Date start = new Date();
		ArrayList<String> data = Sider.GetSiderDrugData(new String[] {"*"});
		ArrayList<String> ATC = SearchStitch.SearchStitchAll(data);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);
		Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		System.out.println("***************************");
		System.out.println();
		System.out.println("Results :");
		for(String s : Labels)
			System.out.println(s);*/

		/*Date start = new Date();
		ArrayList<String> data = Sider.GetStitchIDfromDisease(new String[] {"Hypoglycaemia","Varicella"});
		ArrayList<String> ATC = SearchStitch.SearchStitchAll(data);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);
		Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		System.out.println("***************************");
		System.out.println();
		System.out.println("Results :");
		for(String s : Labels)
			System.out.println(s);*/

		/*Date start = new Date();
		ArrayList<String> data = Sider.GetStitchIDfromCUI(new String[] {"C0020615","C0008049"});
		ArrayList<String> ATC = SearchStitch.SearchStitchAll(data);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);
		Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		System.out.println("***************************");
		System.out.println();
		System.out.println("Results :");
		for(String s : Labels)
			System.out.println(s);*/
	}
}

package Search;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class HpoSqliteLucas {

	public static ArrayList<String[]> GetHPidFROMOrphaID(ArrayList<String[]> OrphaID) {

		if(OrphaID.size() == 0)
			return new ArrayList<String[]>();
		Connection conn = null;
        ArrayList<String[]> res =new ArrayList<String[]>();
        try {
            // db parameters

            //String url = "jdbc:sqlite:C:/Users/lulu/Desktop/Projet/Données/hpo/hpo_annotations.sqlite";
            
        	String url = "jdbc:sqlite:C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/hpo_annotations.sqlite";

            //String url = "jdbc:sqlite:C:/Users/lulu/Desktop/Projet/Données/hpo/hpo_annotations.sqlite";
            //String url = "jdbc:sqlite:C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/hpo_annotations.sqlite";

            Statement statement = null;
            ResultSet result = null;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            String where = "";
            for(int i=0;i<OrphaID.size() - 1;i++)
            {
            	where += "\"" + OrphaID.get(i)[0] + "\",";
            }
            where += "\"" + OrphaID.get(OrphaID.size() - 1)[0] + "\"";
            //System.out.println("Connection to SQLite has been established.");
            String query = "Select distinct sign_id,disease_label from phenotype_annotation where disease_id IN ("+where+")";
            //System.out.println("ici");
		    statement = conn.createStatement();
		    
		    result = statement.executeQuery(query);
		    while(result.next())
		    {
		    //System.out.println(result.getString(2));
		    res.add(new String[] {result.getString(1),result.getString(2)});
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
	
	public static ArrayList<String[]> GetCUI(ArrayList<ArrayList<String[]>> HPid) {
		if(HPid.size() == 0)
			return new ArrayList<String[]>();
		Connection conn = null;
        ArrayList<String[]> results = new ArrayList<String[]>();
		ArrayList<ArrayList<String[]>> res = new ArrayList<ArrayList<String[]>>();
        try {
            // db parameters
            //String url = "jdbc:sqlite:C:/Users/lulu/Desktop/Projet/Données/hpo/hpo_annotations.sqlite";
            for(ArrayList<String[]> id : HPid)
            {
                ArrayList<String[]> tmp =new ArrayList<String[]>();
	        	String url = "jdbc:sqlite:C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/hpo_annotations.sqlite";
	            Statement statement = null;
	            ResultSet result = null;
	            // create a connection to the database
	            conn = DriverManager.getConnection(url);
	            String where = "";
	            for(int i=0;i<id.size() - 1;i++)
	            {
	            	where += "\"" + id.get(i)[0] + "\",";
	            }
	            where += "\"" + id.get(id.size() - 1)[0] + "\"";
	            //System.out.println("Connection to SQLite has been established.");
	            String query = "Select distinct sign_id,disease_label from phenotype_annotation where sign_id IN ("+where+")";
	            //System.out.println("ici");
			    statement = conn.createStatement();
			    
			    result = statement.executeQuery(query);
			    while(result.next())
			    {
			    	tmp.add(new String[] {result.getString(1),result.getString(2)});
			    }
			    if(tmp.size() > 0)
			    	res.add(tmp);
            }
		    if(res.size() > 1)
		    {
			    int index = GetsmallerSet(res);
			    for(int i =0;i<res.get(index).size();i++)
			    {
				    int cpt = 0;
				    boolean contains = true;
			    	String label = res.get(index).get(i)[1];
			    	while(cpt < res.size())
			    	{
			    		if(cpt != index)
			    		{
				    		if(Contains(label,res.get(cpt)) < 0)
				    		{
				    			contains = false;
				    			break;
				    		}
			    		}
			    		cpt++;
			    	}
			    	if(contains)
			    		results.add(new String[] {res.get(index).get(i)[0],label});
			    }
		    }
		    else
		    	results = res.get(0);
            
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
		return results;
    }
		/**
	     * Connect to a sample database
	     */
	public static int GetsmallerSet(ArrayList<ArrayList<String[]>> set)
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
	public static int Contains(String value, ArrayList<String[]> list)
	{
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i)[1].equals(value))
				return i;
		}
		return -1;
	}
	    public static ArrayList<String> connect(String id) {
	    	System.out.println(id);
	        Connection conn = null;
	        ArrayList<String> res =new ArrayList<String>();
	        try {
	            // db parameters
	            String url = "jdbc:sqlite:C:/Users/lulu/Desktop/Projet/Données/hpo/hpo_annotations.sqlite";
	           // String url = "jdbc:sqlite:C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/hpo_annotations.sqlite";
	            Statement statement = null;
	            ResultSet result = null;
	            // create a connection to the database
	            conn = DriverManager.getConnection(url);
	            res.add(0,"");
	            
	            //System.out.println("Connection to SQLite has been established.");
	            String query = "Select distinct disease_id,disease_label from phenotype_annotation where sign_id = "+id;
	            //System.out.println("ici");
			    statement = conn.createStatement();
			    
			    result = statement.executeQuery(query);
			    int i = 1;
			    while(result.next())
			    {
			    //System.out.println(result.getString(2));
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
	    
	    public static ArrayList<String> trad(String s){
	    	ArrayList<String> res = new ArrayList<String>();
	    	int cpt = 1;
	    	String [] t = s.split(";");
	    	String[] t2 = t[0].split(",");
	    	if(s.trim().matches("^[a-zA-Z].*")){
				res.add(0,t2[0].trim());
			}
			else{
				String[] t3 = t2[0].split(" ");
				String resw = "";
				for (int k = 1; k < t3.length;k++){
						resw = resw + " " +t3[k];
				}
				res.add(0,resw.trim());
			}
	    	for (int j = 1 ; j < t2.length;j++){
	    		res.add(cpt,t2[j].trim());
				cpt++;
	    	}
	    	for(int i = 1 ; i < t.length;i++){
	    		t2 = t[i].split(",");
	    		for (int j = 0 ; j < t2.length;j++){
	    			if(!t2[j].equals("")){
	    				res.add(cpt,t2[j].trim());
	    				cpt++;
	    			}
	    		}
	    	}
	    	return res;
	    }
	    
	    public static void main(String[] args) {
	    	/*Date start = new Date();
	    	ArrayList<String[]> OrphaID = searchOrphadata.getOrphadataData(new String[]{"Micropenis", "Delayed dentition"});
	    	ArrayList<String[]> HPidsAndDisease = GetHPidFROMOrphaID(OrphaID);
	    	ArrayList<String[]> CUIList = SearchHpo.GetCUIFromHPOid(HPidsAndDisease);
	    	Date end = new Date();
	    	System.out.println(end.getTime() - start.getTime());
			ArrayList<String> Stitch = Sider.GetStitchIDfromCUI(CUIList);
			ArrayList<String> ATC = SearchStitch.SearchStitchAll(Stitch);
			ArrayList<String> Labels = SearchATC.SearchATC(ATC);
	    	System.out.println("Nb Results : " + Labels.size());
	    	for(String lab : Labels)
	    		System.out.println(lab);*/
	        //connect("\"HP:0000013\"");
	        //String test = "PARKINSON DISEASE 1, AUTOSOMAL DOMINANT; PARK1";
	        //affiche(trad(test ));
	    
	}
	    
		public static void affiche(ArrayList<String> t ){
			for (int i = 0; i < t.size(); i++){
				System.out.println(t.get(i));
			}
		}
		
}

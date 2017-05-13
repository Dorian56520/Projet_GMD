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
	            String url = "jdbc:sqlite:C:/Users/lulu/Desktop/Projet/Donn�es/hpo/hpo_annotations.sqlite";
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
	    	boolean split = true;
	    	int cpt = 1;
	    	String x[];
	    	String [] t = s.split(";");
	    	String[] t2 = t[0].split(",");
	    	if(s.trim().matches("^[a-zA-Z].*")){
	    		
	    		//System.out.println("ici");
				res.add(0,t2[0].trim());
			}
			
			else{
				
				String[] t3 = t2[0].split(" ");
				String resw = "";
						for (int k = 1; k < t3.length;k++){
							
								resw = resw + " " +t3[k];
								
							
							
						}
						//System.out.println("resw = " + resw);
						res.add(0,resw.trim());
				
			}
	    	
	    	for (int j = 1 ; j < t2.length;j++){
	    		res.add(cpt,t2[j].trim());
				cpt++;
	    	}
	    	
	    	for(int i = 1 ; i < t.length;i++){
	    		//System.out.println("i ="+i + t[i]);
	    		t2 = t[i].split(",");
	    		for (int j = 0 ; j < t2.length;j++){
	    			//System.out.println("j ="+j + t[j]);
	    			if(!t2[j].equals("")){
	    				res.add(cpt,t2[j].trim());
	    				cpt++;
	    			}
	    			
	    		
	    		}
	    	}
	    	
	    	return res;
	    }
	    
	    public static void main(String[] args) {
	        connect("\"HP:0000013\"");
	        //String test = "PARKINSON DISEASE 1, AUTOSOMAL DOMINANT; PARK1";
	        //affiche(trad(test ));
	    
	}
	    
		public static void affiche(ArrayList<String> t ){
			for (int i = 0; i < t.size(); i++){
				System.out.println(t.get(i));
			}
		}
	    
	    
	
}
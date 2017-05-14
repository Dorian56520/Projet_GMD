package Search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class searchOrphadata {
	
	static String symptom = "Autosomal%20recessive%20inheritance";
	static String symptom2 = "Abnormal%20cry/voice/phonation%20disorder/nasal%20speech";
	static ArrayList<ArrayList<String>> stockOrpha = new ArrayList<ArrayList<String>>();

	
	public searchOrphadata() throws IOException, ParseException{
			
        }
	
	 public static void affiche() throws IOException, ParseException{
		 	URL FichierJson = new URL("http://couchdb.telecomnancy.univ-lorraine.fr/orphadatabase/_design/clinicalsigns/_view/GetDiseaseByClinicalSign?key=%22"+symptom+"%22");
		    BufferedReader br = new BufferedReader(new InputStreamReader(FichierJson.openStream()));
		    JSONParser parser = new JSONParser();
		    Object obj = parser.parse(br);
	        JSONObject jsonObject = (JSONObject) obj;
	        
	        JSONArray rows = ((JSONArray) jsonObject.get("rows"));
	        int i = 0;
	        if(rows != null){
	        	while(i < rows.size()){
	        		//System.out.println(rows.get(i).toString()); 
	        		JSONObject elem = (JSONObject) rows.get(i);
	        		JSONObject value = (JSONObject) elem.get("value");
	        		JSONObject disease = (JSONObject) value.get("disease");
	        		String id_desease =(String) disease.get("id");
	        		
	        		JSONObject named = (JSONObject) disease.get("Name");
	        		String name_desease = (String)  named.get("text");
	            	
	        		
	        		
	        		ArrayList<String> identifiant = new ArrayList<String>();
	        		identifiant.add(0, id_desease);
	        		identifiant.add(1, name_desease);
	        		stockOrpha.add(i,identifiant);
	        		
	        		i ++;
	        		
	        		
	        	}
	        	
	        }
     }
	
	  public static void main (String[] args) throws IOException, ParseException{
      	affiche();
      	for (int i= 0; i < stockOrpha.size();i++){
      		System.out.print( stockOrpha.get(i).get(0) + "   ");
      		System.out.println( stockOrpha.get(i).get(1));
      	}
	}




    
   
	
}

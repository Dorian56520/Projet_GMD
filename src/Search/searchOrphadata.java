package Search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class searchOrphadata {
	
	static String symptom = "Autosomal%20recessive%20inheritance";
	static String symptom2 = "Abnormal%20cry/voice/phonation%20disorder/nasal%20speech";
	static ArrayList<ArrayList<String[]>> stockOrpha = new ArrayList<ArrayList<String[]>>();
	
	
	public searchOrphadata() throws IOException, ParseException{
			
        }

	
	// public static void affiche() throws IOException, ParseException{
	//	 	URL FichierJson = new URL("http://couchdb.telecomnancy.univ-lorraine.fr/orphadatabase/_design/clinicalsigns/_view/GetDiseaseByClinicalSign?key=%22"+symptom+"%22");

	 public static ArrayList<String[]> getOrphadataData(String[] args) {
		 ArrayList<String[]> results = new ArrayList<String[]>();
		 Date start = new Date();
		 try
		 {
		 for (String arg : args){
			String trimedArg=arg.trim();
			String transformedArg=trimedArg.replace(" ", "%20");
			String key=transformedArg.substring(0,transformedArg.length()-1);
			URL FichierJson = new URL("http://couchdb.telecomnancy.univ-lorraine.fr/orphadatabase/_design/clinicalsigns/_view/GetDiseaseByClinicalSign?startkey=%22"+key+"%22&endkey=%22"+key+"z%22");

		    BufferedReader br = new BufferedReader(new InputStreamReader(FichierJson.openStream()));
		    JSONParser parser = new JSONParser();
		    Object obj = parser.parse(br);
	        JSONObject jsonObject = (JSONObject) obj;	        
	        JSONArray rows = ((JSONArray) jsonObject.get("rows"));
	        
	        
	        	ArrayList<String[]> tmp_names = new ArrayList<String[]>();
	        	int i = 0;
	        	if(rows != null){
	        		while(i < rows.size()){
	        			
	        			JSONObject elem = (JSONObject) rows.get(i);
	        			
	        			JSONObject value = (JSONObject) elem.get("value");
	        			JSONObject disease = (JSONObject) value.get("disease");

	        			String id_disease = disease.get("OrphaNumber").toString();

	        			JSONObject named = (JSONObject) disease.get("Name");
	        			String name_disease = (String)  named.get("text");
	        			String[] array = new String[]{id_disease,name_disease};
	        			tmp_names.add(array);
	        		
	        			i ++;
	        		
	        		}
	        	}
	        	stockOrpha.add(tmp_names);
	        }
		 if(stockOrpha.size() > 1)
		    {
			    
			    int index =0;
				int tm = stockOrpha.get(0).size();
				for (int i =1; i< stockOrpha.size();i++){
					if(stockOrpha.get(i).size() < tm){
						tm = stockOrpha.get(i).size();
						index = i;
					}
				}
				
			    for(int i =0;i<stockOrpha.get(index).size();i++)
			    {	int cpt = 0;
				    boolean contains = true;
			    	String[] orpha = stockOrpha.get(index).get(i);
			    	while(cpt < stockOrpha.size())
			    	{
			    		if(cpt != index)
			    		{
				    		if(!Contains(orpha,stockOrpha.get(cpt)))
				    		{
				    			contains = false;
				    			break;
				    		}
			    		}
			    		cpt++;
			    	}
			    	
//			    	boolean doublon=false;
//			    	for (int j=0; j<results.size();j++){
//			    		if (results.get(j)[1].equals(orpha[1]))
//			    			doublon=true;
//			    			break;
//			    	}
			    	
			    	if(contains /*&& !doublon*/)
		    			results.add(orpha);
			    }
		    }
		    else
		    	results = stockOrpha.get(0);
		 }
		 catch(Exception e){}
			Date end = new Date();
		      System.out.println("---------------------------");
		    System.out.println(end.getTime() - start.getTime() + " Orpha milliseconds");
			System.out.println("Orpha match : " + results.size());
		      System.out.println("---------------------------");
		return results;
		 
  }
	 public static boolean Contains(String[] value,ArrayList<String[]> List)
     {
         for(String[] val : List)
         {
             if(val[0].equals(value[0]) && val[1].equals(value[1]))
                 return true;
         }
         return false;
     }
	 
	 public static void affiche(ArrayList<String[]> t ){
	        for (int i = 0; i < t.size(); i++){
	            System.out.print(t.get(i)[0]+"\t");
	            System.out.println(t.get(i)[1]);
	        }
	    }
	 
	  public static void main (String[] args) throws IOException, ParseException{
		  
		  ArrayList<String[]> data=getOrphadataData(new String[]{"Micropenis","Delayed dentition"});

      	affiche(data);
      	System.out.println("Size of the list : " +data.size());
	}  
	
}

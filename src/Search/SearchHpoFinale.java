package Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import Main.HpoSqliteLucas;

public class SearchHpoFinale {
	
	ArrayList<ArrayList<String>> resf = new ArrayList<ArrayList<String>>();
	
	public SearchHpoFinale(){}
	
	
	public static void SearchHpof(String [] symptoms ) throws IOException{
	
		ArrayList<ArrayList<String>>resr = new ArrayList<ArrayList<String>>();
		ArrayList<String> resff = new ArrayList<String>();
		int tsymp = symptoms.length;
		int cpt2 = 0;
		for(int h = 0; h < tsymp;h++){
			String symptom = symptoms[h];
			ArrayList<String> res = SearchHpo.SearchHpo(SearchHpo.trait(symptom));
			ArrayList<String> res2 = HpoSqliteLucas.connect("\""+ res.get(0) + "\"");
			ArrayList<String> resf = new ArrayList<String>();
			int cpt = 0;
			for (int i =0; i < res2.size();i++){
				ArrayList<String> res3 = HpoSqliteLucas.trad(res2.get(i));
				int t = res3.size();
				for(int j = 0; j < t;j++){
					resf.add(cpt,res3.get(j));
					cpt++;
				}
			}
			resr.add(h,resf);
			affiche(resf);
		}
		if(resr.size() > 1){
			
			int ind =0;
			int tm = resr.get(0).size();
			for (int i =0; i< resr.size();i++){
				if(resr.get(i).size() < tm){
					tm = resr.get(i).size();
					ind = i;
				}
				
			}
			
			
					
					for(int j =0; j < resr.size();j++){
						if(j != ind){
							for(int i =0; i < resr.get(ind).size();i++){
								if(resr.get(j).contains(resr.get(ind).get(i))){
									if(!resff.contains(resr.get(ind).get(i))){
										resff.add(cpt2,resr.get(ind).get(i));
										cpt2++;
									}
									
								}
									
							}
						}
					
					}
					
				
				
			}
		
		else{
			for (int i = 0 ;i <resr.get(0).size();i++){
				if(!resff.contains(resr.get(0).get(i))){
					resff.add(cpt2,resr.get(0).get(i));
					cpt2++;
				}
			}
			
		}
		affiche(resff);
		
		
		}
		
		
	
	
	
	

	public static void main(String[] args) throws IOException {
		Date start = new Date();
		SearchHpof(new String[]{"Bladder diverticulum"});
		Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " HPO milliseconds");
		//,"Urinary urgency"
		
	}
	
	public static void affiche(ArrayList<String> t ){
		System.out.println("\n\n\naffichage finale");
		for (int i = 0; i < t.size(); i++){
			
			System.out.println(t.get(i));
		}
	}
	
	public static void affiche2(ArrayList<ArrayList<String>> t){
		for(int i=0; i< t.size();i++){
			affiche(t.get(i));
		}
	}
	
	
}

package Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import Main.HpoSqliteLucas;

public class SearchHpoFinale {
	
	ArrayList<ArrayList<String>> resf = new ArrayList<ArrayList<String>>();
	
	public SearchHpoFinale(){}
	
	
	public static ArrayList<String[]> SearchHpof(String [] symptoms ){
	
		
		
		ArrayList<ArrayList<String>>resr = new ArrayList<ArrayList<String>>();
		ArrayList<String[]> resff = new ArrayList<String[]>();
		ArrayList<String> lCUI = new ArrayList<String>();
		try
		{
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
			lCUI.add(h,res.get(1));
			resr.add(h,resf);
			//affiche(resf);
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
									if(!Contains(resff,resr.get(ind).get(i))&& !resr.get(ind).get(i).equals("") ){
										ArrayList<String> CUIf  = concateneCui(lCUI.get(j),lCUI.get(ind));
										for (int w = 0; w< CUIf.size();w++){
											String [] tampon = new  String[2]; 
											tampon[0] = CUIf.get(w);
											tampon[1] = resr.get(ind).get(i);
											resff.add(cpt2,tampon);
											cpt2++;
										}
									}				
								}	
							}
						}
					}
				}
		
		else{
			for (int i = 0 ;i <resr.get(0).size()-1;i++){
				if(!Contains(resff,resr.get(0).get(i)) && !resr.get(0).get(i).equals("")){
					ArrayList<String> CUIf  = getallcui(lCUI.get(0));
					for (int w = 0; w< CUIf.size();w++){
						String [] tampon = new  String[2]; 
						tampon[0] = CUIf.get(w);
						tampon[1] = resr.get(0).get(i);
						resff.add(cpt2,tampon);
						cpt2++;
					}
					
					
				
				}
			}
			
		}
		//affiche3(resff);
		} catch(Exception e) {}
		return resff;
		
		
		}
		
		
	
	public static ArrayList<String> getallcui (String CUI1){
		ArrayList<String> resf =new ArrayList<String>();
		String [] res1 = CUI1.split(";");
		int t1 = res1.length;
		int cpt = 0;
		for (int i =0;i<t1;i++){
			//System.out.println(res1[i]);
			//System.out.println("");
			if(!resf.contains(res1[i])){
				resf.add(cpt,res1[i]);
				cpt ++;
			}
			
		}
		return resf;
	}
	
	public static ArrayList<String> concateneCui(String CUI1, String CUI2){
		ArrayList<String> resf =new ArrayList<String>();
		String [] res1 = CUI1.split(";");
		String [] res2 = CUI2.split(";");
		int t1 = res1.length;
		int t2 = res2.length;
		int cpt = 0;
		for (int i =0;i<t1;i++){
			//System.out.println(res1[i]);
			//System.out.println("");
			if(!resf.contains(res1[i])){
				resf.add(cpt,res1[i]);
				cpt ++;
			}
			
		}
		for (int i =0;i<t2;i++){
			//System.out.println(res2[i]);
			if(!resf.contains(res2[i])){
				
				resf.add(cpt,res2[i]);
				cpt ++;
			}
			
		}
		return resf;
		
		
	}

	public static void main(String[] args) throws IOException {

		
		SearchHpof(new String[]{"Urinary urgency"});
		//"Bladder diverticulum","Urinary urgency"
		//ArrayList<String> test = concateneCui("C0085606;C3544092;C4020898","C40208958;A");
		//affiche(test);
Date start = new Date();
SearchHpof(new String[]{"Bladder diverticulum"});
Date end = new Date();
System.out.println(end.getTime() - start.getTime() + " HPO milliseconds");
		

		
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
	
	public static void affiche3(ArrayList<String []> t){
		System.out.println("Start");
		for(int i=0; i< t.size();i++){
			System.out.println("item 1 = :"  + t.get(i)[0]);
			System.out.println("item 2 = :"  + t.get(i)[1] + "\n");
		}
	}
	
	public static boolean Contains( ArrayList<String[]> list, String value)
    {
        for(String[] data : list)
        {
            if(data[1].equals(value))
                return true;
        }
        return false;
    }
	
	
}

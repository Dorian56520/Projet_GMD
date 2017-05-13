package Search;

import java.io.IOException;
import java.util.ArrayList;

import Main.HpoSqliteLucas;

public class SearchHpoFinale {
	
	ArrayList<ArrayList<String>> resf = new ArrayList<ArrayList<String>>();
	
	public SearchHpoFinale(){}
	
	
	public static void SearchHpof(String symptom) throws IOException{
		
		ArrayList<String> res = SearchHpo.SearchHpo(SearchHpo.trait(symptom));
		ArrayList<String> res2 = HpoSqliteLucas.connect("\""+ res.get(0) + "\"");
		affiche(res2);
		
		
	}

	public static void main(String[] args) throws IOException {
		SearchHpof("Urinary urgency");
		
	}
	
	public static void affiche(ArrayList<String> t ){
		for (int i = 0; i < t.size(); i++){
			System.out.println(t.get(i));
		}
	}
	
}

package Search;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SearchHpo {

	public static ArrayList<ArrayList<String>> GetCUIFromHPOid (ArrayList<String[]> HPids)
	{

		String index = "C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexHpoobo";
		ArrayList<ArrayList<String>> DiseaseList = new ArrayList<ArrayList<String>>();
		Date start = new Date();
		try
		{
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();
			for(String[] arg : HPids)
			{
				ArrayList<String> tmp = new ArrayList<String>();
				String id = arg[0].replace("HP:", "").trim();
				Query query = new QueryParser("ID",analyzer).parse(id);
				
				TopDocs results = searcher.search(query, 100);
				ScoreDoc[] hits = results.scoreDocs;
				for(ScoreDoc scoredoc: hits)
				{
					String value = searcher.doc(scoredoc.doc).getField("CUI").stringValue().trim();
					int ind = Contains(arg[1],DiseaseList);
					if(ind < 0)
					{
						tmp.add(arg[1]);
						if(value.matches(".*[,;].*"))
						{
							String[] cuis = value.split("[,;]");
							for(String cui : cuis)
								tmp.add(cui.trim());
						}
						else
							tmp.add(value);
						DiseaseList.add(tmp);
					}
					else
					{
						if(value.matches(".*[,;].*"))
						{
							String[] cuis = value.split("[,;]");
							for(String cui : cuis)
								DiseaseList.get(ind).add(cui.trim());
						}
						else
							DiseaseList.get(ind).add(value);
					}
				}
			}
		}
		catch(Exception e){}
		Date end = new Date();
	      System.out.println("---------------------------");
	    System.out.println(end.getTime() - start.getTime() + " HPO milliseconds");
		System.out.println("HPO match : " + DiseaseList.size());
	      System.out.println("---------------------------");
		return DiseaseList;
	}
	public static ArrayList<String[]> SearchHPOCUIandHPOids (String[] args)
	{

		String index = "C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexHpoobo";
		ArrayList<String[]> IDandCUI = new ArrayList<String[]>();
		Date start = new Date();
		try
		{
			for(String arg : args)
			{
				IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
				IndexSearcher searcher = new IndexSearcher(reader);
				Analyzer analyzer = new StandardAnalyzer();
				String queryString = arg.trim();
				Term term = new Term("name", queryString);
				WildcardQuery query = new WildcardQuery(term);
	
				TopDocs results = searcher.search(query,24000);
				ScoreDoc[] hits = results.scoreDocs;
				for(ScoreDoc scoredoc: hits)
				{
					String id = searcher.doc(scoredoc.doc).getField("ID").stringValue().trim();
					String CUI = searcher.doc(scoredoc.doc).getField("CUI").stringValue().trim();
					IDandCUI.add(new String[] {"HP:" + id,CUI});
				}
			}
		}
		catch(Exception e){}
		Date end = new Date();
	      System.out.println("---------------------------");
	    System.out.println(end.getTime() - start.getTime() + " HPO milliseconds");
		System.out.println("HPO match : " + IDandCUI.size());
	      System.out.println("---------------------------");
		return IDandCUI;
	}	
	public static ArrayList<String> SearchHpo (String symptom) throws IOException{
		StandardAnalyzer analyzer = new StandardAnalyzer();
		//Directory index = FSDirectory.open(Paths.get("C:/Users/lulu/Desktop/Projet/Données/hpo/index"));
		Directory index = FSDirectory.open(Paths.get("C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexHpoobo"));
		ArrayList<String> res = new ArrayList<String>();
		String id = "";
		String CUI = "";
		 //querystr est la recherche
		Query q = null;
		try {
			q = new QueryParser("name", analyzer).parse(symptom);
			//on cherche sur les chemical
			
		} catch (org.apache.lucene.queryparser.classic.ParseException e) {
			e.printStackTrace();
		}
		int hitsPerPage = 100;
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		// =============== Display  =============================
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			//System.out.println("id = " +d.get("id"));
			 id = (String) d.get("id");
			CUI = (String) d.get("CUI");
			
			//System.out.println("CUI =  "+ d.get("CUI") + "\n");
		}
		System.out.println(id + " " + CUI) ;
		res.add(id);
		res.add(CUI);
		reader.close();
		return res;

	}

	public static int Contains(String value, ArrayList<ArrayList<String>> list)
	{
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).get(0).equals(value))
				return i;
		}
		return -1;
	}
	public static void main(String[] args) throws IOException{
		/*
		SearchHpo(trait("Abnormality of the nail"));
		*/
		ArrayList<String[]> IDandCUI = SearchHPOCUIandHPOids(new String[] {"Bladder diverticulum","Urinary urgency"});
		ArrayList<String[]> IDandLabel = HpoSqliteLucas.GetCUI(IDandCUI);
		ArrayList<ArrayList<String>> DiseaseANDcui = new ArrayList<ArrayList<String>>();
		for(String[] arg : IDandLabel)
		{
			ArrayList<String> tmp = new ArrayList<String>();
			int ind = Contains(arg[1],DiseaseANDcui);
			if(ind < 0)
			{
				tmp.add(arg[1]);
				String value = "";
				for(String[] cui : IDandCUI)
				{
					if(arg[0].equals(cui[0]))
					{
						value = cui[1];
						break;
					}
				}
				if(value.matches(".*[,;].*"))
				{
					String[] cuis = value.split("[,;]");
					for(String cui : cuis)
						tmp.add(cui.trim());
				}
				else
					tmp.add(value);
				DiseaseANDcui.add(tmp);
			}
			else
			{
				String value = "";
				for(String[] cui : IDandCUI)
				{
					if(arg[0].equals(cui[0]))
					{
						value = cui[1];
						break;
					}
				}
				if(value.matches(".*[,;].*"))
				{
					String[] cuis = value.split("[,;]");
					for(String cui : cuis)
						DiseaseANDcui.get(ind).add(cui.trim());
				}
				else
					DiseaseANDcui.get(ind).add(value);
			}
		}
	}
	public static String trait(String s){
		String[] name = s.split(" ");
		String res = "";
		for (int i =0; i < name.length;i++){
			res = res + name[i];
		}
		return res;
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

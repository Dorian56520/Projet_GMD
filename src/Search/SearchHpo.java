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
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SearchHpo {

	public static ArrayList<String> GetCUIFromHPOid (ArrayList<String> HPids)
	{

		String index = "C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexHpoobo";
		ArrayList<String> CUIList = new ArrayList<String>();
		Date start = new Date();
		try
		{
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();
			for(String arg : HPids)
			{
				String id = arg.replace("HP:", "").trim();
				Query query = new QueryParser("ID",analyzer).parse(id);
				
				TopDocs results = searcher.search(query, 100);
				ScoreDoc[] hits = results.scoreDocs;
				for(ScoreDoc scoredoc: hits)
				{
					String value = searcher.doc(scoredoc.doc).getField("CUI").stringValue();
					if(!CUIList.contains(value))
						CUIList.add(value);
				}
			}
		}
		catch(Exception e){}
		Date end = new Date();
	      System.out.println("---------------------------");
	    System.out.println(end.getTime() - start.getTime() + " HPO milliseconds");
		System.out.println("HPO match : " + CUIList.size());
	      System.out.println("---------------------------");
		return CUIList;
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
	public static void main(String[] args) throws IOException{
		
		SearchHpo(trait("Abnormality of the nail"));
	}
	
	public static String trait(String s){
		String[] name = s.split(" ");
		String res = "";
		for (int i =0; i < name.length;i++){
			res = res + name[i];
		}
		return res;
	}
	
	

	
}

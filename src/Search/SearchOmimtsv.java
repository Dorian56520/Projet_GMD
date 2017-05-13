package Search;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.FSDirectory;

public class SearchOmimtsv {

	public static ArrayList<String> SearchOmimtsvCUI(ArrayList<String> args)
	{
		String index = "C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexOmimtsv";
		ArrayList<String> ATCList = new ArrayList<String>();
		Date start = new Date();
		try
		{
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();
			for(String arg : args)
			{
				//String[] queries = arg;
				Query query = new QueryParser("ID",analyzer).parse(arg);
				//Query query = MultiFieldQueryParser.parse(queries, new String[] {"CID1","CID2"},analyzer);
				
				TopDocs results = searcher.search(query, 10);
				ScoreDoc[] hits = results.scoreDocs;
				for(ScoreDoc scoredoc: hits)
				{
					String value = searcher.doc(scoredoc.doc).getField("CUI").stringValue();
					if(!ATCList.contains(value))
					   ATCList.add(value);
				}
			}
		}
		catch(Exception e){}
		Date end = new Date();
	      System.out.println("---------------------------");
	    System.out.println(end.getTime() - start.getTime() + " Omimtsv milliseconds");
		System.out.println("Omimtsv match : " + ATCList.size());
	      System.out.println("---------------------------");
		return ATCList;
	}
	public static void main(String[] args) 
	{
		Date start = new Date();
		ArrayList<String> data = SearchOmimtxt.SearchOmimtxtCS(new String[] {"*"});
		ArrayList<String> CUI = SearchOmimtsv.SearchOmimtsvCUI(data);
		/*ArrayList<String> Labels = SearchATC.SearchATC(ATC);*/
		Date end = new Date();
		/*System.out.println("***************************");
		System.out.println();
		System.out.println("Results :");
		for(String s : Labels)
			System.out.println(s);
	    model.notifyObserver(null);
	    model.sendResult(Labels);*/
	}
}
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

public class SearchOmimtxt {

	public static ArrayList<String> SearchOmimtxtCS(String[] args)
	{

		String index = "F:/Ecole(Telecom)/cours telecom/Projet_GMD/indexs/indexOmimtxt";

		//String index = "C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexOmimtxt";

		ArrayList<String> NOList = new ArrayList<String>();
		Date start = new Date();
		try
		{
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();
			String queryString = "";
			for(int i=0;i<args.length - 1;i++)
			{
				queryString += args[i].toLowerCase().trim() + " AND ";
			}
			queryString += args[args.length-1].toLowerCase().trim();
			Term term = new Term("CS", queryString);
			WildcardQuery query = new WildcardQuery(term);
			TopDocs results = searcher.search(query,24000);
			ScoreDoc[] hits = results.scoreDocs;
			for(ScoreDoc scoredoc: hits)
			{
				String value = searcher.doc(scoredoc.doc).getField("NO").stringValue();
				if(!NOList.contains(value))
					NOList.add(value);
			}
		}
		catch(Exception e){System.out.println(e.getMessage());}
		Date end = new Date();
	      System.out.println("---------------------------");
	    System.out.println(end.getTime() - start.getTime() + " Omimtxt milliseconds");
		System.out.println("Omimtxt match : " + NOList.size());
	      System.out.println("---------------------------");
		return NOList;
	}
	public static void main(String[] args) 
	{
		Date start = new Date();
		/*ArrayList<String> data = SearchOmimtxt.SearchOmimtxtCS(new String[] {"Abnormal*"});
		ArrayList<String> ATC = SearchStitch.SearchStitchAll(data);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);*/
		Date end = new Date();
		System.out.println("***************************");
		System.out.println();
		System.out.println("Results :");
		/*for(String s : Labels)
			System.out.println(s);
	    model.notifyObserver(null);
	    model.sendResult(Labels);*/
	}
}

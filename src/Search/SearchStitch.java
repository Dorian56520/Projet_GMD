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
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SearchStitch {
	public static ArrayList<String> SearchStitchAll(ArrayList<String> args)
	{
		args = new ArrayList<String>();
		args.add("C0000727");
		if(args.size() == 0)
			return new ArrayList<String>();
		String index = "C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexStitch";
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
				Query query = new QueryParser("CID1",analyzer).parse(arg.trim());
				//Query query = MultiFieldQueryParser.parse(queries, new String[] {"CID1","CID2"},analyzer);
				
				TopDocs results = searcher.search(query, 10);
				ScoreDoc[] hits = results.scoreDocs;
				for(ScoreDoc scoredoc: hits)
				{
					String value = searcher.doc(scoredoc.doc).getField("ATC").stringValue();
					if(!ATCList.contains(value))
					   ATCList.add(value);
				}
			}
		}
		catch(Exception e){}
		Date end = new Date();
	      System.out.println("---------------------------");
	    System.out.println(end.getTime() - start.getTime() + " Stitch milliseconds");
		System.out.println("Stitch match : " + ATCList.size());
	      System.out.println("---------------------------");
		return ATCList;
	}
}

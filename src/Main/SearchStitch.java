package Main;

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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SearchStitch {
	public static ArrayList<String> SearchStitch(ArrayList<String[]> args)
	{
		String index = "C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexStitch";
		ArrayList<String> ATCList = new ArrayList<String>();
		Date start = new Date();
		try
		{
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();
			for(String[] arg : args)
			{
				String[] queries = arg;
				Query query = MultiFieldQueryParser.parse(queries, new String[] {"CID1","CID2"},analyzer);
				
				TopDocs results = searcher.search(query, 10);
				ScoreDoc[] hits = results.scoreDocs;
				for(ScoreDoc scoredoc: hits)
				{
					   ATCList.add(searcher.doc(scoredoc.doc).getField("ATC").stringValue());
				}
			}
		}
		catch(Exception e){}
		Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		System.out.println("Nb match ATC : " + ATCList.size());
		return ATCList;
	}
}

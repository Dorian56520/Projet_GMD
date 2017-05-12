package Main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SearchStitch {
	public static ArrayList<String> SearchStitch(String[] args)
	{
		String index = "C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/index";
		try
		{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();
		String[] queries = args;
		Query query = MultiFieldQueryParser.parse(queries, new String[] {"CID1","CID2"},analyzer);
		
		TopDocs results = searcher.search(query, 100);
		ScoreDoc[] hits = results.scoreDocs;
		//System.out.println(hits[0]);
		}
		catch(Exception e){}
		return null;
	}
}

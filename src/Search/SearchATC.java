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

public class SearchATC {
	public static ArrayList<ArrayList<String>> SearchATC(ArrayList<ArrayList<String>> args)
	{
		if(args.size() == 0)
			return new ArrayList<ArrayList<String>>();
		String index = "F:/Ecole(Telecom)/cours telecom/Projet_GMD/indexs/indexATC";
		//String index = "C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexATC";
		ArrayList<ArrayList<String>> AllLabelList = new ArrayList<ArrayList<String>>();
		Date start = new Date();
		try
		{
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();
			for(ArrayList<String> arg : args)
			{
				ArrayList<String> LabelList = new ArrayList<String>();
				LabelList.add(arg.get(0));
				LabelList.add(arg.get(1));
				LabelList.add(arg.get(2));
				for(String s : arg)
				{
					String queryString = s;
					Query query = new QueryParser("ATC",analyzer).parse(queryString);
					
					TopDocs results = searcher.search(query, 10);
					ScoreDoc[] hits = results.scoreDocs;
					for(ScoreDoc scoredoc: hits)
					{
						String value = searcher.doc(scoredoc.doc).getField("Label").stringValue();
						if(!LabelList.contains(value))
						   LabelList.add(value);
					}
				}
				//if(LabelList.size() > 3)
					AllLabelList.add(LabelList);
			}
		}
		catch(Exception e){}
		Date end = new Date();
	      System.out.println("---------------------------");
	    System.out.println(end.getTime() - start.getTime() + " ATC milliseconds");
		System.out.println("ATC match : " + (AllLabelList.size() == 0 ? "0" : AllLabelList.get(0).size()));
	      System.out.println("---------------------------");
		return AllLabelList;
	}
	public static ArrayList<String> SearchATCDrug(ArrayList<String> args)
	{
		if(args.size() == 0)
			return new ArrayList<String>();

		String index = "F:/Ecole(Telecom)/cours telecom/Projet_GMD/indexs/indexATC";

		//String index = "C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexATC";

		ArrayList<String> LabelList = new ArrayList<String>();
		Date start = new Date();
		try
		{
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();
			for(String arg : args)
			{
				String queryString = arg;
				Query query = new QueryParser("ATC",analyzer).parse(queryString);
				
				TopDocs results = searcher.search(query, 10);
				ScoreDoc[] hits = results.scoreDocs;
				for(ScoreDoc scoredoc: hits)
				{
					String value = searcher.doc(scoredoc.doc).getField("Label").stringValue();
					if(!LabelList.contains(value))
					   LabelList.add(value);
				}
			}
		}
		catch(Exception e){}
		Date end = new Date();
	      System.out.println("---------------------------");
	    System.out.println(end.getTime() - start.getTime() + " ATC milliseconds");
		System.out.println("ATC match : " + LabelList.size());
	      System.out.println("---------------------------");
		return LabelList;
	}
}
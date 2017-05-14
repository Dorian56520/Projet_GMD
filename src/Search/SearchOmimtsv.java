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

	public static ArrayList<String[]> SearchOmimtsvCUIandDisease(ArrayList<String> args)
	{
		if(args.size() == 0)
			return new ArrayList<String[]>();
		String index = "C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexOmimtsv";
		ArrayList<String[]> CUIList = new ArrayList<String[]>();
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
					String cui = searcher.doc(scoredoc.doc).getField("CUI").stringValue();
					String label = searcher.doc(scoredoc.doc).getField("Label").stringValue();
					if(cui.contains("|"))
					{
						String[] cuis = cui.split("\\|");
						for(String s : cuis)
						{
							if(!Contains(s,CUIList))
								CUIList.add(new String[] {s.trim(),label});
						}
					}
					else
					{
						if(!Contains(cui,CUIList))
							CUIList.add(new String[] {cui.trim(),label});
					}
				}
			}
		}
		catch(Exception e){}
		Date end = new Date();
	      System.out.println("---------------------------");
	    System.out.println(end.getTime() - start.getTime() + " Omimtsv milliseconds");
		System.out.println("Omimtsv match : " + CUIList.size());
	      System.out.println("---------------------------");
		return CUIList;
	}
	public static boolean Contains(String cui,ArrayList<String[]> list)
	{
		for(String[] value : list)
		{
			if(value[0].equals(cui))
				return true;
		}
		return false;
	}
	public static void main(String[] args) 
	{
		Date start = new Date();
		ArrayList<String> data = SearchOmimtxt.SearchOmimtxtCS(new String[] {"*"});
		ArrayList<String[]> CUIandDisease = SearchOmimtsv.SearchOmimtsvCUIandDisease(data);
		ArrayList<String> Stitch = Sider.GetStitchIDfromCUI(CUIandDisease);
		ArrayList<String> ATC = SearchStitch.SearchStitchAll(Stitch);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);
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
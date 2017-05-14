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

	public static ArrayList<ArrayList<String>> SearchOmimtsvCUIandDisease(ArrayList<String> args)
	{
		if(args.size() == 0)
			return new ArrayList<ArrayList<String>>();
		String index = "F:/Ecole(Telecom)/cours telecom/Projet_GMD/indexs/indexOmimtsv";
		ArrayList<ArrayList<String>> DiseaseList = new ArrayList<ArrayList<String>>();
		Date start = new Date();
		try
		{
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();
			for(String arg : args)
			{
				ArrayList<String> tmp = new ArrayList<String>();
				//String[] queries = arg;
				Query query = new QueryParser("ID",analyzer).parse(arg);
				//Query query = MultiFieldQueryParser.parse(queries, new String[] {"CID1","CID2"},analyzer);
				
				TopDocs results = searcher.search(query, 10);
				ScoreDoc[] hits = results.scoreDocs;
				for(ScoreDoc scoredoc: hits)
				{
					String cui = searcher.doc(scoredoc.doc).getField("CUI").stringValue();
					String label = searcher.doc(scoredoc.doc).getField("Label").stringValue();
					int ind = Contains(label,DiseaseList);
					if(ind < 0)
					{
						tmp.add(label);
						if(cui.contains("|"))
						{
							String[] cuis = cui.split("\\|");
							for(String s : cuis)
							{
								tmp.add(s.trim());
							}
						}
						else
							tmp.add(cui.trim());
						DiseaseList.add(tmp);
					}
					else
					{
						if(cui.contains("|"))
						{
							String[] cuis = cui.split("\\|");
							for(String s : cuis)
							{
								DiseaseList.get(ind).add(s.trim());
							}
						}
						else
							DiseaseList.get(ind).add(cui.trim());
					}
				}
			}
		}
		catch(Exception e){}
		Date end = new Date();
	      System.out.println("---------------------------");
	    System.out.println(end.getTime() - start.getTime() + " Omimtsv milliseconds");
		System.out.println("Omimtsv match : " + DiseaseList.size());
	      System.out.println("---------------------------");
		return DiseaseList;
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
	public static void main(String[] args) 
	{
		/*Date start = new Date();
		ArrayList<String> data = SearchOmimtxt.SearchOmimtxtCS(new String[] {"*"});
		ArrayList<ArrayList<String>> CUIandDisease = SearchOmimtsv.SearchOmimtsvCUIandDisease(data);
		ArrayList<String> Stitch = Sider.GetStitchIDfromCUI(CUIandDisease);
		ArrayList<String> ATC = SearchStitch.SearchStitchAll(Stitch);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);*/
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
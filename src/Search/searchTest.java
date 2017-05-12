package Search;



import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class searchTest {
	public static void SearchStitch (String querystr) throws IOException{
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Directory index = FSDirectory.open(Paths.get("C:/Users/lulu/Desktop/Projet/Données/stitch/index"));

		 //querystr est la recherche
		Query q = null;
		try {
			q = new QueryParser("CID1", analyzer).parse(querystr);
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
			System.out.println((i + 1) + ". " + d.get("CID1") + "\t"+"|| " +"\t"+d.get("CID2")+"|| " +"\t"+d.get("ATC"));
		}

		reader.close();
	
	}
	public static void main(String[] args) throws IOException{
		String r="CIDm05462337";
		System.out.println("*********************************************************");
		System.out.println("|"+"\t"+"\t"+"Vous recherchez : "+r+"\t"+"\t"+"|");
		System.out.println("*********************************************************");
		System.out.println("chemical"+"\t"+"alias"+"\t     "+"drug");
		SearchStitch(r);

	}

	
	
		
}
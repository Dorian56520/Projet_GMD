package index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexerATC {
	public static void main(String[] args) {
	Date start = new Date();
    final Path docDir = Paths.get("F:/Ecole(Telecom)/cours telecom/Projet_GMD/bases/br08303.keg");
    
   try {
      System.out.println("Indexing to directory '" + "C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexATC" + "'...");

     Directory dir = FSDirectory.open(Paths.get("F:/Ecole(Telecom)/cours telecom/Projet_GMD/indexs/indexATC"));
      Analyzer analyzer = new StandardAnalyzer();
     IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
     iwc.setOpenMode(OpenMode.CREATE);
     

      // Optional: for better indexing performance, if you
      // are indexing many documents, increase the RAM
    // buffer.  But if you do this, increase the max heap
      // size to the JVM (eg add -Xmx512m or -Xmx1g):
      //
     // iwc.setRAMBufferSizeMB(256.0);

      IndexWriter writer = new IndexWriter(dir, iwc);
     indexDocs(writer, docDir);

     // NOTE: if you want to maximize search performance,
      // you can optionally call forceMerge here.  This can be
      // a terribly costly operation, so generally it's only
      // worth it when your index is relatively static (ie
      // you're done adding documents to it):
     //
      // writer.forceMerge(1);

      writer.close();

      Date end = new Date();
      System.out.println(end.getTime() - start.getTime() + " total milliseconds");

    } catch (IOException e) {
      System.out.println(" caught a " + e.getClass() +
       "\n with message: " + e.getMessage());
    }
  }
  /**
135   * Indexes the given file using the given writer, or if a directory is given,
136   * recurses over files and directories found under the given directory.
137   * 
138   * NOTE: This method indexes one document per input file.  This is slow.  For good
139   * throughput, put multiple documents into your input file(s).  An example of this is
140   * in the benchmark module, which can create "line doc" files, one document per line,
141   * using the
142   * <a href="../../../../../contrib-benchmark/org/apache/lucene/benchmark/byTask/tasks/WriteLineDocTask.html"
143   * >WriteLineDocTask</a>.
144   *  
145   * @param writer Writer to the index where the given file/dir info will be stored
146   * @param path The file to index, or the directory to recurse into to find files to index
147   * @throws IOException If there is a low-level I/O error
148   */
  static void indexDocs(final IndexWriter writer, Path path) throws IOException {
    if (Files.isDirectory(path)) {
      Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          try {
            indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
          } catch (IOException ignore) {
            // don't index files that can't be read.
          }
          return FileVisitResult.CONTINUE;
        }
      });
    } else {
      indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
    }
  }

  /** Indexes a single document */
  static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
    try (InputStream stream = Files.newInputStream(file)) {
    	int eltCount=0;
    	InputStreamReader ipsr= new InputStreamReader(stream, StandardCharsets.UTF_8);
    	BufferedReader br = new BufferedReader(ipsr);
    	String line;
      // make a new, empty document
      Document doc = new Document();
      
      // Add the path of the file as a field named "path".  Use a
      // field that is indexed (i.e. searchable), but don't tokenize 
      // the field into separate words and don't index term frequency
      // or positional information:
      Field pathField = new StringField("path", file.toString(), Field.Store.YES);
      doc.add(pathField);
      
      // Add the last modified date of the file a field named "modified".
      // Use a LongPoint that is indexed (i.e. efficiently filterable with
      // PointRangeQuery).  This indexes to milli-second resolution, which
      // is often too fine.  You could instead create a number based on
      // year/month/day/hour/minutes/seconds, down the resolution you require.
      // For example the long value 2011021714 would mean
      // February 17, 2011, 2-3 PM.
      doc.add(new LongPoint("modified", lastModified));
      
      // Add the contents of the file to a field named "contents".  Specify a Reader,
      // so that the text of the file is tokenized and indexed, but not stored.
      // Note that FileReader expects the file to be in UTF-8 encoding.
      // If that's not the case searching for special characters will fail.
    
    	 while((line = br.readLine()) != null)
    	 {
	    	 if(line.matches("^E.*"))
	    	 {
	    		 line = line.substring(1).trim();
	    		 String[] tmp = line.split(" ");
				  String ATC =line.split(" ")[0];
				  String label = line.substring(ATC.length(),line.length()).trim();
				  label = label.replaceAll("[(\\[].*[)\\]]", "").trim();
				  doc.add(new TextField("ATC",ATC,Field.Store.YES));
				  doc.add(new StoredField("Label",label));
    			  writer.addDocument(doc);
				  eltCount++;
    		  	  doc = new Document();
	    	 }
    	 }
      if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
        // New index, so we just add the document (no old document can be there):
        System.out.println("adding " + file);
        writer.addDocument(doc);
        System.out.println("Nb label : "+eltCount);
      } else {
        // Existing index (an old copy of this document may have been indexed) so 
        // we use updateDocument instead to replace the old one matching the exact 
        // path, if present:
        System.out.println("updating " + file);
        writer.updateDocument(new Term("path", file.toString()), doc);
      }
    }
  }
}

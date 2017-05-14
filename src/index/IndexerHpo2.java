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
public class IndexerHpo2 {



	



		public IndexerHpo2() {}
		

		
		 public static void main(String[] args) {
			 Date start = new Date();
			    final Path docDir = Paths.get("C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/hpo.obo");
			    
			   try {
				   
			      System.out.println("Indexing to directory '" + "C:/Users/lulu/Desktop/Projet/Données/hpo/hp.obo" + "'...");

			     Directory dir = FSDirectory.open(Paths.get("C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/indexHpoobo"));
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
			      
			      
			      
			      
			      
			      
			      
			      
			      String xref = "";
			      line = br.readLine();
			      
			      while (line != null){
			    	  
			    		doc = new Document();
			    	  while( line != null && !(line.equals("[Term]"))){
				    	  line = br.readLine();
				      }
			    	  
			    	  if(line != null){
			    		  line = br.readLine();
			    		  String id = line.substring(7,line.length());
	    				  //System.out.println("id = " + id);
	    				  //doc.add(new TextField("id",id,Field.Store.YES));
	    				  doc.add(new TextField("id",id,Field.Store.YES));
	    				  line = br.readLine();
	    				 // System.out.println(line.substring(6,line.length()));
		    				String[] name = line.substring(6,line.length()).split(" ");
		    				String res = "";
		    				for (int i =0; i < name.length;i++){
		    					res = res + name[i];
		    				}
		    				//System.out.println(res);
		    				doc.add(new TextField("name",res,Field.Store.NO));
		    				
		    				
		    				while(line.length() != 0&& !line.substring(0,4).equals("xref") ){
		    					line=br.readLine();
		    				}
		    				
		    				while(line.length() != 0 &&line.substring(0,4).equals("xref")){
		    					if(line.substring(6,10).equals("UMLS")){
				    				//System.out.println("line 0 : 4 = " +line.substring(11,line.length()));
				    				
				    				//doc.add(new TextField("CUI",line.substring(11,line.length()),Field.Store.YES));
				    				if(!xref.equals("")){
		    							xref = xref + ";"+line.substring(11,line.length());
		    						}
		    						else{
		    							xref = line.substring(11,line.length());
		    						}
				    				
				    				
				    				
				    				
				    				
				    			}
		    					line = br.readLine();
		    				}
		    				//System.out.println("xref = " + xref);
		    				doc.add(new StoredField("CUI",xref));
		    				
				    		
		    				
		    			
			    	  }
			    	  
			    	  
			    	  
			    	  xref = "";
			    	  writer.addDocument(doc);
			    	  line = br.readLine();
			    	  
			      }
			      
			    
			      
			      
			      
			      
			      // Add the contents of the file to a field named "contents".  Specify a Reader,
			      // so that the text of the file is tokenized and indexed, but not stored.
			      // Note that FileReader expects the file to be in UTF-8 encoding.
			      // If that's not the case searching for special characters will fail.
			      if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
				        // New index, so we just add the document (no old document can be there):
				        System.out.println("adding " + file);
				        writer.addDocument(doc);
				        System.out.println("Nombre d'éléments : "+eltCount);
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

	


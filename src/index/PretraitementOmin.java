package index;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class PretraitementOmin {

	public ArrayList<Record> RecordList = new ArrayList<Record>();
	public PretraitementOmin()
	{
		FileReader Fr;
		BufferedReader br;
		boolean read = false;
		try {
			Date start = new Date();
			Fr = new FileReader("C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/omim.txt");
	    	br = new BufferedReader(Fr);
	    	String line = br.readLine();
	    	Record r = null;
	    	while(line != null)
	    	{
	    		if(line.startsWith("*RECORD*"))
	    		{
	    			r = new Record();
	        		RecordList.add(r);
	    		}
	    		else if(line.equals("*FIELD* NO"))
	    		{
	    			line = br.readLine();
	    			r.NO = Integer.parseInt(line.trim());
	    		}
	    		else if(line.equals("*FIELD* TI"))
	    		{
    				line = br.readLine();
    				if(!line.contains("MOVED TO"))
    					r.TI = line.substring(line.split(" ")[0].length()).trim();
    				else
    				{
    					line = line.substring(line.indexOf("MOVED TO") + "MOVED TO".length()).trim();
    					if(!line.toUpperCase().contains("AND"))
						{
    						Record tmp = SearchNo(Integer.parseInt(line));
    						if(tmp == null)
    							r.movedto.add(Integer.parseInt(line));
    						else
    							r.TI = SearchNo(Integer.parseInt(line)).TI;
						}
    					else
    					{
    						String[] tokens;
    						if(line.contains("AND"))
    							tokens = line.split("AND");
    						else
    							tokens = line.split("and");
    						for(String s : tokens)
    						{
        						Record tmp = SearchNo(Integer.parseInt(s.trim()));
        						if(tmp == null)
        						{
        							r.movedto.add(Integer.parseInt(s.trim()));
        						}
        						else
        						{
        							r.TI += tmp.TI + " ";
        						}
        					}
    					}
    				}
	    		}
	    		else if(line.equals("*FIELD* CS"))
	    		{
	    			line = br.readLine();
	    			do
	    			{
    					if(!line.trim().endsWith(":") && !line.contains("[") && !line.isEmpty())
    					{
    						if(line.endsWith(";"))
    							line = line.substring(0, line.length()-1).trim();
    						if(line.contains("("))
    							line = line.replaceAll("\\(.*\\)", "").trim();
    						r.CS += line.endsWith(";") ? line.substring(0, line.length()-1).trim() + ";" : line.trim() + ";";
    					}
    	    			line = br.readLine();
	    			} while(!line.startsWith("*FIELD*") && !line.startsWith("*RECORD*"));
	    			if(line.startsWith("*RECORD*"))
	    				read = true;
	    				
	    		}
	    		if(read)
	    			read = !read;
	    		else
	    			line = br.readLine();
	    	}
			for(Record rec : RecordList)
			{
				for(int i = 0; i < rec.movedto.size();i++)
				{
					Record tmp = SearchNo(rec.movedto.get(i));
					if(tmp == null)
						rec.TI = rec.TI;
					else
						rec.TI = rec.TI + " " + SearchNo(rec.movedto.get(i)).TI;
					rec.TI = tmp == null ? rec.TI : rec.TI + " " + SearchNo(rec.movedto.get(i)).TI;
				}
				if(rec.CS != "")
					System.out.println(rec.toString() + "\n");
			}
		      Date end = new Date();
		      System.out.println(end.getTime() - start.getTime() + " total milliseconds");
	    	System.out.println("Nb record : " + RecordList.size());
			br.close();
			Fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Date start = new Date();
		int count = PretraitementOmimCsv.OmimCsv(RecordList);
	    Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " total milliseconds");
	    System.out.println("Nb CUI : " + count);*/
	}
	public class Record {
		public int getNO() {
			return NO;
		}
		public void setNO(int nO) {
			NO = nO;
		}
		public String getTI() {
			return TI;
		}
		public void setTI(String tI) {
			TI = tI;
		}
		public String getCS() {
			return CS;
		}
		public void setCS(String cS) {
			CS = cS;
		}
		public String getCUI() {
			return CUI;
		}
		public void setCUI(String cUI) {
			CUI = cUI;
		}
		private ArrayList<Integer> movedto = new ArrayList<Integer>();
		private int NO;
		private String TI = "";
		private String CS = "";
		private String CUI;
		public String toString()
		{
			return "NO : " + NO + "\nTI : " + TI + "\nCS : " + CS;
		}
	}
	public Record SearchNo(int no)
	{
		for(Record r : RecordList)
		{
			if(r.NO == no)
				return r;
		}
		return null;
	}
	public static void main(String[] args) {
		new PretraitementOmin();
	}
}

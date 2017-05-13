package index;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import Main.Record;

public class PretraitementOmin {

	public static ArrayList<Record> PretraitementOmin()
	{
		ArrayList<Record> RecordList = new ArrayList<Record>();
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
	    			r.setNO(Integer.parseInt(line.trim()));
	    		}
	    		//A modfier pour récupérer tout le TI
	    		else if(line.equals("*FIELD* TI"))
	    		{
    				line = br.readLine();
    				if(!line.contains("MOVED TO"))
    					r.setTI(line.substring(line.split(" ")[0].length()).trim());
    				else
    				{
    					line = line.substring(line.indexOf("MOVED TO") + "MOVED TO".length()).trim();
    					if(!line.toUpperCase().contains("AND"))
						{
    						Record tmp = SearchNo(RecordList,Integer.parseInt(line));
    						if(tmp == null)
    							r.getMovedto().add(Integer.parseInt(line));
    						else
    							r.setTI(SearchNo(RecordList,Integer.parseInt(line)).getTI());
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
        						Record tmp = SearchNo(RecordList,Integer.parseInt(s.trim()));
        						if(tmp == null)
        						{
        							r.getMovedto().add(Integer.parseInt(s.trim()));
        						}
        						else
        						{
        							r.setTI(r.getTI() + (tmp.getTI() + " "));
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
    						r.setCS(r.getCS() + (line.endsWith(";") ? line.substring(0, line.length()-1).trim() + ";" : line.trim() + ";"));
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
				for(int i = 0; i < rec.getMovedto().size();i++)
				{
					Record tmp = SearchNo(RecordList,rec.getMovedto().get(i));
					if(tmp == null)
						rec.setTI(rec.getTI());
					else
						rec.setTI(rec.getTI() + " " + SearchNo(RecordList,rec.getMovedto().get(i)).getTI());
					rec.setTI(tmp == null ? rec.getTI() : rec.getTI() + " " + SearchNo(RecordList,rec.getMovedto().get(i)).getTI());
				}
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
		return RecordList;
	}
	
	public static Record SearchNo(ArrayList<Record> RecordList,int no)
	{
		for(Record r : RecordList)
		{
			if(r.getNO() == no)
				return r;
		}
		return null;
	}
	public static void main(String[] args) {
	}
}

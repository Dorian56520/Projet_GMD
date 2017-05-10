package index;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

public class PretraitementOmin {

	public ArrayList<Record> RecordList = new ArrayList<Record>();
	public PretraitementOmin()
	{
		FileReader Fr;
		try {
			Date start = new Date();
			Fr = new FileReader("/home/depot/2A/gmd/projet_2016-17/omim/omim.txt");
	    	BufferedReader br = new BufferedReader(Fr);
	    	String line;
	    	Record r = null;
	    	while((line = br.readLine()) != null)
	    	{
	    		if(line.startsWith("*RECORD*"))
	    		{
	    			r = new Record();
	    			while(!line.equals("*FIELD* NO"))
	    				line = br.readLine();
    				line = br.readLine();
	    			r.NO = Integer.parseInt(line.trim());
	    			
	    			while(!line.equals("*FIELD* TI"))
	    				line = br.readLine();
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
    				r.CS = null;
    				RecordList.add(r);
	    		}
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
			}
		      Date end = new Date();
		      System.out.println(end.getTime() - start.getTime() + " total milliseconds");
	    	System.out.println("Nb record : " + RecordList.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public class Record {
		private ArrayList<Integer> movedto = new ArrayList<Integer>();
		private int NO;
		private String TI = "";
		private String CS;
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

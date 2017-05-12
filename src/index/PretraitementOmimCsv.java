package index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import index.PretraitementOmin.Record;

public class PretraitementOmimCsv {

	public static int OmimCsv(ArrayList<Record> RecordList)
	{
		FileReader Fr;
		BufferedReader br;
		int count = 0;
		try 
		{
			String[] fields = new String[2];
			Fr = new FileReader("C:/Users/gauthier/Desktop/TELECOM/2A/GMD/Projet/omim_onto.csv");
	    	br = new BufferedReader(Fr);
	    	String line = br.readLine();
	    	while((line = br.readLine()) != null)
	    	{
	    		if(Pattern.matches("\".*,.*\"", line))
	    			line = replace(line);
	    		else
	    		{
		    		String[] tmp = line.split(",");
		    		fields[0] = tmp[1];
		    		for(Record r : RecordList)
		    		{
		    			if(r.getCS().toLowerCase().contains(tmp[1].toLowerCase()))
		    			{
		    				r.setCUI(tmp[5]);
		    				count++;
		    			}
		    		}
	    		}
	    	}
			br.close();
			Fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static String replace(String line)
	{
		return "";
	}
}

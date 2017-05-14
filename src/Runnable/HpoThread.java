package Runnable;

import java.util.ArrayList;

import Model.Model;
import Search.HpoSqliteLucas;
import Search.SearchHpo;

public class HpoThread implements Runnable {

	Model model;
	String[] items;
	//private final Object lock1;
	public HpoThread(Model m,String[] items/*,Object lock1*/)
	{
		model = m;
		this.items = items;
		//this.lock1 = lock1;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		/*ArrayList<String[]> IDandCUI = SearchHpo.SearchHPOCUIandHPOids(new String[] {"*pain*"});
		ArrayList<String[]> IDandLabel = HpoSqliteLucas.GetCUI(IDandCUI);
		ArrayList<ArrayList<String>> DiseaseANDcui = new ArrayList<ArrayList<String>>();
		for(String[] arg : IDandLabel)
		{
			ArrayList<String> tmp = new ArrayList<String>();
			int ind = Contains(arg[1],DiseaseANDcui);
			if(ind < 0)
			{
				tmp.add(arg[1]);
				String value = "";
				for(String[] cui : IDandCUI)
				{
					if(arg[0].equals(cui[0]))
					{
						value = cui[1];
						break;
					}
				}
				if(value.matches(".*[,;].*"))
				{
					String[] cuis = value.split("[,;]");
					for(String cui : cuis)
						tmp.add(cui.trim());
				}
				else
					tmp.add(value);
				DiseaseANDcui.add(tmp);
			}
			else
			{
				String value = "";
				for(String[] cui : IDandCUI)
				{
					if(arg[0].equals(cui[0]))
					{
						value = cui[1];
						break;
					}
				}
				if(value.matches(".*[,;].*"))
				{
					String[] cuis = value.split("[,;]");
					for(String cui : cuis)
						DiseaseANDcui.get(ind).add(cui.trim());
				}
				else
					DiseaseANDcui.get(ind).add(value);
			}
		}*/
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
}

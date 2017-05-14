package Thread;

import java.util.ArrayList;
import java.util.Date;

import Model.Model;
import Search.HpoSqliteLucas;
import Search.SearchHpo;

public class HpoThread extends Thread {

	Model model;
	String[] items;
	private final Object lockSqllite;
	private final Object lockHpo;
	public ArrayList<ArrayList<String>> DiseaseANDcui;
	public HpoThread(Model m,String[] items,Object lockSqllite,Object lockHpo)
	{
		model = m;
		this.items = items;
		this.lockSqllite = lockSqllite;
		this.lockHpo = lockHpo;
	}
	@Override
	public void run() {
		ArrayList<ArrayList<String[]>> IDandCUIList;
		ArrayList<String[]> IDandLabel;

		synchronized(lockHpo)
		{
			IDandCUIList = SearchHpo.SearchHPOCUIandHPOids(items);
		}
		synchronized(lockSqllite)
		{
			IDandLabel = HpoSqliteLucas.GetCUI(IDandCUIList);
		}
		DiseaseANDcui = new ArrayList<ArrayList<String>>();
		MagouillepourHPO(IDandLabel,IDandCUIList,DiseaseANDcui);
	}
	public static void MagouillepourHPO(ArrayList<String[]> IDandLabel, ArrayList<ArrayList<String[]>> IDandCUIList, ArrayList<ArrayList<String>> DiseaseANDcui)
	 {
		 for(String[] arg : IDandLabel)
			{
				ArrayList<String> tmp = new ArrayList<String>();
				int ind = Contains(arg[1],DiseaseANDcui);
				if(ind < 0)
				{
					tmp.add(arg[1]);
					String value = "";
					for(ArrayList<String[]> IDandCUI : IDandCUIList)
					{
						for(String[] val : IDandCUI)
						{
							if(arg[0].equals(val[0]))
							{
								value = val[1];
								break;
							}
						}
						if(value.equals(""))
							break;
						if(value.matches(".*[,;].*"))
						{
							String[] cuis = value.split("[,;]");
							for(String cui : cuis)
								tmp.add(cui.trim());
						}
						else
							tmp.add(value.trim());
						DiseaseANDcui.add(tmp);
						}
				}
				else
				{
					String value = "";
					for(ArrayList<String[]> IDandCUI : IDandCUIList)
					{
						for(String[] val : IDandCUI)
						{
							if(arg[0].equals(val[0]))
							{
								value = val[1];
								break;
							}
						}
					}
					if(value.matches(".*[,;].*"))
					{
						String[] cuis = value.trim().split("[,;]");
						for(String cui : cuis)
							DiseaseANDcui.get(ind).add(cui.trim());
					}
					else
						DiseaseANDcui.get(ind).add(value.trim());
				}
			}
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

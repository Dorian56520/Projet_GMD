package Search;

import java.util.ArrayList;
import java.util.Date;

import Main.HpoSqliteLucas;

public class SearchHpoOrphaOmim {

	
	public static void SearchHpoOrphaOmim (String[] items)
	{
		Date start = new Date();
		int cpt=0;
		//1st 
    	ArrayList<String> Drugdata = Sider.GetSiderDrugData(items);
    	
    	ArrayList<String[]> OrphaID = searchOrphadata.getOrphadataData(new String[]{"Micropenis", "Delayed dentition"});
    	ArrayList<String[]> HPidsAndDisease = HpoSqliteLucas.GetHPidFROMOrphaID(OrphaID);
    	ArrayList<String[]> CUIandDiseaseOrpha = SearchHpo.GetCUIFromHPOid(HPidsAndDisease);
		
    	ArrayList<String> Diseasedata = SearchOmimtxt.SearchOmimtxtCS(items);
		ArrayList<String[]> CUIandDiseaseOmim = SearchOmimtsv.SearchOmimtsvCUIandDisease(Diseasedata);
		
		//Then concat all CUI
		ArrayList<String[]> AllCUI = new ArrayList<String[]>();
		
		for(String[] value : CUIandDiseaseOrpha)
		{
			AllCUI.add(value);
		}
		for(String[] value : CUIandDiseaseOmim)
		{
			if(!Contains(value,AllCUI))
				AllCUI.add(value);
			else
				cpt++;
		}
		System.out.println("Nb doublon : " + cpt);
    	//SE Drug
		ArrayList<String> DrugATC = SearchStitch.SearchStitchAll(Drugdata);
		ArrayList<String> SELabels = SearchATC.SearchATC(DrugATC);

    	//Thus search cure drug
		ArrayList<String> Stitch = Sider.GetStitchIDfromCUI(AllCUI);
		ArrayList<String> ATC = SearchStitch.SearchStitchAll(Stitch);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);
		Date end = new Date();
		System.out.println(end.getTime() - start.getTime() + " total milliseconds");
	}
	public static boolean Contains(String[] value, ArrayList<String[]> list)
	{
		for(String[] data : list)
		{
			if(data[0].equals(value[0]))
				return true;
		}
		return false;
	}
    public static void main(String[] args) {
    	SearchHpoOrphaOmim.SearchHpoOrphaOmim(new String[] {"*"});
    }
}

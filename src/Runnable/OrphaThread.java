package Runnable;

import java.util.ArrayList;

import Model.Model;
import Search.HpoSqliteLucas;
import Search.SearchHpo;
import Search.searchOrphadata;

public class OrphaThread implements Runnable {

	Model model;
	String[] items;
	//private final Object lock1;
	public OrphaThread(Model m,String[] items/*,Object lock1*/)
	{
		model = m;
		this.items = items;
		//this.lock1 = lock1;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
    	ArrayList<String[]> OrphaID = searchOrphadata.getOrphadataData(items);
    	ArrayList<String[]> HPidsAndDisease = HpoSqliteLucas.GetHPidFROMOrphaID(OrphaID);
    	ArrayList<ArrayList<String>> CUIandDiseaseOrpha = SearchHpo.GetCUIFromHPOid(HPidsAndDisease);
	}

}

package Runnable;

import java.util.ArrayList;
import java.util.Date;

import Model.Model;
import Search.HpoSqliteLucas;
import Search.SearchHpo;
import Search.searchOrphadata;

public class OrphaThread implements Runnable {

	Model model;
	String[] items;
	private final Object lockSqllite;
	private final Object lockHpo;
	public OrphaThread(Model m,String[] items,Object lockSqllite,Object lockHpo)
	{
		model = m;
		this.items = items;
		this.lockSqllite = lockSqllite;
		this.lockHpo = lockHpo;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ArrayList<String[]> OrphaID = searchOrphadata.getOrphadataData(items);
		ArrayList<String[]> HPidsAndDisease;
		ArrayList<ArrayList<String>> CUIandDiseaseOrpha;
		Date start = new Date();
		synchronized(lockSqllite)
		{
	        HPidsAndDisease = HpoSqliteLucas.GetHPidFROMOrphaID(OrphaID);
		}
		synchronized(lockHpo)
		{
			CUIandDiseaseOrpha = SearchHpo.GetCUIFromHPOid(HPidsAndDisease);
		}
		Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " total milliseconds");
	}

}

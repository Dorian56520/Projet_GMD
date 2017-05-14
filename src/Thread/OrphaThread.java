package Thread;

import java.util.ArrayList;
import java.util.Date;

import Model.Model;
import Search.HpoSqliteLucas;
import Search.SearchHpo;
import Search.searchOrphadata;

public class OrphaThread extends Thread {

	Model model;
	String[] items;
	private final Object lockSqllite;
	private final Object lockHpo;
	public ArrayList<ArrayList<String>> CUIandDiseaseOrpha;
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
		synchronized(lockSqllite)
		{
	        HPidsAndDisease = HpoSqliteLucas.GetHPidFROMOrphaID(OrphaID);
		}
		synchronized(lockHpo)
		{
			CUIandDiseaseOrpha = SearchHpo.GetCUIFromHPOid(HPidsAndDisease);
		}
	}

}

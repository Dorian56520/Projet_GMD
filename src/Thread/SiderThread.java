package Thread;

import java.util.ArrayList;
import java.util.Date;

import Model.Model;
import Search.SearchATC;
import Search.SearchStitch;
import Search.Sider;

public class SiderThread extends Thread{
	Model model;
	String[] items;
	ArrayList<String> Labels;
	public SiderThread(Model m,String[] items)
	{
		model = m;
		this.items = items;
	}
	public void run(){
		Date start = new Date();
		ArrayList<String> data = Sider.GetSiderDrugData(items);
		ArrayList<String> ATC = SearchStitch.SearchStitchAllDrug(data);
		Labels = SearchATC.SearchATCDrug(ATC);
		Date end = new Date();
	    //System.out.println(end.getTime() - start.getTime() + " total milliseconds");
	  } 
}

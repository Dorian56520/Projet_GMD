package Main;

import java.util.ArrayList;
import java.util.Date;

import Model.Model;
import Search.SearchATC;
import Search.SearchStitch;
import Search.Sider;

public class DrugThread implements Runnable{
	Model model;
	public DrugThread(Model m)
	{
		model = m;
	}
	public void run(){
		Date start = new Date();
		ArrayList<String[]> data = Sider.GetSiderData(new String[] {"*pain*"});
		ArrayList<String> ATC = SearchStitch.SearchStitch(data);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);
		Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		System.out.println("***************************");
	    model.notifyObserver(null);
	  } 
}

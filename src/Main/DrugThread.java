package Main;

import java.util.ArrayList;
import java.util.Date;

import Model.Model;
import Search.SearchATC;
import Search.SearchStitch;
import Search.Sider;

public class DrugThread implements Runnable{
	Model model;
	String[] items;
	public DrugThread(Model m,String[] items)
	{
		model = m;
		this.items = items;
	}
	public void run(){
		Date start = new Date();
		ArrayList<String[]> data = Sider.GetSiderData(items);
		ArrayList<String> ATC = SearchStitch.SearchStitch(data);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);
		Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		System.out.println("***************************");
		System.out.println();
		System.out.println("Results :");
		for(String s : Labels)
			System.out.println(s);
	    model.notifyObserver(null);
	    model.sendResult(Labels);
	  } 
}

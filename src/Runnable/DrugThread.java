package Runnable;

import java.util.ArrayList;
import java.util.Date;

import Model.Model;
import Search.SearchATC;
import Search.SearchStitch;
import Search.Sider;

public class DrugThread implements Runnable{
	Model model;
	String[] items;
	private final Object lock1;
	public DrugThread(Model m,String[] items,Object lock1)
	{
		model = m;
		this.items = items;
		this.lock1 = lock1;
	}
	public void run(){
		Date start = new Date();
		ArrayList<String> data;
		synchronized(lock1)
		{
			data = Sider.GetSiderDrugData(items);
		}
		ArrayList<String> ATC = SearchStitch.SearchStitchAll(data);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);
		Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		/*System.out.println("***************************");
		System.out.println();
		System.out.println("Results :");
		for(String s : Labels)
			System.out.println(s);*/
//	    model.sendResult(Labels);
	  } 
}

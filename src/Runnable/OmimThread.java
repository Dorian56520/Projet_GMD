package Runnable;

import java.util.ArrayList;
import java.util.Date;

import Model.Model;
import Search.SearchATC;
import Search.SearchOmimtxt;
import Search.SearchStitch;
import Search.Sider;

public class OmimThread implements Runnable{
	Model model;
	String[] items;
	public OmimThread(Model m,String[] items)
	{
		model = m;
		this.items = items;
	}
	public void run(){
		Date start = new Date();
		ArrayList<String> data = SearchOmimtxt.SearchOmimtxtCS(items);
		/*ArrayList<String> ATC = SearchStitch.SearchStitchAll(data);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);*/
		Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		System.out.println("***************************");
		System.out.println();
		System.out.println("Results :");
		/*for(String s : Labels)
			System.out.println(s);
	    model.notifyObserver(null);
	    model.sendResult(Labels);*/
	  } 
}
package Runnable;

import java.util.ArrayList;
import java.util.Date;

import Model.Model;
import Search.SearchATC;
import Search.SearchOmimtsv;
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
		ArrayList<String> data = SearchOmimtxt.SearchOmimtxtCS(new String[] {"*"});
		ArrayList<String> CUI = SearchOmimtsv.SearchOmimtsvCUI(data);
		ArrayList<String> Stitch = Sider.GetStitchIDfromCUI(CUI);
		ArrayList<String> ATC = SearchStitch.SearchStitchAll(Stitch);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);
		Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		System.out.println("***************************");
		System.out.println();
		System.out.println("Results :");
		for(String s : Labels)
			System.out.println(s);
	  } 
}
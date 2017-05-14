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
	//private final Object lock1;
	public OmimThread(Model m,String[] items/*,Object lock1*/)
	{
		model = m;
		this.items = items;
		/*this.lock1 = lock1;*/
	}
	public void run(){
		Date start = new Date();
		ArrayList<String> Diseasedata = SearchOmimtxt.SearchOmimtxtCS(items);
	    ArrayList<ArrayList<String>> CUIandDiseaseOmim = SearchOmimtsv.SearchOmimtsvCUIandDisease(Diseasedata);
		Date end = new Date();
	    //System.out.println(end.getTime() - start.getTime() + " total milliseconds");
	  } 
}
package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import Interface.*;
import Main.Window;
import Runnable.DrugThread;
import Runnable.OmimThread;
import View.MainView;
import Search.SearchATC;
import Search.SearchStitch;
import Search.Sider;

public class Model implements Observable{
	Window f;
	ArrayList<Observer> views = new ArrayList<Observer>();
	
	public Window getF() {
		return f;
	}
	public void setF(Window f) {
		this.f = f;
	}
	public ArrayList<Observer> getViews() {
		return views;
	}
	public void setViews(ArrayList<Observer> views) {
		this.views = views;
	}
	
	public Model(Window f)
	{
		this.f = f;
	}
	public void CreateQuery(String[] s)
	{
		System.out.println("***************************");
		System.out.print("SEARCH WHERE CS IN [");
		for(int j=0;j<s.length - 1;j++)
		{
			System.out.print(s[j]);
			System.out.print( " AND ");
		}
		System.out.println(s[s.length - 1] + "]");
		Thread t1 = new Thread(new DrugThread(this,s));
		t1.start();
		Thread t2 = new Thread(new OmimThread(this,s));
		t2.start();
		notifyObserver(null);
	}
	public void sendResult(ArrayList<String> result)
	{
		for(Observer view : views)
		{
			if(view == Instanceof("MainView"))
			{
				((MainView)view).SetLabel(result);
			}
		}
	}
	public Observer Instanceof(String typeofview)
	{
		for(Observer v : views)
		{
			if(v.getClass().getName().endsWith((typeofview)))
				return v;
		}
		return null;
	}
	@Override
	public void addObserver(Observer obs) {
		if(!views.contains(obs))
			views.add(obs);
		notifyObserver(obs);
	}
	@Override
	public void removeObserver(Observer obs) {
		if(obs == null)
			views = new ArrayList<Observer>();
		else
		{
			if(views.contains(obs))
				views.remove(obs);
		}
	}
	@Override
	public void notifyObserver(Observer obs) {
		if(obs == null)
		{
			for(Observer o : views)
			      o.update();
		}
		else
			obs.update();
	}
}

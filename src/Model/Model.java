package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Interface.*;
import Main.Window;

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
	public void CreateQuery(ArrayList<String[]> s,ArrayList<String[]> di,ArrayList<String[]> dr)
	{
		System.out.print("SEARCH IN SIGNS WHERE ");
		for(int j=0;j<s.size() - 1;j++)
		{
			String[] tmp = s.get(j);
			System.out.print(" ( ");
			for(int i=0;i<tmp.length - 1;i++)
				System.out.print(tmp[i].trim() + " OU ");
			System.out.print(tmp[tmp.length - 1].trim() + " ) ");
			System.out.print( " AND ");
		}
		System.out.print(" ( ");
		for(int i=0;i<s.get(s.size() - 1).length - 1;i++)
			System.out.print(s.get(s.size() - 1)[i].trim() + " OU ");
		System.out.print(s.get(s.size() - 1)[s.get(s.size() - 1).length - 1].trim() + " ) ");
		System.out.print(" ) ");
		
		System.out.println();
		System.out.print("SEARCH IN DISEASE WHERE ");
		for(int j=0;j<di.size() - 1;j++)
		{
			String[] tmp = di.get(j);
			System.out.print(" ( ");
			for(int i=0;i<tmp.length - 1;i++)
				System.out.print(tmp[i].trim() + " OU ");
			System.out.print(tmp[tmp.length - 1].trim() + " ) ");
			System.out.print( " AND ");
		}
		System.out.print(" ( ");
		for(int i=0;i<di.get(di.size() - 1).length - 1;i++)
			System.out.print(di.get(di.size() - 1)[i].trim() + " OU ");
		System.out.print(di.get(di.size() - 1)[di.get(di.size() - 1).length - 1].trim() + " ) ");
		
		System.out.println();
		System.out.print("SEARCH IN DRUG WHERE ");
		for(int j=0;j<dr.size() - 1;j++)
		{
			String[] tmp = dr.get(j);
			System.out.print(" ( ");
			for(int i=0;i<tmp.length - 1;i++)
				System.out.print(tmp[i].trim() + " OU ");
			System.out.print(tmp[tmp.length - 1].trim() + " ) ");
			System.out.print( " AND ");
		}
		System.out.print(" ( ");
		for(int i=0;i<dr.get(dr.size() - 1).length - 1;i++)
			System.out.print(dr.get(dr.size() - 1)[i].trim() + " OU ");
		System.out.print(dr.get(dr.size() - 1)[dr.get(dr.size() - 1).length - 1].trim() + " ) ");
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

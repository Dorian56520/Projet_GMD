package Main;

import java.util.ArrayList;

import Controlers.Controlers;
import Model.Model;
import Search.HpoSqliteLucas;
import Search.SearchHpo;
import Search.SearchHpoOrphaOmim;
import Search.SearchOmimtsv;
import Search.SearchOmimtxt;
import Search.searchOrphadata;

import View.MainView;

import View.SearchView;

public class Main 
{
	public static void main (String [] args)
	{
		Window f = new Window();
		
	    Model model = new Model(f);

	    Controlers controler = new Controlers(model);
	    
	    //MainView vue = new MainView(controler);
	    SearchView vue = new SearchView(controler);
	    
	    model.addObserver(vue);
	}
}

package Main;

import Controlers.Controlers;
import Model.Model;
import View.MainView;
import View.SearchView;

public class Main 
{
	public static void main (String [] args)
	{
		Window f = new Window();
		
	    Model model = new Model(f);

	    Controlers controler = new Controlers(model);
	    
	    MainView vue = new MainView(controler);
	    //SearchView vue = new SearchView(controler);
	    
	    model.addObserver(vue);
	}
}

package Main;

import java.awt.*;

import javax.swing.*;

import Controlers.Controlers;
import Model.Model;
import View.MainView;

public class Main 
{
	public static void main (String [] args)
	{
		Window f = new Window();
		
	    Model model = new Model(f);

	    Controlers controler = new Controlers(model);
	    
	    MainView vue = new MainView(controler);
	    
	    model.addObserver(vue);
	}
}

package Controlers;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Main.Window;
import Model.Model;
import View.*;

public class Controlers {

	Model model;
	String OrSeparator = "/";
	public Controlers(Model m)
	{
		model = m;
	}
	public Window GetMainWindow()
	{
		return model.getF();
	}
	public void ParseIntoQuery(String[] s)
	{
		model.CreateQuery(s);
	}
}

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
	public void ParseIntoQuery(String[] s, String[] di,String[] dr)
	{
		ArrayList<String[]> signs = new ArrayList<String[]>();
		ArrayList<String[]> diseases = new ArrayList<String[]>();
		ArrayList<String[]> drugs = new ArrayList<String[]>();
		for(String tmp : s)
		{
			String[] values = tmp.contains(OrSeparator) ? tmp.split(OrSeparator) : new String[] {tmp};
			signs.add(values);
		}

		for(String tmp : di)
		{
			String[] values = tmp.contains(OrSeparator) ? tmp.split(OrSeparator) : new String[] {tmp};
			diseases.add(values);
		}

		for(String tmp : dr)
		{
			String[] values = tmp.contains(OrSeparator) ? tmp.split(OrSeparator) : new String[] {tmp};
			drugs.add(values);
		}
		model.CreateQuery(signs,diseases,drugs);
	}
}

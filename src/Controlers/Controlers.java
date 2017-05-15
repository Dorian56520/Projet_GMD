package Controlers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
		model.addObserver(new SearchView(this));
		model.CreateQuery(s);
	}
	
	
	public void addDiseaseList(ArrayList<ArrayList<String>> list) {
		ListPanel resultDisease=new ListPanel((SearchView) model.Instanceof("SearchView"),list);
		((SearchView) model.Instanceof("SearchView")).DiseasePanel.add(resultDisease);
	}
	
	
	public void addDrugList(ArrayList<String> list) { 
		JPanel listmedoc= new JPanel(new GridLayout(list.size(), 1));
		
		for(int i = 0;i<list.size();i++){			
			JLabel drugName=new JLabel(list.get(i));
			listmedoc.add(drugName);
		
		}
		((SearchView) model.Instanceof("SearchView")).DrugPanel.add(listmedoc);
	}
	public void addCureList(ArrayList<String> l){
		
		/*ArrayList<String> list = new ArrayList<String>();
		for( int j =3; j < (l.get(indexMaladie)).size();j++){
                    list.add(l.get(indexMaladie).get(j));
                    System.out.println(l.get(indexMaladie).get(j));
                }
		//Mettre tout ça dans un panel
		
		for(int i = 0;i<list.size();i++){			
			JLabel cureName=new JLabel(list.get(i));
			listRemed.add(cureName);
		}*/
		JPanel listRemed = new JPanel(new GridLayout(l.size(),1));
		for(int i=3;i<l.size();i++)
		{
			JLabel cureName=new JLabel(l.get(i));
			listRemed.add(cureName);
		}
		((SearchView) model.Instanceof("SearchView")).CurePanel.removeAll();
		JLabel titleCure = new JLabel("                     Cure");
		titleCure.setFont(new Font(titleCure.getFont().getName(),titleCure.getFont().getStyle(),30));
		titleCure.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		((SearchView) model.Instanceof("SearchView")).CurePanel.add(titleCure,BorderLayout.NORTH);
		((SearchView) model.Instanceof("SearchView")).CurePanel.add(listRemed);
		((SearchView) model.Instanceof("SearchView")).repaint();
		((SearchView) model.Instanceof("SearchView")).revalidate();
	}
}

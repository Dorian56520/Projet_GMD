package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;

import Controlers.Controlers;
import Interface.Observer;
import View.MainView.AddListener;
import View.MainView.ButtonListener;
import View.MainView.CustomPanel;
import View.MainView.TimerListener;

public class SearchView extends ImagePanel implements Observer{
	JLabel titleD = new JLabel("Disease",JLabel.CENTER);
	JLabel titleDrugs = new JLabel("Drug",JLabel.CENTER);
	JLabel titleCure = new JLabel("Cure",JLabel.CENTER);
	JPanel DiseasePanel = new JPanel(new BorderLayout());
	JPanel DrugPanel = new JPanel(new BorderLayout());
	JPanel CurePanel = new JPanel(new BorderLayout());
	JPanel GJPsign = new JPanel(new GridLayout(1,1));
	JPanel north = new JPanel();
	JPanel center = new JPanel(new GridLayout(1,3));
	JButton search  = new JButton();
	JButton loadImage = new JButton();
	JLabel result = new JLabel();
	
	ArrayList<ArrayList<String>> test= new ArrayList<ArrayList<String>>();
	ArrayList<String>test1 = new ArrayList<String>();
	ArrayList<String>test2 = new ArrayList<String>();
	Controlers controler;
	
	ArrayList<JTextField> signs = new ArrayList<JTextField>();
	
	boolean searchClicked;
	Timer tim;
	ImageIcon[] IconList = new ImageIcon[] {new ImageIcon ("./Images/Spinner Frame 1-64.png"),new ImageIcon ("./Images/Spinner Frame 2-64.png"),new ImageIcon ("./Images/Spinner Frame 3-64.png"),new ImageIcon ("./Images/Spinner Frame 4-64.png"),new ImageIcon ("./Images/Spinner Frame 5-64.png"),new ImageIcon ("./Images/Spinner Frame 6-64.png"),new ImageIcon ("./Images/Spinner Frame 7-64.png"),new ImageIcon ("./Images/Spinner Frame 8-64.png")};
	int cpt = 0;
	
	public SearchView(Controlers c) {
		super(".");
		controler=c;
		this.setLayout(new BorderLayout());
		
		search.setText("Search");
		search.setPreferredSize(new Dimension(200,50));
		search.addActionListener(new ButtonListener());
		
		tim = new Timer(100,new TimerListener());

		loadImage.setOpaque(false);
		loadImage.setBorder(null);
		loadImage.setBorderPainted(false);
		loadImage.setContentAreaFilled(false);
		loadImage.setPreferredSize(new Dimension(50,50));
		
		GJPsign.add(new CustomPanel(0));
		north.add(GJPsign,BorderLayout.WEST);
		north.add(search,BorderLayout.CENTER);
		north.add(loadImage);
		//in order to test the view :
//		test1.add("Maladie1");
//		test1.add("prov1");
//		test1.add("score1");
//		test2.add("Maladie2");
//		test2.add("prov2");
//		test2.add("score2");
//		test.add(test1);
//		test.add(test2);
//		ListPanel DiseasePan= new ListPanel(this,test);
//		CurePanel.add(DiseasePan);
			
		
		//Disease panel
		titleD.setFont(new Font(titleD.getFont().getName(),titleD.getFont().getStyle(),30));
		titleD.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		DiseasePanel.add(titleD,BorderLayout.NORTH);
		JScrollPane DiseaseScrollPane=new JScrollPane(DiseasePanel);
		DiseaseScrollPane.getVerticalScrollBar().setUnitIncrement(15);
		
		//Drugs panel
		titleDrugs.setFont(new Font(titleDrugs.getFont().getName(),titleDrugs.getFont().getStyle(),30));
		titleDrugs.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		DrugPanel.add(titleDrugs,BorderLayout.NORTH);
		JScrollPane MedicineScrollPane=new JScrollPane(DrugPanel);
		//MedicineScrollPane.add(DrugListPanel);
		MedicineScrollPane.getVerticalScrollBar().setUnitIncrement(15);
		
		//Remedy panel
		titleCure.setFont(new Font(titleCure.getFont().getName(),titleCure.getFont().getStyle(),30));
		titleCure.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		CurePanel.add(titleCure,BorderLayout.NORTH);
		JScrollPane SideEffectScrollPane=new JScrollPane(CurePanel);
		SideEffectScrollPane.getVerticalScrollBar().setUnitIncrement(15);
		

		center.add(DiseaseScrollPane);
		center.add(MedicineScrollPane);
		center.add(SideEffectScrollPane);
		
		add(center,BorderLayout.CENTER);
		add(north,BorderLayout.NORTH);
		
	}
	@Override
	public void update() {
		if(searchClicked)
		{
			if(tim.isRunning())
			{
				tim.stop();
				search.setEnabled(true);
				cpt=0;
				loadImage.setIcon(null);
				searchClicked = false;
				
			}
			else
				tim.start();
		}
		else{
		controler.GetMainWindow().setContentPane(this);
		controler.GetMainWindow().update();
		}
		
	}
	class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			searchClicked = true;
			search.setEnabled(false);
			String[] signsTab = new String[signs.size()];
			for(int i=0;i<signs.size();i++)
				signsTab[i] = signs.get(i).getText();
			controler.ParseIntoQuery(signsTab);
		}
	}
	class TimerListener implements ActionListener
	{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(cpt == IconList.length-1)
				cpt=0;
			loadImage.setIcon(IconList[cpt]);
			cpt++;
			revalidate();
			repaint();
		}
	}
	class CustomPanel extends JPanel
	{
		int contenttype;
		JTextField tf = new JTextField();
		JButton add = new JButton(new ImageIcon ("./Images/Add-64.png"));
		public CustomPanel(int contenttype)
		{
			JPanel Panel= new JPanel(new BorderLayout());
			this.contenttype = contenttype;
			signs.add(tf);
			tf.setPreferredSize(new Dimension(500, 45));
			
			add.setOpaque(false);
			add.setBorder(null);
			add.setBorderPainted(false);
			add.setContentAreaFilled(false);
			add.addActionListener(new AddListener());
			add.setPreferredSize(new Dimension(53, 45));
			
			Panel.add(add,BorderLayout.EAST);
			Panel.setPreferredSize(new Dimension(53,45));;
			
			add(tf);
			add(Panel);
		   
		}
	}
	class AddListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			if(GJPsign.getComponentCount() == ((GridLayout)GJPsign.getLayout()).getRows())
				GJPsign.setLayout(new GridLayout(((GridLayout)GJPsign.getLayout()).getRows() + 1,((GridLayout)GJPsign.getLayout()).getColumns()));			
			GJPsign.add(new CustomPanel(0));
			((CustomPanel)GJPsign.getComponent(GJPsign.getComponentCount() - 1)).add.setVisible(false);
			
			GJPsign.repaint();
			GJPsign.revalidate();
			
		}
	}
	
}

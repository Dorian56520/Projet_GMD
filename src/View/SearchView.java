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

import Controlers.Controlers;
import Interface.Observer;
import Search.HpoSqliteLucas;
import Search.SearchATC;
import Search.SearchHpo;
import Search.SearchOmimtsv;
import Search.SearchOmimtxt;
import Search.SearchStitch;
import Search.Sider;
import Search.*;

public class SearchView extends ImagePanel implements Observer{
	JLabel titleD = new JLabel("                   Disease");
	JLabel titleDrugs = new JLabel("                     Drug");
	public JLabel titleCure = new JLabel("                     Cure");
	public JPanel DiseasePanel = new JPanel(new BorderLayout());
	public JPanel DrugPanel = new JPanel(new BorderLayout());
	public JPanel CurePanel = new JPanel(new BorderLayout());
	JPanel GJPsign = new JPanel(new GridLayout(1,1));
	JPanel north = new JPanel();
	JPanel center = new JPanel(new GridLayout(1,3));
	JButton search  = new JButton();
	JButton loadImage = new JButton();
	JLabel result = new JLabel();
	JScrollPane DiseaseScrollPane=new JScrollPane(DiseasePanel);
	JScrollPane MedicineScrollPane=new JScrollPane(DrugPanel);
	JScrollPane SideEffectScrollPane=new JScrollPane(CurePanel);
	
	ArrayList<ArrayList<String>> Labels=new ArrayList<ArrayList<String>>();
	ArrayList<String> DrugLabels =new ArrayList<String>();
	
	Controlers controler;
	
	public Controlers getControler() {
		return controler;
	}
	public void setControler(Controlers controler) {
		this.controler = controler;
	}
	ArrayList<JTextField> signs = new ArrayList<JTextField>();
	
	
	boolean searchClicked = true;
	Timer tim;
	ImageIcon[] IconList = new ImageIcon[] {new ImageIcon ("./Images/Spinner Frame 1-64.png"),new ImageIcon ("./Images/Spinner Frame 2-64.png"),new ImageIcon ("./Images/Spinner Frame 3-64.png"),new ImageIcon ("./Images/Spinner Frame 4-64.png"),new ImageIcon ("./Images/Spinner Frame 5-64.png"),new ImageIcon ("./Images/Spinner Frame 6-64.png"),new ImageIcon ("./Images/Spinner Frame 7-64.png"),new ImageIcon ("./Images/Spinner Frame 8-64.png")};
	int cpt = 0;
	
	public SearchView(Controlers c) {
		super(".");
		 /*String[] items = new String[] {"microp*"};
		 ArrayList<String> Diseasedata = SearchOmimtxt.SearchOmimtxtCS(items);
	     ArrayList<ArrayList<String>> CUIandDiseaseOmim = SearchOmimtsv.SearchOmimtsvCUIandDisease(Diseasedata);
	 	
	 
	     ArrayList<String[]> OrphaID = searchOrphadata.getOrphadataData(items);
	     ArrayList<String[]> HPidsAndDisease = HpoSqliteLucas.GetHPidFROMOrphaID(OrphaID);
	     ArrayList<ArrayList<String>> CUIandDiseaseOrpha = SearchHpo.GetCUIFromHPOid(HPidsAndDisease);	
	     
	     ArrayList<ArrayList<String[]>> IDandCUIList = SearchHpo.SearchHPOCUIandHPOids(items);
			ArrayList<String[]> IDandLabel = HpoSqliteLucas.GetCUI(IDandCUIList);
			
			ArrayList<ArrayList<String>> DiseaseANDcui = new ArrayList<ArrayList<String>>();
			SearchHpoOrphaOmim.MagouillepourHPO(IDandLabel,IDandCUIList,DiseaseANDcui);
			
	 	ArrayList<ArrayList<String>> presquefin = SearchHpoOrphaOmim.combin(CUIandDiseaseOrpha,DiseaseANDcui, CUIandDiseaseOmim);
	 	presquefin = SearchHpoOrphaOmim.trie(presquefin);
	 	System.out.println("presque fin     "+presquefin.size());
	 	ArrayList<ArrayList<String>> AllStitchID = Sider.GetStitchIDfromCUI(presquefin);
	 	System.out.println("All stitch ID        " +AllStitchID.size() );
	 	ArrayList<ArrayList<String>> ATC = SearchStitch.SearchStitchAll(AllStitchID);
	 	System.out.println("ATC       "+ ATC.size());
			Labels = SearchATC.SearchATC(ATC);
			System.out.println("Labels       "+ Labels.size());	
			//SearchHpoFinale.affiche2(Labels);
			ArrayList<String> data = Sider.GetSiderDrugData(items);
			ArrayList<String> DrugATC = SearchStitch.SearchStitchAllDrug(data);
			DrugLabels = SearchATC.SearchATCDrug(DrugATC);
			System.out.println(DrugLabels.size());*/
		
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
				
		
		//Disease panel
		titleD.setFont(new Font(titleD.getFont().getName(),titleD.getFont().getStyle(),30));
		titleD.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		DiseasePanel.add(titleD,BorderLayout.NORTH);
		DiseaseScrollPane.getVerticalScrollBar().setUnitIncrement(15);
		
		//Drugs panel
		titleDrugs.setFont(new Font(titleDrugs.getFont().getName(),titleDrugs.getFont().getStyle(),30));
		titleDrugs.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		DrugPanel.add(titleDrugs,BorderLayout.NORTH);
		
		//MedicineScrollPane.add(DrugListPanel);
		MedicineScrollPane.getVerticalScrollBar().setUnitIncrement(15);
		
		//Remedy panel
		titleCure.setFont(new Font(titleCure.getFont().getName(),titleCure.getFont().getStyle(),30));
		titleCure.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		CurePanel.add(titleCure,BorderLayout.NORTH);
		SideEffectScrollPane.getVerticalScrollBar().setUnitIncrement(15);
		

		center.add(DiseaseScrollPane);
		center.add(MedicineScrollPane);
		center.add(SideEffectScrollPane);
		
		add(center,BorderLayout.CENTER);
		add(north,BorderLayout.NORTH);
	}
	public void SetPanels(ArrayList<ArrayList<String>> Disease, ArrayList<String> Drug)
	{
		controler.addDiseaseList(Disease);
		controler.addDrugList(Drug);
		update();
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
		controler.GetMainWindow().setContentPane(this);
		controler.GetMainWindow().update();
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
			/*lalistedemaladies= la fonction qu'ils vont m'envoyer;
			 * lalistededrugs= pareil ;*/
			
		}
	}
	class TimerListener implements ActionListener
	{

		public void actionPerformed(ActionEvent arg0) {
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
	}public static ArrayList<ArrayList<String>> combin(ArrayList<ArrayList<String>> Orpha,ArrayList<ArrayList<String>> HPO ,ArrayList<ArrayList<String>> Omim){
		 ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>> ();
		 int cpt = 0;
		 boolean BOrpha = true;
		 boolean BHPO = false;
		 boolean BOmim = false;
		 for(int i = 0; i < Orpha.size();i++){
			if(SearchHpoOrphaOmim.ContainsMaladie(res,Orpha.get(i).get(0)) != -2){
				String sAajouter = res.get((SearchHpoOrphaOmim.ContainsMaladie(res,Orpha.get(i).get(0)))).get(1);
				int score = 1;
				
				if (!sAajouter.contains("Orpha")&&BOrpha){
					
					sAajouter = sAajouter + "," + "Orpha";
					score++;
				}
				
				
				if (!sAajouter.contains("HPO")&&BHPO){
					sAajouter = sAajouter + "," + "HPO";
					score++;
					
				}

				if (!sAajouter.contains("Omim")&&BOmim){
		
					sAajouter = sAajouter + "," + "Omim";
					score++;
				}
				
				
				
				
				res.get((SearchHpoOrphaOmim.ContainsMaladie(res,Orpha.get(i).get(0)))).set(1, sAajouter);
				
				res.get((SearchHpoOrphaOmim.ContainsMaladie(res,Orpha.get(i).get(0)))).set(2,String.valueOf(score) );
				for(int j = 1; j<Orpha.get(i).size();j++){
					if(!SearchHpoOrphaOmim.ContainsCui(res,Orpha.get(i).get(j))){
						res.get(SearchHpoOrphaOmim.ContainsMaladie(res,Orpha.get(i).get(0))).add(res.get(SearchHpoOrphaOmim.ContainsMaladie(res,Orpha.get(i).get(0))).size(),Orpha.get(i).get(j));
					}
				}
				
			}
			
			
			else{
				ArrayList<String> aAjoute = new ArrayList<String>();
				aAjoute.add(0,Orpha.get(i).get(0));
				aAjoute.add(1,"Orpha");
				aAjoute.add(2,"1");
				
				for(int j =3 ; j < Orpha.get(i).size();j++){
					aAjoute.add(j,Orpha.get(i).get(j));
					
				}
				res.add(cpt,aAjoute);
				cpt++;
			}
	    		
	    }
		 
		  BOrpha = false;
		  BHPO = true;
		  BOmim = false;
		
		  for(int i = 0; i < HPO.size();i++){
				if(SearchHpoOrphaOmim.ContainsMaladie(res,HPO.get(i).get(0)) != -2){
					String sAajouter = res.get((SearchHpoOrphaOmim.ContainsMaladie(res,HPO.get(i).get(0)))).get(1);
					int score = 1;
					
					if (!sAajouter.contains("Orpha")&&BOrpha){
						
						sAajouter = sAajouter + "," + "Orpha";
						score++;
					}
					
					
					if (!sAajouter.contains("HPO")&&BHPO){
						sAajouter = sAajouter + "," + "HPO";
						score++;
						
					}

					if (!sAajouter.contains("Omim")&&BOmim){
			
						sAajouter = sAajouter + "," + "Omim";
						score++;
					}
					
					
					
					
					res.get((SearchHpoOrphaOmim.ContainsMaladie(res,HPO.get(i).get(0)))).set(1, sAajouter);
					
					res.get((SearchHpoOrphaOmim.ContainsMaladie(res,HPO.get(i).get(0)))).set(2,String.valueOf(score) );
					for(int j = 1; j<HPO.get(i).size();j++){
						if(!SearchHpoOrphaOmim.ContainsCui(res,HPO.get(i).get(j))){
							res.get(SearchHpoOrphaOmim.ContainsMaladie(res,HPO.get(i).get(0))).add(res.get(SearchHpoOrphaOmim.ContainsMaladie(res,HPO.get(i).get(0))).size(),HPO.get(i).get(j));
						}
					}
					
				}
				
				
				else{
					ArrayList<String> aAjoute = new ArrayList<String>();
					aAjoute.add(0,HPO.get(i).get(0));
					aAjoute.add(1,"HPO");
					aAjoute.add(2,"1");
					
					for(int j =3 ; j < HPO.get(i).size();j++){
						aAjoute.add(j,HPO.get(i).get(j));
						
					}
					res.add(cpt,aAjoute);
					cpt++;
				}
		    		
		    }
		  
		  BOrpha = false;
		  BHPO = false;
		  BOmim = true;
		  
		  for(int i = 0; i < Omim.size();i++){
				if(SearchHpoOrphaOmim.ContainsMaladie(res,Omim.get(i).get(0)) != -2){
					String sAajouter = res.get((SearchHpoOrphaOmim.ContainsMaladie(res,Omim.get(i).get(0)))).get(1);
					int score = 1;
					
					if (!sAajouter.contains("Orpha")&&BOrpha){
						
						sAajouter = sAajouter + "," + "Orpha";
						score++;
					}
					
					
					if (!sAajouter.contains("HPO")&&BHPO){
						sAajouter = sAajouter + "," + "HPO";
						score++;
						
					}

					if (!sAajouter.contains("Omim")&&BOmim){
			
						sAajouter = sAajouter + "," + "Omim";
						score++;
					}
					
					
					
					
					res.get((SearchHpoOrphaOmim.ContainsMaladie(res,Omim.get(i).get(0)))).set(1, sAajouter);
					
					res.get((SearchHpoOrphaOmim.ContainsMaladie(res,Omim.get(i).get(0)))).set(2,String.valueOf(score) );
					for(int j = 1; j<Omim.get(i).size();j++){
						if(!SearchHpoOrphaOmim.ContainsCui(res,Omim.get(i).get(j))){
							res.get(SearchHpoOrphaOmim.ContainsMaladie(res,Omim.get(i).get(0))).add(res.get(SearchHpoOrphaOmim.ContainsMaladie(res,Omim.get(i).get(0))).size(),Omim.get(i).get(j));
						}
					}
					
				}
				
				
				else{
					ArrayList<String> aAjoute = new ArrayList<String>();
					aAjoute.add(0,Omim.get(i).get(0));
					aAjoute.add(1,"Omim");
					aAjoute.add(2,"1");
					
					for(int j =3 ; j < Omim.get(i).size();j++){
						aAjoute.add(j,Omim.get(i).get(j));
						
					}
					res.add(cpt,aAjoute);
					cpt++;
				}
		    		
		    }
		
		 
		 
		 return res;
	    }
	 public static void MagouillepourHPO(ArrayList<String[]> IDandLabel, ArrayList<ArrayList<String[]>> IDandCUIList, ArrayList<ArrayList<String>> DiseaseANDcui)
	 {
		 for(String[] arg : IDandLabel)
			{
				ArrayList<String> tmp = new ArrayList<String>();
				int ind = SearchHpoOrphaOmim.Contains(arg[1],DiseaseANDcui);
				if(ind < 0)
				{
					tmp.add(arg[1]);
					String value = "";
					for(ArrayList<String[]> IDandCUI : IDandCUIList)
					{
						for(String[] val : IDandCUI)
						{
							if(arg[0].equals(val[0]))
							{
								value = val[1];
								break;
							}
						}
						if(value.equals(""))
							break;
						if(value.matches(".*[,;].*"))
						{
							String[] cuis = value.split("[,;]");
							for(String cui : cuis)
								tmp.add(cui.trim());
						}
						else
							tmp.add(value.trim());
						DiseaseANDcui.add(tmp);
						}
				}
				else
				{
					String value = "";
					for(ArrayList<String[]> IDandCUI : IDandCUIList)
					{
						for(String[] val : IDandCUI)
						{
							if(arg[0].equals(val[0]))
							{
								value = val[1];
								break;
							}
						}
					}
					if(value.matches(".*[,;].*"))
					{
						String[] cuis = value.trim().split("[,;]");
						for(String cui : cuis)
							DiseaseANDcui.get(ind).add(cui.trim());
					}
					else
						DiseaseANDcui.get(ind).add(value.trim());
				}
			}
	 }
	
}

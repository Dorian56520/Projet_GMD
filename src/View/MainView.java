package View;

import Controlers.*;
import Interface.Observer;
import Main.SpringUtilities;
import Model.Model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class MainView extends ImagePanel implements Observer{

	/**
	 * Create the frame.
	 */
	JPanel north = new JPanel();
	JPanel center = new JPanel(new GridLayout(1,3));

	JScrollPane JSsigne;
	JScrollPane JSdisease;
	JScrollPane JSdrug;
	
	JPanel GJPsigne = new JPanel(new GridLayout(5,1));
	JPanel GJPdisease = new JPanel(new GridLayout(5,1));
	JPanel GJPdrug = new JPanel(new GridLayout(5,1));
	
	JPanel BJPsigne = new JPanel(new BorderLayout());
	JPanel BJPdisease = new JPanel(new BorderLayout());
	JPanel BJPdrug = new JPanel(new BorderLayout());
	
	JButton search  = new JButton(/*new ImageIcon ("./images/boutonconnexion.png")*/);
	Controlers controler;
	
	ArrayList<JTextField> signes = new ArrayList<JTextField>();
	ArrayList<JTextField> diseases = new ArrayList<JTextField>();
	ArrayList<JTextField> drugs = new ArrayList<JTextField>();
	
	public MainView(Controlers c) {
		super("./images/Présentation.jpg");
		controler = c;
		
		search.setName("search");
		search.setText("Rechercher");
		search.addActionListener(new ButtonListener());
		
		JLabel titleSigne = new JLabel("Signes & symptomes",JLabel.CENTER);
		titleSigne.setFont(new Font(titleSigne.getFont().getName(),titleSigne.getFont().getStyle(),30));
		titleSigne.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JLabel titledisease = new JLabel("diseases",JLabel.CENTER);
		titledisease.setFont(new Font(titledisease.getFont().getName(),titledisease.getFont().getStyle(),30));
		titledisease.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JLabel titledrugs = new JLabel("Médicaments",JLabel.CENTER);
		titledrugs.setFont(new Font(titledrugs.getFont().getName(),titledrugs.getFont().getStyle(),30));
		titledrugs.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		

		GJPsigne.add(new CustomPanel(0));
		GJPdisease.add(new CustomPanel(1));
		GJPdrug.add(new CustomPanel(2));
		
		BJPsigne.add(titleSigne,BorderLayout.NORTH);
		BJPdisease.add(titledisease,BorderLayout.NORTH);
		BJPdrug.add(titledrugs,BorderLayout.NORTH);

		BJPsigne.add(GJPsigne,BorderLayout.CENTER);
		BJPdisease.add(GJPdisease,BorderLayout.CENTER);
		BJPdrug.add(GJPdrug,BorderLayout.CENTER);
		
		JSsigne = new JScrollPane(BJPsigne);
		JSdisease = new JScrollPane(BJPdisease);
		JSdrug = new JScrollPane(BJPdrug);
		
		center.add(JSsigne);
		center.add(JSdisease);
		center.add(JSdrug);
		
		north.add(search);
		
		add(center,BorderLayout.CENTER);
		add(north,BorderLayout.NORTH);
	}
	
	class CustomPanel extends JPanel
	{
		int contenttype;
		JTextField tf = new JTextField();
		JButton add = new JButton("+");
		public CustomPanel(int contenttype)
		{
			this.contenttype = contenttype;
			switch(contenttype)
			{
				case 0:
					signes.add(tf);
					break;
				case 1:
					diseases.add(tf);
					break;
				case 2:
					drugs.add(tf);
					break;
			}
			tf.setPreferredSize(new Dimension(300, 40));
			add.addActionListener(new AddListener());
			add.setPreferredSize(new Dimension(40, 40));
			SpringLayout layout = new SpringLayout();
			layout.putConstraint(SpringLayout.EAST, this,5,SpringLayout.EAST, add);
			layout.putConstraint(SpringLayout.SOUTH, this,5,SpringLayout.SOUTH, add);
			add(tf);
			add(add);
		}
	}
	 
	public void update() 
	{
		controler.GetMainWindow().setContentPane(this);
		controler.GetMainWindow().update();
	}
	class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String[] signsTab = new String[signes.size()];
			String[] diseasesTab = new String[diseases.size()];
			String[] drugsTab = new String[drugs.size()];
			for(int i=0;i<signes.size();i++)
				signsTab[i] = signes.get(i).getText();
			for(int i=0;i<diseases.size();i++)
				diseasesTab[i] = diseases.get(i).getText();
			for(int i=0;i<drugs.size();i++)
				drugsTab[i] = drugs.get(i).getText();
				
			controler.ParseIntoQuery(signsTab,diseasesTab,drugsTab);
		}
	}
	class AddListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			switch(((CustomPanel)((JButton)e.getSource()).getParent()).contenttype)
			{
				case 0:
					if(GJPsigne.getComponentCount() == ((GridLayout)GJPsigne.getLayout()).getRows())
						GJPsigne.setLayout(new GridLayout(((GridLayout)GJPsigne.getLayout()).getRows() + 1,((GridLayout)GJPsigne.getLayout()).getColumns()));
					((CustomPanel)GJPsigne.getComponent(GJPsigne.getComponentCount() - 1)).add.setVisible(false);
					GJPsigne.add(new CustomPanel(0));
					GJPsigne.repaint();
					GJPsigne.revalidate();
					break;
				case 1:
					if(GJPdisease.getComponentCount() == ((GridLayout)GJPdisease.getLayout()).getRows())
						GJPdisease.setLayout(new GridLayout(((GridLayout)GJPdisease.getLayout()).getRows() + 1,((GridLayout)GJPdisease.getLayout()).getColumns()));
					((CustomPanel)GJPdisease.getComponent(GJPdisease.getComponentCount() - 1)).add.setVisible(false);
					GJPdisease.add(new CustomPanel(1));
					GJPdisease.repaint();
					GJPdisease.revalidate();
					break;
				case 2:
					if(GJPdrug.getComponentCount() == ((GridLayout)GJPdrug.getLayout()).getRows())
						GJPdrug.setLayout(new GridLayout(((GridLayout)GJPdrug.getLayout()).getRows() + 1,((GridLayout)GJPdrug.getLayout()).getColumns()));
					((CustomPanel)GJPdrug.getComponent(GJPdrug.getComponentCount() - 1)).add.setVisible(false);
					GJPdrug.add(new CustomPanel(2));
					GJPdrug.repaint();
					GJPdrug.revalidate();
					break;
			}
		}
	}
}

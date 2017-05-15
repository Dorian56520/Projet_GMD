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
	JLabel titleSign = new JLabel("Clinical Signs",JLabel.CENTER);
	
	JPanel north = new JPanel();
	JPanel center = new JPanel(new GridLayout(1,3));
	
	JScrollPane JSsign;
	
	JPanel GJPsign = new JPanel(new GridLayout(5,1));
	JPanel BJPsign = new JPanel(new BorderLayout());
	
	JButton search  = new JButton(/*new ImageIcon ("./images/boutonconnexion.png")*/);
	//JButton loadImage = new JButton();
	//JLabel result = new JLabel();
	Controlers controler;
	
	ArrayList<JTextField> signs = new ArrayList<JTextField>();
	
	//boolean searchClicked;
	//Timer tim;
	//ImageIcon[] IconList = new ImageIcon[] {new ImageIcon ("./Images/Spinner Frame 1-64.png"),new ImageIcon ("./Images/Spinner Frame 2-64.png"),new ImageIcon ("./Images/Spinner Frame 3-64.png"),new ImageIcon ("./Images/Spinner Frame 4-64.png"),new ImageIcon ("./Images/Spinner Frame 5-64.png"),new ImageIcon ("./Images/Spinner Frame 6-64.png"),new ImageIcon ("./Images/Spinner Frame 7-64.png"),new ImageIcon ("./Images/Spinner Frame 8-64.png")};
	int cpt = 0;
	
	public MainView(Controlers c) {
		super("./images/Pr�sentation.jpg");
		controler = c;
		
		search.setText("Search");
		search.setPreferredSize(new Dimension(200,50));
		search.addActionListener(new ButtonListener());
		

		/*loadImage.setOpaque(false);
		loadImage.setBorder(null);
		loadImage.setBorderPainted(false);
		loadImage.setContentAreaFilled(false);
		loadImage.setPreferredSize(new Dimension(50,50));
		
		tim = new Timer(100,new TimerListener());*/
		

		titleSign.setFont(new Font(titleSign.getFont().getName(),titleSign.getFont().getStyle(),30));
		titleSign.setBorder(BorderFactory.createLineBorder(Color.BLACK));		

		GJPsign.add(new CustomPanel(0));
		BJPsign.add(titleSign,BorderLayout.NORTH);
		BJPsign.add(GJPsign,BorderLayout.CENTER);
		
		JSsign = new JScrollPane(BJPsign);
		JSsign.getVerticalScrollBar().setUnitIncrement(15);
		center.add(JSsign);
		
		north.add(search);
		/*north.add(loadImage);
		north.add(result);*/
		
		add(center,BorderLayout.CENTER);
		add(north,BorderLayout.NORTH);
	}
	/*public void SetLabel(ArrayList<String> Result)
	{
		result.setText(Result.size() + " results found");
	}*/
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
			search.setEnabled(false);
			String[] signsTab = new String[signs.size()];
			for(int i=0;i<signs.size();i++)
				signsTab[i] = signs.get(i).getText();
			controler.ParseIntoQuery(signsTab);
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

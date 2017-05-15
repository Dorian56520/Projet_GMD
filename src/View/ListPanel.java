package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Interface.Observer;

public class ListPanel extends JPanel{
	public ArrayList<JLabel> listeDiseaseNames= new ArrayList<JLabel>();
	Observer vue;
	public ListPanel(Observer vue,ArrayList<ArrayList<String>> list){
		this.vue=vue;
		if(list != null)
		{
			if(list.size() != 0)
			{	
				GridLayout layout=new GridLayout(list.size(),3);
				//layout.setHgap(5);
				this.setLayout(layout);
				
				for (ArrayList<String> list2 : list){
								
							  try {
								
								//Name
								JLabel name=new JLabel((list2.get(0)));
								 
								Font font = new Font("Arial",Font.BOLD,12);
								name.setFont(font);
							    listeDiseaseNames.add(name);
								name.addMouseListener(new DiseaseListener(list2));
								//Where does it come from ?
								JLabel from = new JLabel("||"+(list2.get(1)).toString()+"||");
								from.setFont(font);
								//Score
								JLabel score = new JLabel((list2.get(2)).toString());
			
								score.setFont(font);
								this.add(name);
								this.add(from);
								this.add(score);

								  } catch (Exception exp) {
					                  exp.printStackTrace();
					              }
					
							}
						}
						else
						{
							// Titre
							JLabel lab=new JLabel("No results to show");
							//Police titre
							Font font = new Font("Arial",Font.BOLD,15);
							lab.setFont(font);
							JPanel pp=new JPanel(); // Englobant
							pp.setLayout(new FlowLayout(FlowLayout.LEFT));
							pp.setOpaque(false);
							pp.add(lab);
							this.add(pp,BorderLayout.WEST);
						}
					}
					else
					{
						// Titre
						JLabel lab=new JLabel("No connection from databases...");

						//Police titre
						Font font = new Font("Arial",Font.BOLD,15);
						lab.setFont(font);
						JPanel pp=new JPanel(); // Englobant
						pp.setLayout(new FlowLayout(FlowLayout.LEFT));
						pp.setOpaque(false);
						pp.add(lab);
						this.add(pp,BorderLayout.WEST);
					}
				}
	class DiseaseListener implements MouseListener
	{	//ArrayList<ArrayList<String>> list;
		ArrayList<String> list;
		public DiseaseListener(/*ArrayList<ArrayList<String>> list*/ArrayList<String> list){
			this.list=list;
		}
		public void mouseClicked(MouseEvent arg0) {
			
		}
		
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent arg0) {
			int index=listeDiseaseNames.indexOf(arg0.getSource());
			((SearchView)vue).getControler().addCureList(list);
			
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
	}
}


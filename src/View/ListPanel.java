package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Interface.Observer;

public class ListPanel extends JPanel{
	
	public ListPanel(Observer vue,ArrayList<ArrayList<String>> list){
		if(list != null)
		{
			if(list.size() != 0)
			{	
				GridLayout layout=new GridLayout(list.size(),3);
				layout.setHgap(100);
				this.setLayout(layout);
				
				for (ArrayList<String> list2 : list){
								
							  try {
								
								//Name
								JLabel name=new JLabel((list2.get(0)).toString());
								Font font = new Font("Arial",Font.BOLD,14);
								name.setFont(font);
								//Where does it come from ?
								JLabel from = new JLabel((list2.get(1)).toString());
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
						JLabel lab=new JLabel("No connexion from databases...");

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
}


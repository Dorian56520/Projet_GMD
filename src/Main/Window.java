package Main;

import javax.swing.JFrame;

import Model.Model;
import View.*;

public class Window extends JFrame {
	
	public Window()
	{
		// Window
		this.setTitle("GMD Project");
	    this.setSize(1280, 1024);        
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setVisible(false);
	}
	public void update()
	{
		this.setVisible(true);
		this.revalidate();
	}
}

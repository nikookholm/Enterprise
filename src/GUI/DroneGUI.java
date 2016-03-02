package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Main.Enterprise;

public class DroneGUI {
	
	private Enterprise main;
	
	private JPanel q1;
	private JPanel q2;
	private JPanel q3;
	private JPanel q4;
	
	private final int SCREEN_HEIGHT = 800;
	private final int SCREEN_WIDTH  = 800;
	
	
	public DroneGUI(Enterprise enterprise)
	{
		
		initizlaize(enterprise);
		
	}
	
	public  void initizlaize(Enterprise enterprise){
		
		main = enterprise;
		
		GridBagLayout	   gbLayout = new GridBagLayout();
		GridBagConstraints c 		= new GridBagConstraints();
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setLayout(gbLayout);
		
		q1 = new PanelQ1(enterprise);
		q2 = new PanelQ2();
		q3 = new PanelQ3();
		q4 = new PanelQ4();
		
		c.weightx = 1;
		c.weighty = 1;
		
		c.gridx = 0;
		c.gridy = 0;
		q2.setPreferredSize(new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT/2));
		frame.add(q2, c);

		c.gridx = 1;
		c.gridy = 0;
		q1.setPreferredSize(new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT/2));
		frame.add(q1, c);
		

		c.gridx = 0;
		c.gridy = 1;
		q3.setPreferredSize(new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT/2));
		frame.add(q3, c);
	
		c.gridx = 1;
		c.gridy = 1;
		q4.setPreferredSize(new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT/2));
		frame.add(q4, c);

		
		frame.pack();
		
		frame.setVisible(true);
	}

	
	
}

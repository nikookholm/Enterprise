package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DroneGUI {
	
	
	private JPanel q1;
	private JPanel q2;
	private JPanel q3;
	private JPanel q4;
	
	private final int SCREEN_HEIGHT = 800;
	private final int SCREEN_WIDTH = 800;
	
	
	public DroneGUI()
	{
		initizlaize();
		
	}
	
	public  void initizlaize(){
		
		GridBagLayout gbLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setLayout(gbLayout);
		
		
		q1 = new PanelQ1();
		q2 = new PanelQ2();
		q3 = new PanelQ3();
		q4 = new PanelQ4();
		
		c.gridx = 0;
		c.gridy = 0;
		frame.pack();
		frame.setMaximumSize(q2.getMaximumSize());
		frame.add(q2, c);

		c.gridx = 1;
		c.gridy = 0;
		frame.add(q1, c);
		

		c.gridx = 0;
		c.gridy = 1;
		frame.add(q3, c);
	
		c.gridx = 1;
		c.gridy = 1;
		frame.add(q4, c);

		
		frame.pack();
		frame.setVisible(true);
	}

	
	
}

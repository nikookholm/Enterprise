package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DroneGUI extends JFrame {
	
	
	static JPanel q1;
	static JPanel q2;
	static JPanel q3;
	static JPanel q4;

	public static void main(String[] agas)
	{
		initizlaize();
		
	}
	
	public static  void initizlaize(){
		
		GridBagLayout gbLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		DroneGUI frame = new DroneGUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(1200, 800);
		frame.setLayout(gbLayout);
		
		
		q1 = new PanelQ1();
		q2 = new PanelQ2();
		q3 = new PanelQ3();
		q4 = new PanelQ4();
		
		c.gridx = 0;
		c.gridy = 0;
		frame.add(q2, c);
		
		c.gridx = 0;
		c.gridy = 1;
		frame.add(q1, c);
		
		c.gridx = 1;
		c.gridy = 0;
		frame.add(q3, c);
		
		c.gridx = 1;
		c.gridy = 1;
		frame.add(q4, c);
	
		frame.setVisible(true);
	}

	
	
}

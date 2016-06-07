package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import Main.Enterprise;
import de.yadrone.base.navdata.AcceleroListener;
import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.BatteryListener;
import de.yadrone.base.video.ImageListener;

public class DroneGUI extends JFrame implements iDroneGUI {
	
	private Enterprise main;
	
	private PanelQ1 q1;
	private PanelQ2 q2;
	private PanelQ3 q3;
	private PanelQ4 q4;
	
	private final int MIN_SCREEN_HEIGHT = 800;
	private final int MIN_SCREEN_WIDTH  = 800;
	private Log log;	
	
	public void initialize(Enterprise enterprise){
		
		main = enterprise;
		
		GridBagLayout	   gbLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(MIN_SCREEN_WIDTH, MIN_SCREEN_HEIGHT));
		setMinimumSize(getPreferredSize());
		setSize(MIN_SCREEN_WIDTH, MIN_SCREEN_HEIGHT);
		setLayout(gbLayout);
		
		q1 = new PanelQ1(this);
		q2 = new PanelQ2(this);
		q3 = new PanelQ3();
		q4 = new PanelQ4();
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		q2.setPreferredSize(new Dimension(MIN_SCREEN_WIDTH/2, MIN_SCREEN_HEIGHT/2));
	    q2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(q2, gbc);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		q1.setPreferredSize(new Dimension(MIN_SCREEN_WIDTH/2, MIN_SCREEN_HEIGHT/2));
	    q1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(q1, gbc);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		q3.setPreferredSize(new Dimension(MIN_SCREEN_WIDTH/2, MIN_SCREEN_HEIGHT/2));
	    q3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(q3, gbc);
	
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 1;
		gbc.gridy = 1;
		q4.setPreferredSize(new Dimension(MIN_SCREEN_WIDTH/2, MIN_SCREEN_HEIGHT/2));
		q4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(q4, gbc);

		// attach Log.java class to PanalQ3.java class
		log = new Log(q3.getTextArea());
		
		pack();
		setVisible(true);
	}
	
	public Enterprise getMain() {
		return main;
	}

	public ImageListener getImageListener() {
		return new ImageListener() {
			
			@Override
			public void imageUpdated(BufferedImage buffImg) {
				q2.updateCameraPanel(buffImg);
			}
		};
	}
	
	public Log getLog()
	{
		return log;
	}
	
//	public BufferedImage getCode()
//	{
//		return null;
//	}
//	
	public BatteryListener getBatteryListener()
	{
		return q1.new Q1BatteryListener();
	}
	
	public AltitudeListener getAltitudeListener()
	{	
		return q1.new Q1AltitudeListener();
	}
	
	public AcceleroListener getAcceleroListener()
	{
		return q1.new Q1AcceleroListener();
	}
	
}

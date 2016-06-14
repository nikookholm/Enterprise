package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import GUI.TopDown.TopDown;
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
	
	private TopDown map;
	
	public void initialize(Enterprise enterprise){
		
		main = enterprise;
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		this.setTitle("Enterprise Drone");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(MIN_SCREEN_WIDTH, MIN_SCREEN_HEIGHT));
		setMinimumSize(getPreferredSize());
		setSize(MIN_SCREEN_WIDTH, MIN_SCREEN_HEIGHT);
		setLayout(new GridBagLayout());
		
		q1 = new PanelQ1(this);
		q2 = new PanelQ2(this);
		q3 = new PanelQ3();
		q4 = new PanelQ4();
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(q2, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		add(q1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(q4, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		add(q3, gbc);
		map = new TopDown(q4, main.getDrone());

		// attach Log.java class to PanalQ3.java class
		log = new Log(q3.getTextArea());
		
		pack();
		setVisible(true);
	}
	
	public Enterprise getMain() {
		return main;
	}

	public ImageListener getCameraImageListener() {
		return new ImageListener() {
			
			@Override
			public void imageUpdated(BufferedImage buffImg) {
				q2.setCameraImage(buffImg);
				q2.updateCameraPanel();
			}
			
		};
	}
	
	public void updateCorrectedImage(BufferedImage img)
	{
		q2.setCorrected(img);
	}
	
	public ImageListener getCorrectedImageListener() {
		return new ImageListener() {
			
			@Override
			public void imageUpdated(BufferedImage buffImg) {
				q2.setCorrected(buffImg);
				q2.updateCameraPanel();
			}
			
		};
	}
	
	public Log getLog()
	{
		return log;
	}
	
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

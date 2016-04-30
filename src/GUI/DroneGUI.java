package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.rtf.RTFEditorKit;

import org.opencv.core.Core;

import Main.Enterprise;
import Navigation.QRfinder;
import de.yadrone.base.navdata.Altitude;
import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.BatteryListener;
import de.yadrone.base.navdata.VideoListener;
import de.yadrone.base.video.ImageListener;

public class DroneGUI implements iDroneGUI {
	
	private Enterprise main;
	
	private PanelQ1 q1;
	private PanelQ2 q2;
	private PanelQ3 q3;
	private PanelQ4 q4;
	
	private final int SCREEN_HEIGHT = 800;
	private final int SCREEN_WIDTH  = 800;
	private Log log;
	private QRfinder qrFinder;
	
	
	public DroneGUI()
	{


	}
	
	public  void initialize(Enterprise enterprise){
		
		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
		
		
		main = enterprise;
		
		GridBagLayout	   gbLayout = new GridBagLayout();
		GridBagConstraints c 		= new GridBagConstraints();
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMaximumSize(DimMax);
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setLayout(gbLayout);
		
		q1 = new PanelQ1(enterprise);
		q2 = new PanelQ2(this);
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

		log = new Log(q3.getTextArea());
		
		frame.pack();
		
		frame.setVisible(true);
	}
	
	public Enterprise getMain() {
		return main;
	}



	@Override
	public void updateCameraPanel(Image image) {
		q2.updateCameraPanel(image);
		
	}

	@Override
	public ImageListener getImageListener() {
		return new ImageListener() {
			
			@Override
			public void imageUpdated(BufferedImage arg0) {
				q2.updateCameraPanel(arg0);
				
			}
		};
	}
	
	
	public Log getLog(){
		
		return log;
		
	}
	
	public BufferedImage getCode(){
		return null;
		
		
	}
	
	public BatteryListener getBatteryListener(){
			return q1.new Battery();
		
	}
	public AltitudeListener getAltitudeListener() {
		
		return  q1.new Altitude();
	}
	
}

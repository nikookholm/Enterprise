package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import Main.Enterprise;
import Navigation.HoughCircles;
import Navigation.QRPoi;
import Navigation.QRfinder;
import de.yadrone.base.video.ImageListener;

public class PanelQ2 extends JPanel{

	private CameraPanel cameraPanel;
	private ImageIcon   img, img2;
	private JButton     frontBtn, bottomBtn, imageBtn;
	private BufferedImage image;

	private GridBagLayout gbLayout;
	GridBagConstraints c;
	private DroneGUI droneGui;
	private List<QRPoi> im;
	
	
	public PanelQ2(DroneGUI owner){
		initialize();
		droneGui = owner;
//		owner = enterprise;
		
	}

	public void updateCameraPanel(Image image)
	{
		cameraPanel.updateCameraPanel(image);
	}

	private void initialize()
	{
		setBackground(Color.BLACK);
		 gbLayout = new GridBagLayout();
		 c 	= new GridBagConstraints();
		 
//		 this.setLayout(gbLayout);

		cameraPanel = new CameraPanel();
		
		

		frontBtn  = new JButton("FRONT CAMERA");
		bottomBtn = new JButton("BOTTOM CAMERA");
		imageBtn = new JButton("image");

		try {
			img = new ImageIcon( ImageIO.read(this.getClass().getResource("/Images/circle-check.png")));
			img2 = new ImageIcon( ImageIO.read(this.getClass().getResource("/Images/uncheck.png")));
		} catch (IOException ex) {
		
			ex.printStackTrace();
		}

		ToggelCamera tc = new ToggelCamera();
		
		
		
		frontBtn.addActionListener(tc);
		bottomBtn.addActionListener(tc);
		imageBtn.addActionListener(tc);

		frontBtn.setIcon(img);
		bottomBtn.setIcon(img2);
		imageBtn.setIcon(img2);
		
		cameraPanel.setPreferredSize(new Dimension(390, 350));


		this.add(frontBtn);
		this.add(bottomBtn);
		this.add(imageBtn);
		this.add(cameraPanel);
		
		
		
	}

	public class ToggelCamera implements ActionListener{


		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource().equals(frontBtn)){
				
				

				droneGui.getMain().getDrone().toggleCamera();
				frontBtn.setIcon(img);
				bottomBtn.setIcon(img2);
				imageBtn.setIcon(img2);
			} else if (e.getSource().equals(bottomBtn)){
				
				droneGui.getMain().getDrone().toggleCamera();
				bottomBtn.setIcon(img);
				frontBtn.setIcon(img2);
				imageBtn.setIcon(img2);
			}
			else {
				imageBtn.setIcon(img);
				frontBtn.setIcon(img2);
				bottomBtn.setIcon(img2);
			}


		}
	}

	public class CameraPanel extends JPanel
	{
		
		private BufferedImage image;

		@Override
		public void paint(Graphics g)
		{
			super.paint(g);
			if (image != null)
			{
				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		}

		public void updateCameraPanel(Image image)
		{
			this.image = (BufferedImage)image;
			cameraPanel.paint(getGraphics());
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			Mat imageMat = new Mat();
			imageMat = new HoughCircles().bufferedImageToMat(this.image);
		
			try {
		  im =  new QRfinder().findQR(imageMat);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
			for(int i= 0; i< im.size(); i++){
				System.out.println("test123");
				if(im.get(i).getCode() != null)
		System.out.println("new qr " +  im.get(i).getCode());	
			}

		}


	}
}
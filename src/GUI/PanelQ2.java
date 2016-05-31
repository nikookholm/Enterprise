package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.color.CMMException;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import Common.Drone;
import Main.Enterprise;
import Navigation.HoughCircles;
import Navigation.QRPoi;
import Navigation.QRfinder;
import Vector.Vector3D;
import de.yadrone.base.command.VideoCodec;
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
	private QRfinder qrfind = new QRfinder();
	private boolean showImg = false;
	private Drone dron;
	private boolean camTjek = false;


	public PanelQ2(DroneGUI owner){
		//		initialize();

		droneGui = owner;
		dron = owner.getMain().getDrone();
		cameraPanel = new CameraPanel();


		gbLayout = new GridBagLayout();
		this.setLayout(gbLayout);


		//         cameraPanel resizing
		c 	= new GridBagConstraints();
		c = new java.awt.GridBagConstraints();
		c.gridwidth = java.awt.GridBagConstraints.RELATIVE;
		c.fill = java.awt.GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		this.add(cameraPanel,c);		

	}

	public void updateCameraPanel(Image image)
	{
		cameraPanel.updateCameraPanel(image);
	}

	public class ToggelCamera implements ActionListener{


		@Override
		public void actionPerformed(ActionEvent e) {


			if(e.getSource().equals(frontBtn) && camTjek == true){
				showImg = false;
				droneGui.getMain().getDrone().toggleCamera();
				camTjek = false;

				frontBtn.setIcon(img);
				bottomBtn.setIcon(img2);
				imageBtn.setIcon(img2);

			} else if (e.getSource().equals(bottomBtn) && camTjek == false){
				showImg = false;
				droneGui.getMain().getDrone().toggleCamera();
				camTjek = true;
				bottomBtn.setIcon(img);
				frontBtn.setIcon(img2);
				imageBtn.setIcon(img2);
				VideoCodec qual = VideoCodec.H264_720P;				
			}
			else if (e.getSource().equals(imageBtn)){
				showImg = true;
				imageBtn.setIcon(img);
				frontBtn.setIcon(img2);
				bottomBtn.setIcon(img2);
			}


		}
	}

	public class CameraPanel extends JPanel{
		private BufferedImage image;

		public CameraPanel() {	

			initialize();



		}
		private void initialize()
		{

			gbLayout = new GridBagLayout();
			c 	= new GridBagConstraints();

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

			this.add(frontBtn);
			this.add(bottomBtn);
			this.add(imageBtn);

		}


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

			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			this.image = (BufferedImage)image;
			Mat imageMat = new Mat();
			imageMat = new HoughCircles().bufferedImageToMat(this.image);

			Vector3D test = new Vector3D();

			try {
				qrfind.findQR(imageMat,test,0);
			} catch (Exception e) {				
			}

			im = qrfind.getQRFun();
			for(int i= 0; i< im.size(); i++){
				if(im.get(i).getCode() != null)

					droneGui.getLog().add(im.get(i).getCode());
					droneGui.getLog().add(im.get(i).getDistance() + "");

				System.out.println("new qr " +  im.get(i).getCode() + " Distance er i M: " + im.get(i).getDistance());
			}



			//			if(showImg == true)
			//				this.image = qrfind.getQRimg();

			//this.image = qrfind.getQrdet();

			cameraPanel.paint(getGraphics());
			im.clear();

		}



	}
}
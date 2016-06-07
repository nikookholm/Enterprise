package GUI;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import Common.Drone;
import Navigation.HoughCircles;
import Navigation.QRPoi;
import Navigation.QRfinder;
import Vector.Vector3D;
import de.yadrone.base.command.VideoCodec;

public class PanelQ2 extends JPanel{

	private CameraPanel cameraPanel;
	private ImageIcon   img, img2;
	private JButton     frontBtn, bottomBtn, imageBtn;

	private GridBagLayout gbLayout;
	private GridBagConstraints c;
	private DroneGUI droneGui;
	private List<QRPoi> im;
	private QRfinder qrfind = new QRfinder();
	private boolean camTjek = false;

	public PanelQ2(DroneGUI owner){

		// Flyt alt dette ned i en initialize metode

		droneGui = owner;
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
		add(cameraPanel,c);	
		

	}

	public void updateCameraPanel(Image image)
	{
		cameraPanel.updateCameraPanel(image);
	}

	public class ToggelCamera implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource().equals(frontBtn) && camTjek == true){
				droneGui.getMain().getDrone().toggleCamera();
				camTjek = false;

				frontBtn.setIcon(img);
				bottomBtn.setIcon(img2);
				imageBtn.setIcon(img2);

			} else if (e.getSource().equals(bottomBtn) && camTjek == false){
				droneGui.getMain().getDrone().toggleCamera();
				camTjek = true;
				bottomBtn.setIcon(img);
				frontBtn.setIcon(img2);
				imageBtn.setIcon(img2);
				VideoCodec qual = VideoCodec.H264_720P;				
			}
			else if (e.getSource().equals(imageBtn)){
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
				g.drawImage(image, 0, 0, this.getWidth(), 50-this.getHeight(), null);
			}
		}

		public void updateCameraPanel(Image image)
		{
			
			DecimalFormat numberFormat = new DecimalFormat("0.00");
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
				if(im.get(i).getCode() != null){

					droneGui.getLog().add("QRcode:  " + im.get(i).getCode());
					droneGui.getLog().add("Distance:  " + numberFormat.format(im.get(i).getDistance()) +"m");

				System.out.println("new qr " +  im.get(i).getCode() + " Distance er i M: " + im.get(i).getDistance()/2);
				}
			}

			cameraPanel.paint(getGraphics());
			im.clear();

		}

	}
}
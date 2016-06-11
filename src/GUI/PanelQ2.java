package GUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import Navigation.HoughCircles;
import Navigation.QRPoi;
import Navigation.QRfinder;

public class PanelQ2 extends JPanel{

	private CameraPanel cameraPanel;
	private ImageIcon   img, img2;
	private JButton     frontBtn, bottomBtn, imageBtn;
	private GridBagLayout gbLayout;
	private GridBagConstraints c;
	private DroneGUI droneGui;
	//private List<QRPoi> im;
	//private QRfinder qrfind = new QRfinder();
	private boolean camTjek = false;
	private boolean imgTjek = false;
	private String changeMe = "cam"; 
	private BufferedImage camImage;
	private BufferedImage correctedImage;

	public PanelQ2(DroneGUI owner){

		// Flyt alt dette ned i en initialize metode

		droneGui = owner;
		cameraPanel = new CameraPanel();

		GridBagConstraints c = new GridBagConstraints();
		gbLayout = new GridBagLayout();
		this.setLayout(gbLayout);
		
		// cameraPanel resizing
		//		c.gridwidth = java.awt.GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 3;
		add(cameraPanel,c);	
		
		JPanel Q2Buttons = new JPanel(new BorderLayout(0,5));

		frontBtn  = new JButton("FRONT CAMERA");
		bottomBtn = new JButton("BOTTOM CAMERA");
		imageBtn = new JButton("image");
		c.fill = GridBagConstraints.NORTH;
		c.weightx = 1;
		c.weighty = 1;
		this.add(Q2Buttons);

		Q2Buttons.add(frontBtn, BorderLayout.NORTH);
		Q2Buttons.add(bottomBtn);
		Q2Buttons.add(imageBtn, BorderLayout.SOUTH);
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
	}

	public void updateCameraPanel()
	{
		BufferedImage image;
		
		if (!changeMe.equals("image"))
		{
			image = camImage;
		}
		else
		{
			image = correctedImage;
		}
		
		cameraPanel.updateCameraPanel(image);
	}

	public class ToggelCamera implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource().equals(frontBtn) && camTjek == true){
				droneGui.getMain().getDrone().toggleCamera();
				camTjek = false;
				imgTjek = false;

				frontBtn.setIcon(img);
				bottomBtn.setIcon(img2);
				imageBtn.setIcon(img2);
				
				changeMe = "cam";

			} else if (e.getSource().equals(bottomBtn) && camTjek == false){
				droneGui.getMain().getDrone().toggleCamera();
				camTjek = true;
				imgTjek = false;

				bottomBtn.setIcon(img);
				frontBtn.setIcon(img2);
				imageBtn.setIcon(img2);
				
				changeMe = "cam";
			}
			else if (e.getSource().equals(imageBtn)){
				imgTjek = !imgTjek;
				imageBtn.setIcon(img);
				frontBtn.setIcon(img2);
				bottomBtn.setIcon(img2);
				
				changeMe = "image";
			}

		}
	}

	public class CameraPanel extends JPanel{
		private BufferedImage image;

		public CameraPanel() {	
			
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

		public void updateCameraPanel(BufferedImage image)
		{
			cameraPanel.paint(getGraphics());
			
//			DecimalFormat numberFormat = new DecimalFormat("0.00");
//			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			this.image = (BufferedImage)image;
//			Mat imageMat = new Mat();
//			imageMat = new HoughCircles().bufferedImageToMat(this.image);
//
//			try {
//				qrfind.findQR(imageMat);
//			} catch (Exception e) {				
//			}
//
//			im = qrfind.getQRFun();
//			for(int i= 0; i< im.size(); i++){
//				if(im.get(i).getCode() != null){
//
//					droneGui.getLog().add("QRcode:  " + im.get(i).getCode());
//					droneGui.getLog().add("Distance:  " + numberFormat.format(im.get(i).getDistance()) +"m");
//					
//					System.out.println("new qr " +  im.get(i).getCode() + " Distance er i M: " + im.get(i).getDistance()/2);
//				}
//			}
//			if(imgTjek == true){
//				this.image = qrfind.getDebuImg();
//			}
//			im.clear();
		}
	}

	public void setCameraImage(BufferedImage buffImg) {
		camImage = buffImg;
		
	}

	public void setCorrected(BufferedImage buffImg) {
		correctedImage = buffImg;
		
	}
}
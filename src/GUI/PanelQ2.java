package GUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelQ2 extends JPanel{

	private CameraPanel cameraPanel;
	private JPanel		buttonsPanel;
	private ImageIcon   img, img2;
	private JButton     frontBtn, bottomBtn, imageBtn;
	private DroneGUI droneGui;
	private boolean camTjek = false;
	private boolean imgTjek = false;
	private String changeMe = "cam"; 
	private BufferedImage camImage;
	private BufferedImage correctedImage;

	public PanelQ2(DroneGUI owner){

		droneGui 	 = owner;
		cameraPanel  = new CameraPanel();
		buttonsPanel = new JPanel();

		this.setLayout(new BorderLayout());
		
		frontBtn  = new JButton("FRONT CAMERA");
		bottomBtn = new JButton("BOTTOM CAMERA");
		imageBtn  = new JButton("image");

		buttonsPanel.add(frontBtn);
		buttonsPanel.add(bottomBtn);
		buttonsPanel.add(imageBtn);
		
		this.add(buttonsPanel, BorderLayout.NORTH);
		this.add(cameraPanel,  BorderLayout.CENTER);
		
		try {
			img  = new ImageIcon( ImageIO.read(this.getClass().getResource("/Images/circle-check.png")));
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
		public void actionPerformed(ActionEvent eventListener) {

			if(eventListener.getSource().equals(frontBtn) && camTjek == true){
				droneGui.getMain().getDrone().toggleCamera();
				camTjek = false;
				imgTjek = false;

				frontBtn.setIcon(img);
				bottomBtn.setIcon(img2);
				imageBtn.setIcon(img2);

//				droneGui.getMain().getDrone().getCommandManager().setVideoChannel(VideoChannel.LARGE_HORI_SMALL_VERT);
				changeMe = "cam";

			} else if (eventListener.getSource().equals(bottomBtn) && camTjek == false){
//				droneGui.getMain().getDrone().toggleCamera();
//				droneGui.getMain().getDrone().getCommandManager().setVideoChannel(VideoChannel.LARGE_VERT_SMALL_HORI);

				camTjek = true;
				imgTjek = false;

				bottomBtn.setIcon(img);
				frontBtn.setIcon(img2);
				imageBtn.setIcon(img2);
				
				changeMe = "cam";
			}
			else if (eventListener.getSource().equals(imageBtn)){
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
			this.image = (BufferedImage)image;
		}
	}

	public void setCameraImage(BufferedImage buffImg) {
		camImage = buffImg;
	}

	public void setCorrected(BufferedImage buffImg) {
		correctedImage = buffImg;
		updateCameraPanel();
	}
}
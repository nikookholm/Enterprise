package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.crypto.AEADBadTagException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import de.yadrone.base.navdata.HDVideoStreamData;
import de.yadrone.base.navdata.VideoListener;
import de.yadrone.base.navdata.VideoStreamData;
import de.yadrone.base.video.ImageListener;

public class PanelQ2 extends JPanel{
	
	private CameraPanel cameraPanel;
	private ImageIcon   img;
	private JButton     frontBtn, bottomBtn;
	
	public PanelQ2(){
		initialize();
	}
	
	public void updateCameraPanel(Image image)
	{
		cameraPanel.updateCameraPanel(image);
	}
	
	private void initialize()
	{
		setBackground(Color.BLACK);
		
		cameraPanel = new CameraPanel();
		
		frontBtn  = new JButton("FRONT CAMERA");
		bottomBtn = new JButton("BOTTOM CAMERA");
		
		try {
			img = new ImageIcon( ImageIO.read(this.getClass().getResource("/Images/circle-check.png")));
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		ToggelCamera tc = new ToggelCamera();
		frontBtn.addActionListener(tc);
		bottomBtn.addActionListener(tc);
		
		frontBtn.setIcon(img);
		
		
		this.add(frontBtn);
		this.add(bottomBtn);
	}
	
	public class ToggelCamera implements ActionListener{


	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(e.getSource().equals(frontBtn)){
		
			frontBtn.setIcon(img);
			bottomBtn.setIcon(null);
		} else {
			bottomBtn.setIcon(img);
			frontBtn.setIcon(null);
		}
			
		}
	}
	
	public class CameraPanel extends JPanel
	{
		private Image image;
		
		@Override
		public void paint(Graphics g)
		{
			super.paint(g);
	        if (image != null)
	            g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
		}
		
		public void updateCameraPanel(Image image)
		{
			this.image = image;
			paint(this.getGraphics());
			
		}
		
		
	}
	public class VideoVeiw implements ImageListener{

		@Override
		public void imageUpdated(BufferedImage arg0) {
			
			cameraPanel.updateCameraPanel(arg0);
			
		}


	}
	
		
}

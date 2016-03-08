package GUI;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelQ2 extends JPanel{
	
	private Image cameraPanel;
	private ImageIcon img;
	private JButton frontBtn, bottomBtn;
	
	public PanelQ2(){
		

		initialize();
		
	}
	
	private void initialize()
	{
		setBackground(Color.BLACK);
		
		JPanel cameraPanel = new JPanel();
		
		frontBtn = new JButton("FRONT CAMERA");
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
	
	public Image getCameraPanel()
	{
		return cameraPanel;
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
	

}

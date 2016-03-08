package GUI;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JPanel;

public class PanelQ2 extends JPanel{
	
	private Image cameraPanel;
	
	public PanelQ2(){
		

		initialize();
		
	}
	
	private void initialize()
	{
		setBackground(Color.GREEN);
		
		JPanel cameraPanel = new JPanel();
	}
	
	public Image getCameraPanel()
	{
		return cameraPanel;
	}
	
	

}

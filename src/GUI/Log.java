package GUI;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.lang.annotation.Target;

import javax.swing.JTextArea;


public class Log {
	
private JTextArea target;
private BufferedImage qrcode;
	
	
	public Log(JTextArea target) {
		
		this.target = target;
		
		//   
		
	}
	
	public void add(String text){
		
		String oldText  =target.getText();
		target.setText(oldText + "\n" + text);
	}
	
}

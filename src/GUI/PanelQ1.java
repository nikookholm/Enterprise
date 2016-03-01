package GUI;

import java.awt.Button;
import java.awt.Color;

import javax.swing.JPanel;

public class PanelQ1 extends JPanel {
	
	Button function1, function2, function3, allInOneFunctin;
	
	public  PanelQ1() {
		
		setBackground(Color.BLUE);
		
		function1 = new Button("F1");
		function2 = new Button("F2");
		function3 = new Button("F3");
		allInOneFunctin = new Button("F4");
		
		this.add(function1);
		this.add(function2);
		this.add(function3);
		this.add(allInOneFunctin);
		
		
		
		
		
		
	}
	

}

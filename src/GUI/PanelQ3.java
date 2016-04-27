package GUI;

import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.TextArea;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelQ3 extends JPanel {
	
	private Log log;
	private String text;
	private DroneGUI gui;
	JTextArea textArea = new JTextArea(22, 30);
	
	
	public PanelQ3() {
	
		setBackground(Color.RED);
		
		initialize();
		
		
		
	}
	
	public void initialize(){
		
		
		textArea = new JTextArea(22, 30);
		textArea.setEditable(false);
		
		this.setBorder(new TitledBorder ( new EtchedBorder (), "Log" ));

	    JScrollPane scroll = new JScrollPane(textArea);

	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
		this.add(scroll);
		
	}
	
	public JTextArea getTextArea(){
		
		return textArea;
		
	}
	

}

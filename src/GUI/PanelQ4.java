package GUI;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelQ4 extends JPanel{
	
	private JTextArea codeArea; 
	
	public PanelQ4(){
		
		initialize();
		
	}
	
	
	public void initialize(){
		
//	 codeArea = new JTextArea(20, 20);
//	 codeArea.setEditable(false);
//	 JScrollPane scroll = new JScrollPane(codeArea);
//	 this.setBorder(new TitledBorder ( new EtchedBorder (), "Quick Response Code" ));
//	 scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//	 this.add(scroll);
//	 
		
		
	}
	
public JTextArea getQRCode(){
		
		return codeArea;
		
	}

}

package GUI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelQ3 extends JPanel {
	
	private JTextArea textArea = new JTextArea(22, 30);

	public PanelQ3()
	{
		initialize();
	}
	
	private void initialize(){
		
		textArea = new JTextArea(22, 30);
		textArea.setEditable(false);
		
		this.setBorder(new TitledBorder ( new EtchedBorder (), "Log" ));

	    JScrollPane scroll = new JScrollPane(textArea);

	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
		this.add(scroll);	
	}
	
	public JTextArea getTextArea()
	{
		return textArea;
	}
	
}

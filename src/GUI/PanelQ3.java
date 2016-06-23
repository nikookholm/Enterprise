package GUI;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelQ3 extends JPanel {
	

	private JTextArea 	textArea;
	private JScrollPane scrollPane;

	public PanelQ3()
	{
		textArea   = new JTextArea();
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		textArea.setEditable(false);
		
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public JTextArea getTextArea()
	{
		return textArea;
	}

	@Override
	public void repaint() {
		super.repaint();
		}
	
	
	
}

package GUI;

import javax.swing.JTextArea;

public class Log {
	
private JTextArea target;
	
	public Log(JTextArea target) {
		this.target = target;
	}
	
	public void add(String text)
	{
		String oldText = target.getText();
		target.setText(text + "\n" + oldText);
	}
	
}

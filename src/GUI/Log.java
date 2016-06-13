package GUI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

public class Log {
	
private JTextArea target;
	
	public Log(JTextArea target) {
		this.target = target;
	}
	
	public void add(String text)
	{
		DateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
		Date date = new Date();
		String time = dateFormat.format(date);
		
		String oldText = target.getText();
		target.setText(time + ": " + text + "\n" + oldText);
	}
	
}

package GUI;


import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelQ1 extends JPanel{
	
	private JButton start, cancel;
	private JComboBox box = new JComboBox();
	private JTextField functions = new JTextField(5);
	private int count = 0;
	private String[] description = {
		"select function", "function 1", "function 2", "function 3", " All in one"	
	};
	
	public  PanelQ1() {
		
		 setBackground(Color.BLUE);
		 
			initizlaize();
		
	}
	
	public  void initizlaize(){
		
		start = new JButton("START");
		cancel = new JButton("CANCEL");
		
		for(int i=0; i<5; i++){
			
			box.addItem(description[count++]);
			functions.setEditable(false);
			start.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (count < description.length){
						box.addItem(description[count++]);
						
					}
				}
			});
			
//			box.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					// TODO Auto-generated method stub
//					functions.setText("index: " + box.getSelectedIndex() + " "
//					+ ((JComboBox) e.getSource()).getSelectedIndex());
//					
//				}
//			});
			}
		
		start.setEnabled(false);
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				
			}
		});
		cancel.setEnabled(false);
		
		this.add(box);
		this.add(functions);
		this.add(start);
		this.add(cancel);
		
	}
	

}

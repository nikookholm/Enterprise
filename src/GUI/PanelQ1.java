package GUI;


import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Main.DroneProgram;
import Main.Enterprise;

public class PanelQ1 extends JPanel{

	private JButton start, cancel;
	private JComboBox box = new JComboBox();
	private int count = 0;

	private Enterprise main;

	public  PanelQ1(Enterprise enterprise) {

//		setBackground(Color.BLUE);

		initizlaize(enterprise);

	}

	public  void initizlaize(Enterprise enterprise){

		main = enterprise;

		box.addItem("select progarm!");



		for( DroneProgram dp: main.getDronePrograms()){
			box.addItem(dp.getName());



			start = new JButton("START");
			cancel = new JButton("ABORT");

			start.setEnabled(false);
			cancel.setEnabled(false);

			box.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (box.getSelectedIndex() != 0){

						start.setEnabled(true);
						cancel.setEnabled(true);
					}else
					{
						start.setEnabled(false);
						cancel.setEnabled(false);
					}
				}
			});

			start.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					main.startProgram(main.getDronePrograms().get(box.getSelectedIndex() - 1));
				}
			});

			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {


				}
			});
			
			
			this.add(box);
			this.add(start);
			this.add(cancel);

		}
	}
}

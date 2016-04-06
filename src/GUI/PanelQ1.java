package GUI;


import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.yadrone.base.navdata.AltitudeListener;
import Main.DroneProgram;
import Main.Enterprise;

public class PanelQ1 extends JPanel{

	private JButton start, cancel;
	private JLabel altitud, altiText;
	private JComboBox box = new JComboBox();
	private int count = 0;

	private Enterprise main;

	public  PanelQ1(Enterprise enterprise) {


		initizlaize(enterprise);

	}

	public  void initizlaize(Enterprise enterprise){

		main = enterprise;

		box.addItem("select progarm!");
		start = new JButton("START");
		cancel = new JButton("ABORT");
		altitud = new JLabel("Altitude: ");
		altiText = new JLabel("er: ");
		


		for( DroneProgram dp: main.getDronePrograms()){
			box.addItem(dp.getProgramName());



			

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
			this.add(altitud);
			this.add(altiText);

		}
	}
	
 public class Altitude implements AltitudeListener{
	 
	@Override
	public void receivedAltitude(int arg0) {
		
		altiText = new JLabel(" " + arg0);
		
	}

	@Override
	public void receivedExtendedAltitude(de.yadrone.base.navdata.Altitude arg0) {
		// TODO Auto-generated method stub
		
	}
	 
 }

	












}

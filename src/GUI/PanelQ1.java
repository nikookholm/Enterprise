package GUI;


import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.yadrone.base.navdata.AcceleroListener;
import de.yadrone.base.navdata.AcceleroPhysData;
import de.yadrone.base.navdata.AcceleroRawData;
import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.BatteryListener;
import Main.DroneProgram;
import Main.Enterprise;

public class PanelQ1 extends JPanel{

	private JButton start, cancel;
	private JLabel accelero, altitud, battery, batteryTest;
	private JComboBox box = new JComboBox();
	private int count = 0;
	

	private int batteryLevel = 100;
	private Font font = new Font("Helvetica", Font.PLAIN, 10);
	private int voltageLevel;

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
		accelero = new JLabel("accelero: ");
		battery = new JLabel("Battery: ");
		batteryTest = new JLabel(" testB: ");
		
		GridBagLayout gbLayout = new GridBagLayout();
		GridBagConstraints c = new  GridBagConstraints(); 
		this.setLayout(gbLayout);
	

		for( DroneProgram dp: main.getDronePrograms()){
			box.addItem(dp.getProgramName());
		}
		
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
					main.stopProgram();

				}
			});
			c.weighty = 0;
			c.gridx = 0;
			c.gridy = 0;
			gbLayout.setConstraints(box, c);
			this.add(box);
			
			c.gridx = 1;
			c.gridy = 0;
			this.add(start, c);
			
			c.gridx = 2;
			c.gridy = 0;
			this.add(cancel,c);
			
			c.gridx = 0;
			c.gridy = 1;
			this.add(altitud,c);
			
			c.gridx = 0;
			c.gridy = 2;
			this.add(battery,c);
	
			c.gridx = 0;
			c.gridy = 3;
			this.add(accelero, c);

		
	}

		public class Altitude implements AltitudeListener{

			@Override
			public void receivedAltitude(int arg0) {

				altitud.setText("Altitud :" + arg0);

			}

			@Override
			public void receivedExtendedAltitude(de.yadrone.base.navdata.Altitude arg0) {
				// TODO Auto-generated method stub

			}

		}
		
		public class Battery implements BatteryListener{

			@Override
			public void batteryLevelChanged(int arg0) {
				battery.setText("Battery: " + arg0 + " % ");
				
			}

			@Override
			public void voltageChanged(int arg0) {

				
				
			}
			
			
		}
		
		public class Accelero implements AcceleroListener{

			@Override
			public void receivedPhysData(AcceleroPhysData arg0) {

//				accelero.setText("acceler is :" + arg0);
				
			}

			@Override
			public void receivedRawData(AcceleroRawData arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		}

	
}

package GUI;



import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import de.yadrone.base.navdata.AcceleroListener;
import de.yadrone.base.navdata.AcceleroPhysData;
import de.yadrone.base.navdata.AcceleroRawData;
import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.BatteryListener;
import Main.DroneProgram;
import Main.Enterprise;

public class PanelQ1 extends JPanel{

	private JButton start, cancel;
	private JLabel accelero, altitud, battery;// batteryTest;
	private JComboBox box = new JComboBox();
	//private int count = 0;

	//private int batteryLevel = 100;
	//private Font font = new Font("Helvetica", Font.PLAIN, 10);
	//private int voltageLevel;
	
	private Enterprise main;

	public  PanelQ1(DroneGUI owner)
	{
		initialize(owner);
	}

	public  void initialize(DroneGUI owner){

		main = owner.getMain();

		box.addItem("select progarm!");
		start = new JButton("START");
		cancel = new JButton("ABORT");
		altitud = new JLabel("Altitude: 0 ");
		accelero = new JLabel("accelero:  0 m/s^2");
		battery = new JLabel("Battery: %");
		//batteryTest = new JLabel(" testB: ");
				
		GridBagLayout gbLayout = new GridBagLayout();
		this.setLayout(gbLayout);
		GridBagConstraints c = new  GridBagConstraints();
		
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
			
		c.fill= GridBagConstraints.BOTH;
		c.weightx =0.0;
		c.weighty = 0.0;
		
		c.gridx = 0;
		c.gridy = 0;
		this.add(box,c );
		
		c.fill= GridBagConstraints.BOTH;
		c.weightx =0.0;
		c.weighty = 0.0;
		c.gridx = 1;
		c.gridy = 0;
		this.add(start, c);
		
		c.fill= GridBagConstraints.BOTH;
		c.weightx =0.0;
		c.weighty = 0.0;
		c.gridx = 2;
		c.gridy = 0;
		this.add(cancel,c);
		
		
		c.fill= GridBagConstraints.BOTH;
		c.weightx =1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		this.add(altitud,c);
		
		c.fill= GridBagConstraints.BOTH;
		c.weightx =1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 2;
		this.add(battery,c);

		
		c.fill= GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
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

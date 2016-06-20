package GUI;

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
import de.yadrone.base.navdata.Altitude;
import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.BatteryListener;
import Main.DroneProgram;

public class PanelQ1 extends JPanel{

	private static final long serialVersionUID = 45366;
	private JComboBox<String> box;
	private JButton			  start, cancel;
	private JPanel			  programPanel;
	private JLabel 			  listenersLabel, coordinatesLabel;
	private DroneGUI		  gui;
	
	private int batteryLvl = 0;
	private int accelero   = 0;
	private int altitude   = 0;

	protected PanelQ1(DroneGUI owner)
	{
		initialize(owner);
	}

	private void initialize(DroneGUI owner){

		gui = owner;
		
		// Creates program panel and sets its elements
		start  = new JButton("START");
		cancel = new JButton("ABORT");
		
		start.setEnabled(false);
		cancel.setEnabled(true);
		
		box = new JComboBox<String>();
		box.addItem("select progarm!");
		
		for(DroneProgram dp: owner.getMain().getDronePrograms()){
			box.addItem(dp.getProgramName());
		}

		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (box.getSelectedIndex() != 0)
				{
					start.setEnabled(true);
					cancel.setEnabled(true);
				}
				else
				{
					start.setEnabled(false);
					cancel.setEnabled(true);
				}
			}
		});

		start.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				owner.getMain().startProgram(owner.getMain().getDronePrograms().get(box.getSelectedIndex() - 1));
				start.setEnabled(false);
				cancel.setEnabled(true);
			}
		});

		cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
			    //owner.getMain().stopProgram();
				cancel.setEnabled(true);
				start.setEnabled(true);
				owner.getMain().getDrone().reset();
				owner.getMain().getDrone().landing();
			}
		});
		
		programPanel = new JPanel();
		
		programPanel.add(box);
		programPanel.add(start);
		programPanel.add(cancel);
		
		// Creates and sets Listeners Label
		listenersLabel = new JLabel();
		updateListenersLabel();
		
		// Creates and sets Coordinates Label
		coordinatesLabel = new JLabel();
		updateCoordinatesLabel(0, 0, 0);
		
		// Sets up layout for this panel
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridwidth = 2;
		gbc.weightx   = 1;
		gbc.fill   = GridBagConstraints.HORIZONTAL;
		
		this.add(programPanel, gbc);

		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weighty   = 1; 
		gbc.gridwidth = 1;
		gbc.gridy     = 1;
		
		this.add(listenersLabel, gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		
		this.add(coordinatesLabel, gbc);
		
	}
	
	private void updateListenersLabel()
	{
		listenersLabel.setText(
				"<html>" +
				"Battery: " + batteryLvl + "<br />" +
				"Altitude: " + altitude + "<br />" +
				"Accelero: " + accelero + "<br />" +
				"</html>"
		);
	}
	
	protected void updateCoordinatesLabel(int x, int y, int z)
	{
		coordinatesLabel.setText(
				"<html><table>" +
				"<tr><td colspan=\"2\">Coordinates:</td></tr>" +
				"<tr><td>X:</td><td>" + x + "</td></tr>" +
				"<tr><td>Y:</td><td>" + y + "</td></tr>" +
				"<tr><td>Z:</td><td>" + z + "</td></tr>" +
				"</table></html>"
		);
	}
	
	protected class Q1AltitudeListener implements AltitudeListener
	{

		@Override
		public void receivedAltitude(int arg0) {
			altitude = arg0;			
		}

		@Override
		public void receivedExtendedAltitude(Altitude arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
	protected class Q1BatteryListener implements BatteryListener
	{

		@Override
		public void batteryLevelChanged(int arg0) {
			batteryLvl = arg0;
		}

		@Override
		public void voltageChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	protected class Q1AcceleroListener implements AcceleroListener
	{

		@Override
		public void receivedPhysData(AcceleroPhysData arg0) {
			accelero = (int)arg0.getAccsTemp();
		}

		@Override
		public void receivedRawData(AcceleroRawData arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}

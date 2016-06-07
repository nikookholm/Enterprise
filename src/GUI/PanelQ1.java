package GUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.acl.Owner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.ui.HorizontalAlignment;

import Main.DroneProgram;
import Main.Enterprise;

public class PanelQ1 extends JPanel{

	private static final long serialVersionUID = 45366;
	private JComboBox<String> box;
	private JButton			  start, cancel;
	private JPanel			  programPanel;
	private JLabel 			  listenersLabel, coordinatesLabel;
	private DroneGUI		  gui;

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
		cancel.setEnabled(false);
		
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
					cancel.setEnabled(false);
				}
				else
				{
					start.setEnabled(false);
					cancel.setEnabled(false);
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
			    owner.getMain().stopProgram();
				cancel.setEnabled(false);
				start.setEnabled(true);
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
		updateCoordinatesLabel();
		
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
		listenersLabel.setText("Listeners");
	}
	
	private void updateCoordinatesLabel()
	{
		coordinatesLabel.setText(
				"<html>" +
				"Coordinates:<br />" +
				"X: 0<br />" +
				"Y: 0<br />" +
				"Z: 0<br />" +
				"</html>"
		);
	}
}

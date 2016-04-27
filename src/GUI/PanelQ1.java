package GUI;


import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.BatteryListener;
import Main.DroneProgram;
import Main.Enterprise;

public class PanelQ1 extends JPanel{

	private JButton start, cancel, battery, altitud;
	private JLabel altiText;
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
		altitud = new JButton("Altitude: ");
		altitud.setEnabled(false);
		altiText = new JLabel("er: ");
		battery = new JButton("Battery");
		battery.setEnabled(false);;



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


			this.add(box);
			this.add(start);
			this.add(cancel);
			this.add(altitud);
			this.add(altiText);
			this.add(battery);

		
	}

//	public void paint(Graphics g)
//	{
//		g.setColor(Color.black);
//		g.drawRect(0, 0, this.getWidth(), this.getHeight());
//
//		Color c;
//		if (batteryLevel >= 50)
//		{
//			c = new Color((Math.abs(batteryLevel-100f)/100f) * 2f, 1f, 0f);
//		}
//		else
//		{
//			c = new Color(1f, (batteryLevel/100f) * 2f, 0f);
//		}
//
//		g.setColor(c);
//		g.fillRect(0, getHeight() * (batteryLevel / 100), this.getWidth(), this.getHeight());
//
//		FontMetrics metrics = g.getFontMetrics(font);
//		int hgt = metrics.getHeight();
//		g.setFont(font);
//
//		g.setColor(Color.black);
//		g.drawString("Battery", (this.getWidth() / 2) - (metrics.stringWidth("Battery") / 2), (this.getHeight() / 2) - (hgt / 2));
//		g.drawString(batteryLevel + " %", (this.getWidth() / 2) - (metrics.stringWidth(batteryLevel + " %") / 2), (this.getHeight() / 2) + (hgt / 2));
//		g.drawString(voltageLevel + " V", (this.getWidth() / 2) - (metrics.stringWidth(voltageLevel + " V") / 2), (int)((this.getHeight() / 2) + ((hgt / 2) * 2.5)));
//	}
//
//
//	public class Battary implements BatteryListener{
//
//		@Override
//		public void batteryLevelChanged(int arg0) {	}
//
//		@Override
//		public void voltageChanged(int battaryLevel) {
//
//
//		}
//	}


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

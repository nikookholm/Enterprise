package GUI;

import java.awt.Image;

import GUI.PanelQ1.Accelero;
import Main.Enterprise;
import de.yadrone.base.navdata.AcceleroListener;
import de.yadrone.base.navdata.Altitude;
import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.BatteryListener;
import de.yadrone.base.navdata.HDVideoStreamData;
import de.yadrone.base.navdata.VideoListener;
import de.yadrone.base.navdata.VideoStreamData;
import de.yadrone.base.video.ImageListener;

public interface iDroneGUI {
	
	public void initialize(Enterprise enterprise);
	public AltitudeListener getAltitudeListener();
	public ImageListener    getImageListener();
	public BatteryListener  getBatteryListener();
	public AcceleroListener getAcceleroListener();

}

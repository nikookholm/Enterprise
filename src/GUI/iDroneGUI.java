package GUI;

import java.awt.image.BufferedImage;

import Main.Enterprise;
import de.yadrone.base.navdata.AcceleroListener;
import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.BatteryListener;
import de.yadrone.base.video.ImageListener;

public interface iDroneGUI {
	
	public void initialize(Enterprise enterprise);
	public AltitudeListener getAltitudeListener();
	public ImageListener    getCameraImageListener();
	public ImageListener 	getCorrectedImageListener();
	public BatteryListener  getBatteryListener();
	public AcceleroListener getAcceleroListener();
	public Log				getLog();
	public void 			updateCorrectedImage(BufferedImage img);
	public void 			updateCoordsPanel();

}

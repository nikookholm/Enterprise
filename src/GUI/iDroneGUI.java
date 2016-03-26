package GUI;

import java.awt.Image;

import Main.Enterprise;
import de.yadrone.base.navdata.Altitude;
import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.HDVideoStreamData;
import de.yadrone.base.navdata.VideoListener;
import de.yadrone.base.navdata.VideoStreamData;
import de.yadrone.base.video.ImageListener;

public interface iDroneGUI {
	
	public VideoListener 	getVideoListener();
	public AltitudeListener getAltitudeListener();
	public void initialize(Enterprise enterprise);
	public void updateCameraPanel(Image image);
	public ImageListener    getImageListener();

}

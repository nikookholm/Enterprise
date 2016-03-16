package GUI;

import Main.Enterprise;
import de.yadrone.base.navdata.Altitude;
import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.HDVideoStreamData;
import de.yadrone.base.navdata.VideoListener;
import de.yadrone.base.navdata.VideoStreamData;

public interface iDroneGUI {
	
	public VideoListener 	getVideoListener();
	public AltitudeListener getAltitudeListener();
	public void initialize(Enterprise enterprise);

}

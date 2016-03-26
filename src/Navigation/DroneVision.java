package Navigation;

import java.util.List;

import de.yadrone.base.video.ImageListener;
import Common.Drone;
import Common.POI;

public class DroneVision implements iDroneVision {

	private Drone drone;
	
	public DroneVision(Drone drone) {
		this.drone = drone;	
	}
	
	@Override
	public List<POI> scan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageListener getImageListener() {
		// TODO Auto-generated method stub
		return null;
	}

}

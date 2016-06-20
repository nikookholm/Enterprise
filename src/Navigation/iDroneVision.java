package Navigation;

import java.util.ArrayList;
import java.util.List;

import Common.Drone;
import Navigation.iDroneVision.Condition;
import Navigation.iDroneVision.Movement;
import POI.POI;
import Vector.Vector3D;
import de.yadrone.base.video.ImageListener;

public interface iDroneVision {

	public List<POI> scanQR( Condition condition);
	public Movement calibrateToCircle(Vector3D dronepos); 
	public Vector3D dronePosition(boolean firstTime, ArrayList<POI> poi);
	public ImageListener getImageListener();
	public enum Movement { 	Initial, Forward, Backward, Left, Right, SpinLeft, SpinRight, Up, Down, UpLeft, UpRight, DownLeft, DownRight,
							StopAndSpin, None, RightUp, RightDown, LeftDown, LeftUp};
	
	public enum Condition { Initial, Flying, CircleQR }
	
	

}

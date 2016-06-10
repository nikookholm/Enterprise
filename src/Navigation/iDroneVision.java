package Navigation;

import java.util.List;

import POI.POI;
import Vector.Vector3D;
import de.yadrone.base.video.ImageListener;

public interface iDroneVision {

	public List<POI> scanQR(Movement movement, Condition condition);
	public Movement scanCircle(Vector3D dronepos);
	public Vector3D dronePosition(boolean firstTime);
	public ImageListener getImageListener();
	
	public enum Movement { 	Initial, Forward, Backward, Left, Right, SpinLeft, SpinRight, Up, Down, UpLeft, UpRight, DownLeft, DownRight,
							StopAndSpin};
	
	public enum Condition { Initial, Flying, CircleQR }

}

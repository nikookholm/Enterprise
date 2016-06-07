package Navigation;

import java.util.List;

import POI.POI;
import de.yadrone.base.video.ImageListener;

public interface iDroneVision {

	public List<POI> scan(Movement movement, Condition condition);
	public ImageListener getImageListener();
	
	public enum Movement { Forward, Backward, Left, Right, SpinLeft, SpinRight };
	
	public enum Condition { Initial, Flying, CircleQR };
}

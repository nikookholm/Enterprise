package Navigation;

import java.util.List;

import POI.POI;
import de.yadrone.base.video.ImageListener;

public interface iDroneVision {

	public List<POI> scan();
	public ImageListener getImageListener();
	
}

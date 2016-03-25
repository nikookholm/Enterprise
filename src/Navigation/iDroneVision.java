package Navigation;

import java.util.List;

import de.yadrone.base.video.ImageListener;
import Common.POI;

public interface iDroneVision {

	public List<POI> scan();
	public ImageListener getImageListener();
	
}

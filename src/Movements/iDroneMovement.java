package Movements;

import POI.POI;
import de.yadrone.base.navdata.AttitudeListener;
import de.yadrone.base.navdata.GyroListener;

public interface iDroneMovement {
	
	public void hoverTo(int height);      // Fly to the height and stay there.
	public void flyTo(POI interest);      // Align it self to the POI.
	public void rotateToAngle(int angle); 
	public void flyForward();
	public void flyForward(int cm);
	public void flyHome();
	public void flyBackwards();
	public void flyLeft();
	public void flyRight();
	public void spinLeft();
	public void spinRight();
	public void flyToRing(POI nextRing);
	public void flyThroughRing(POI nextRing);
	
	public GyroListener getGyroListener();
	public AttitudeListener getAttitudeListener();

}

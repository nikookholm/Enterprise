package Movements;

import POI.POI;
import de.yadrone.base.navdata.AttitudeListener;
import de.yadrone.base.navdata.GyroListener;

public interface iDroneMovement {
	
	//Our combined flight methods
	public void start();
	public void hoverTo(int height);      // Fly to the height and stay there.
	public void flyTo(POI interest);      // Align it self to the POI.
	public void rotateToAngle(int angle); 
	public void flyForward(int cm);		 	// fly destination manual
	public void flyHome();
	public void flyToRing(POI nextRing);
	public POI flyThroughRing(POI nextRing);

	//Standard Methods, uses Schedule to set how long
	//uses double to avoid conflicts
	public void flyForward(double schedule);
	public void flyBackward(double schedule);
	public void flyLeft(double schedule);
	public void flyRight(double schedule);
	public void spinLeft(double schedule);
	public void spinRight(double schedule);
	//hover does not use schedule
	public void hover();
	
	public GyroListener getGyroListener();
	public AttitudeListener getAttitudeListener();

}

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
	public void flyForwardConstant(int cm, int hoverTime);// fly destination manual
	public void flyHome();
	public POI flyThroughRing(POI nextRing);

	//Standard Methods
	public void flyForward();
	public void flyBackward();
	public void flyLeft();
	public void flyRight();
	public void spinLeft();
	public void spinRight();
	//hover does not use schedule
	public void hover();
	
	public GyroListener getGyroListener();
	public AttitudeListener getAttitudeListener();

}

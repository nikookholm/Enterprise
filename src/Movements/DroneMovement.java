package Movements;

import Common.Drone;
import POI.POI;
import POI.POICircle;

public class DroneMovement implements iDroneMovement {
	
	private Drone drone;
	
	public DroneMovement(Drone drone)
	{
		this.drone = drone;
	}
	
	/**
	 * The hoverTo method sets the max height, to be sure how high it is allowed to flight.
	 * It then flies as high as possible and hovers until another command is given.
	 * @param height
	 * @return void
	 */
	public void hoverTo(int height) {
		drone.getCommandManager().setMaxAltitude(height);
		drone.getCommandManager().up(30);
	}
	
	/**
	 * 
	 */
	
	public void flyTo(POI interest) {
		
	
	}
	
	/**
	 * 
	 */
	

	
	@Override
	public void rotateAngle(int angle) {
		if(checkInterval(angle)){
			return;
		}
	}
	
	/***********************************************************/
	/*********************private*******************************/
	/***********************************************************/
	
	private boolean checkInterval(int degrees){
		if(0 <= degrees && degrees < 360){
			return true;
		}
		return false;
	}
	
	private void rotateRight(int degrees) {
		drone.goRight();
	}
	
	/**
	 * 
	 */
	
	private void rotateLeft(int degrees) {
		drone.goLeft();
	}
	
	private void flyThroughRing(POICircle poiC){
		
	}
}

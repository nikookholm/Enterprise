package Movements;

import Common.Drone;
import POI.POI;
import POI.POICircle;
import Vector.Vector3D;

public class DroneMovement implements iDroneMovement {
	
	private Drone drone;
	double z;
	
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
	//testes 27/4
	private void rotateRight(int degrees) {
		drone.getCommandManager().goRight(degrees);
		drone.incAngle(-degrees);
	}
	/**
	 * 
	 */
	//testes 27/4
	private void rotateLeft(int degrees) {
		drone.getCommandManager().goLeft(degrees);
		drone.incAngle(degrees);
	}

	public void flyForward(int cm){
		int speed = 25;
		drone.getCommandManager().forward(speed); // omregnes til en mængde tid
	// mangler hastighed til at begrænse distancen
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			
		}
		drone.setCoords(calculateNewCoords(cm));
		
	}
	private void flyThroughRing(POICircle poiC){
		
	}
	
	private Vector3D calculateNewCoords(int distance){
		
		
		double x = drone.getCoordX();
		double y = drone.getCoordY();
		int angle = drone.getAngle();
		
		double newX = distance*Math.cos(x);
		double newY = distance*Math.sin(y);
		
		return new Vector3D(x+newX, y+newY, z);
		
	}
}

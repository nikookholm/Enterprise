package Movements;

import Common.Drone;
import POI.POI;

public class DroneMovement implements iDroneMovement {
	
	private Drone drone;
	private int currentAngle = 0;
	
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
	
	
	
	public void flyForward(int cm){
		int speed = 5;
		System.out.println("Syg so 1");
		drone.getCommandManager().forward(speed); // omregnes til en mængde tid
		System.out.println("syg so 2");
		//mangler hastighed til at begrænse distancen
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
		}
		//drone.setCoords(calculateNewCoords(cm));
		
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
	public void rotateToAngle(int angle) {
		angle = angle%360;
		int aod; //amount of degrees
		aod = ((currentAngle-angle)+180)%360-180;
		if(aod < -180){
			aod += 360;
		}
		
		if(aod < 0){
			rotateRight(Math.abs(aod));
		}else if(aod > 0){
			rotateLeft(Math.abs(aod));
		}else {
			System.out.println("You are already there");
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
		int aot; //amount of time to go right
		drone.goRight();
	}
	
	/**
	 * 
	 */
	
	private void rotateLeft(int degrees) {
		int aot; //amount of time to go left
		drone.goLeft();
	}
}

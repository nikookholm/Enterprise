package Movements;

import java.rmi.server.ExportException;

import Common.Drone;
import POI.POI;
import de.yadrone.base.navdata.AcceleroListener;
import de.yadrone.base.navdata.AcceleroPhysData;
import de.yadrone.base.navdata.AcceleroRawData;
import de.yadrone.base.navdata.AttitudeListener;
import de.yadrone.base.navdata.BatteryListener;
import de.yadrone.base.navdata.GyroListener;
import de.yadrone.base.navdata.GyroPhysData;
import de.yadrone.base.navdata.GyroRawData;

public class DroneMovement implements iDroneMovement {
	
	private Drone drone;
	private int currentAngle = 0;
	
	GyroPhysData gyro; 
	float pitch;
	float roll;
	float yaw;
	
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
	
	
	public void flyForward()
	{
		try {
			throw new Exception("Fly forward er fucked!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void flyForward(int cm){
		int speed = 8;
		System.out.println("Syg so 1");
		drone.setSpeed(8);
		drone.forward();
		// omregnes til en mængde tid
		System.out.println("syg so 2");
		//mangler hastighed til at begrænse distancen
		try {
			Thread.sleep(cm*8);
		} catch (InterruptedException e) {
			
		}
		//drone.setCoords(calculateNewCoords(cm));
		
	}
	
	/**
	 * 
	 */
	@Override
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
			spinRight(Math.abs(aod));
		}else if(aod > 0){
			spinLeft(Math.abs(aod));
		}else {
			System.out.println("You are already there");
		}
		
	}
	
	@Override
	public void flyHome() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flyBackwards() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flyLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flyRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spinLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spinRight() {
		// TODO Auto-generated method stub
		
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
	
	private void spinRight(int degrees) {
		int aot; //amount of time to go right
		drone.goRight();
	}
	
	/**
	 * 
	 */
	
	private void spinLeft(int degrees) {
		int aot; //amount of time to go left
		drone.goLeft();
	}
	
	/**
	 * 
	 */
	
	private void hardRecover() {
		
	}
	
	private void softRecover() {
		
	}
	
	public enum Movement { Forward, Backwards, Left, Right, SpinLeft, SpinRight };

	@Override
	public void flyThroughRing(POI nextRing) {
		// TODO Auto-generated method stub
		
	}
	
	/*******************************/
	/*****Listener to interface*****/
	/*******************************/
	
	@Override
	public GyroListener getGyroListener() {
		return new Gyro();
	}

	@Override
	public AttitudeListener getAttitudeListener() {
		return new Attitude();
	}
	
	/**********************************/
	/**********class listener**********/
	/**********************************/
	
	public class Attitude implements AttitudeListener{

		@Override
		public void attitudeUpdated(float arg0, float arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void attitudeUpdated(float arg0, float arg1, float arg2) {
			pitch = arg0;
			roll = arg1;
			yaw = arg2;
			
		}

		@Override
		public void windCompensation(float arg0, float arg1) {
			// TODO Auto-generated method stub
			
		}

	}
	
	public class Gyro implements GyroListener{

		@Override
		public void receivedOffsets(float[] arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void receivedPhysData(GyroPhysData arg0) {
			gyro = arg0;
		}

		@Override
		public void receivedRawData(GyroRawData arg0) {
			// TODO Auto-generated method stub
			
		}
	
	}
	
}

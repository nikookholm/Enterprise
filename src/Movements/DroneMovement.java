package Movements;


import Common.Drone;
import POI.POI;
import Vector.Vector3D;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.navdata.AttitudeListener;
import de.yadrone.base.navdata.GyroListener;
import de.yadrone.base.navdata.GyroPhysData;
import de.yadrone.base.navdata.GyroRawData;

public class DroneMovement implements iDroneMovement {

	private Drone drone;
	private int currentAngle = 0;
	private int threadID = 0;
	private int amountOfThreads = 0;
	private CommandManager cmd;

	GyroPhysData gyro; 
	float pitch;
	float roll;
	float yaw;

	public DroneMovement(Drone drone)
	{
		this.drone = drone;
		cmd = drone.getCommandManager();
	}

	/**
	 * The hoverTo method sets the max height, to be sure how high it is allowed to flight.
	 * It then flies as high as possible and hovers until another command is given.
	 * @param height
	 * @return void
	 */
	public void hoverTo(int height) {
		cmd.setMaxAltitude(height);
		cmd.up(15);
	}

	public void start() {		
		
		cmd.flatTrim().doFor(100).takeOff().doFor(5000);
		hover();
	}
	
	// Flyv frem i bestemme distance (cm) 
	public void flyForwardConstant(int cm){
		cmd.schedule(0, new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0 ; i<cm ; i++){
					cmd.forward(20).doFor(2);
					updatePositionForward(1);
					if(i%100==0 && i!=0){
						cmd.hover().doFor(500);
					}
				}
			}
		});
		
	}

	// Flyv tilbage i bestemme distance (cm) 
	public void flyBackwardConstant(int cm){
		
		cmd.schedule(0, new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0 ; i<cm ; i++){
					cmd.backward(20).doFor(2);
					updatePositionBackward(1);
					if(i%100==0 && i!=0){
						cmd.hover().doFor(500);
					}
				}
			}
		});
	}
	
	// Flyv frem i bestemme distance (cm) 
	public void flyLeftConstant(int cm, int hoverTime){
		updatePositionLeft(cm);
		/** Mangler at finde konstant hastigheden for flyLeft**/
//		for(int i = 0 ; i<cm ; i++){
//			cmd.forward(20).doFor(2);
//			updatePositionForward(1);
//			if(i%100==0 && i!=0){
//				cmd.hover().doFor(500);
//			}
//		}

	}
	
	// Flyv frem i bestemme distance (cm) 
	public void flyRightConstant(int cm, int hoverTime){
		updatePositionRight(cm);
		/** Mangler at finde konstant hastigheden for flyRight**/
//		for(int i = 0 ; i<cm ; i++){
//			cmd.forward(20).doFor(2);
//			updatePositionForward(1);
//			if(i%100==0 && i!=0){
//				cmd.hover().doFor(500);
//			}
//		}
	}
	
	/**
	 * 
	 */
	@Override
	public void flyTo(POI interest) {
		int currX = (int) drone.getCoordX();
		int currY = (int) drone.getCoordY();
		int flyToX = (int) interest.getxPos();
		int flyToY = (int) interest.getyPos();
		int flyXDir = flyToX-currX;
		int flyYDir = flyToY-currY;
		cmd.schedule(0, new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(flyXDir==0 && flyYDir>0){
					rotateToAngle(0);
					flyForwardConstant(flyYDir);
				} else if(flyXDir==0 && flyYDir>0){
					
				} else if(flyXDir>0 && flyYDir==0){
					
				} else if(flyXDir<0 && flyYDir==0){
					
				} else if(flyXDir<0 && flyYDir<0){
					
				} else if(flyXDir<0 && flyYDir>0){
					
				} else if(flyXDir>0 && flyYDir<0){
					
				} else if(flyXDir>0 && flyYDir>0){
					
				} 
			}
		});
		
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
		currentAngle = angle;

	}

	@Override
	public void flyHome() {
		//mangler startcoordinates
		//mangler flytoCoordinate
	}

	//kører loop med scan ring, skal tage imod 
	@Override
	public POI flyThroughRing(POI nextRing) {
		return nextRing;
		// TODO Auto-generated method stub

	}
	
	@Override
	public void stopAndDecent() {
		// TODO Auto-generated method stub
		
	}

	///////////////////////////
	//Standard Flight Methods//
	public void flyForward(){
		int id = newThreadID();
		cmd.schedule(0, new Runnable(){
			@Override
			public void run() {
				drone.getMain().getGUI().getLog().add("id: " + id + ", threadID: " + threadID + ", flyv fremad!");
				drone.getMain().getGUI().getLog().add("Threads: " + amountOfThreads);
				while(id == threadID){
					cmd.forward(20).doFor(10);
				}
			}
		});
		
	}
	@Override
	public void flyBackward(){
		int id = newThreadID();
		
		cmd.schedule(0, new Runnable(){
			@Override
			public void run() {
				drone.getMain().getGUI().getLog().add("id: " + id + ", threadID: " + threadID + ", flyv bagud!");
				drone.getMain().getGUI().getLog().add("Threads: " + amountOfThreads);
				while(id == threadID){
					cmd.backward(20).doFor(10);
				}
			}
		});
	}
	@Override
	public void flyLeft(){

	}
	@Override
	public void flyRight(){
	}
	@Override
	public void spinLeft(){
	}
	@Override
	public void spinRight(){
	}
	@Override
	public void hover(){
		for (int i = 0 ; i<=150 ; i++){
			cmd.hover().doFor(30);
		}
	}

	@Override
	public void landing() {
		cmd.hover().doFor(500);
		cmd.landing();
		
	}

	/***********************************************************/
	/*********************private*******************************/
	/***********************************************************/

	private void forward(int doFor){
		cmd.forward(20).doFor(doFor);
	}
	private void backward(int doFor){
		cmd.backward(20).doFor(doFor);
	}
	private void left(int doFor){
		cmd.goLeft(20).doFor(doFor);
	}
	private void right(int doFor){
		cmd.goRight(20).doFor(doFor);
	}
	
	private void spinRight(int degrees){
		for(int i=0; i<degrees; i++){
			hover();
			if(i!=0 && i%10 == 0){
				hover();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			cmd.spinRight(20);
			try {
				Thread.sleep(28);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void spinLeft(int degrees) {
		for(int i=0; i<degrees; i++){
			hover();
			drone.spinLeft();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
	
	//ThreadControl
	private int newThreadID(){
		threadID++;
		amountOfThreads++;
		return threadID;
	}
	
	
	//Update position
	
	private Vector3D updateXY(int distance){
		Vector3D coordinates = new Vector3D(0,0,0);
		int angle = currentAngle;
		if(angle == 0){
			coordinates.setXCoord(0);
			coordinates.setYCoord(distance);
		} else if(angle == 90){
			coordinates.setXCoord(-(distance));
			coordinates.setYCoord(0);
		} else if(angle == 180){
			coordinates.setXCoord(0);
			coordinates.setYCoord(-(distance));
		} else if(angle == 270){
			coordinates.setXCoord(distance);
			coordinates.setYCoord(0);
		} else if(angle < 90){
			coordinates.setXCoord(-(Math.cos(angle)*distance));
			coordinates.setYCoord(Math.sin(angle)*distance);
		} else if(angle < 180){
			angle -= 90; 
			coordinates.setXCoord(-(Math.cos(angle)*distance));
			coordinates.setYCoord(-(Math.sin(angle)*distance));
			return coordinates;
		} else if(angle < 270) {
			angle -= 180;
			coordinates.setXCoord(Math.cos(angle)*distance);
			coordinates.setYCoord(-(Math.sin(angle)*distance));
		} else {
			angle -= 270;
			coordinates.setXCoord(Math.cos(angle)*distance);
			coordinates.setYCoord(Math.sin(angle)*distance);
		}
		return coordinates;
	}
	
	private void updatePositionForward(int distance){
		Vector3D coordinates = updateXY(distance);
		drone.incCoordX(coordinates.getXCoord());
		drone.incCoordY(coordinates.getYCoord());
	}
	
	private void updatePositionBackward(int distance){
		Vector3D coordinates = updateXY(distance);
		drone.incCoordX(-(coordinates.getXCoord()));
		drone.incCoordY(-(coordinates.getYCoord()));		
	}
	
	private void updatePositionLeft(int distance){
		Vector3D coordinates = updateXY(distance);
		drone.incCoordX(-(coordinates.getYCoord()));
		drone.incCoordY(-(coordinates.getXCoord()));		
	}
	
	private void updatePositionRight(int distance){	
		Vector3D coordinates = updateXY(distance);
		drone.incCoordX(coordinates.getYCoord());
		drone.incCoordY(coordinates.getXCoord());	
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

		}

		@Override
		public void attitudeUpdated(float arg0, float arg1, float arg2) {
			pitch = arg0;
			roll = arg1;
			yaw = arg2;

		}

		@Override
		public void windCompensation(float arg0, float arg1) {

		}

	}

	public class Gyro implements GyroListener{

		@Override
		public void receivedOffsets(float[] arg0) {
	
		}

		@Override
		public void receivedPhysData(GyroPhysData arg0) {
			gyro = arg0;
		}

		@Override
		public void receivedRawData(GyroRawData arg0) {
			
		}

	}
}

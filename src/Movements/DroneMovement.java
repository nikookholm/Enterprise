package Movements;


import Common.Drone;
import POI.POI;
import Vector.Vector3D;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.command.FlatTrimCommand;
import de.yadrone.base.command.HoverCommand;
import de.yadrone.base.command.TakeOffCommand;
import de.yadrone.base.navdata.AttitudeListener;
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
		drone.getCommandManager().up(15);
	}




	public void start() {		
		drone.getCommandManager().flatTrim().doFor(100);
		drone.getCommandManager().takeOff().doFor(2000);
		drone.getCommandManager().hover().doFor(5000);
	}
	
	
	
	

	// Flyv frem i bestemme distance (cm) 
	public void flyForwardConstant(int cm, int hoverTime){

		drone.getCommandManager().forward(10).doFor(5*cm);
		updatePositionForward(cm);
		drone.getCommandManager().hover().doFor(1000 * hoverTime);
		
	}

	// Flyv tilbage i bestemme distance (cm) 
	public void flyBackwardConstant(int cm, int hoverTime){

		drone.getCommandManager().forward(10).doFor(5*cm);
		updatePositionBackward(cm);
		drone.getCommandManager().hover().doFor(1000 * hoverTime);
		
//		drone.getCommandManager().freeze().doFor(3000);

	}
	
	// Flyv frem i bestemme distance (cm) 
	public void flyLeftConstant(int cm, int hoverTime){

		drone.getCommandManager().forward(10).doFor(5*cm);
		updatePositionLeft(cm);
		drone.getCommandManager().hover().doFor(1000 * hoverTime);
		
//		drone.getCommandManager().freeze().doFor(3000);

	}
	
	// Flyv frem i bestemme distance (cm) 
	public void flyRightConstant(int cm, int hoverTime){

		drone.getCommandManager().forward(10).doFor(5*cm);
		updatePositionRight(cm);
		drone.getCommandManager().hover().doFor(1000 * hoverTime);
		
//		drone.getCommandManager().freeze().doFor(3000);

	}
	
	/**
	 * 
	 */
	@Override
	public void flyTo(POI interest) {
		//mangler current coordinates
		//mangler fly to coordinates

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

	///////////////////////////
	//Standard Flight Methods//
	public void flyForward(){
	}
	@Override
	public void flyBackward(){

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
		drone.hover();
	}

	/***********************************************************/
	/*********************private*******************************/
	/***********************************************************/

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
			drone.spinRight();
			try {
				Thread.sleep(28);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */

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

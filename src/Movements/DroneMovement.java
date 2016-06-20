package Movements;


import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Common.Drone;
import Navigation.DroneVision;
import Navigation.OpenCVOperations;
import Navigation.iDroneVision;
import Navigation.iDroneVision.Condition;
import Navigation.iDroneVision.Movement;
import POI.POI;
import Vector.Vector3D;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.AttitudeListener;
import de.yadrone.base.navdata.GyroListener;
import de.yadrone.base.navdata.GyroPhysData;
import de.yadrone.base.navdata.GyroRawData;
import de.yadrone.base.video.ImageListener;

public class DroneMovement implements iDroneMovement {

	private Drone drone;
	private int currentAngle = 0;
	private int threadID = 0;
	private int amountOfThreads = 0;
	private CommandManager cmd;

	GyroPhysData gyro; 
	private float pitch;
	private float roll;
	private float yaw;
	private int altitude;
	private BufferedImage currentImage;
	OpenCVOperations opCV;

	public DroneMovement(Drone drone)
	{
		this.drone = drone;
		cmd = drone.getCommandManager();
		opCV = new OpenCVOperations();
	}
	
	

	/**
	 * The hoverTo method sets the max height, to be sure how high it is allowed to flight.
	 * It then flies as high as possible and hovers until another command is given.
	 * @param height
	 * @return void
	 */
	public void hoverTo(int height) {
		cmd.setMaxAltitude(height);
		cmd.up(30).doFor(1600);
	}
	
	public boolean calibrateToCircle(int cenX,double CircleX, int closeMargin, int farMargin,double distance){
		if(distance >3000){
		if(cenX + farMargin < CircleX){
			flyRightConstant(20, 50);
			System.out.println("RIGHT RIGHT RIGHT!!!");
		}
		if(cenX - farMargin > CircleX){
			System.out.println("LEFT LEFT LEFT!!!");
			flyLeftConstant(20, 50);
		}
		}
		else if(distance<3000){
			if(cenX + closeMargin < CircleX){
				flyRightConstant(20, 500);
				System.out.println("CLOSE RIGHT RIGHT RIGHT");
			}
			if(cenX - closeMargin > CircleX){
				flyLeftConstant(20, 500);
				System.out.println("CLOSE LEFT LEFT LEFT");
			}
			if(cenX > CircleX-closeMargin && cenX < CircleX+closeMargin){
				System.out.println("JACKPOT! GO!!!");
				return true;
			}
		}
		

		return false;
	}
	

	public Vector3D initialSearch(ArrayList<POI> poi)
	{
		
	cmd.setMinAltitude(1450);
	start();
	hoverTo(1500);
	spinRight();
	System.out.println(">>>>>>>>>> <<<<<<<<<<<<");
	Vector3D dronePos = drone.getNavigation().getVision().dronePosition(true, poi);
	
	System.out.println(dronePos.getXCoord() +"<<<---- X . Y---->>>"+ dronePos.getYCoord() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	drone.getMovement().landing();
	
	return dronePos;
	}
	
	public void start() {		
		
		cmd.takeOff();
		hover();
	}
	
	public void spinRight(int cm, int startTime){
		newThreadID();

		cmd.schedule(startTime,new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0; i<cm; i++){
					cmd.spinRight(30).doFor(30);
				}
				threadID--;
			}
		});
	}
	public void spinLeft(int cm, int startTime){
		newThreadID();

		cmd.schedule(startTime,new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0; i<cm; i++){
					cmd.spinLeft(30).doFor(30);
				}
				threadID--;
			}
		});
	}
	
	
	public void goUp(int cm, int startTime){
		newThreadID();

		cmd.schedule(startTime,new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0; i<cm; i++){
					cmd.up(30).doFor(30);
				}
				threadID--;
			}
		});
	}
	public void goDown(int cm, int startTime){
		newThreadID();

		cmd.schedule(startTime,new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0; i<cm; i++){
					cmd.down(30).doFor(30);
				}
				threadID--;
			}
		});
	}
	
	
	// Flyv frem i bestemme distance (cm) 
	public void flyForwardConstant(int cm, int startTime){
		newThreadID();
		cmd.schedule(startTime, new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0 ; i<cm ; i++){
					cmd.forward(5).doFor(20);
					updatePositionForward(1);
					if(i%100==0 && i!=0){
						cmd.hover().doFor(500);
					}
				}
				threadID--;
			}
		});
		
	}

	// Flyv tilbage i bestemme distance (cm) 
	public void flyBackwardConstant(int cm, int startTime){
		newThreadID();
		cmd.schedule(startTime, new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0 ; i<cm ; i++){
					cmd.backward(20).doFor(2);
					updatePositionBackward(1);
					if(i%100==0 && i!=0){
						cmd.hover().doFor(500);
					}
				}
				threadID--;
			}
		});
	}
	
	// Flyv frem i bestemme distance (cm) 
	public void flyLeftConstant(int cm, int startTime){
		newThreadID();
		cmd.schedule(startTime, new Runnable() {

			@Override
			public void run() {
				for(int i=0; i<cm; i++){
					cmd.goLeft(20).doFor(20);
					updatePositionBackward(1);
				}
				threadID--;
			}
		});
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
	public void flyRightConstant(int cm, int startTime){
		newThreadID();
		cmd.schedule(startTime, new Runnable() {

			@Override
			public void run() {
				for(int i=0; i<cm; i++){
					cmd.goRight(20).doFor(20);
					updatePositionBackward(1);
				}
				threadID--;
			}
		});
			updatePositionLeft(cm);
			
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
					
					rotateToAngle(0,0);
					tempFlight();
					flyForwardConstant(flyYDir, 0);
					tempFlight();
				
				} else if(flyXDir==0 && flyYDir<0){
					
					rotateToAngle(180,0);
					tempFlight();
					flyForwardConstant(flyYDir, 0);
					tempFlight();
					
				} else if(flyXDir>0 && flyYDir==0){
				
					rotateToAngle(270,0);
					tempFlight();
					flyForwardConstant(flyXDir, 0);
					tempFlight();
					
				} else if(flyXDir<0 && flyYDir==0){
					
					rotateToAngle(90,0);
					tempFlight();
					flyForwardConstant(flyXDir, 0);
					tempFlight();
					
				} else if(flyXDir<0 && flyYDir<0){
					
					rotateToAngle(90,0);
					tempFlight();
					flyForwardConstant(flyXDir, 0);
					tempFlight();
					rotateToAngle(180,0);
					tempFlight();
					flyForwardConstant(flyYDir, 0);
					tempFlight();
					
				} else if(flyXDir<0 && flyYDir>0){
					
					rotateToAngle(90,0);
					tempFlight();
					flyForwardConstant(flyXDir, 0);
					tempFlight();
					rotateToAngle(0,0);
					tempFlight();
					flyForwardConstant(flyYDir, 0);
					tempFlight();
					
				} else if(flyXDir>0 && flyYDir<0){
					
					rotateToAngle(270,0);
					tempFlight();
					flyForwardConstant(flyXDir, 0);
					tempFlight();
					rotateToAngle(180,0);
					tempFlight();
					flyForwardConstant(flyYDir, 0);
					tempFlight();
					
				} else if(flyXDir>0 && flyYDir>0){
					
					rotateToAngle(270,0);
					tempFlight();
					flyForwardConstant(flyXDir, 0);
					tempFlight();
					rotateToAngle(0,0);
					tempFlight();
					flyForwardConstant(flyYDir, 0);
					tempFlight();
					
				} 
			}
		});
		
	}

	/**
	 * 
	 */

	@Override
	public void rotateToAngle(int angle, int startTime) {
		angle = angle%360;
		int aod; //amount of degrees
		aod = ((currentAngle-angle)+180)%360-180;
		if(aod < -180){
			aod += 360;
		}
		final int aodFix = aod;

		if(aod < 0){
			cmd.schedule(startTime, new Runnable() {	
				@Override
				public void run() {
					turnLeft(Math.abs(aodFix));	
				}
			});
		}else if(aod > 0){
			cmd.schedule(startTime, new Runnable() {
				@Override
				public void run() {
					turnRight(Math.abs(aodFix));
				}
			});
		}else {
			System.out.println("You are already there");
		}
	}

	@Override
	public void flyHome() {
		//mangler startcoordinates
		//mangler flytoCoordinate
	}

	
	//kører loop med scan ring, skal tage imod 
	// metoderne blev ikke testet.
	@Override
	public void flyThroughRing() {
		System.out.println("thoughtringr1");
		
		
		Vector3D differen = opCV.findCircle(currentImage);
		
		Movement move = drone.getNavigation().getVision().calibrateToCircle(differen); // 
		int distanceToFly = 5; // in cm
		int millis = 0;
		System.out.println(move);
			switch (move) {
			
			case Up :
				goUp(distanceToFly, millis);
				tempFlight();
				
				break;
			
			case RightUp:
				flyRightConstant(distanceToFly, millis);
				tempFlight();
				goUp(distanceToFly, millis);
				tempFlight();
				break;
			
			case Right:
				flyRightConstant(distanceToFly, millis);
				tempFlight();
				break;
			
			case RightDown:
				flyRightConstant(distanceToFly, millis);
				tempFlight();
				goDown(distanceToFly, millis);
				tempFlight();
				break;
				
			case Down:
				goDown(distanceToFly, millis);
				tempFlight();
				break;
				
			case LeftDown:
				
				flyLeftConstant(distanceToFly, millis);
				tempFlight();
				goDown(distanceToFly, millis);
				tempFlight();
			
				break;
				
			case Left:
				flyLeftConstant(distanceToFly, millis);
				tempFlight();
				break;
	
			case LeftUp:
				flyLeftConstant(distanceToFly, millis);
				tempFlight();
				break;
				
			case Forward:
				flyForwardConstant(300, millis);
				tempFlight();
				break;
			case None:
				hover();
				break
						
			default:
				
				break;
			}
			
		
		

	}
	
	@Override
	public void stopAndDecent() {
		// TODO Auto-generated method stub
		
	}

	///////////////////////////
	//Standard Flight Methods//
	public void flyForward(){
		newThreadID();
		cmd.schedule(0, new Runnable(){
			@Override
			public void run() {
				for (int i = 0 ; i<=150 ; i++){
					cmd.forward(25).doFor(30);
				}
				threadID--;
			}
		});
	}
	@Override
	public void flyBackward(){
		newThreadID();

		cmd.schedule(0, new Runnable(){
			@Override
			public void run() {
				for (int i = 0 ; i<=150 ; i++){
					cmd.backward(25).doFor(30);
				}
				threadID--;
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
		newThreadID();
		cmd.schedule(0, new Runnable(){
			@Override
			public void run() {
				float a = yaw;
				System.out.println(a + " a <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				cmd.spinLeft(20).doFor(10);

				int i = 0;

				while(a!=yaw){
					if(i!=0 && i%100 == 0){
						cmd.hover().doFor(20);
					}
					System.out.println(yaw + " yaw <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

					cmd.spinLeft(10).doFor(10);
				}
			}
		});
		threadID--;
	}
	@Override
	public void spinRight(){
		newThreadID();
		cmd.schedule(0, new Runnable(){
			@Override
			public void run() {
				float a = yaw;
				System.out.println(a + " a <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				cmd.spinRight(20).doFor(10);

				int i = 0;

				while(a!=yaw){
					if(i!=0 && i%100 == 0){
						cmd.hover().doFor(20);
					}
					System.out.println(yaw + " yaw <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

					cmd.spinRight(10).doFor(10);
				}
			}
		});
		threadID--;
	}
	@Override
	public void hover(){
		cmd.schedule(0, new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0 ; i<=10 ; i++){
					cmd.hover().doFor(30);
				}		
			}
		});
		
	}

	@Override
	public void landing() {
		cmd.hover().doFor(500);
		cmd.landing();

	}

	public int getCurrentAngle(){
		return currentAngle;
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
	
	private void turnRight(int degrees){
		newThreadID();
		for(int i=0; i<degrees; i++){
			if(i!=0 && i%10 == 0){
				cmd.hover().doFor(20);
				
			}
			cmd.spinRight(20).doFor(35);
			currentAngle -= 1;
			if(currentAngle<0){
				currentAngle += 360;
			}
		}
		cmd.hover();
		threadID--;
	}

	private void turnLeft(int degrees) {
		newThreadID();
		for(int i=0; i<degrees; i++){
			if(i!=0 && i%10 == 0){
				cmd.hover().doFor(30);
				
			}
			cmd.spinLeft(25).doFor(30);
			currentAngle += 1;
			if(currentAngle>359){
				currentAngle -= 360;
			}
		}
		cmd.hover();
		threadID--;
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
	
	private void tempFlight(){
		int tempThreadID = threadID;
		while(tempThreadID == threadID){
			drone.getNavigation().getVision().scanQR(Condition.Flying);
		}
	}
	/*******************************/
	/*****Listener to interface*****/
	/*******************************/

	@Override
	public GyroListener getGyroListener() {
		return new Gyro();
	}
	@Override
	public ImageListener getImage(){
		return new imageList();
	}

	@Override
	public AttitudeListener getAttitudeListener() {
		return new Attitude();
	}
	
	private class imageList implements ImageListener{

		@Override
		public void imageUpdated(BufferedImage arg0) {
			currentImage = arg0;
		}
		
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
	
	public class Altitude implements AltitudeListener{

		@Override
		public void receivedAltitude(int arg0) {
			altitude = arg0;
		}

		@Override
		public void receivedExtendedAltitude(de.yadrone.base.navdata.Altitude arg0) {
			// TODO Auto-generated method stub
			
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

	@Override
	public void search() {
		
		rotateToAngle(90, 0);
		flyForwardConstant(100, 100);
		
	}
	public int getAltitude() {
		return altitude;
	}
	public float getPitch() {
		return pitch;
	}
	public float getRoll() {
		return roll;
	}
	public float getYaw() {
		return yaw;
	}
	
	public Vector3D onAQuestForCoordinates()
	{
		int i;
		int j;

		if(drone.getNavigation().getVision().dronePosition(true, null)!=null){
			flyLeftConstant(50, 0);
			spinRight();
			System.out.println("Looking for Position!!! test1");
			if(drone.getNavigation().getVision().dronePosition(true, null)!=null){
				flyRightConstant(200, 0);
				spinRight();
				System.out.println("Looking for Position!!! test2");
				if(drone.getNavigation().getVision().dronePosition(true, null)!=null){
					for(j = 0 ; j<4 ; j++){
						if(drone.getNavigation().getVision().dronePosition(true, null) != null){
							flyForwardConstant(200, 0);
							spinRight();
							System.out.println("Looking for Position!!! testForloop Kørt:" + j);
						}
					}
				}
				//			
				//			for(i = 0 ; i<3 ; i++){
				//				if(drone.getNavigation().getVision().dronePosition(true, null)!=null){
				////					flyForwardConstant(200, startTime);
				////					flyLeftConstant(200, startTime);
				////		 			rotate 360%
				//				}if(i<3){
				//					for(j = 0 ; j<4 ; j++){
				//						if(drone.getNavigation().getVision().dronePosition(true, null) != null){
				////							flyForwardConstant(200, startTime);
				//							//rotate 360%
				//						}
				//					}
				//				}
				//			}

			}else{
				System.out.println("position found");
			}
	}
		return drone.getNavigation().getVision().dronePosition(true, null);
}
}

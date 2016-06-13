package Movements;

import Common.Drone;
import Navigation.iDroneVision.Movement;

public class DroneMovementThread implements Runnable{
	Movement movement;
	boolean abort;
	//schedule is milliseconds the standard methods do stuff
	final int schedule = 500;
	DroneMovement droneMovement;
	
	public DroneMovementThread(Movement movement, Drone drone) {
		this.movement = movement;
		droneMovement = new DroneMovement(drone);
		abort = false;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		switch(movement){
		case Initial:
			while(!abort){		
				droneMovement.spinRight(schedule);
				try {
					Thread.sleep(schedule);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case Forward:
			while(!abort){		//schedule er en fastsat tid.
				droneMovement.flyForward(schedule);
				try {
					Thread.sleep(schedule);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			break;
		case Backward:
			while(!abort){		
				droneMovement.flyBackward(schedule);
				try {
					Thread.sleep(schedule);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case Left:
			while(!abort){		
				droneMovement.flyLeft(schedule);
				try {
					Thread.sleep(schedule);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			abort = false;
			break;
		case Right:
			while(!abort){		
				droneMovement.flyRight(schedule);
				try {
					Thread.sleep(schedule);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case SpinLeft:
			while(!abort){		
				droneMovement.spinLeft(schedule);
				try {
					Thread.sleep(schedule);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case SpinRight:
			while(!abort){		
				droneMovement.spinRight(schedule);
				try {
					Thread.sleep(schedule);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
			
		case None:
			break;
		default:
			//should not be used
			break;
				
		}
	}
	
	public void abort(){
		abort = true;
	}

}

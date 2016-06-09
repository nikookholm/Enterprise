package Movements;

import Common.Drone;
import Navigation.iDroneVision.Movement;

public class DroneMovementThread implements Runnable{
	Movement movement;
	boolean abort;
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
				droneMovement.spinRight();
				try {
					Thread.sleep(schedule);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case Forward:
			while(!abort){		
				droneMovement.flyForward();
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
				droneMovement.flyBackward();
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
				droneMovement.flyLeft();
				try {
					Thread.sleep(schedule);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case Right:
			while(!abort){		
				droneMovement.flyRight();
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
				droneMovement.spinLeft();
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
				droneMovement.spinRight();
				try {
					Thread.sleep(schedule);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
				
		}
	}
	
	public void abort(){
		abort = true;
	}

}

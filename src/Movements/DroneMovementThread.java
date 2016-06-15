package Movements;

import Common.Drone;
import Navigation.iDroneVision.Movement;

public class DroneMovementThread implements Runnable{
	Movement movement;
	boolean abort;
	DroneMovement droneMovement;
	
	public DroneMovementThread(Drone drone) {
//		this.movement = movement;  <<<<<<<<<<<<<<<<<<< skal slettes <<<<<<<<<<<<<<<<<<<<<<<<<

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
			}
			break;
		case Forward:
			while(!abort){		//schedule er en fastsat tid.
				droneMovement.flyForward();
			}
			
			break;
		case Backward:
			while(!abort){		
				droneMovement.flyBackward();
			}
			break;
		case Left:
			while(!abort){		
				droneMovement.flyLeft();
			}
			abort = false;
			break;
		case Right:
			while(!abort){		
				droneMovement.flyRight();
			}
			break;
		case RightDown:
			while (!abort) {
				
				
			}
		case SpinLeft:
			while(!abort){		
				droneMovement.spinLeft();
			}
			break;
		case SpinRight:
			while(!abort){		
				droneMovement.spinRight();
				
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

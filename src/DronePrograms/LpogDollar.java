package DronePrograms;

import Main.DroneProgram;
import Movements.DroneMovement;


public class LpogDollar extends DroneProgram {
	
	private DroneMovement droneMovement; 
	
	@Override
	public void abort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProgramName() {
		return "LP og DOLLAR holder!";
	}

	@Override
	public void run() {
		drone.hover();
		drone.getCommandManager().manualTrim(0, 0, 0);
		droneMovement.flyForward(4);
		
		
		
		
	}

}

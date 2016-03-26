package DronePrograms;

import Common.Drone;
import Main.DroneProgram;
import Main.IDroneProgram;

public class NikosDroneProgram extends DroneProgram {
	
	@Override
	public void run() {
		
		
		
		drone.takeOff();
		drone.hover();
		
		drone.up();
	}

	@Override
	public void abort() {
		// TODO Auto-generated method stub
		
	}
}

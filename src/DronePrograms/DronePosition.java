package DronePrograms;

import Common.Drone;
import Main.DroneProgram;
import Navigation.DroneVision;
import Vector.Vector3D;

public class DronePosition extends DroneProgram {

	@Override
	public void abort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProgramName() {
		return "Dronepositions test";
	}

	@Override
	public void run() {
		
		Drone d = getDrone();
		DroneVision v = d.getNavigation().getVision();
		
		d.getMovement().start();
		d.getMovement().spinRight();
		Vector3D dronePos = v.dronePosition(true);
		System.out.println(dronePos.getXCoord() +"<<<---- X . Y---->>>"+ dronePos.getYCoord() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}
	
}

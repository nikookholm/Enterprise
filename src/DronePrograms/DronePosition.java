package DronePrograms;

import Common.Drone;
import Main.DroneProgram;
import Navigation.DroneVision;
import Vector.Vector3D;

public class DronePosition extends DroneProgram {

	@Override
	public void abort() {
		getDrone().landing();
	}

	@Override
	public String getProgramName() {
		return "Dronepositions test";
	}

	@Override
	public void run() {
		
		Drone d = getDrone();
		d.getCommandManager().setMinAltitude(1450);
		DroneVision v = d.getNavigation().getVision();
		
		d.getMovement().start();
		d.getMovement().hoverTo(1500);
		d.getMovement().spinRight();
		Vector3D dronePos = v.dronePosition(true);
		System.out.println(dronePos.getXCoord() +"<<<---- X . Y---->>>"+ dronePos.getYCoord() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		d.getMovement().landing();
	}
	
}

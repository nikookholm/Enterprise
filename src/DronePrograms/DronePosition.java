package DronePrograms;

import Common.Drone;
import Main.DroneProgram;
import Navigation.DroneVision;
import Vector.Vector3D;
import de.yadrone.base.command.CommandManager;

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
		
		Vector3D dronePos = v.dronePosition(true);
		
	}

	
}

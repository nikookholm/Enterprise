package DronePrograms;

import java.util.ArrayList;

import Common.Drone;
import Main.DroneProgram;
import Main.Enterprise;
import Movements.DroneMovement;
import Navigation.DroneVision;
import POI.POI;
import Vector.Vector3D;
import de.yadrone.apps.controlcenter.plugins.keyboard.KeyboardCommandManager;

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
		
		DroneMovement dM = new DroneMovement(getDrone());
		dM.initialSearch(new ArrayList<POI>());
	
//		Drone d = getDrone();
//		KeyboardCommandManager keys = new KeyboardCommandManager(d);
//		d.getCommandManager().setMinAltitude(1450);
//		DroneVision v = d.getNavigation().getVision();
//		
//		d.getMovement().start();
//		d.getMovement().hoverTo(1500);
//		d.getMovement().spinRight();
//		Vector3D dronePos = v.dronePosition(true);
//		System.out.println(dronePos.getXCoord() +"<<<---- X . Y---->>>"+ dronePos.getYCoord() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		d.getMovement().landing();
	}
	
}

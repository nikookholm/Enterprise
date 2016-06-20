package DronePrograms;

import java.util.ArrayList;

import Common.Drone;
import Main.DroneProgram;
import Movements.iDroneMovement;
import Navigation.DroneVision;
import Navigation.iDroneVision.Condition;
import POI.POI;
import de.yadrone.base.command.CommandManager;

public class FlyThroughRingTest extends DroneProgram{
	
	@Override
	public String getProgramName() {
		return "Fly through ring v_1";
	}

	@Override
	public void run() {
		Drone d = getDrone();
		
		d.getMovement().start(); // Takeoff + Hover
		ArrayList<POI> foundObjects = new ArrayList<>();
		do{
		foundObjects = d.getNavigation().getVision().scanQR(Condition.CircleQR);
		}while(foundObjects.isEmpty());
		
		for(int i = 0; i < foundObjects.size(); i++){
			System.out.println(foundObjects.get(i).getCode());
		}
		
		d.getMovement().landing();
		
		
		
		
		
		
	}

	@Override
	public void abort() {
		
		
	}
}

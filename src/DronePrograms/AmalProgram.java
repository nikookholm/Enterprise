package DronePrograms;

import Main.DroneProgram;
import Movements.iDroneMovement;
import Navigation.iDroneVision;
import Navigation.iDroneVision.Condition;
import POI.POI;
import Vector.Vector3D;

public class AmalProgram extends DroneProgram {





	@Override
	public void abort() {
		getDrone().reset();
		getDrone().getMovement().landing();
	
	}

	@Override
	public String getProgramName() {
		return "AmalPro";
	}

	@Override
	public void run() {
		
		iDroneMovement mcmd = getDrone().getMovement();
		iDroneVision   vcmd = getDrone().getNavigation().getVision();
		
		mcmd.start();
		
		mcmd.hover();
		
		if(vcmd.scanQR(Condition.CircleQR) == null){
			
			mcmd.spinLeft();
		
		} else {
			
			Vector3D v = vcmd.dronePosition(true, super.getPOIList());
			
			
			mcmd.flyForwardConstant(cm, startTime);
		}
		
		
		mcmd.hoverTo(1500);
		
		mcmd.landing();
		
	}

}
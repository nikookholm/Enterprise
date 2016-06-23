package DronePrograms;

import Main.DroneProgram;
import Movements.iDroneMovement;
import Navigation.iDroneVision;
import Navigation.iDroneVision.Condition;

public class AmalProgram extends DroneProgram {

	@Override
	public void abort() {
		getDrone().reset();
		getDrone().getMovement().landing();
	
	}

	@Override
	public String getProgramName() {
		return "test start position";
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
			
		
		}
		
		
		mcmd.hoverTo(1500);
		
		mcmd.landing();
		
	}

}
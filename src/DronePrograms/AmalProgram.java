package DronePrograms;

import Main.DroneProgram;
import Movements.iDroneMovement;
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
		
		iDroneMovement dcmd = getDrone().getMovement();
	
		dcmd.start();
		
		dcmd.hoverTo(1500);
//		
//		do
//		{
//			test = getDrone().getNavigation().getVision().dronePosition(true);
//			dcmd.rotateToAngle(angle, 0);
//			angle += 5;
//		} while (test == null && angle < 90);
		
		//dcmd.flyRightConstant(100, 0);
		
		dcmd.landing();
		
	}

}
package DronePrograms;

import Main.DroneProgram;
import Movements.iDroneMovement;

public class AmalProgram extends DroneProgram {





	@Override
	public void abort() {
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
		
		dcmd.flyRightConstant(100, 0);
		
		dcmd.landing();
		
	}

}
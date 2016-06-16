package DronePrograms;

import Main.DroneProgram;


public class LpogDollar extends DroneProgram {

	@Override
	public void run() {

		System.out.println(" Starter start ><");
		getDrone().getMovement().start();
		System.out.println("Kalder rotate(90)");
		
		getDrone().getMovement().rotateToAngle(90, 100);
		while(getDrone().getMovement().getCurrentAngle()!=270){
			System.out.println(getDrone().getMovement().getCurrentAngle());
		}

		getDrone().getMovement().landing();
	
	}
	@Override
	public void abort() {
		// TODO Auto-generated method stub
	}
	@Override
	public String getProgramName() {
		// TODO Auto-generated method stub
		return "rotate test";
	}
}


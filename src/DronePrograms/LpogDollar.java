package DronePrograms;

import Main.DroneProgram;


public class LpogDollar extends DroneProgram {

	@Override
	public void run() {

		System.out.println(" Starter start ><");
		getDrone().getMovement().start();
		System.out.println("Kalder rotate(90)");
		
		getDrone().getMovement().rotateToAngle(90, 0);
		while(getDrone().getMovement().getCurrentAngle()!=90){
			getDrone().getMain().getGUI().getLog().add("" + getDrone().getMovement().getCurrentAngle());
		}
		getDrone().getMovement().flyForwardConstant(50, 0);
		while(getDrone().getCoordY()<50){
			getDrone().getMain().getGUI().getLog().add("+1");
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
		return "rotate fly test";
	}
}


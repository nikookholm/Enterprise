package DronePrograms;

import Main.DroneProgram;


public class LpogDollar extends DroneProgram {

	@Override
	public void run() {
		try {
		System.out.println(" Starter start ><");
		getDrone().getMovement().start();
		System.out.println("Kalder rotate(90)");
		getDrone().getMovement().rotateToAngle(90, 0);

		Thread.sleep(2500);
	
		System.out.println("Kalder rotate(270)");
		getDrone().getMovement().rotateToAngle(270, 0);		

		Thread.sleep(2500);
		
		System.out.println("Kalder rotate(0)");
		getDrone().getMovement().rotateToAngle(0, 0);

		Thread.sleep(2500);
		
		System.out.println("bør være på start pos");
		getDrone().getMovement().landing();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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


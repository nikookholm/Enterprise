package DronePrograms;

import Main.DroneProgram;


public class LpogDollar extends DroneProgram {

	@Override
	public void run() {
		
		System.out.println(" Starter staaaet ><");
		getDrone().getMovement().start();
		System.out.println(" Kalder fly forward");
		getDrone().getMovement().flyForward();
		try {
			System.out.println("starter sleep til forward");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(" parat til fly backward");
		getDrone().getMovement().flyBackward();
		try {
			System.out.println("starter sleep til backwards");
			Thread.sleep(5000);
		
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		System.out.println(" parat til fly backward");
		getDrone().getMovement().landing();
	}
	@Override
	public void abort() {
		// TODO Auto-generated method stub
	}
	@Override
	public String getProgramName() {
		// TODO Auto-generated method stub
		return "schedule thread test";
	}
}


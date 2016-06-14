package DronePrograms;

import Main.DroneProgram;


public class LpogDollar extends DroneProgram {

	@Override
	public void run() {

		getDrone().getMovement().start();
		getDrone().getMovement().flyForward();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getDrone().getMovement().flyBackward();
		
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


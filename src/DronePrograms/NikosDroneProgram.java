package DronePrograms;

import Main.DroneProgram;

public class NikosDroneProgram extends DroneProgram {
	
	int lel;
	@Override
	public void run() {
		lel = 1;
		drone.start();
		drone.takeOff();
		drone.getCommandManager().doFor(5000);
		drone.setMaxAltitude(lel);
		
		System.out.println(">>><<<<<<<<<<< altitude" + lel);
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		drone.landing();
		
	}

	@Override
	public void abort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProgramName() {
		return "Nikos test program";
	}

}

package DronePrograms;

import Main.DroneProgram;

public class NikosDroneProgram extends DroneProgram {
	
	@Override
	public void run() {
		

		drone.getCommandManager().setMaxAltitude(1);
		drone.takeOff();
		drone.getCommandManager().hover();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		drone.getCommandManager().landing();
		drone.stop();
		
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

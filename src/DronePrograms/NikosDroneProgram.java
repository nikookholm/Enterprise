package DronePrograms;

import Main.DroneProgram;

public class NikosDroneProgram extends DroneProgram {
	
	@Override
	public void run() {
		//drone.getCommandManager().setVideoBitrate(800); // du må meget gerne teste den og se om den gør kammereat bedre!

		drone.getCommandManager().setMaxAltitude(1);
		drone.getCommandManager().takeOff();
		drone.getCommandManager().hover();
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		drone.getCommandManager().landing();
		drone.getCommandManager().stop();
		
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

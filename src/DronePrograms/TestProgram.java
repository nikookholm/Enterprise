package DronePrograms;

import Common.Drone;
import Main.DroneProgram;

public class TestProgram extends DroneProgram {
	
	

	@Override
	public void abort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		
		Drone d = getDrone();
		
		d.getCommandManager().setMaxAltitude(1);
		d.getCommandManager().takeOff().doFor(5000);
		
		// Tricky part
		
		d.getCommandManager().hover().doFor(5000);
		d.getCommandManager().manualTrim(0, 0, 0);
		//d.getCommandManager().freeze();
		
		d.getMovement().flyForward(200);
		
		d.getCommandManager().hover().doFor(5000);
		d.getCommandManager().manualTrim(0, 0, 0);
		//d.getCommandManager().freeze()
		
		d.getCommandManager().landing();
		d.getCommandManager().stop();
		
	}

	@Override
	public String getProgramName() {
		return "Proof of Concept!";
	}

}

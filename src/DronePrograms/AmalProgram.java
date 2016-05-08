package DronePrograms;

import java.time.LocalTime;

import Main.DroneProgram;

public class AmalProgram extends DroneProgram {
	
	
	

	@Override
	public void abort() {
		getDrone().getCommandManager().landing().doFor(2000);
	}

	@Override
	public String getProgramName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		getDrone().getCommandManager().setMaxAltitude(1);
		getDrone().getCommandManager().takeOff().doFor(2000);
		getDrone().getCommandManager().hover().doFor(2000);
		getDrone().getCommandManager().manualTrim(0, 0, 0).doFor(2000);
	
		getDrone().getMovement().flyForward(500);

		getDrone().getCommandManager().manualTrim(0, 0, 0).doFor(2000);
		getDrone().getCommandManager().hover().doFor(2000);
		
	}

}

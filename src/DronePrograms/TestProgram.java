package DronePrograms;

import de.yadrone.base.command.CommandManager;
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
		CommandManager cmd = d.getCommandManager();
		
		cmd.setMaxAltitude(3);
		cmd.takeOff().doFor(7000);
		
		cmd.hover().doFor(3000);
	
		d.getMovement().flyForward(200);
		
		cmd.hover().doFor(2000);
		d.getMovement().rotateToAngle(180);
		
		d.getMovement().flyForward(200);

		cmd.hover().doFor(2000);		
		cmd.landing();
		
	}

	@Override
	public String getProgramName() {
		return "Proof of Concept!";
	}

}

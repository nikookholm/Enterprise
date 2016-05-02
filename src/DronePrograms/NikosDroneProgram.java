package DronePrograms;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import Main.DroneProgram;
import de.yadrone.base.command.FlightAnimation;

public class NikosDroneProgram extends DroneProgram {
	
	static LocalDateTime date = LocalDateTime.now();
	
	@Override
	public void run() {
		
		getDrone().getCommandManager().setMaxAltitude(1);
		getDrone().getCommandManager().takeOff().doFor(2000);
		getDrone().getCommandManager().hover().doFor(2000);
		getDrone().getCommandManager().manualTrim(0, 0, 0).doFor(2000);
		
		LocalTime førTid = date.toLocalTime().now();
		getDrone().getMovement().flyForward(500);
		LocalTime EfterTid = date.toLocalTime().now();

		System.out.println(førTid  + " <-FØR || EFTER->" + EfterTid);
		getDrone().getCommandManager().manualTrim(0, 0, 0).doFor(2000);
		getDrone().getCommandManager().hover().doFor(2000);
		
	}

	@Override
	public void abort() {
		getDrone().getCommandManager().landing().doFor(2000);

	}

	@Override
	public String getProgramName() {
		return "Anwar test program";
	}

}

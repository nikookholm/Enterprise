package DronePrograms;

import java.time.LocalTime;
import java.util.Date;

import de.yadrone.base.command.ATCommand;
import de.yadrone.base.command.CalibrationCommand;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.command.Device;
import de.yadrone.base.command.FlatTrimCommand;
import de.yadrone.base.command.HoverCommand;
import de.yadrone.base.command.TakeOffCommand;
import Common.Drone;
import Main.DroneProgram;

public class AmalProgram extends DroneProgram {




	@Override
	public void abort() {
		getDrone().getCommandManager().landing().doFor(2000);
	}

	@Override
	public String getProgramName() {
		return "AmalPro";
	}

	@Override
	public void run() {
		
		CommandManager cmd = getDrone().getCommandManager();
		
		cmd.setOutdoor(false, false);
		
		cmd.setMaxAltitude(1);
		
//		cmd.flatTrim().doFor(1000);
		
		cmd.setCommand(new FlatTrimCommand()).doFor(100);
		
		cmd.setCommand(new TakeOffCommand()).doFor(3000);
		
		cmd.setCommand(new HoverCommand()).doFor(1000);
		
//		cmd.takeOff().doFor(3000);
		
//		cmd.hover().doFor(3000);
		
		cmd.setCommand(new CalibrationCommand(Device.MAGNETOMETER)).doFor(5000);
		
		cmd.setMaxAltitude(20000).doFor(500);
		
		cmd.up(10).doFor(700);

		cmd.freeze().doFor(2000);
		
		cmd.spinLeft(50).doFor(1000);
		
//		getDrone().getMovement().flyForward(20);

		cmd.landing();

	
	}

}
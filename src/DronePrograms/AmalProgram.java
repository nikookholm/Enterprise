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
		
		cmd.takeOff();
		
		cmd.schedule(3000, new Runnable() {
			
			@Override
			public void run() {
				cmd.hover();
			}
		});
		
		cmd.schedule(7000, new Runnable() {
			public void run() {
				cmd.up(5);
			}
		});
		
		cmd.schedule(5000, new Runnable() {
			public void run() {
				cmd.forward(6);
			}
		});
		
		cmd.schedule(4000, new Runnable() {
			public void run() {
				cmd.down(3);
			}
		});
		
		cmd.landing();
		
		
		
//		cmd.setMaxAltitude(2000);
//		cmd.manualTrim(0, 0, 0);
//		cmd.takeOff();
//		cmd.setCommand(new CalibrationCommand(Device.MAGNETOMETER)).doFor(3000);
//		cmd.freeze().doFor(5000);
//		cmd.hover().waitFor(10000);
//		cmd.down(3);
		cmd.landing();
	
		
//		getDrone().start();
//		
//		cmd.setOutdoor(false, false);
//
//		cmd.setMaxAltitude(1).doFor(1000);
//		
//		cmd.setCommand(new FlatTrimCommand()).doFor(100);
//		
//		cmd.setCommand(new TakeOffCommand()).doFor(3000);
//		
////		cmd.freeze().doFor(2000);
//		cmd.setCommand(new HoverCommand()).doFor(1000);
////		cmd.manualTrim(0, 0, 0);
//		
//		cmd.setCommand(new CalibrationCommand(Device.MAGNETOMETER)).doFor(3000);

			
//		cmd.forward(10).doFor(2000);
//		getDrone().getMovement().flyForward(400);
//		cmd.freeze().doFor(2000);
//		cmd.hover();
		
//		cmd.setMaxAltitude(1).doFor(20000);
//		
//		cmd.up(5);
//		
//		cmd.hover().doFor(1000);
//		cmd.freeze().doFor(2000);
//		cmd.down(3);
//		cmd.landing();
//		
//		System.out.println("end <<<<<<<<<<<<<<<<<<<<<<<<<<<<");


	
	}

}
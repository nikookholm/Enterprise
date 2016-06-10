package DronePrograms;

import de.yadrone.base.command.ATCommand;
import de.yadrone.base.command.CalibrationCommand;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.command.Device;
import de.yadrone.base.command.FlatTrimCommand;
import de.yadrone.base.command.LandCommand;
import de.yadrone.base.command.VideoCodec;
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
		cmd.flatTrim();
		
		cmd.setCommand(new FlatTrimCommand());
		cmd.setCommand(new LandCommand());
		
		cmd.setMaxAltitude(1);
		cmd.takeOff();
		cmd.setOutdoor(true, true);
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("hej!");
		CalibrationCommand cal = new CalibrationCommand(Device.MAGNETOMETER);
		cmd.setCommand(cal);
		System.out.println("hej<<<<<<<<<<<<<<<<<!");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cmd.landing();
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
//		cmd.setMaxAltitude(3);
//		cmd.takeOff().doFor(7000);
//		
//		cmd.hover().doFor(3000);
//	
//		d.getMovement().flyForward(200);
//		
//		cmd.hover().doFor(2000);
//		d.getMovement().rotateToAngle(180);
//		
//		d.getMovement().flyForward(200);
//
//		cmd.hover().doFor(2000);		
//		cmd.landing();
		
	}

	@Override
	public String getProgramName() {
		return "Proof of Concepthva?!";
	}

}

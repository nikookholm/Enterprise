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
		int speed = 2;
		
		
		
		getDrone().getCommandManager().takeOff().doFor(5000);
		
		getDrone().getCommandManager().goLeft(speed).doFor(1000);
		getDrone().getCommandManager().hover().doFor(2000);

		getDrone().getCommandManager().goRight(speed).doFor(1000);
		getDrone().getCommandManager().hover().doFor(2000);

		getDrone().getCommandManager().forward(speed).doFor(2000);
		getDrone().getCommandManager().hover().doFor(1000);

		getDrone().getCommandManager().backward(speed).doFor(2000);
		getDrone().getCommandManager().hover().doFor(2000);

		getDrone().getCommandManager().landing();
		
		
//		
//		getDrone().getCommandManager().takeOff().doFor(4000);
//		getDrone().getCommandManager().schedule(15000, new Runnable() {
//			
//			
//			public void run() {
//				getDrone().getCommandManager().spinLeft(5).doFor(2000);}
//		});
//		getDrone().getCommandManager().schedule(3000, new Runnable() {
//			
//			
//			public void run() {
//				getDrone().getCommandManager().spinRight(5).doFor(2000);
//			}
//		});
//		getDrone().getCommandManager().schedule(3000, new Runnable() {
//			
//			
//			public void run() {
//				getDrone().getCommandManager().forward(5).doFor(3000);
//			}
//		});
//		getDrone().getCommandManager().schedule(3000, new Runnable() {
//			
//			
//			public void run() {
//				getDrone().getCommandManager().hover();
//			}
//		});
//		getDrone().getCommandManager().schedule(3000, new Runnable() {
//			
//			
//			public void run() {
//				getDrone().getCommandManager().landing();
//			}
//		});
//		
//		
//		
//		getDrone().getCommandManager().setMaxAltitude(1).takeOff().doFor(4000).hover().doFor(2000);
//		getDrone().getCommandManager().forward(8).doFor(4000).hover().doFor(2000);
//		getDrone().getCommandManager().backward(8).doFor(4000).hover().doFor(2000).landing().doFor(2000);

//		getDrone().getCommandManager().hover();
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			
//		}
//		getDrone().getCommandManager().manualTrim(0, 0, 0);
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			
//		}
//		
//		LocalTime førTid = date.toLocalTime().now();
//		getDrone().getMovement().flyForward(500);
//		LocalTime EfterTid = date.toLocalTime().now();
//
//		System.out.println(førTid  + " <-FØR || EFTER->" + EfterTid);
//		getDrone().getCommandManager().hover().doFor(1000);
//		getDrone().getCommandManager().manualTrim(0, 0, 0).doFor(1000);
		
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

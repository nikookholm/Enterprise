package DronePrograms;

import java.util.Date;
import Main.DroneProgram;
import Movements.DroneMovement;
import de.yadrone.base.command.VideoCodec;


public class LpogDollar extends DroneProgram {
	private int sleeper = 3000;
	private Date date;
	boolean qrfin = false;
	
	@Override
	public void abort() {
		drone.landing();
		
	}

	@Override
	public String getProgramName() {
		return "LP og DOLLAR holder!";
	}

	@Override
	public void run() {
		System.out.println("test1");
		
		drone.setMaxAltitude(1);
		drone.getCommandManager().takeOff().doFor(3000);
		drone.getCommandManager().hover().doFor(2000);	
		drone.getCommandManager().manualTrim(0, 0, 0);
		try {
			Thread.sleep(2*sleeper);
		} catch (InterruptedException e) {
		}
//		System.out.println("test1");
//		drone.getCommandManager().setMaxVideoBitrate(4000).doFor(2000);
//		System.out.println("test2");
//		drone.getCommandManager().setVideoBitrate(4000).doFor(2000);
//		System.out.println("test3");
//		drone.getCommandManager().setVideoCodec(VideoCodec.H264_720P).doFor(3000);
//		System.out.println("test4");
		//drone.getMovement().flyForward(10);
		//drone.getCommandManager().manualTrim(0, 0, 0);
		System.out.println("Drone Manual trim done getting time...");
		long startTime = date.getTime();
		System.out.println("nuværende tidspunkt " + startTime);
		
		drone.getMovement().flyForward(3);
		
		long endTime = date.getTime();
		System.out.println("slut tidspunkt " + endTime);
		
		Date time = new Date();
		
		time.setTime(endTime-startTime);
		System.out.println("tid i sekunder " + time.getSeconds());

		drone.landing();
	}

}

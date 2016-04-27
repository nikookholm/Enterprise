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
		//long startTime = date.getTime();
	//	System.out.println("starttidspunkt " + startTime);
		
		drone.setMaxAltitude(1);
		drone.getCommandManager().takeOff().doFor(3000);
		drone.getCommandManager().hover().doFor(2000);	
		
		drone.getCommandManager().manualTrim(0, 0, 0);
		try {
			Thread.sleep(1*sleeper);
		} catch (InterruptedException e) {
		}
		System.out.println("test1");
		drone.getCommandManager().setMaxVideoBitrate(4000).doFor(2000);
		System.out.println("test2");
		drone.getCommandManager().setVideoBitrate(4000).doFor(2000);
		System.out.println("test3");
		drone.getCommandManager().setVideoCodec(VideoCodec.H264_720P).doFor(3000);
		System.out.println("test4");
	

		//drone.getMovement().flyForward(10);
		//drone.getCommandManager().manualTrim(0, 0, 0);
		
		try {
			Thread.sleep(2*sleeper);
		} catch (InterruptedException e) {
		}
		
		//drone.setMinAltitude(2);
		try {
			Thread.sleep(2*sleeper);
		} catch (InterruptedException e) {
		}
		
		
		//drone.getMovement().flyForward(10);
		
		drone.getCommandManager().hover();
		
		drone.getCommandManager().manualTrim(0, 0, 0);
		try {
			Thread.sleep(3*sleeper);
		} catch (InterruptedException e) {
		}
		

		//long startTime = date.getTime();

//		drone.getMovement().flyForward(3);
		System.out.println("hej2");

		//long endTime = date.getTime();
//		Date taken = new Date();
//		System.out.println("Start tid " + startTime);
//		
//		System.out.println("Slut tid " + endTime);
//		taken.setTime(endTime-startTime);
//		System.out.println("----->>>>>>" + taken.getSeconds());
		//drone.landing();
		try {
			Thread.sleep(2*sleeper);
		} catch (InterruptedException e) {
		}
	}

}

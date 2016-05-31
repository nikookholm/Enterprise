package DronePrograms;

import java.util.Date;

import Common.Drone;
import Main.DroneProgram;
import Movements.DroneMovement;
import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.VideoCodec;


public class LpogDollar extends DroneProgram {
	private static int sleeper = 3000;
	private static Date date;
	boolean qrfin = false;
	static Drone drone = new Drone();
	
	public static void main(String[] args)
	{
		IARDrone iDrone = null;
	    try
	    {
	    	LpogDollar a = new LpogDollar();
	    	a.run();
	    }
	    catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally
		{
			if (iDrone != null)
				iDrone.stop();
			System.exit(0);
		}
	}
//	@Override
//	public void abort() {
//		drone.landing();
//		
//	}
//
//	@Override
//	public String getProgramName() {
//		return "LP og DOLLAR holder!";
//	}
	public void run() {
		try{
			DroneMovement dM = new DroneMovement(drone);
				
	    	drone.start();
			drone.takeOff();
			Thread.sleep(5000);
			System.out.println("start hover");
			//initial hover
			dM.hover();
			Thread.sleep(500);
			//forward 0.5
			System.out.println("start forward");
			dM.flyForward();
			Thread.sleep(1500);
			//hover bf new command .5
			System.out.println("start hover");
			dM.hover();
			Thread.sleep(500);
			System.out.println("start spin");
			dM.rotateToAngle(180);
			System.out.println("hover");
			dM.hover();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("end");
		
		drone.landing();
	}
@Override
public void abort() {
	// TODO Auto-generated method stub
	
}
@Override
public String getProgramName() {
	// TODO Auto-generated method stub
	return null;
}

}

// kode LP har flyttet for at teste


//System.out.println("test1");
//
//drone.setMaxAltitude(1);
//drone.getCommandManager().takeOff().doFor(3000);
//drone.getCommandManager().hover().doFor(2000);	
//drone.getCommandManager().manualTrim(0, 0, 0);
//try {
//	Thread.sleep(2*sleeper);
//} catch (InterruptedException e) {
//}
////System.out.println("test1");
////drone.getCommandManager().setMaxVideoBitrate(4000).doFor(2000);
////System.out.println("test2");
////drone.getCommandManager().setVideoBitrate(4000).doFor(2000);
////System.out.println("test3");
////drone.getCommandManager().setVideoCodec(VideoCodec.H264_720P).doFor(3000);
////System.out.println("test4");
////drone.getMovement().flyForward(10);
////drone.getCommandManager().manualTrim(0, 0, 0);
//System.out.println("Drone Manual trim done getting time...");
//long startTime = date.getTime();
//System.out.println("nuværende tidspunkt " + startTime);
//
//drone.getMovement().flyForward(400);
//
//long endTime = date.getTime();
//System.out.println("slut tidspunkt " + endTime);
//
//Date time = new Date();
//
//time.setTime(endTime-startTime);
//System.out.println("tid i sekunder " + time.getSeconds());


package DronePrograms;

import java.util.Date;
import Main.DroneProgram;
import Movements.DroneMovement;


public class LpogDollar extends DroneProgram {
	
	private Date date;
	private DroneMovement droneMovement; 
	
	@Override
	public void abort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProgramName() {
		return "LP og DOLLAR holder!";
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		drone.takeOff();
		drone.setMaxAltitude(1);
		drone.hover();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		drone.getCommandManager().manualTrim(0, 0, 0);
		long startTime = date.getTime();
		droneMovement.flyForward(400);
		long endTime = date.getTime();
		Date taken = new Date();
		taken.setTime(endTime-startTime);
		System.out.println("----->>>>>>" + taken.getSeconds());
		drone.landing();
	}

}

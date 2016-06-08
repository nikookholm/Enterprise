package DronePrograms;

import java.time.LocalTime;
import java.util.Date;

import Common.Drone;
import Main.DroneProgram;

public class AmalProgram extends DroneProgram {




	@Override
	public void abort() {
		getDrone().getCommandManager().landing().doFor(2000);
	}

	@Override
	public String getProgramName() {
		// TODO Auto-generated method stub
		return "AmalPro";
	}

	@Override
	public void run() {
		
		
	
		getDrone().getCommandManager().setMaxAltitude(1);
		getDrone().getCommandManager().takeOff().doFor(2000);
		
		getDrone().getCommandManager().hover();
		
//		long startTime = System.currentTimeMillis();
		
		getDrone().getCommandManager().manualTrim(0, 0, 0).doFor(3000);
		
//		long endTime = System.currentTimeMillis();
		
		
		
//		getDrone().getCommandManager().freeze().doFor(3000);
		
		long startTime = System.currentTimeMillis();
//	
//				
		getDrone().getMovement().flyForward(300);
//	
		long endTime = System.currentTimeMillis();
//		
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  " + (endTime - startTime) + "ms   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		getDrone().getCommandManager().landing();
	
	}

}
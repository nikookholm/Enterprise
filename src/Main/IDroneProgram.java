package Main;

import Common.Drone;


public interface IDroneProgram extends Runnable {

	public Drone		 drone = new Drone();
	public static String name  = "Default drone program";
	public void   		 abort();
	
	
}
package Main;

import Common.Drone;


public interface IDroneProgram extends Runnable {
	
	
	private Drone drone;
	public static String name = "Default drone program";
	public void abort();
	
	
}

package Main;

import Common.Drone;
import GUI.DroneGUI;

public class Enterprise {
	
	DroneGUI gui;
	Drone	 drone;

	private void initialize() {
		 gui   = new DroneGUI();
		 drone = new Drone();
	}	
	
	public Enterprise()
	{
		initialize();
	}
	
	private void runProgram(DroneProgram program)
	{
		
	}
	
	

}

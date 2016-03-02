package Main;

import Common.Drone;
import GUI.DroneGUI;

public class Enterprise {
	
	DroneGUI 	 gui;
	Drone	 	 drone;
	DroneProgram program;
	

	
	public Enterprise()
	{
		initialize();
	}
	
	private void initialize() {
		 gui   = new DroneGUI(this);
		 drone = new Drone();
	}	
	
	private void setProgram(DroneProgram program)
	{
		this.program = program;
	}
	
	private void runProgram()
	{
		program.run();
	}
	
	private void stopProgram()
	{
		program.abort();
	}
	
	private Drone getDrone()
	{
		return drone;
	}
	
}

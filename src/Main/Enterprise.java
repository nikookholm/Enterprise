package Main;

import java.util.ArrayList;
import Common.Drone;
import GUI.DroneGUI;

public class Enterprise {
	
	private DroneGUI 	 gui;
	private Drone	 	 drone;
	private DroneProgram activeProgram;
	
	ArrayList<DroneProgram> programs;
	
	public Enterprise()
	{
		initialize();
	}
	
	private void initialize() {
		 gui   = new DroneGUI(this);
		 drone = new Drone();
		 
		 loadDronePrograms();
	}	
	
	private void loadDronePrograms() {
		
		programs = new ArrayList<>();
		
		programs.add(new TestProgram());
		
	}
	
	private void startProgram(DroneProgram program)
	{
		activeProgram = program;
		activeProgram.start(drone);
	}
	
	private void stopProgram()
	{
		activeProgram.abort();
	}
	
	private Drone getDrone()
	{
		return drone;
	}
	
	public ArrayList<DroneProgram> getDronePrograms()
	{
		return programs;  
	}
	
}
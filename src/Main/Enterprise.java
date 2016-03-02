package Main;

import java.util.ArrayList;

import Common.Drone;
import DronePrograms.TestProgram;
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
		 
		 drone = new Drone();
		 
		 loadDronePrograms();
		 
		 gui   = new DroneGUI(this);
	}	
	
	private void loadDronePrograms() {
		
		programs = new ArrayList<>();
		
		programs.add(new TestProgram());
		
	}
	
	public void startProgram(DroneProgram program)
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
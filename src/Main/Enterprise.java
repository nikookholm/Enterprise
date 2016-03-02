package Main;

import java.util.ArrayList;
import java.util.List;

import de.yadrone.apps.controlcenter.YADroneControlCenter;
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
		
		programs.add(new TestProgram("Test program"));
		
	}

	private void setProgram(DroneProgram program)
	{
		this.activeProgram = program;
	}
	
	private void runProgram()
	{
		activeProgram.run();
	}
	
	private void stopProgram()
	{
		activeProgram.abort();
	}
	
	private Drone getDrone()
	{
		return drone;
	}
	
}
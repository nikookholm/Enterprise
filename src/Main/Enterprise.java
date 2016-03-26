package Main;

import java.util.ArrayList;

import Common.Drone;
import DronePrograms.NikosDroneProgram;
import DronePrograms.TestProgram;
import GUI.DroneGUI;
import GUI.iDroneGUI;

public class Enterprise {
	
	private iDroneGUI 	 gui;
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
		 
		 gui = new DroneGUI();
		 gui.initialize(this);
		 
		 attachDroneHandlers();
	}
	
	private void attachDroneHandlers()
	{
		drone.getNavDataManager().addAltitudeListener(gui.getAltitudeListener());
		drone.getNavDataManager().addVideoListener(gui.getVideoListener());
		
		//drone.getVideoManager().addImageListener(drone.getNavigation().getVision().getImageListener());
		
	}
	
	
	private void loadDronePrograms()
	{
		programs = new ArrayList<>();
		programs.add(new NikosDroneProgram());
		
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
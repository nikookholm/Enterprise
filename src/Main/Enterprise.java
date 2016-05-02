package Main;

import java.util.ArrayList;

import Common.Drone;
import DronePrograms.AmalProgram;
import DronePrograms.LpogDollar;
import DronePrograms.NikosDroneProgram;
import DronePrograms.RotationTestProgram;
import DronePrograms.TestProgram;
import GUI.DroneGUI;
import GUI.iDroneGUI;

public class Enterprise {
	
	private iDroneGUI 	 			gui;
	private Drone	 	 			drone;
	private DroneProgram 			activeProgram;
	private ArrayList<DroneProgram> programs;
	
	public Enterprise()
	{
		initialize();
	}
	
	private void initialize()
	{
		 drone = new Drone();
		 
		 loadDronePrograms();
		 
		 gui = new DroneGUI();
		 gui.initialize(this);
		 
		 attachDroneHandlers();
		 
		 try {
			 drone.start();
		 } catch (Exception e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
	}
	
	private void attachDroneHandlers()
	{
		drone.getVideoManager().addImageListener(gui.getImageListener());
		//drone.getNavDataManager().addBatteryListener (gui.getBatteryListener());
		drone.getNavDataManager().addAltitudeListener(gui.getAltitudeListener());
		drone.getNavDataManager().addAcceleroListener(gui.getAcceleroListener());
	}
	
	
	private void loadDronePrograms()
	{
		programs = new ArrayList<>();
		programs.add(new NikosDroneProgram());
		programs.add(new TestProgram());
		//programs.add(new LpogDollar());
		programs.add(new RotationTestProgram());
		programs.add(new AmalProgram());
	}
	
	public void startProgram(DroneProgram program)
	{
		activeProgram = program;
		activeProgram.start(drone);		
	}
	
	public void stopProgram()
	{
		activeProgram.abort();
	}
	
	public Drone getDrone()
	{
		return drone;
	}
	
	public ArrayList<DroneProgram> getDronePrograms()
	{
		return programs;  
	}
	
}
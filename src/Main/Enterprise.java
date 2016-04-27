package Main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.xuggle.xuggler.io.InputOutputStreamHandler;

import de.yadrone.base.video.ImageListener;
import Common.Drone;
import DronePrograms.LpogDollar;
import DronePrograms.NikosDroneProgram;
import DronePrograms.RotationTestProgram;
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
	
	private void initialize()
	{
		 drone = new Drone();
		 
		 loadDronePrograms();
		 
		 gui = new DroneGUI();
		 gui.initialize(this);
		 
		 attachDroneHandlers();
		 
		 drone.start();
	}
	
	private void attachDroneHandlers()
	{
		drone.getVideoManager().addImageListener(gui.getImageListener());
	}
	
	
	private void loadDronePrograms()
	{
		programs = new ArrayList<>();
		programs.add(new NikosDroneProgram());
		programs.add(new TestProgram());
		programs.add(new LpogDollar());
		programs.add(new RotationTestProgram());
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
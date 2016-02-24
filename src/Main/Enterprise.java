package Main;

import Common.Drone;
import GUI.DroneGUI;

public class Enterprise {
	
	DroneGUI gui;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new Enterprise(new Drone(), new IDroneProgram() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void abort() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void initialize() {
		 gui = new DroneGUI();
		
		
	}
	public Enterprise(Drone drone, IDroneProgram program)
	{
		
	}

}

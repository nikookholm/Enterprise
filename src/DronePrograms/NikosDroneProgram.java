package DronePrograms;

import javax.swing.plaf.SliderUI;

import Common.Drone;
import Main.DroneProgram;
import Main.IDroneProgram;

public class NikosDroneProgram extends DroneProgram {
	
	@Override
	public void run() {
		
		System.out.println("They see me dronin', they hatin'...");
		drone.start();
		
		drone.setMaxAltitude(1);
		

		drone.takeOff();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		drone.landing();
		drone.stop();
		
		
		
	}

	@Override
	public void abort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProgramName() {
		return "Nikos test program";
	}

}

package Main;

import Common.Drone;

public abstract class DroneProgram implements IDroneProgram {

	private String name;
	private Drone  drone;
	
	public String getName()
	{
		return name + "HARDCODED";
	}
	
	public void start(Drone drone)
	{
		this.drone = drone;
		this.run();
	}
	
	
	
}

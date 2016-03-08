package Main;

import Common.Drone;

public abstract class DroneProgram implements IDroneProgram {
	
	private Drone drone;
	
	public String getName()
	{
		return name;
	}
	
	public void start(Drone drone)
	{
		this.drone = drone;
		this.run();
	}
	
	
}

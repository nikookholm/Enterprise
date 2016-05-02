package Main;

import Common.Drone;

public abstract class DroneProgram implements IDroneProgram {
	
	private Drone drone;
	
	public void start(Drone drone)
	{
		this.drone = drone;
		
		try
		{
			this.run();
		}
		catch (Exception e)
		{
			System.err.println("Cauht EXCEPTION on STARTUP");
			e.printStackTrace();
			drone.landing();
			drone.stop();
		}
	}
	
	public Drone getDrone()
	{
		return drone;
	}
	
	
}

package Main;

import java.util.ArrayList;

import Common.Drone;
import POI.POI;

public abstract class DroneProgram implements IDroneProgram {
	
	private Drone drone;
	private ArrayList<POI> poiList;
	
	public void start(Drone drone)
	{
		this.drone = drone;
		this.poiList = new ArrayList<POI>();
		
		try
		{
			this.run();
		}
		catch (Exception e)
		{
			System.err.println("Caught EXCEPTION on STARTUP");
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

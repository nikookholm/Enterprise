package Navigation;

import de.yadrone.base.command.CommandManager;
import Common.Drone;
import Common.POI;

public class DroneNavigation implements iDroneNavigation {
	
	private DroneSensors sensors;
	private DroneVision  vision;
	
	private Drone drone;
	
	public DroneNavigation(Drone drone) {
		initialize(drone);
		
	}
	
	private void initialize(Drone drone)
	{
		this.drone   = drone;
		this.sensors = new DroneSensors(drone);
		this.vision  = new DroneVision(drone); 
	}

	@Override
	public DroneSensors getSensors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DroneVision getVision() {
		return null;
	}
	

}

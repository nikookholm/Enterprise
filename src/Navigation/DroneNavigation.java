package Navigation;

public class DroneNavigation implements iDroneNavigation {
	
	private static DroneSensors sensors = new DroneSensors();
	private static DroneVision  vision  = new DroneVision();
	
	
	@Override
	public DroneSensors getSensors() {
		return sensors;
	}
	
	@Override
	public DroneVision getVision() {
		return vision;
	}
	
	

}

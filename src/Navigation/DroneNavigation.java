package Navigation;

import Common.POI;

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

	@Override
	public void hoverTo(int height) {
		/*
		 * The method sets the max height, to be sure how high it is allowed to flight
		 */
		
	}

	@Override
	public void flyTo(POI interest) {
		// TODO venter på billedebehandling
		
	}

	@Override
	public void rotateRight(int degrees) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotateLeft(int degrees) {
		// TODO Auto-generated method stub
		
	}
	
	

}

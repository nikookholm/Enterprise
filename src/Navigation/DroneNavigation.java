package Navigation;

import Common.Drone;
import Common.POI;

public class DroneNavigation implements iDroneNavigation {
	
	private static DroneSensors sensors = new DroneSensors();
	private static DroneVision  vision  = new DroneVision();
	

	/**
	 * 
	 */
	@Override
	public DroneSensors getSensors() {
		return sensors;
	}
	
	/**
	 * 
	 */
	@Override
	public DroneVision getVision() {
		return vision;
	}
	
	/**
	 * The hoverTo method sets the max height, to be sure how high it is allowed to flight.
	 * It then flies as high as possible and hovers until another command is given.
	 * @param height
	 * @return void
	 */
	@Override
	public void hoverTo(int height) {
		CommandManager commands = new drone.getCommanManager();
		commands.setMaxAltitude(height);
		commands.getCommandManager.up();
	}
	
	/**
	 * 
	 */
	@Override
	public void flyTo(POI interest) {
		// TODO venter på billedebehandling
	
	}
	
	/**
	 * 
	 */
	@Override
	public void rotateRight(int degrees) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 */
	@Override
	public void rotateLeft(int degrees) {
		// TODO Auto-generated method stub
	
	}
}

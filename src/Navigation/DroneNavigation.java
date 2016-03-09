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
		CommandManager commands = drone.getCommandManager();
		commands.setMaxAltitude(height);
		//commands.up();
	}
	
	/**
	 * 
	 */
	@Override
	public void flyTo(POI interest) {
		// TODO venter p� billedebehandling
	
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

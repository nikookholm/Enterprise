package Movements;

import Common.Drone;
import Common.POI;
import Navigation.DroneSensors;
import Navigation.DroneVision;
import de.yadrone.base.command.CommandManager;

public class DroneMovement implements iDroneMovement {

	private Drone drone;
	
	public DroneMovement(Drone drone)
	{
		this.drone = drone; 
	}
	
	/**
	 * The hoverTo method sets the max height, to be sure how high it is allowed to flight.
	 * It then flies as high as possible and hovers until another command is given.
	 * @param height
	 * @return void
	 */
	
	public void hoverTo(int height) {
		CommandManager commands = drone.getCommandManager();
		commands.setMaxAltitude(height);
		//commands.up();
	}
	
	/**
	 * 
	 */
	
	public void flyTo(POI interest) {
		// TODO venter p� billedebehandling
	
	}
	
	/**
	 * 
	 */
	
	public void rotateRight(int degrees) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 */
	
	public void rotateLeft(int degrees) {
		// TODO Auto-generated method stub
	
	}
}

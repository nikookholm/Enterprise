package Common;

import Movements.DroneMovement;
import Navigation.DroneNavigation;

public class Drone {
	
	private DroneNavigation navigation;
	private DroneMovement   movement;

	public Drone()
	{
		
	}
	
	public DroneNavigation getNavigation()
	{
		return navigation;
	}
	
	public DroneMovement getMovement()
	{
		return movement;
	}
	
}

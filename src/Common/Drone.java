package Common;

import de.yadrone.base.ARDrone;
import Movements.DroneMovement;
import Movements.iDroneMovement;
import Navigation.DroneNavigation;
import Navigation.iDroneNavigation;

public class Drone extends ARDrone {
	
	private static final iDroneNavigation navigation = new DroneNavigation();
	private static final iDroneMovement   movement   = new DroneMovement();

	public Drone() 
	{
		super();
	}
	
	public iDroneNavigation getNavigation()
	{
		return navigation;
	}
	
	public iDroneMovement getMovement()
	{
		return movement;
	}
	
}

package Common;

import de.yadrone.base.ARDrone;
import de.yadrone.base.navdata.HDVideoStreamData;
import de.yadrone.base.navdata.NavDataManager;
import de.yadrone.base.navdata.VideoListener;
import de.yadrone.base.navdata.VideoStreamData;
import de.yadrone.base.video.xuggler.XugglerDecoder;
import Movements.DroneMovement;
import Movements.iDroneMovement;
import Navigation.DroneNavigation;
import Navigation.iDroneNavigation;

public class Drone extends ARDrone {
	
	private iDroneNavigation navigation;
	private iDroneMovement   movement;
	
	private double x, y, z;

	public Drone() 
	{
		super("192.168.1.1", new XugglerDecoder());
	}
	
	public double getCoordX()
	{
		return x;
	}
	
	public double incCoordX(double inc)
	{
		return x + inc;
	}
	
	public double getCoordY()
	{
		return y;
	}
	
	public double incCoordY(double inc)
	{
		return y + inc;
	}
	
	public double getCoordZ()
	{
		return z;
	}
	
	public double incCoordZ(double inc)
	{
		return z + inc;
	}
	
	private void initialize()
	{
		this.navigation = new DroneNavigation(this);
		this.movement   = new DroneMovement(this);
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

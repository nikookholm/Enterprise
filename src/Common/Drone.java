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
	
	private float x, y, z;
	private float angle = 0;

	public Drone() 
	{
		super("192.168.1.1", new XugglerDecoder());
	}
	
	public float getCoordX()
	{
		return x;
	}
	
	public float incCoordX(float inc)
	{
		return x + inc;
	}
	
	public float getCoordY()
	{
		return y;
	}
	
	public float incCoordY(float inc)
	{
		return y + inc;
	}
	
	public float getCoordZ()
	{
		return z;
	}
	
	public float incCoordZ(float inc)
	{
		return z + inc;
	}
	
	public float getAngle()
	{
		return z;
	}
	
	public float incAngle(float inc)
	{
		return angle + inc;
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

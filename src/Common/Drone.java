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
	
	private int x, y, z;
	private int angle;

	public Drone() 
	{
		super("192.168.1.1", new XugglerDecoder());
	}
	
	public int getCoordX()
	{
		return x;
	}
	
	public int incCoordX(int inc)
	{
		return x + inc;
	}
	
	public int getCoordY()
	{
		return y;
	}
	
	public int incCoordY(int inc)
	{
		return y + inc;
	}
	
	public int getCoordZ()
	{
		return z;
	}
	
	public int incCoordZ(int inc)
	{
		return z + inc;
	}
	
	public int getAngle()
	{
		return z;
	}
	
	public int incAngle(int inc)
	{
		return angle + inc;
	}
	
	private void initialize()
	{
		x = 0;
		y = 0;
		z = 0;
		
		angle = 0;
		
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

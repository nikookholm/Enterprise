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
import Vector.Vector3D;

public class Drone extends ARDrone {
	
	private iDroneNavigation navigation;
	private iDroneMovement   movement;
	
	private Vector3D coords;
	private int 	 angle;

	public Drone() 
	{
		super("192.168.1.1", new XugglerDecoder());
		initialize();
	}
	
	public double getCoordX()
	{
		return coords.getXCoord();
	}
	
	public double incCoordX(double inc)
	{
		coords.
		return x + inc;
	}
	
	public int getCoordY()
	{
		return coords.getXCoord();
	}
	
	public int incCoordY(int inc)
	{
		return y + inc;
	}
	
	public int getCoordZ()
	{
		return coords.getXCoord();
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
		coords = new Vector3D();
		angle  = 0;
		
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

package Common;

import de.yadrone.base.ARDrone;
import de.yadrone.base.navdata.HDVideoStreamData;
import de.yadrone.base.navdata.NavDataManager;
import de.yadrone.base.navdata.VideoListener;
import de.yadrone.base.navdata.VideoStreamData;
import de.yadrone.base.video.xuggler.XugglerDecoder;
import Main.IDroneProgram;
import Movements.DroneMovement;
import Movements.iDroneMovement;
import Navigation.DroneNavigation;
import Navigation.iDroneNavigation;
import Vector.Vector3D;

public class Drone extends ARDrone {
	
	private iDroneNavigation navigation;
	private iDroneMovement   movement;
	
	private Vector3D coords;
	private double 	 angle;

	public Drone() 
	{
		super("192.168.1.1", new XugglerDecoder());
		initialize();
	}
	
	public void setCoords(Vector3D newCoords)
	{
		coords = newCoords;
	}
	
	public double getCoordX()
	{
		return coords.getXCoord();
	}
	
	public double incCoordX(double inc)
	{
		return coords.getXCoord() + inc;
	}
	
	public double getCoordY()
	{
		return coords.getYCoord();
	}
	
	public double incCoordY(double inc)
	{
		return coords.getYCoord() + inc;
	}
	
	public double getCoordZ()
	{
		return coords.getZCoord();
	}
	
	public double incCoordZ(double inc)
	{
		return coords.getZCoord() + inc;
	}
	
	public double getAngle()
	{
		return angle;
	}
	
	public double incAngle(double inc)
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

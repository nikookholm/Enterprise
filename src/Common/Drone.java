package Common;

import de.yadrone.base.ARDrone;
import de.yadrone.base.navdata.HDVideoStreamData;
import de.yadrone.base.navdata.NavDataManager;
import de.yadrone.base.navdata.VideoListener;
import de.yadrone.base.navdata.VideoStreamData;
import de.yadrone.base.video.xuggler.XugglerDecoder;
import Main.Enterprise;
import Main.IDroneProgram;
import Movements.DroneMovement;
import Movements.iDroneMovement;
import Navigation.DroneNavigation;
import Navigation.iDroneNavigation;
import Vector.Vector3D;

public class Drone extends ARDrone {
	
	private iDroneNavigation navigation;
	private iDroneMovement   movement;
	
	private IDroneProgram	 activeProgram;
	
	private Vector3D coords;
	private double 	 angle;
	
	private Enterprise main;

	public Drone(Enterprise main) 
	{
		super("192.168.1.1", new XugglerDecoder());
		initialize(main);
	}
	
	public void setCoords(Vector3D newCoords)
	{
		coords = newCoords;
		main.getGUI().updateCoordsPanel();
	}
	
	public double getCoordX()
	{
		return coords.getXCoord();
	}
	
	public double incCoordX(double inc)
	{
		coords.setXCoord(coords.getXCoord() + inc);
		main.getGUI().updateCoordsPanel();
		return coords.getXCoord();
	}
	
	public double getCoordY()
	{
		return coords.getYCoord();
	}
	
	public double incCoordY(double inc)
	{
		coords.setYCoord(coords.getYCoord() + inc);
		main.getGUI().updateCoordsPanel();
		return coords.getYCoord();
	}
	
	public double getCoordZ()
	{
		return coords.getZCoord();
	}
	
	public double incCoordZ(double inc)
	{
		coords.setZCoord(coords.getZCoord() + inc);
		main.getGUI().updateCoordsPanel();
		return coords.getZCoord();
	}
	
	public double getAngle()
	{
		return angle;
	}
	
	public double incAngle(double inc)
	{
		angle = angle + inc;
		main.getGUI().updateCoordsPanel();
		return angle;
	}
	
	private void initialize(Enterprise main)
	{
		coords = new Vector3D();
		angle  = 0;
		
		this.navigation = new DroneNavigation(this);
		this.movement   = new DroneMovement(this);
		this.main		= main;
	}
	
	public iDroneNavigation getNavigation()
	{
		return navigation;
	}
	
	public iDroneMovement getMovement()
	{
		return movement;
	}
	
	public Enterprise getMain()
	{
		return main;
	}
	
}

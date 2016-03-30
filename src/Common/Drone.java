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

	public Drone() 
	{
		super("192.168.1.1", new XugglerDecoder());
		
	}
	
	private void initialize()
	{
		this.navigation = new DroneNavigation(this);
		this.movement   = new DroneMovement(this);
		
		try
		{
			super.start();
		}
		catch (Exception e)
		{
			System.err.println("Exception caugt, should stop!");
			down();
			this.stop();
		}
		finally
		{
			this.stop();
		}
		
		
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

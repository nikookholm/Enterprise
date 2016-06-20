package Main;

import java.util.ArrayList;

import Common.Drone;
import POI.POI;


public interface IDroneProgram extends Runnable {

	public static String name  = "Default drone program";
	public void   		 abort();
	public String getProgramName();
	public ArrayList<POI> getPOIList();
	
}
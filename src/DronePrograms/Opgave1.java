package DronePrograms;

import java.util.ArrayList;

import Main.DroneProgram;
import Movements.DroneMovement;
import Movements.iDroneMovement;
import Navigation.iDroneNavigation;
import POI.POI;
import POI.POICircle;
import POI.POIWallPoint;

public class Opgave1 extends DroneProgram {

	private ArrayList<POI> pois;
	private POI			   nextRing = null;						

	@Override
	public void abort() {
		getDrone().landing();

	}
	@Override
	public String getProgramName() {
		return "Opgave1 ";
	}
	@Override
	public void run() {

		// Opgave 1 Beskrivelse:
		// 
		// For at kunne vurdere prstationen, bliver der talt portpassager (points). En
		// passage er afsluttet, nar en drone helt krydser hulahopringen i den rigtige retning.
		// Ringerne skal passeres i fastlagt cyklisk rkkeflge. Dronen starter fra et markeret
		// landingsplads og returnere dertil. Hvert sammenstd mellem en drone og et andet
		// objekt tller minus 2 points. Hvert portpassager i den forkerte retning eller rk-
		// keflge tller minus 1 point. At ikke returnere til landingspladsen, tller minus 2
		// points. Tiden er 5 minutter.
		
		


		iDroneMovement   m = getDrone().getMovement();
		iDroneNavigation n = getDrone().getNavigation();
		
		m.start();										// Flyver op til 1 meter
		
		while (!hasFound3WallPOIs())						// Så længe at den ikke har fundet sine to WallPOI's
		{
			n.getVision().search();							// Afsøg rum
		}
		
		getDrone().setCoords(n.getVision().calibrate());	// Når de er fundet og loop-et stopper, bruges calibrate()
															// til at fastsætte dronens position
		
		while (!finished())									// Så længe opgaven ikke er færdig ....							
		{
			while(nextRingIsInList())						// ... og den næste ring vi skal finde er på listen
			{
				m.flyTo(nextRing);							// flyv til den næste ring
				m.flyThroughRing(nextRing);					// ... og igennem den
			}
			
			n.getVision().scan();							// Har den ikke ringen på listen, så scan()-igen.
		}
		
		m.flyHome();										// Og flyv hjem
		
															// All good!
				
	
	}
	
	private boolean nextRingIsInList() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	private boolean finished() {
		
		// Været igennem alle ringe?
		
		// Returneret hjem ?
		
		// Success! 
		
		return false;
		
		 
	}
	private boolean hasFound3WallPOIs() {
		
		int wallPoisFound = 0;
		
		for (POI poi : pois )
		{
			if (poi instanceof POIWallPoint)
			{
				wallPoisFound++;
			}
		}
		
		return (wallPoisFound >= 2);
	}


}

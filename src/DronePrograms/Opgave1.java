package DronePrograms;

import java.util.ArrayList;

import Main.DroneProgram;
import Movements.DroneMovement;
import Movements.iDroneMovement;
import Navigation.iDroneNavigation;
import POI.POI;
import POI.POICircle;

public class Opgave1 extends DroneProgram {

	private ArrayList<POICircle> rings;
	private ArrayList<POI> 		 pois;
	private POICircle 			 closestRing;

	@Override
	public void abort() {
		// TODO Auto-generated method stub

	}
	@Override
	public String getProgramName() {
		return "Opgave 1 er the shit!";
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
		
		
		
		
		
		
		// Kalibrer / find dronens position !
		
		// while POIWanted is in POIList 
		
		// Scan()
		
		
		// Foreach POI fundet ved scan
		
		// Hvis den næste ring er i blandt POI's fundet, flyv der hen
		
		
		
		
		
		
	}


}

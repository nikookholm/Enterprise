package DronePrograms;

import java.util.ArrayList;

import Main.DroneProgram;
import Movements.iDroneMovement;
import Navigation.iDroneNavigation;
import POI.POI;

public class Opgave1 extends DroneProgram {

	private ArrayList<POI> pois			 = new ArrayList<>();
	private POI			   nextRing 	 = null;	
	private final int 	   numberOfRings = 3;

	@Override
	public void abort() {
		getDrone().landing();
	}
	
	@Override
	public String getProgramName() {
		return "Opgave1";
	}
	
	@Override
	public void run() {

		// Opgave 1 Beskrivelse:
		// 
		// For at kunne vurdere præstationen, bliver der talt portpassager (points). En
		// passage er afsluttet, når en drone helt krydser hulahopringen i den rigtige retning.
		// Ringerne skal passeres i fastlagt cyklisk rækkeflge. Dronen starter fra et markeret
		// landingsplads og returnere dertil. Hvert sammenstød mellem en drone og et andet
		// objekt tæller minus 2 points. Hvert portpassager i den forkerte retning eller ræk-
		// keflge tæller minus 1 point. At ikke returnere til landingspladsen, tæller minus 2
		// points. Tiden er 5 minutter.


		iDroneMovement   m = getDrone().getMovement();
		iDroneNavigation n = getDrone().getNavigation();

		m.start();
		m.hoverTo(1500); // Er i milimeter
		
		m.initialSearch(pois);
		
		while (!finished())
		{
			
			if (nextRingIsInList())							// Kigger om den næste ring er i listen
			{
				m.flyTo(nextRing);							// flyv til den næste ring
				m.flyThroughRing(nextRing);					// ... og igennem den
			}
			else
			{
				m.search();									// Afsøg rum efter flere ringe
			}
			
		}
		
		m.flyHome();										// Flyv tilbage til start pos
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
}

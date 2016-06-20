package DronePrograms;

import java.util.ArrayList;
import Common.Drone;
import Main.DroneProgram;
import Movements.iDroneMovement;
import Navigation.iDroneNavigation;
import POI.POI;
import POI.POICircle;
import Vector.Vector3D;

public class Opgave1 extends DroneProgram {

	private ArrayList<POI> pois			 = new ArrayList<>();
	private final int 	   numberOfRings = 3;
	private int			   ringsPassed   = 0;
	private POICircle	   nextRing 	 = null;

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


		Drone			 d = getDrone();
		iDroneMovement   m = d.getMovement();
		iDroneNavigation n = d.getNavigation();

		m.start();											// Take of
		m.hoverTo(1500); // Er i milimeter					// Op i højde til at læse QR-koder
		
		while (m.onAQuestForCoordinates() == null)
		{
			
		}
		
		getDrone().setCoords(m.initialSearch(pois));		// Drejer på stedet og finder sin position
		
		while (!finished())
		{
			
			if (nextRingIsInList())							// Kigger om den næste ring er i listen
			{
				m.flyTo(nextRing);							// flyv til den næste ring
				m.flyThroughRing(nextRing);					// ... og igennem den
				countUpRings();
			}
			else
			{
				m.search();									// Afsøg rum efter flere ringe
			}
			
		}
		
		m.flyHome();										// Flyv tilbage til start pos
	}
		
	private void countUpRings()
	{
		ringsPassed++;				
	}

	private boolean nextRingIsInList() {
		
		boolean nextCircleFound = false;
		
		for (POI p : pois)
		{
			if (p instanceof POICircle)
			{
				if (((POICircle)p).getCode().equals("P.0" + (ringsPassed+1)))
				{
					nextRing = (POICircle)p;
					nextCircleFound = true;
				}
			}
		}
		
		return nextCircleFound;
	}

	private boolean finished()
	{
		return (ringsPassed >= numberOfRings) ? true : false;
		
	}
}

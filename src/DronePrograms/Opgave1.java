package DronePrograms;

import java.util.ArrayList;

import Main.DroneProgram;
import Movements.DroneMovement;
import Movements.iDroneMovement;
import Navigation.DroneVision;
import Navigation.OpenCVOperations;
import Navigation.QRfinder;
import Navigation.iDroneNavigation;
import Navigation.CircleSequence.CircleSequence;
import POI.POI;
import POI.POICircle;
import POI.POIWallPoint;
import Vector.Vector3D;

public class Opgave1 extends DroneProgram {

	private ArrayList<POI> pois;
	private POI			   nextRing = null;	
	private final int 	   numberOfRings = 3;
	private DroneVision    vision;

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
		//OpenCVOperations o = new OpenCVOperations();

		m.start();
		m.hoverTo(1500); // Er i milimeter
		
		n.getVision().initialSearch(pois);
		
		while (!finished())
		{
			
			if (nextRingIsInList())
			{
				
			}
			else
			{
				n.getVision().search();
			}
			
		}
		
		m.landing();
		
		
		// (næsten) Oprindelig opgave 1 herunder.

		m.start();											 // Flyver op til 2 meter
		
		m.rotateToAngle(90, 100);
		
		Vector3D crd =  n.getVision().dronePosition(true); 	 // Afsøg rum

		getDrone().setCoords(crd);	  						 // Når de er fundet og loop-et stopper, bruges calibrate()
		
		if(crd != null){
			int nrOfRingFound = 0;
			
			while(nrOfRingFound < 7){
				
				
			}
		}
		
//		getDrone().getMovement().search();
//															// til at fastsætte dronens position
//
//		while (!finished())									// Så længe opgaven ikke er færdig ....							
//		{
//			while(nextRingIsInList())						// ... og den næste ring vi skal finde er på listen
//			{
//				m.flyTo(nextRing);							// flyv til den næste ring
//				m.flyThroughRing(nextRing);					// ... og igennem den
//			}
//
//		}
//
//		m.flyHome();										// Og flyv hjem

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
	private boolean hasFound2WallPOIs() {

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

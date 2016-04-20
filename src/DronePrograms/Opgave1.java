package DronePrograms;

import java.util.ArrayList;

import Main.DroneProgram;
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


		int holesToDo       = 4;
		int holesPenetrated = 0;
		while (holesPenetrated < holesToDo)
		{
			pois = drone.getNavigation().scan();
			for(POI poi : pois)
			{
				if (poi instanceof POICircle)
				{
					rings.add((POICircle)poi);
					if (((POICircle)poi).getQRValue().equals(holesPenetrated))
					{
						drone.getNavigation().flyTo(poi);
					}

				}
			}
		}
		
		drone.getNavigation().flyToHome();
	}


}

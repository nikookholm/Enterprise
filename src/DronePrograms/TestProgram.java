package DronePrograms;

import Common.Drone;
import Main.DroneProgram;

public class TestProgram extends DroneProgram {
	
	

	@Override
	public void abort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		
		//List<POI> pois = drone.scan();      -- Flyver op og ser alt hvad der kan genkende fra dens position.
		//drone.calibrate();				  -- Kalibrerer i forhold til mærkerne på væggen
		
		//drone.flyTo(pois[0]);				  -- Flyver til X,Y,Z position for den fundne POI
		
		//drone.returnHome();				  -- Bruger sit interne koordinat system til at flyve tilbage til hvor den startede.

	}

	@Override
	public String getProgramName() {
		return "Proof of Concept!";
	}

}

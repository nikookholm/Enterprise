package Movements;

import POI.POI;

public interface iDroneMovement {
	
	public void hoverTo(int height);   // Fly to the height and stay there.
	public void   flyTo(POI interest); // Align it self to the POI.
	public void rotateAngle(int angle);
	

}

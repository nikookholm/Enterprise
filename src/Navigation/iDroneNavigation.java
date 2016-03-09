package Navigation;

import Common.POI;

public interface iDroneNavigation {
	
	
	public DroneSensors getSensors();
	public DroneVision  getVision();
	
	public void hoverTo(int height);   // Fly to the height and stay there.
	public void   flyTo(POI interest); // Align it self to the POI.
	public void rotateRight(int degrees);
	public void rotateLeft(int degrees);


}

package POI;

import java.util.Vector;
import Vector.Vector3D;
public class POI {
	


	private Vector3D coordinates;
	private Vector3D dronePos;
	
	private int angle;
	
	public POI(Vector3D coordinates, Vector3D dronepos)
	{
		
		
		this.coordinates = coordinates;
		this.dronePos = dronepos;
	}
	
	public POI(Vector3D coordinates, int angle)
	{

		this.coordinates = coordinates;
		this.angle = angle;
	}
	
	
	
}

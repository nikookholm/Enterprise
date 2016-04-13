package POI;

import com.sun.javafx.geom.Vec3d;

public class POI {
	


	private Vec3d coordinates;
	private Vec3d dronePos;
	
	private int 	angle;
	
	public POI(Vec3d coordinates)
	{
		
		this.coordinates = coordinates;
	}
	
	public POI(Vec3d coordinates, int angle)
	{

		this.coordinates = coordinates;
		this.angle = angle;
	}
	
	
	
}

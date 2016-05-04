package POI;


import Vector.Vector3D;
public class POI {
	


	private Vector3D coordinates;
	private Vector3D dronePos;
	
	private int angle;
	
	public POI(Vector3D coordinates, Vector3D dronepos, int angle)
	{
		
		
		this.coordinates = coordinates;
		this.dronePos = dronepos;
		this.angle = angle;
	}
	
	public POI(Vector3D coordinates, int angle)
	{

		this.coordinates = coordinates;
		this.angle = angle;
	}
	
	public Vector3D getCoordinates(){
		return coordinates;
	}
	
	public double getxPos(){
		return coordinates.getXCoord();
	}
	
	public double getyPos(){
		return coordinates.getYCoord();
	}
	
	public double getzPos(){
		return coordinates.getZCoord();
	}
	
	
}

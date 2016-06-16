package POI;

import Vector.Vector3D;

public class POIWallPoint extends POI {
	
	private double distance;
	
	public POIWallPoint(Vector3D coordinates, Vector3D dronePos,String QRcontains, int angle) {
		super(coordinates, dronePos, angle,QRcontains);
	}
	public POIWallPoint(String code, double dist,Vector3D cords){
		super(cords,null,0,code);
		this.distance = dist;
	}
	
	public double getDistance() {
		return distance;
	}
	
	

	
}

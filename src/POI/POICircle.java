package POI;

import Vector.Vector3D;

public class POICircle extends POI {

	private int radius;
	private boolean QR;
	private double distance;
	
	public POICircle(Vector3D coordinates, Vector3D dronepos, int radius) {
		super(coordinates, dronepos);
		this.radius = radius;
	}
	
	public POICircle(String Code, double distance){
		super(null,null,0,Code);
		this.distance = distance;
	}
	
	
	
	public int getRadius(){
		return radius;
	}
}

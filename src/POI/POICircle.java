package POI;

import Vector.Vector3D;

public class POICircle extends POI {

	private int radius;
	private boolean QR;
	private double distance;
	private double QRDistance;
	private double centrumX;
	private double centrumY;

	
	public POICircle(Vector3D coordinates, Vector3D dronepos, int radius, double distancee, double cenX, double cenY) {
		super(coordinates, dronepos);
		this.radius = radius;
		this.distance = distancee;
		this.centrumX = cenX;
		this.centrumY = cenY;
	}
	
	public POICircle(String Code, double distance){
		super(null,null,0,Code);
		this.QRDistance = distance;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public int getRadius(){
		return radius;
	}
	public double getCentrumX() {
		return centrumX;
	}
	public double getCentrumY() {
		return centrumY;
	}
}

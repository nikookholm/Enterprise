package POI;

import Vector.Vector3D;

public class POIWallPoint extends POI {
	
	private String QRcontains;
	private double distance;
	
	public POIWallPoint(Vector3D coordinates, Vector3D dronePos,String QRcontains, int angle) {
		super(coordinates, dronePos, angle);
		this.QRcontains = QRcontains;
	}
	public POIWallPoint(String code, double dist){
		super(null,null,0);
		this.QRcontains = code;
		this.distance = dist;
	}
	
	public double getDistance() {
		return distance;
	}
	
	
	public String getQRString(){
		return QRcontains;
	}
	
}

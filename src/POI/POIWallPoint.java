package POI;

import Vector.Vector3D;

public class POIWallPoint extends POI {
	
	String QRcontains;
	
	public POIWallPoint(Vector3D coordinates, Vector3D dronePos,String QRcontains, int angle) {
		super(coordinates, dronePos, angle);
		this.QRcontains = QRcontains;
	}
	
	public String getQRString(){
		return QRcontains;
	}
	
}

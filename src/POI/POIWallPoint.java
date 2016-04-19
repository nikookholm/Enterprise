package POI;

import Vector.Vector3D;

public class POIWallPoint extends POI {
	
	String QRcontains;
	
	public POIWallPoint(Vector3D coordinates, Vector3D dronePos,String QRcontains) {
		super(coordinates, dronePos);
		this.QRcontains = QRcontains;
	}
	
}

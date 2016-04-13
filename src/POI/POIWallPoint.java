package POI;

import Vector.Vector3D;

public class POIWallPoint extends POI {
	
	String QRcontains;
	
	public POIWallPoint(Vector3D coordinates, String QRcontains) {
		super(coordinates);
		this.QRcontains = QRcontains;
	}
	
}

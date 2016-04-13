package POI;

import com.sun.javafx.geom.Vec3d;

public class POIWallPoint extends POI {
	
	String QRcontains;
	
	public POIWallPoint(Vec3d coordinates, String QRcontains) {
		super(coordinates);
		this.QRcontains = QRcontains;
	}
	
}

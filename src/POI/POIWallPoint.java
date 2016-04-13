package POI;

public class POIWallPoint extends POI {
	
	String contains;
	
	public POIWallPoint(POIType type, double x, double y, String contains) {
		super(type, x, y);
		this.contains = contains;
	}
	
	public POIWallPoint(POIType type, double x, double y, double z, String contains) {
		super(type, x, y, z);
		this.contains = contains;
	}
}

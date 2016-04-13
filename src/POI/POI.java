package POI;

public class POI {
	
	public static enum POIType { RING, DICE, QR };

	private double 	x, y, z;
	private POIType type;
	private int 	angle;
	
	public POI(POIType type, double x, double y, double z)
	{
		this.type = type;
		this.x    = x;
		this.y    = y;
		this.z    = z;
	}
	
	public POI(POIType type, int angle)
	{
		this.type  = type;
		this.angle = angle;
	}
	
	public POI(POIType type, double x, double y){
		this.type = type;
		this.x = x;
		this.y = y;
	}
	// Subject to change
	public double[] getCoordinates(){
		double[] coordinates = {x,y,z};
		return coordinates;
	}
	
}

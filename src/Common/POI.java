package Common;

public class POI {
	
	public static enum POIType { RING, DICE };

	private int 	x, y, z;
	private POIType type;
	private int 	angle;
	
	public POI(POIType type, int x, int y, int z)
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
	
}

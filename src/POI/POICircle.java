package POI;

public class POICircle extends POI {

	int radius;
	boolean QR;
	String contains;
	
	public POICircle(POIType type, double x, double y, int radius) {
		super(type, x, y);
		this.radius = radius;
	}

	public POICircle(POIType type, double x, double y, double z, int radius){
		super(type, x, y, z);
		this.radius = radius;
	}
	
	public void setQR(String contains){
		this.contains = contains;
	}
	
	public String getQRValue(){
		return contains;
	}
	
	public int getRadius(){
		return radius;
	}
}

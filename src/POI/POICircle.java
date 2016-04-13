package POI;

import com.sun.javafx.geom.Vec3d;

public class POICircle extends POI {

	int radius;
	boolean QR;
	String contains;
	
	public POICircle(Vec3d coordinates, int radius) {
		super(coordinates);
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

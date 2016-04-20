package Vector;

public class Vector3D {
	double x;
	double y;
	double z;
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D()
	{
		
	}
	
	public double getXCoord(){
		return x;
	}
	
	public double getYCoord(){
		return y;
	}
	
	public double getZCoord(){
		return z;
	}
	
	public double incXCoord(double incX){
		return x += incX;
	}
	
	public double incYCoord(double incY){
		return y += incY;
	}
	
	public double incZCoord(double incZ){
		return z += incZ;
	}
}

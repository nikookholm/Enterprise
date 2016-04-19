package Navigation;

import java.util.ArrayList;

import POI.*;
import Vector.Vector3D;

public class vectorDistance {
	
	public void calcCoordinates(Vector3D object1, Vector3D object2){
		double alpha = Math.acos(object1.getXCoord()*object2.getXCoord()+object1.getXCoord()*object2.getYCoord()/(Math.sqrt(Math.pow(object1.getYCoord(), 2)+(Math.pow(object1.getYCoord(), 2))*Math.sqrt(Math.pow(object2.getXCoord(), 2)+(Math.pow(object2.getYCoord(), 2))))));
		System.out.print(alpha);
	
	}
	
}

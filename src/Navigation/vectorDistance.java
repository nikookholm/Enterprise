package Navigation;

import java.util.ArrayList;

import POI.*;
import Vector.Vector3D;

public class vectorDistance {
	
	public void calcCoordinates(Vector3D object1, Vector3D object2, Vector3D object3) {
		double dotAB = dot(object1, object2);
		double dotBC = dot(object2, object3);
		double magnitudeA = magnitude(object1);
		double magnitudeB = magnitude(object2);
		double magnitudeC = magnitude(object3);

		double alpha;
		double beta;

		alpha = Math.acos(dotAB / (Math.abs(magnitudeA) * Math.abs(magnitudeB)));
		beta = Math.acos(dotBC / Math.abs(magnitudeB) * Math.abs(magnitudeC));
		System.out.print(radToDegree(alpha));
		
		

	}
	
	public Vector3D calcDronePos(Vector3D wallmark1, Vector3D wallmark2, Vector3D wallmark3, double a, double b, double alpha, double beta){
		Vector3D dronepos = null;
		
		double c1_radius = (1/2)*(a/Math.sin(alpha));
		Vector3D c1_center = new Vector3D(0,0,0);
		
		c1_center.incXCoord((1/2)*(wallmark2.getYCoord() - wallmark1.getYCoord()/magnitude(wallmark1)));
		
		return dronepos;
	}

	double dot(Vector3D object1, Vector3D object2) {
		double sum;

		sum = object1.getXCoord() * object2.getXCoord() + object1.getYCoord() * object2.getYCoord();

		return sum;
	}

	double magnitude(Vector3D object) {
		double sum;

		sum = Math.sqrt(Math.pow(object.getXCoord(), 2) + Math.pow(object.getYCoord(), 2));

		return sum;
	}
	double magnitude(Vector3D object1, Vector3D object2){
		double sum;
		
		sum = Math.sqrt(Math.pow(-object2.getYCoord() + object1.getYCoord(), 2) + Math.pow(-object2.getXCoord() + object1.getXCoord(), 2));
		
		return sum;
	}

	double radToDegree(double value){
		return (value*(180)/Math.PI);
	}
	
}

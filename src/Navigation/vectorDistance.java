package Navigation;

import java.text.DecimalFormat;
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

	}

	/**
	 * Method returns Vector -1,-1,-1 if it was unable to find Drone position.
	 */

	public Vector3D calcDronePos(Vector3D wallmark1, Vector3D wallmark2, Vector3D wallmark3,
			double alpha, double beta) {
		System.out.println("WM1: " + wallmark1.getXCoord() + "," + wallmark1.getYCoord() + " WM2: " + wallmark2.getXCoord() + "," + wallmark2.getYCoord() + " WM3: " + wallmark3.getXCoord() + "," + wallmark3.getYCoord() + " Alpha: " + alpha + " Beta: "+ beta);


		

		
		
		Vector3D c1_c = calcCircleCoords(wallmark1, wallmark2, alpha);
		Vector3D c2_c = calcCircleCoords(wallmark2, wallmark3, beta);

		System.out.println("C1: " + c1_c.getXCoord() + ", " + c1_c.getYCoord() + " radius: " + c1_c.getZCoord());
		System.out.println("C2: " + c2_c.getXCoord() + ", " + c2_c.getYCoord() + " radius: " + c2_c.getZCoord());
		
		calcCircleIntersects(c1_c, c2_c);
		

		return new Vector3D(-1, -1, -1);
	}
	
	ArrayList<Vector3D> calcCircleIntersects(Vector3D point1, Vector3D point2){
		
		ArrayList<Vector3D> possiblePoints = new ArrayList<>();
		
		double pointDistance = magnitude(point1, point2);
		System.out.println(pointDistance);
		if(pointDistance < (point1.getZCoord() - point2.getZCoord())){
			System.out.println("No possible points found");
			return null;
		}else if(pointDistance > (point1.getZCoord() + point2.getZCoord())){
			System.out.println("No possible points found");
			return null;
		}
		
		if(pointDistance == (point1.getZCoord() + point2.getZCoord())){
			System.out.println("There is only one point");
			
		}
		
		double d = magnitude(point1,point2);
		double l1 = Math.pow(point1.getZCoord(), 2) - Math.pow(point2.getZCoord(), 2) + Math.pow(d, 2);
		double l = l1/(2*d);
		double h = Math.sqrt(Math.pow(point1.getZCoord(), 2) - Math.pow(l, 2));
		
		double x1 = (l/d)*(point2.getXCoord() - point1.getXCoord()) + (h/d)*(point2.getYCoord() - point1.getYCoord()) + point1.getXCoord();
		double y1 = (l/d)*(point2.getYCoord() - point1.getYCoord()) - (h/d)*(point2.getXCoord() - point1.getXCoord()) + point1.getYCoord();
		double x2 = (l/d)*(point2.getXCoord() - point1.getXCoord()) - (h/d)*(point2.getYCoord() - point1.getYCoord()) + point1.getXCoord();
		double y2 = (l/d)*(point2.getYCoord() - point1.getYCoord()) + (h/d)*(point2.getXCoord() - point1.getXCoord()) + point1.getYCoord();
		
		System.out.println("x1: " + x1 + " y1: " + y1);
		System.out.println("x2: " + x2 + " y2: " + y2);
		possiblePoints.add(new Vector3D(x1, y1, 0.0));
		possiblePoints.add(new Vector3D(x2, y2, 0.0));
		return possiblePoints;
		
	}
	
	Vector3D calcCircleCoords(Vector3D wallmark1, Vector3D wallmark2, double alpha){
		Vector3D circle = new Vector3D(0,0,0);

		double a = magnitude(wallmark1, wallmark2);
	
		double t1 = a*a;

		double t2 = Math.sin(alpha);

		
		
		double t3 = t2*t2;
		double t7 = Math.sqrt((1.0 / t3 * t1 - t1));
	
		double t8 = wallmark1.getYCoord() - wallmark2.getYCoord();
	
		double t10 = t8*t8;
		
		double t12 = wallmark1.getXCoord() - wallmark2.getXCoord();
		double t13 = t12*t12;
		
		double t15 = Math.sqrt((t10+t13));
		
		
		double c_x = -t8 / t15 * t7 / 2.0 +wallmark1.getXCoord() / 2.0 + wallmark2.getXCoord() / 2.0;
		
		double c_y = t12 / t15 * t7 / 2.0 + wallmark1.getYCoord() / 2.0 + wallmark2.getYCoord() / 2.0;
	
		
		
		circle.incXCoord(-t8 / t15 * t7 / 2.0 + wallmark1.getXCoord() / 2.0 + wallmark2.getXCoord() / 2.0);
		circle.incYCoord(t12 / t15 * t7 / 2.0 + wallmark1.getYCoord() / 2.0 + wallmark2.getYCoord() / 2.0);
	
		double r = 0.5*a/Math.sin(alpha);
		circle.incZCoord(r);
		
		return circle;
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

	double magnitude(Vector3D object1, Vector3D object2) {
		double sum;

		double xt = Math.pow(object1.getXCoord()-object2.getXCoord(), 2);
		double yt = Math.pow(object1.getYCoord()-object2.getYCoord(), 2);
		sum = Math.sqrt(xt + yt);

		return sum;
	}

	double radToDegree(double value) {
		return (value * ((double)180/Math.PI));
	}
	
	double degreeToRad(double value){
		return (value * (Math.PI/(double)180));
	}
}

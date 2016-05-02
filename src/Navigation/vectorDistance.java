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


		

		double c1_r = (0.5*magnitude(wallmark1, wallmark2) / (Math.sin(alpha)));
		double c2_r = (0.5*magnitude(wallmark2, wallmark3) / (Math.sin(beta)));
		
		Vector3D c1_c = calcCircleCoords(wallmark1, wallmark2, alpha);
		Vector3D c2_c = calcCircleCoords(wallmark2, wallmark3, beta);

		System.out.println("C1: " + c1_c.getXCoord() + ", " + c1_c.getYCoord() + " radius: " + c1_r);
		System.out.println("C2: " + c2_c.getXCoord() + ", " + c2_c.getYCoord() + " radius: " + c2_r);
			
		ArrayList<Vector3D> p1_pot = new ArrayList<>();
		ArrayList<Vector3D> p2_pot = new ArrayList<>();
		
		double radius = (double) Math.round(Math.pow(c2_r, 2) * 100000d)/ 100000d;
		double c1radius = (double) Math.round((Math.pow(10.350 - c2_c.getXCoord(), 2)+Math.pow(2.728 - c2_c.getYCoord(), 2)) * 100000d) / 100000d;
		
		System.out.println(radius);
		System.out.println(c1radius);
//		c1_r = Double.parseDouble(new DecimalFormat("#,##").format(c1_r));
//		for(double x = 0; x < 10; x+=0.1){
//			for(double y = 0; y < 10; y+=0.1){
//				if(allowedRange(x,y, c1_c, c2_c)){
//					System.out.println("Success at: " + x + ", " + y);
//				}
//			}
//		}
//		
		return new Vector3D(-1, -1, -1);
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

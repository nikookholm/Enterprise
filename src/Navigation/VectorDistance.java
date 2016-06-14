package Navigation;

import java.text.DecimalFormat;
import java.util.ArrayList;

import POI.*;
import Vector.Vector3D;

public class VectorDistance {


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
	 * @deprecated Use {@link #getDronePosTwoPoints(Vector3D, double, Vector3D, double) getDronePosTwoPoints instead}
	 */
	@Deprecated
	public Vector3D calcDronePos(Vector3D wallmark1, Vector3D wallmark2, Vector3D wallmark3,
			double alpha, double beta) {
		System.out.println("WM1: " + wallmark1.getXCoord() + "," + wallmark1.getYCoord() + " WM2: " + wallmark2.getXCoord() + "," + wallmark2.getYCoord() + " WM3: " + wallmark3.getXCoord() + "," + wallmark3.getYCoord() + " Alpha: " + alpha + " Beta: "+ beta);

		Vector3D c1_c = calcCircleCoords(wallmark1, wallmark2, alpha);
		Vector3D c2_c = calcCircleCoords(wallmark2, wallmark3, beta);
		
		ArrayList<Vector3D> PossiblePoints = calcCircleIntersects(c1_c, c2_c);
		
		
			if((PossiblePoints.get(0).getXCoord() == wallmark2.getXCoord()) && (PossiblePoints.get(0).getYCoord() == wallmark2.getYCoord())){
				return PossiblePoints.get((1));
			}else{
				return PossiblePoints.get(0);
			}
	}
	/**
	 * @deprecated Only used with {@link #calcDronePos(Vector3D, Vector3D, Vector3D, double, double)}
	 */
	@Deprecated
	ArrayList<Vector3D> calcCircleIntersects(Vector3D point1, Vector3D point2){
		
		ArrayList<Vector3D> possiblePoints = new ArrayList<>();
		
		double pointDistance = magnitude(point1, point2);
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
		
		possiblePoints.add(new Vector3D(x1, y1, 0.0));
		possiblePoints.add(new Vector3D(x2, y2, 0.0));
		return possiblePoints;
		
	}
	/**
	 * 
	 * @deprecated Only used with {@link #calcDronePos(Vector3D, Vector3D, Vector3D, double, double)}
	 */
	@Deprecated
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
	
		
		
		circle.setXCoord(-t8 / t15 * t7 / 2.0 + wallmark1.getXCoord() / 2.0 + wallmark2.getXCoord() / 2.0);
		circle.setYCoord(t12 / t15 * t7 / 2.0 + wallmark1.getYCoord() / 2.0 + wallmark2.getYCoord() / 2.0);
	
		double r = 0.5*a/Math.sin(alpha);
		circle.setZCoord(r);
		
		return circle;
	}
	
	/**
	 * Is used to calculate the dot value of two vectors
	 * 
	 * @param Vector 1
	 * @param Vector 2
	 * @return The dot value of the two vectors
	 */
	double dot(Vector3D object1, Vector3D object2) {
		double sum;
		sum = object1.getXCoord() * object2.getXCoord() + object1.getYCoord() * object2.getYCoord();

		return sum;
	}

	/**
	 * Is used to calculate the magnitude of a 2D vector
	 * @param Vector
	 * @return double magnitude
	 */
	double magnitude(Vector3D object) {
		double sum;

		sum = Math.sqrt(Math.pow(object.getXCoord(), 2) + Math.pow(object.getYCoord(), 2));

		return sum;
	}

	/**
	 * Is used to calculate the magnitude of two 2D vectors
	 * @param Vector 1
	 * @param Vector 2
	 * @return double magnitude
	 */
	double magnitude(Vector3D object1, Vector3D object2) {
		double sum;

		double xt = Math.pow(object1.getXCoord()-object2.getXCoord(), 2);
		double yt = Math.pow(object1.getYCoord()-object2.getYCoord(), 2);
		sum = Math.sqrt(xt + yt);

		return sum;
	}

	/**
	 *  Converts a value in radians to a value in degrees
	 * @param value in radians
	 * @return value in degrees
	 */
	double radToDegree(double value) {
		return (value * ((double)180/Math.PI));
	}
	/**
	 *  Converts a value in degrees to a value in radians
	 * @param value in degrees
	 * @return value in radians
	 */
	double degreeToRad(double value){
		return (value * (Math.PI/(double)180));
	}
	
	
	/******************************************************************/
	/*****************Calculate from 2 qr-codes************************/
	/******************************************************************/
	
	
	public Vector3D getDronePosTwoPoints(Vector3D wallPoint1, double distance1, Vector3D wallPoint2, double distance2){
		double x0, y0, x1, y1, x2, y2, x3, y3;
		x0 = wallPoint1.getXCoord();
		y0 = wallPoint1.getYCoord();
		x1 = wallPoint2.getXCoord();
		y1 = wallPoint2.getYCoord();
		
		//Find the distance between the two points, the parallel point to what
		//we are looking for and the height from this line to the desired point.
		double d = Math.sqrt(Math.pow((x1 - x0), 2) + Math.pow((y1 - y0), 2));
		double a = (Math.pow(distance1, 2) - Math.pow(distance2, 2) + Math.pow(d, 2))/(2*d);
		double h = Math.sqrt(Math.pow(distance1,2) - Math.pow(a,2));
		
		//Find the coordinates to the parallel point
		x2 = x0 + (a * (x1 - x0)) / d;
		y2 = y0 + (a * (y1 - y0)) / d;
		
		
		//Find the desired point and return the correct one
		x3 = x2 + (h * (y1 - y0)) / d;
		y3 = y2 - (h * (x1 - x0)) / d;
		
		if(x3>0 && x3<1078 && y3>0 && y3<963){
			return new Vector3D(x3, y3, 0);
		}
		
		x3 = x2 - (h * (y1 - y0)) / d;
		y3 = y2 + (h * (x1 - x0)) / d;
		
		return new Vector3D(x3,y3,0);
	}
	
	
	
	
	
}

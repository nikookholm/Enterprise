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

	/**
	 * Method returns Vector -1,-1,-1 if it was unable to find Drone position.
	 */

	public Vector3D calcDronePos(Vector3D wallmark1, Vector3D wallmark2, Vector3D wallmark3, double a, double b,
			double alpha, double beta) {
		System.out.println("WM1: " + wallmark1.getXCoord() + "," + wallmark1.getYCoord() + " WM2: " + wallmark2.getXCoord() + "," + wallmark2.getYCoord() + " WM3: " + wallmark3.getXCoord() + "," + wallmark3.getYCoord() + " Alpha: " + alpha + " Beta: "+ beta);
		alpha = degreeToRad(alpha);
		beta = degreeToRad(beta);
		
		System.out.println("Angles after conversion, alpha: " + alpha + " beta: " + beta);
		double c1_r = (0.5*a / (Math.sin(alpha)));
		Vector3D c1_c = new Vector3D(0, 0, 0);
		
		double l1 = -wallmark2.getXCoord() + wallmark1.getXCoord();
		double l2 = magnitude(wallmark1,wallmark2);
		double l3 = Math.sqrt(((Math.pow(a, 2)))/(Math.pow(Math.sin(alpha), 2)));
		double l5 = (1)/(2*wallmark1.getYCoord());
		double l6 = (1)/(2*wallmark2.getYCoord());
		
		c1_c.incYCoord(0.5 * (l1/l2)*(l3)+l5+l6);
//		c1_c.incXCoord(0.5 * (wallmark2.getXCoord() - wallmark1.getXCoord() / magnitude(wallmark1, wallmark2))
//				* Math.sqrt(Math.pow(a, 2) / Math.pow(Math.sin(alpha), 2)) + (1 / (2 * wallmark1.getYCoord()))
//				+ 1 / (2 * wallmark2.getYCoord()));
		
		double c2_r = (0.5*b / (Math.sin(beta)));
		System.out.println("c1_r: " + c1_r +" c2_r: "+c2_r);
		System.out.println("l1: " + l1);
		System.out.println("l2: " + l2);
		System.out.println("l3: " + l3);
		System.out.println("l5: " + l5);
		System.out.println("l6: " + l6);
		Vector3D c2_c = new Vector3D(0, 0, 0);

		ArrayList<Vector3D> p1_pot = new ArrayList<>();
		ArrayList<Vector3D> p2_pot = new ArrayList<>();

		for (double x = 0; x < 10.78; x += 0.01) {
			for (double y = 0; y < 9.63; y += 0.01) {
			//	System.out.println("Result need to be: " + c1_r + " is actually: " + (Math.pow(x, 2)-c1_c.getXCoord()*x + Math.pow(c1_c.getXCoord(), 2)+Math.pow(y, 2)-c1_c.getYCoord()*y + Math.pow(c1_c.getYCoord(), 2)) + " x: " + x + " y: " + y);
				if ((Math.pow(x, 2)-c1_c.getXCoord()*x + Math.pow(c1_c.getXCoord(), 2)+Math.pow(y, 2)-c1_c.getYCoord()*y + Math.pow(c1_c.getYCoord(), 2)) == c1_r) {
					System.out.println("success!");
					p1_pot.add(new Vector3D(x, y, 0));
				}
				if (Math.pow(x, 2) - c2_c.getXCoord() + Math.pow(c2_c.getXCoord(), 2) + Math.pow(y, 2)
						- c2_c.getYCoord() * y + Math.pow(c2_c.getYCoord(), 2) == c2_r) {
					p2_pot.add(new Vector3D(x, y, 0));
				}
			}
		}
		System.out.println((1 / (2 * wallmark2.getXCoord())));
		System.out.println(p1_pot.size());
		System.out.println(p2_pot.size());
		for (int i = 0; i < p1_pot.size(); i++) {
			for (int j = 0; j < p2_pot.size(); j++) {
				if (p1_pot.get(i).getXCoord() == p2_pot.get(j).getXCoord()
						&& p1_pot.get(i).getYCoord() == p2_pot.get(j).getYCoord()) {
					if (p1_pot.get(i).getXCoord() < 0 | p1_pot.get(i).getYCoord() < 0) {
						return p1_pot.get(i);
					}
				}
			}
		}

		return new Vector3D(-1, -1, -1);
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

		sum = Math.sqrt(Math.pow(Math.abs(-object2.getYCoord() + object1.getYCoord()), 2)
				+ Math.pow(Math.abs(-object2.getXCoord() + object1.getXCoord()), 2));

		return sum;
	}

	double radToDegree(double value) {
		return (value * (180) / Math.PI);
	}
	
	double degreeToRad(double value){
		return ((value * Math.PI)/180);
	}
}

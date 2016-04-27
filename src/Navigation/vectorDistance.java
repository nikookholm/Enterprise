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
		

		double c1_r = (1 / 2) * (a / Math.sin(alpha));
		Vector3D c1_c = new Vector3D(0, 0, 0);

		c1_c.incYCoord((1 / 2) * (wallmark2.getYCoord() - wallmark1.getYCoord() / magnitude(wallmark1, wallmark2))
				* Math.sqrt(Math.pow(a, 2) / Math.pow(Math.sin(alpha), 2)) + (1 / 2 * wallmark1.getXCoord())
				+ 1 / 2 * wallmark2.getXCoord());
		c1_c.incXCoord((1 / 2) * (wallmark2.getXCoord() - wallmark1.getXCoord() / magnitude(wallmark1, wallmark2))
				* Math.sqrt(Math.pow(a, 2) / Math.pow(Math.sin(alpha), 2)) + (1 / 2 * wallmark1.getYCoord())
				+ 1 / 2 * wallmark2.getYCoord());

		double c2_r = (1 / 2) * (b / Math.sin(beta));
		Vector3D c2_c = new Vector3D(0, 0, 0);

		c2_c.incYCoord((1 / 2) * (wallmark3.getYCoord() - wallmark2.getYCoord() / magnitude(wallmark2, wallmark3))
				* Math.sqrt(Math.pow(b, 2) / Math.pow(Math.sin(beta), 2)) + (1 / 2 * wallmark2.getXCoord())
				+ 1 / 2 * wallmark3.getXCoord());
		c2_c.incXCoord((1 / 2) * (wallmark3.getXCoord() - wallmark2.getXCoord() / magnitude(wallmark2, wallmark3))
				* Math.sqrt(Math.pow(b, 2) / Math.pow(Math.sin(beta), 2)) + (1 / 2 * wallmark2.getYCoord())
				+ 1 / 2 * wallmark3.getYCoord());

		ArrayList<Vector3D> p1_pot = new ArrayList<>();
		ArrayList<Vector3D> p2_pot = new ArrayList<>();

		for (double x = 0; x < 10.78; x += 0.01) {
			for (double y = 0; y < 9.63; y += 0.01) {
				if (Math.pow(x, 2) - c1_c.getXCoord() + Math.pow(c1_c.getXCoord(), 2) + Math.pow(y, 2)
						- c1_c.getYCoord() * y + Math.pow(c1_c.getYCoord(), 2) == c1_r) {
					p1_pot.add(new Vector3D(x, y, 0));
				}
				if (Math.pow(x, 2) - c2_c.getXCoord() + Math.pow(c2_c.getXCoord(), 2) + Math.pow(y, 2)
						- c2_c.getYCoord() * y + Math.pow(c2_c.getYCoord(), 2) == c2_r) {
					p2_pot.add(new Vector3D(x, y, 0));
				}
			}
		}
		
		for(int i = 0; i < p1_pot.size(); i++){
			for(int j = 0; j < p2_pot.size(); i++){
				if(p1_pot.get(i).getXCoord() == p2_pot.get(j).getXCoord() && p1_pot.get(i).getYCoord() == p2_pot.get(j).getYCoord()){
					return p1_pot.get(i);//TODO can return negative so far.
				}
			}
		}

		
		return new Vector3D(-1,-1,-1);
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

}

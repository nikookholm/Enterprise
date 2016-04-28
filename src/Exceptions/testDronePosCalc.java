package Exceptions;

import Navigation.vectorDistance;
import Vector.Vector3D;

public class testDronePosCalc {

	public static void main(String[] args) {
		vectorDistance VD = new vectorDistance();
		
		Vector3D wallmark1 = new Vector3D(9,9,0);
		Vector3D wallmark2 = new Vector3D(11,7,0);
		Vector3D wallmark3 = new Vector3D(10,5, 0);
		double a = 2.83;
		double b = 2.24;
		double alpha = 18.43;
		double beta = 15.95;
		Vector3D dronePos = VD.calcDronePos(wallmark1, wallmark2, wallmark3, a, b, alpha, beta);
		System.out.println(dronePos.getXCoord()+ ","+ dronePos.getYCoord());
	}

}

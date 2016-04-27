package Exceptions;

import Navigation.vectorDistance;
import Vector.Vector3D;

public class testDronePosCalc {

	public static void main(String[] args) {
		vectorDistance VD = new vectorDistance();
		
		Vector3D wallmark1 = new Vector3D(2,4,0);
		Vector3D wallmark2 = new Vector3D(5,5,0);
		Vector3D wallmark3 = new Vector3D(8,7,0);
		double a = 1.5;
		double b = 1.5;
		double alpha = 22.38;
		double beta = 14.18;
		Vector3D dronePos = VD.calcDronePos(wallmark1, wallmark2, wallmark3, a, b, alpha, beta);
		System.out.println(dronePos.getXCoord()+ ","+ dronePos.getYCoord());
	}

}

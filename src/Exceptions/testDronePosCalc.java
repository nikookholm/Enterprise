package Exceptions;

import Navigation.vectorDistance;
import Vector.Vector3D;

public class testDronePosCalc {

	public static void main(String[] args) {
		vectorDistance VD = new vectorDistance();
		
		Vector3D wallmark1 = new Vector3D(4.9,2.06,0);
		Vector3D wallmark2 = new Vector3D(8.5,0.46,0);
		Vector3D wallmark3 = new Vector3D(10.48, 1.92, 0);
		double a = 3.94;
		double b = 2.46;
		double alpha = 88.08;
		double beta = 45.5;
		Vector3D dronePos = VD.calcDronePos(wallmark1, wallmark2, wallmark3, a, b, alpha, beta);
		System.out.println(dronePos.getXCoord()+ ","+ dronePos.getYCoord());
	}

}

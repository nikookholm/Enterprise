package Tests;

import Navigation.vectorDistance;
import Vector.Vector3D;

public class testDronePosCalc {

	public static void main(String[] args) {
		vectorDistance VD = new vectorDistance();
		
		Vector3D wallmark1 = new Vector3D(0.0,0.0,0);
		Vector3D wallmark2 = new Vector3D(1.0,2.0,0);
		Vector3D wallmark3 = new Vector3D(0.0,4.0, 0);
	//	double a = 2.83;
		//double b = 2.24;
		double alpha = 0.18;
		double beta = 0.20;
		Vector3D dronePos = VD.calcDronePos(wallmark1, wallmark2, wallmark3, alpha, beta);
		System.out.println(dronePos.getXCoord()+ ","+ dronePos.getYCoord());
	}

}

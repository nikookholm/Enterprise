package Navigation;

import Vector.Vector3D;

public class vectorTester {

	public static void main(String[] args) {
		
		vectorDistance test = new vectorDistance();
		
		Vector3D object1 = new Vector3D(1,2,0);
		Vector3D object2 = new Vector3D(2,1,0);
		
		test.calcCoordinates(object1, object2);

	}

}

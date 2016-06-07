package Navigation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Marshaller.Listener;

import de.yadrone.base.ARDrone;
import de.yadrone.base.video.*;
import Common.Drone;
import Movements.DroneMovement.Movement;
import Movements.DroneMovementThread;
import Navigation.ScanListeners.ScanListener;
import POI.POI;
import POI.POICircle;
import POI.POIWallPoint;

import org.opencv.core.Point;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


import Vector.Vector3D;

import org.opencv.highgui.VideoCapture;

public class DroneVision implements iDroneVision {

	private Drone drone;
	
	BufferedImage lastImage;
	BufferedImage newImage;
	

	ArrayList<POI> tempPoI = new ArrayList<POI>();
	OpenCVOperations CVOp;
	vectorDistance VD;
	
	public DroneVision(Drone drone) {
		this.drone = drone;
		OpenCVOperations CVOp = new OpenCVOperations();
		vectorDistance VD = new vectorDistance();
	}

	@Override
	public ArrayList<POI> scan(Movement movement, Condition condition) {
		ArrayList<POI> poi = new ArrayList<POI>();
		
		DroneMovementThread movementThread = new DroneMovementThread(movement);
		movementThread.run();

		int i = 0;
		int wallPoints;
		int circlePoints = 0;
		switch(condition){
			case Initial:
				while(poi.size()<3){
					//TODO skal ændres til kun at finde wallPoint qr koder
					tempPoI = CVOp.findObjects(null, null, 0); //check with PAWURHAUZ
					tempPoI.removeAll(poi);
					poi.addAll(tempPoI);
				}
				break;
				
			case CircleQR:
				i = 0;
				circlePoints = 0;
				while(circlePoints<5){
					tempPoI = CVOp.findObjects(null, null, 0); //check with PAWURHAUZ
					tempPoI.removeAll(poi);
					poi.addAll(tempPoI);
					while(i<poi.size()){
						if(poi.get(i) instanceof POICircle){
							circlePoints++;
						}
						i++;
					}
				}
				break;
			default:
				//should not be used
				break;
		}
		return poi;
	}
	
	public Vector3D calibrate(){
		Vector3D dronepos;
		
		ArrayList<POI> poi = scan(Movement.Forward, Condition.Initial);
		ArrayList<POI> Wallpoints = null;
		int wallpointsFound = 0;
		for(int i = 0; i < poi.size(); i++){
			if(poi.get(i) instanceof POIWallPoint){
				if((((POIWallPoint)poi.get(i)).getQRString() == "W00.00") | (((POIWallPoint)poi.get(i)).getQRString() == "W00.01") | (((POIWallPoint)poi.get(i)).getQRString() == "W00.02")){
					Wallpoints.add(poi.get(i));
					wallpointsFound++;
				}
			}
		}
		if(wallpointsFound == 3){
		//	VD.calcDronepos(Wallpoints.get(0).getCoordinates(), Wallpoints.get(1).getCoordinates(), Wallpoints.get(2).getCoordinates());
		}
		return null;//return dronepos;
		
	}

	private class ImageHandler implements ImageListener {

		@Override
		public void imageUpdated(BufferedImage arg0) {
			if (newImage != null) {
				lastImage = newImage;
				newImage = arg0;
			}else{
				newImage = arg0;
				
			}
		}
		
	}


	private void scan(Movement movement, ScanListener listener) {
		
		VisionThread vt = new VisionThread();
		
		switch (movement)
		{
			case Forward:
				drone.getMovement().flyForward();
				break;
			case Backward:
				drone.getMovement().flyBackward();
				break;
			case Left:
				drone.getMovement().flyLeft();
				break;
			case Right:
				drone.getMovement().flyRight();
				break;
			case SpinLeft:
				drone.getMovement().spinLeft();
				break;
			case SpinRight:
				drone.getMovement().spinRight();
				break;
		}
//		while (!listener.executed)
//		{
//			
//			tempPoI = CVOp.compareImages(lastImage, newImage,coordinates, i);
//			Thread.sleep();
//			if (vt.POIFound != null)
//			{
//				
//				listener.execute();
//			}
//			
//		}	
	}

	@Override
	public ImageListener getImageListener() {
		// TODO Auto-generated method stub
		return null;
	}
}

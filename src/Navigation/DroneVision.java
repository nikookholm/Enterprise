package Navigation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.yadrone.base.ARDrone;
import de.yadrone.base.video.*;
import Common.Drone;
import POI.POI;
import POI.POIWallPoint;

import org.opencv.core.Point;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.sun.javafx.geom.Vec3d;
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
	public ArrayList<POI> scan() {

		ArrayList<POI> poi = new ArrayList<POI>();
		for(int i = 0; i <360; i+=5){
		//	drone.getMovement().rotateAngle(i);
			Vector3D coordinates = new Vector3D(drone.getCoordX(), drone.getCoordY(), drone.getCoordZ());

			tempPoI = CVOp.compareImages(lastImage, newImage,coordinates, i);
		}
		poi = tempPoI;
		
		
		return poi;
	}
	
	public Vector3D calibrate(){
		Vector3D dronepos;
		
		ArrayList<POI> poi = scan();
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
			VD.calcDronepos(Wallpoints.get(0).getCoordinates(), Wallpoints.get(1).getCoordinates(), Wallpoints.get(2).getCoordinates());
		}
		return dronepos;
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

	public void search() {
		// TODO Auto-generated method stub
		
	}

}

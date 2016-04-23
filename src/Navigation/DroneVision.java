package Navigation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.yadrone.base.ARDrone;
import de.yadrone.base.video.*;
import Common.Drone;
import POI.POI;



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
	
	public DroneVision(Drone drone) {
		this.drone = drone;
		OpenCVOperations CVOp = new OpenCVOperations();
	}

	@Override
	public List<POI> scan() {

		ArrayList<POI> poi = new ArrayList<POI>();
		for(int i = 0; i <360; i+=5){
		//	drone.getMovement().rotateAngle(i);
			Vec3d coordinates;
			coordinates.x = drone.getCoordX();
			coordinates.y = drone.getCoordY();
			coordinates.z = drone.getCoordZ();

			tempPoI = CVOp.compareImages(lastImage, newImage,coordinates, i);
		}
		poi = tempPoI;
		
		
		return poi;
	}

	@Override
	public ImageListener getImageListener() {
		// TODO Auto-generated method stub
		return null;
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

}

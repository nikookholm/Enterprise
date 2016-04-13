package Navigation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.yadrone.base.ARDrone;
import de.yadrone.base.video.*;
import Common.Drone;
import POI.POI;
import POI.POI.POIType;

import org.opencv.core.Point;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.VideoCapture;

public class DroneVision implements iDroneVision {

	private Drone drone;

	ArrayList<POI> tempPoI = new ArrayList<POI>();
	
	public DroneVision(Drone drone) {
		this.drone = drone;
	}

	@Override
	public List<POI> scan() {

		ArrayList<POI> poi = new ArrayList<POI>();
		poi = tempPoI;
		
		
		return poi;
	}

	@Override
	public ImageListener getImageListener() {
		// TODO Auto-generated method stub
		return null;
	}

	

	private class ImageHandler implements ImageListener {

		BufferedImage lastImage = null;
		BufferedImage newImage = null;

		OpenCVOperations CVOp = new OpenCVOperations();
		

		@Override
		public void imageUpdated(BufferedImage arg0) {
			if (newImage != null) {
				lastImage = newImage;
				newImage = arg0;
					
				tempPoI = CVOp.compareImages(lastImage, newImage);
			}else{
				newImage = arg0;
				
			}
		}
		
	}

}

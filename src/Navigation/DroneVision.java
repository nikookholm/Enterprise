package Navigation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.yadrone.base.ARDrone;
import de.yadrone.base.video.*;
import Common.Drone;
import Common.POI;
import Common.POI.POIType;

import org.opencv.core.Point;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.VideoCapture;

public class DroneVision implements iDroneVision {

	private Drone drone;

	public DroneVision(Drone drone) {
		this.drone = drone;
	}

	@Override
	public List<POI> scan() {
		// circles = findCircles();
		// dices = findDices();

		ArrayList<POI> poi = new ArrayList<POI>();

		return poi;
	}

	@Override
	public ImageListener getImageListener() {
		// TODO Auto-generated method stub
		return null;
	}

	private void findCircles() {
		BufferList bf = new BufferList();
		HoughCircles circles = new HoughCircles();

		ArrayList<BufferedImage> bImages = bf.getImages();
		ArrayList<POI> tempPoI = new ArrayList<POI>();
		
		for (int i = 0; i < bImages.size(); i++) {
			circles.findCircles(bImages.get(i));
		}
	}

	private class BufferList implements ImageListener {

		ArrayList<BufferedImage> listOfImages = new ArrayList<BufferedImage>();

		@Override
		public void imageUpdated(BufferedImage arg0) {
			if (listOfImages.size() < 15) {
				listOfImages.add(arg0);
			} else {
				listOfImages.removeAll(listOfImages);
				listOfImages.add(arg0);
			}
		}

		public ArrayList<BufferedImage> getImages() {

			return listOfImages;
		}

		public BufferedImage getImage() {

			return listOfImages.get(listOfImages.size());
		}

	}

}

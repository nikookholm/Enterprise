package Navigation;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.sun.javafx.geom.Vec3d;

import POI.POI;
import POI.POI.POIType;
import POI.POICircle;

public class OpenCVOperations {

	ArrayList<POI> objectsFound = new ArrayList<POI>();

	ArrayList<POI> interestsFound = new ArrayList<POI>();

	private ArrayList<POI> findObjects(BufferedImage arg0, Vec3d coordinates) {

		objectsFound.clear();

		Mat ImageMat = bufferedImageToMat(arg0);
		findQR(ImageMat, coordinates);
		findCircles(ImageMat, coordinates);
		findBlocks(ImageMat, coordinates);
		findAirports(ImageMat, coordinates);

		return objectsFound;
	}
	/**
	 * Returns a single ArrayList containing points of interest, that can be located on both images.
	 * The function is used to reduce noise and possible only gather the true points of interest.
	 * POI types includes rings, blocks, QR or Airports.
	 * 
	 * @param lastImage the previous image stored
	 * @param newImage the newest image stored
	 * @return Points of Interests
	 */
	public ArrayList<POI> compareImages(BufferedImage lastImage, BufferedImage newImage, Vec3d coordinates, int angle) {

		ArrayList<POI> li = findObjects(lastImage, coordinates);
		ArrayList<POI> ni = findObjects(newImage, coordinates);

		for(POI liCheck : li){
			for(POI niCheck : ni){
				if(liCheck instanceof POICircle && niCheck instanceof POICircle){
					if(((POICircle)liCheck).getRadius() == ((POICircle)niCheck).getRadius() && ((POICircle)liCheck).getRadius() > 10){
					
					}
				}
				// elseif QR
				// elseif block
				// elseif airport
				
			}
		}
		return interestsFound;
	}

	public void findQR(Mat image, Vec3d coordinates) {

		

		

		
	}

	public void findCircles(Mat image, Vec3d coordinates) {

		Mat image_gray = new Mat();
		Mat circles = new Mat();

		Imgproc.cvtColor(image, image_gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(image_gray, image_gray, new Size(9, 9), 2, 2);
		Imgproc.HoughCircles(image_gray, circles, Imgproc.CV_HOUGH_GRADIENT, 1, image_gray.rows() / 8, 200, 100, 0, 0);

		for (int i = 0; i < circles.cols(); i++) {

			double circle[] = circles.get(0, i);
			int radius = (int) Math.round(circle[2]);
			//objectsFound.add(new POICircle(POIType.RING, Math.round(circle[0]), Math.round(circle[1]), -1));
			objectsFound.add(new POICircle(new Vec3d(0, 0, 0), radius));
		}

	}

	public void findBlocks(Mat image, Vec3d coordinates) {
		// Add code to find Blocks
	}

	public void findAirports(Mat image, Vec3d coordinates) {
		// Add code to find Airports
	}

	public Mat bufferedImageToMat(BufferedImage bi) {
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}
}

package Navigation;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import Navigation.QRPoi;



import POI.POI;

import POI.POIWallPoint;
import POI.POICircle;
import Vector.Vector3D;

public class OpenCVOperations {

	ArrayList<POI> objectsFound = new ArrayList<POI>();

	ArrayList<POI> interestsFound = new ArrayList<POI>();
	
	public ArrayList<POI> findObjects(BufferedImage arg0, Vector3D coordinates, int angle) {
		objectsFound.clear();

		Mat ImageMat = bufferedImageToMat(arg0);
		findQR(ImageMat, coordinates, angle);
	//	findCircles(ImageMat, coordinates, angle);
		findBlocks(ImageMat, coordinates, angle);
		findAirports(ImageMat, coordinates, angle);

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
//	public ArrayList<POI> compareImages(BufferedImage lastImage, BufferedImage newImage, Vector3D coordinates, int angle) {
//
//		ArrayList<POI> li = findObjects(lastImage, coordinates, angle);
//		ArrayList<POI> ni = findObjects(newImage, coordinates, angle);
//
//		for(POI liCheck : li){
//			for(POI niCheck : ni){
//				if(liCheck instanceof POICircle && niCheck instanceof POICircle){
//					if(((POICircle)liCheck).getRadius() == ((POICircle)niCheck).getRadius() && ((POICircle)liCheck).getRadius() > 10){
//					
//					}
//				}
//				// elseif QR
//				// elseif block
//				// elseif airport
//				
//			}
//		}
//		return interestsFound;
//	}

	public ArrayList<POI> compareImages(BufferedImage lastImage, BufferedImage newImage){
		

////		ArrayList<POI> li = findObjects(lastImage, coordinates, angle);
////		ArrayList<POI> ni = findObjects(newImage, coordinates, angle);
//
//		for(POI liCheck : li){
//			for(POI niCheck : ni){
//				if(liCheck instanceof POICircle && niCheck instanceof POICircle){
//					if(((POICircle)liCheck).getRadius() == ((POICircle)niCheck).getRadius() && ((POICircle)liCheck).getRadius() > 10){
//					
//					}
//				}
//				// elseif QR
//				// elseif block
//				// elseif airport
//				
//			}
//		}
		return interestsFound;
	}
	public void findQR(Mat image, Vector3D coordinates, int angle) {

		ArrayList<POIWallPoint> fundet = new ArrayList<>();
		QRfinder findqr = new QRfinder();
		try {
			fundet = findqr.findQR(image, coordinates, angle);
			
			for(int i = 0; i < fundet.size(); i++) interestsFound.add(fundet.get(i));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  // exceptions klare jeg senere.

		
	}

	public void findCircles(Mat image, Vector3D coordinates, int angle) {

		Mat image_gray = new Mat();
		Mat circles = new Mat();

		Imgproc.cvtColor(image, image_gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(image_gray, image_gray, new Size(9, 9), 2, 2);
		Imgproc.HoughCircles(image_gray, circles, Imgproc.CV_HOUGH_GRADIENT, 1, image_gray.rows() / 8, 200, 100, 0, 0);

		for (int i = 0; i < circles.cols(); i++) {

			double circle[] = circles.get(0, i);
			int radius = (int) Math.round(circle[2]);
			objectsFound.add(new POICircle(new Vector3D(0,0,0), coordinates, radius));
		}

	}

	public void findBlocks(Mat image, Vector3D coordinates, int angle) {
		// Add code to find Blocks
	}

	public void findAirports(Mat image, Vector3D coordinates, int angle) {
		// Add code to find Airports
	}

	public Mat bufferedImageToMat(BufferedImage bi) {
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}
}

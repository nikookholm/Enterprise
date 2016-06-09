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
	
	/**
	 * Returns a single ArrayList containing points of interest, that can be located on both images.
	 * The function is used to reduce noise and possible only gather the true points of interest.
	 * POI types includes rings, blocks, QR or Airports.
	 * 
	 * @param lastImage the previous image stored
	 * @param newImage the newest image stored
	 * @return Points of Interests
	 */
	public ArrayList<POI> findObjects(BufferedImage lastImage, BufferedImage newImage, Vector3D DronePos, int angle){
		

		ArrayList<POI> li = findObjects(lastImage, DronePos, angle);
		ArrayList<POI> ni = findObjects(newImage, DronePos, angle);

		for(POI liCheck : li){
			for(POI niCheck : ni){
				if(liCheck instanceof POICircle && niCheck instanceof POICircle){
					if(((POICircle)liCheck).getQRValue() == ((POICircle)niCheck).getQRValue()){
						if(!objectsFound.contains(liCheck)) objectsFound.add(liCheck);
					
					}
				}else if(liCheck instanceof POIWallPoint && niCheck instanceof POIWallPoint){
					if(((POIWallPoint)liCheck).getQRString() == ((POIWallPoint)niCheck).getQRString()){
						if(!objectsFound.contains(liCheck)) objectsFound.add(liCheck);
					}
				}
				
				/* When expanding to more than challenge 1:
				 elseif block
				 elseif airport 
				*/ 
				
			}
			
		}
		return interestsFound;
	}

	public ArrayList<POI> findQR(BufferedImage image) {

		return findQR(bufferedImageToMat(image));
	}

	public Vector3D findCircle(BufferedImage image, Vector3D dronePos){
		POICircle circleCoordinates = findCircle(bufferedImageToMat(image), dronePos);
		
		Vector3D centerPoint = new Vector3D(400, 300, 0);
		Vector3D distanceToPoint = new Vector3D(0,0,0);
		if(circleCoordinates.getxPos() > 400){
			distanceToPoint.setXCoord(circleCoordinates.getxPos() - centerPoint.getXCoord());
		}else{
			distanceToPoint.setXCoord(-(centerPoint.getXCoord() - circleCoordinates.getxPos()));
		}
		if(circleCoordinates.getzPos() > 300){
			distanceToPoint.setZCoord(circleCoordinates.getyPos() - centerPoint.getYCoord());
		}else{
			distanceToPoint.setZCoord(-(centerPoint.getYCoord() - circleCoordinates.getyPos()));
		}
		
		return distanceToPoint;
	}
	
	/******************************private************************************/
	
	private ArrayList<POI> findObjects(BufferedImage arg0, Vector3D coordinates, int angle) {
		objectsFound.clear();

		Mat ImageMat = bufferedImageToMat(arg0);
		findQR(ImageMat);
	//	findCircles(ImageMat, coordinates, angle);
		findBlocks(ImageMat, coordinates, angle);
		findAirports(ImageMat, coordinates, angle);

		return objectsFound;
	}
	
	private ArrayList<POI> findQR(Mat image) {

		ArrayList<POIWallPoint> fundet = new ArrayList<>();
		QRfinder findqr = new QRfinder();
		try {
			fundet = findqr.findQR(image);
			
			for(int i = 0; i < fundet.size(); i++) interestsFound.add(fundet.get(i));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  // exceptions klare jeg senere.

		return interestsFound;
	}

	// needs to be changed to finding single Circle after moving to a given circles QR code
//	private void findCircles(Mat image, Vector3D coordinates, int angle) {
//
//		Mat image_gray = new Mat();
//		Mat circles = new Mat();
//
//		Imgproc.cvtColor(image, image_gray, Imgproc.COLOR_BGR2GRAY);
//		Imgproc.GaussianBlur(image_gray, image_gray, new Size(9, 9), 2, 2);
//		Imgproc.HoughCircles(image_gray, circles, Imgproc.CV_HOUGH_GRADIENT, 1, image_gray.rows() / 8, 200, 100, 0, 0);
//
//		for (int i = 0; i < circles.cols(); i++) {
//
//			double circle[] = circles.get(0, i);
//			int radius = (int) Math.round(circle[2]);
//			objectsFound.add(new POICircle(new Vector3D(0,0,0), coordinates, radius));
//		}
//
//	}

	private POICircle findCircle(Mat image, Vector3D dronePos){
		Mat image_gray = new Mat();
		Mat circles = new Mat();
		
		Imgproc.cvtColor(image, image_gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(image_gray, image_gray, new Size(9, 9), 2, 2);
		Imgproc.HoughCircles(image_gray, circles, Imgproc.CV_HOUGH_GRADIENT, 1, image_gray.rows() / 8, 200, 100, 0, 0);

		for (int i = 0; i < circles.cols(); i++) {

			double circle[] = circles.get(0, i);
			int radius = (int) Math.round(circle[2]);
			
			if(radius < 1 && radius > 0.5)
				return new POICircle(new Vector3D(circle[0], 0, circle[1]), dronePos, radius);
			
		}
		return null;
		
	}
	
	
	private void findBlocks(Mat image, Vector3D coordinates, int angle) {
		// Add code to find Blocks
	}

	private void findAirports(Mat image, Vector3D coordinates, int angle) {
		// Add code to find Airports
	}

	/*****************************buff_to_mat*****************************/
	
	private Mat bufferedImageToMat(BufferedImage bi) {
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}

}

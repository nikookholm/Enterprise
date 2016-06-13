package Navigation;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
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

	QRfinder findqr;
	ArrayList<double[]> circlesFound = new ArrayList<>();
	
	public OpenCVOperations(){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		findqr = new QRfinder();
		circlesFound = new ArrayList<>();
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

	public Vector3D findCircle(BufferedImage image, BufferedImage image2, Vector3D dronePos){
		ArrayList<POICircle> PotentialCircleCoordinates = findCircle(bufferedImageToMat(image), dronePos);
		ArrayList<POICircle> PotentialCircleCoordinates1 = findCircle(bufferedImageToMat(image2), dronePos);
		
		ArrayList<POICircle> result = new ArrayList<>();
		for(POICircle liCheck : PotentialCircleCoordinates){
			for(POICircle niCheck : PotentialCircleCoordinates1){
				if((liCheck.getRadius() == niCheck.getRadius()) && (liCheck.getCoordinates() == niCheck.getCoordinates())){
					result.add(liCheck);
				}
			}
		}
		
		Vector3D centerPoint = new Vector3D(400, 300, 0);
		Vector3D distanceToPoint = new Vector3D(0,0,0);
		if(result.get(0).getxPos() > 400){
			distanceToPoint.setXCoord(result.get(0).getxPos() - centerPoint.getXCoord());
		}else{
			distanceToPoint.setXCoord(-(centerPoint.getXCoord() - result.get(0).getxPos()));
		}
		if(result.get(0).getzPos() > 300){
			distanceToPoint.setZCoord(result.get(0).getyPos() - centerPoint.getYCoord());
		}else{
			distanceToPoint.setZCoord(-(centerPoint.getYCoord() - result.get(0).getyPos()));
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

		ArrayList<POI> fundet = new ArrayList<>();
		
		try {
			findqr.findQR(image);
			fundet = findqr.getFunderQR();
			
			for(int i = 0; i < fundet.size(); i++){
				
			}
			
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

	private ArrayList<POICircle> findCircle(Mat image, Vector3D dronePos){
		Mat image_gray = new Mat();
		Mat circles = new Mat();
		
		Imgproc.cvtColor(image, image_gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(image_gray, image_gray, new Size(9, 9), 2, 2);
		Imgproc.Canny(image_gray, image_gray, 50, 150);
		Imgproc.HoughCircles(image_gray, circles, Imgproc.CV_HOUGH_GRADIENT, 1, image_gray.rows() / 8, 200, 100, 0, 0);

		ArrayList<POICircle> results = new ArrayList<>();
	
		circlesFound.clear();
		
		for (int i = 0; i < circles.cols(); i++) {

			double circle[] = circles.get(0, i);
			circlesFound.add(circle);
			int radius = (int) Math.round(circle[2]);
			
			
			results.add(new POICircle(new Vector3D(circle[0], 0, circle[1]), dronePos, radius));
			
		}
		
				
		return results;
		
	}
	
	
	private void findBlocks(Mat image, Vector3D coordinates, int angle) {
		// Add code to find Blocks
	}

	private void findAirports(Mat image, Vector3D coordinates, int angle) {
		// Add code to find Airports
	}

	public ArrayList<double[]> getCircles(){
		return circlesFound;
	}
	/*****************************buff_to_mat*****************************/
	
	private Mat bufferedImageToMat(BufferedImage bi) {
		System.out.println("height: " + bi.getHeight()+ " width: " + bi.getWidth());
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}

}

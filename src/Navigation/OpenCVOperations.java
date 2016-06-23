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
	
	/*
	 * Constructor.
	 * OpenCV requires the System.loadLibrary to work
	 */
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
					if(((POICircle)liCheck).getCode() == ((POICircle)niCheck).getCode()){
						if(!objectsFound.contains(liCheck)) objectsFound.add(liCheck);
					
					}
				}else if(liCheck instanceof POIWallPoint && niCheck instanceof POIWallPoint){
					if(((POIWallPoint)liCheck).getCode() == ((POIWallPoint)niCheck).getCode()){
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
/*
 * Finds the QR codes in a given image
 */
	public ArrayList<POI> findQR(BufferedImage image) {

		return findQR(bufferedImageToMat(image));
	}
	
	/**
	 *  This function takes a buffered image and checks for circles in it, then finds the correct circle
	 *  and returns the Vector3D that the drone needs to adjust to.
	 * @param img
	 * @return A Vector3D position for the correct circle
	 */
/*
	public Vector3D findCircle(BufferedImage image){
		System.out.println("findimage");
		ArrayList<POICircle> result = findCircle(bufferedImageToMat(image));
		int margin = 35;
		Vector3D distanceToPoint = new Vector3D(0,10,0);

		//		ArrayList<POICircle> PotentialCircleCoordinates1 = findCircle(bufferedImageToMat(image2), dronePos);
		
		ArrayList<POICircle> result = new ArrayList<>();
		for(POICircle liCheck : PotentialCircleCoordinates){
			for(POICircle niCheck : PotentialCircleCoordinates1){
				if((liCheck.getRadius() == niCheck.getRadius()) && (liCheck.getCoordinates() == niCheck.getCoordinates())){
					result.add(liCheck);
				}
			}
		}
		if(result.size()>0){
		Vector3D centerPoint = new Vector3D(image.getWidth()/2, image.getHeight(), 0);
		if(result.get(0).getxPos() > centerPoint.getXCoord()+margin){
			distanceToPoint.setXCoord(result.get(0).getxPos() - centerPoint.getXCoord());
			distanceToPoint.setYCoord(0);
		}else if(result.get(0).getxPos() < centerPoint.getXCoord()-margin){
			distanceToPoint.setXCoord(-(centerPoint.getXCoord() - result.get(0).getxPos()));
			distanceToPoint.setYCoord(0);

		}
		if(result.get(0).getzPos() > centerPoint.getXCoord()+margin){
			//distanceToPoint.setZCoord(result.get(0).getyPos() - centerPoint.getYCoord());
			//distanceToPoint.setYCoord(0);

		}else if(result.get(0).getzPos() < centerPoint.getXCoord()-margin){
			//distanceToPoint.setZCoord(-(centerPoint.getYCoord() - result.get(0).getyPos()));
			//distanceToPoint.setYCoord(0);

		}
		}
		System.out.println(distanceToPoint.getXCoord()  +"<-<<<<<---- XXXX YYYYY---->>>>> " + distanceToPoint.getYCoord() + " ------------>>>>>>>>> Z" + distanceToPoint.getZCoord());
		return distanceToPoint;
	}
*/	
	
	public ArrayList<POICircle> findCircles(BufferedImage img){
		
				
		ArrayList<POICircle> circlesFound = new ArrayList<POICircle>();
			
		return  (circlesFound = findCircle(bufferedImageToMat(img)));
	}
	/******************************private************************************/
	/*
	 * Finds all objects in the image, calculates the coordinates for them aswell
	 * with a given droneposition and angle to the objects
	 */
	private ArrayList<POI> findObjects(BufferedImage arg0, Vector3D coordinates, int angle) {
		objectsFound.clear();

		Mat ImageMat = bufferedImageToMat(arg0);
		findQR(ImageMat);
	//	findCircles(ImageMat, coordinates, angle);
		findBlocks(ImageMat, coordinates, angle);
		findAirports(ImageMat, coordinates, angle);

		return objectsFound;
	}
	/*
	 * Checks if the QR codes found in the image is identical to older QRs found, if they arent, they will be added
	 * to the interests found
	 */
	public void checkForOld(ArrayList<POI> qrFun, ArrayList<POI> interestFound){
		String tempCode;
		int counter = 0;
		for(int i = 0; i<qrFun.size();i++){
			tempCode = qrFun.get(i).getCode();
			
			for(int j = 0; j<interestFound.size(); j++){
				if(!tempCode.equals(interestFound.get(j).getCode())){
					counter++;
				}
			}
				if(counter == interestFound.size()){
					interestFound.add(qrFun.get(i));
				}
			counter = 0;
		}
	}
	/*
	 * Finds QR codes in the given matrix image, returns all the QRs found.
	 */
	private ArrayList<POI> findQR(Mat image) {

		ArrayList<POI> fundet = new ArrayList<>();
		
		try {
			findqr.findQR(image);
			fundet = findqr.getFunderQR();
			checkForOld(fundet, interestsFound);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  // exceptions klare jeg senere.

		return interestsFound;
	}

	public ArrayList<POICircle> findCircle(Mat image/*, Vector3D dronePos*/){
		Mat image_gray = new Mat();
		Mat circles = new Mat();
		
		Imgproc.cvtColor(image, image_gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.HoughCircles(image_gray, circles, Imgproc.CV_HOUGH_GRADIENT, 1, image_gray.rows() / 8, 225, 100, 0, 0);
		ArrayList<POICircle> results = new ArrayList<>();
	
		circlesFound.clear();
		for (int i = 0; i < circles.cols(); i++) {
			
			double circle[] = circles.get(0, i);
			circlesFound.add(circle);
			int radius = (int) Math.round(circle[2]);
			double cenX = Math.round(circle[0]);
			double cenY = Math.round(circle[1]);


			double dist = ((4.45*500*720)/(3.17*radius))/10;
			results.add(new POICircle(new Vector3D(circle[0], 0, circle[1]), /*dronePos,*/ radius,dist,cenX,cenY));
			
		}
		
				
		return results;
		
	}
	
	/*
	 * Would be used for assignment 2, to find blocks
	 */
	private void findBlocks(Mat image, Vector3D coordinates, int angle) {
		// Add code to find Blocks
	}
	/*
	 * Would be used for assignment 3, to find blocks
	 */
	private void findAirports(Mat image, Vector3D coordinates, int angle) {
		// Add code to find Airports
	}

	/*
	 * Returns the circles found
	 */
	public ArrayList<double[]> getCircles(){
		return circlesFound;
	}
	/*****************************buff_to_mat*****************************/
	
	private Mat bufferedImageToMat(BufferedImage bi) {
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}

}

package Navigation;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import Common.POI;
import Common.POI.POIType;

public class OpenCVOperations {

	ArrayList<POI> objectsFound = new ArrayList<POI>();

	ArrayList<POI> interestsFound = new ArrayList<POI>();

	private ArrayList<POI> findObjects(BufferedImage arg0) {

		objectsFound.clear();

		Mat ImageMat = bufferedImageToMat(arg0);
		findQR(ImageMat);
		findCircles(ImageMat);
		findBlocks(ImageMat);
		findAirports(ImageMat);

		return objectsFound;
	}

	public ArrayList<POI> compareImages(BufferedImage lastImage, BufferedImage newImage) {

		ArrayList<POI> li = findObjects(lastImage);
		ArrayList<POI> ni = findObjects(newImage);

		return interestsFound;
	}

	public Mat findQR(Mat image) {

		boolean QRFound = false;

		if (QRFound) {
			// tempPoI.add();
		}

		return image;
	}

	public void findCircles(Mat image) {

		Mat image_gray = new Mat();
		Mat circles = new Mat();

		Imgproc.cvtColor(image, image_gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(image_gray, image_gray, new Size(9, 9), 2, 2);
		Imgproc.HoughCircles(image_gray, circles, Imgproc.CV_HOUGH_GRADIENT, 1, image_gray.rows() / 8, 200, 100, 0, 0);

		for (int i = 0; i < circles.cols(); i++) {

			double circle[] = circles.get(0, i);

			objectsFound.add(new POI(POIType.RING, Math.round(circle[0]), Math.round(circle[1]), -1));
		}

	}

	public void findBlocks(Mat image) {
		// Add code to find Blocks
	}

	public void findAirports(Mat image) {
		// Add code to find Airports
	}

	public Mat bufferedImageToMat(BufferedImage bi) {
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}
}

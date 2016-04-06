package Navigation;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import Common.POI;

public class OpenCVOperations {

	public Mat findObjects(BufferedImage arg0) {

		Mat ImageMat = bufferedImageToMat(arg0);
		Mat QR = findQR(ImageMat);
		Mat circles = findCircles(ImageMat);
		Mat blocks = findBlocks(ImageMat);
		Mat airports = findAirports(ImageMat);
		
		return ImageMat;
	}
	
	public ArrayList<Mat> findObjects(BufferedImage arg0, BufferedImage arg1){
		ArrayList<Mat> test = new ArrayList<Mat>();
		return test;
	}
	
	public ArrayList<POI> compareImages(BufferedImage lastImage, BufferedImage newImage) {
		
		ArrayList<POI> interestsFound = new ArrayList<POI>();

		return interestsFound;
	}

	public Mat findQR(Mat image) {
		
		boolean QRFound = false;

		if (QRFound) {
			// tempPoI.add();
		}

		return image;
	}

	public Mat findCircles(Mat image) {
		Mat image_gray = new Mat();
		Mat circles = new Mat();

		Imgproc.cvtColor(image, image_gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(image_gray, image_gray, new Size(9, 9), 2, 2);
		Imgproc.HoughCircles(image_gray, circles, Imgproc.CV_HOUGH_GRADIENT, 1, image_gray.rows() / 8, 200, 100, 0, 0);

		return circles;
	}

	public Mat findBlocks(Mat image) {
		return image;
	}

	public Mat findAirports(Mat image) {
		return image;
	}

	public Mat bufferedImageToMat(BufferedImage bi) {
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}
}

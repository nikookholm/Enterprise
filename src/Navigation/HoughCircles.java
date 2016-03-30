package Navigation;

import javax.print.attribute.Size2DSyntax;
import javax.swing.JFrame;

import Navigation.PanelVid;
import org.opencv.core.Core;
import org.opencv.core.CvType;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import Common.POI;
public class HoughCircles {
	
	private JFrame frameCamera;
	private PanelVid panelCamera = new PanelVid();
	private JFrame cameraFilter;
	private PanelVid panelFilter = new PanelVid();
	private JFrame billedeTest;
	private PanelVid billede = new PanelVid();
	
public void findCircles(BufferedImage bi){
		
	}
	
	
	private ArrayList<Point> PointsOfInterest = new ArrayList<Point>();

	public void CaptureWebcam(BufferedImage argImage) throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		//VideoCapture frame = new VideoCapture(0);
		Mat image = new Mat();
		image = bufferedImageToMat(argImage);
		
		Mat imageFilter = new Mat();
		Mat imageFilter2 = new Mat();
		Size fff = new Size();
		

		/*try {
			frame.read(image);
		} catch (Exception e) {
			throw new Exception("Kamera virker ikke.");
		}*/

		//setFramesSizes(image);

		System.out.println("der");
		Mat fundetCirkler = new Mat(); // matrix til at indsætte de cirkler der bliver fundet.
		Mat fundetlinjer = new Mat();
		
				
		if (true) {
			/*
			 * Parameter definitioner til Hough alghoritmen
			 * 
			 */
			
			fff.height = 3;
			fff.width = 3;
			int radius;
			double j1 = 2;
			double j2 = 30;
			double j3 = 200;
			double j4 = 100;
			Point centrum;
			System.out.println("ind123");
			if(true) {
				System.out.println("ind12");
				//frame.read(image);
				// vis kamereat fungere, hopper den ind i denne funktion.
				if (!image.empty()) {
					System.out.println("ind");
					Imgproc.cvtColor(image, imageFilter2, Imgproc.COLOR_BGR2GRAY); // gør billedet til grådt og indsætter det i den filtreret JFrame
					
					Imgproc.Canny(imageFilter2, imageFilter, 10, 100);
					
				
					Imgproc.HoughLinesP(imageFilter, fundetlinjer, 1, Math.PI/180,250,30,150);
					
				System.out.println("detikkeher");
					for (int hi = 0; hi < fundetlinjer.cols(); hi++){
						double vfundetLinjer[] = fundetlinjer.get(0, hi);
						
						if (vfundetLinjer == null)
							break;
						
					
						
						Point[] fundetP = new Point[2];
						
						fundetP[0] = new Point(vfundetLinjer[0],vfundetLinjer[1]);
						fundetP[1] = new Point(vfundetLinjer[2],vfundetLinjer[3]);
						
					
							
							Core.line(image, fundetP[1], fundetP[0], new Scalar(0,0,0));
						
						
						
						
						
					}
					
					
					/*
					 * Tegner cirkel rundt om de bolde den finder igennem videon. den udregner også centrum og radius
					 * 
					 */
					
					Imgproc.GaussianBlur(imageFilter2, imageFilter2, fff, 3, 3); // et filter det gør det mere "blurred" det hjælper åbenbart programmet med at finde boldene hurtigere og mere præcist
					Imgproc.HoughCircles(imageFilter2, fundetCirkler, Imgproc.CV_HOUGH_GRADIENT, j1, j2, j3, j4, 70, 100); // alghoritmen der finder cirklerne, de sidste 2 parameter er min radius størrelse den skal finde og max.
					
					for (int i = 0; i < fundetCirkler.cols(); i++) { // tjekker om den har fundet nogle cirkler og hvor mange, og går igennem alle de cirkler den finder.
						double vfundetCirkler[] = fundetCirkler.get(0, i);
						
						if (vfundetCirkler == null) 
							// vis den ikke finder nogle cirkler, hopper den ud af for loop.
							break;
						centrum = new Point(Math.round(vfundetCirkler[0]), Math.round(vfundetCirkler[1])); // udregner centrum af fra de punkter den har fundet på billedet
						PointsOfInterest.add(centrum);
						radius = (int) Math.round(vfundetCirkler[2]); // udregner radius.
						Core.circle(image, centrum, radius, new Scalar(0, 0, 0), 1); // tegner cirklen 
						Core.circle(image, centrum, 3, new Scalar(255,255,255),3);
						System.out.println(radius);
					}

				} else {
					throw new Exception("Kunne ikke læse kamera"); // smider exceptions	
				}
				
				//updateFrames(image, imageFilter); 
				billede.setImageWithMat(image);
			}

		} else {
			throw new Exception("Kunne ikke få billeder fra kamera."); // smider exceptions																		// exception																	// vis																		// kamera																		// ikke																		// virker
		}

	}
	

	/*
	 * Updater frames, livefeed.
	 */

	private void updateFrames(Mat image, Mat thresholdedImage) {
		setPanelsImages(image, thresholdedImage);
		repaintFrames();
	}

	/*
	 * Tilføjer nyt billede
	 */

	private void repaintFrames() {
		frameCamera.repaint();
		cameraFilter.repaint();

	}

	/*
	 * >størrelse af selve video frames.
	 * 
	 */

	private void setFramesSizes(Mat image) {
		frameCamera.setSize(image.width() + 80, image.height() + 120);
		cameraFilter.setSize(image.width() + 20, image.height() + 60);

	}

	/*
	 * bygger JFrame til livefeed. både til almendlig og til filtreret.
	 * 
	 */

	private JFrame createFrame(String Navn, PanelVid cameraFilter2) {
		JFrame frame = new JFrame(Navn);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		frame.setContentPane(cameraFilter2);
		frame.setVisible(true);

		return frame;
	}

	/*
	 * Tilslutter selve billede med JFrame.
	 * 
	 */
	private void setPanelsImages(Mat image, Mat imageFilter) {
		panelCamera.setImageWithMat(image);
		panelFilter.setImageWithMat(imageFilter);
	}
	
	private Mat bufferedImageToMat(BufferedImage bi){
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}
	
	public ArrayList<Point> getPOI(){
		
		return PointsOfInterest;
	}

}

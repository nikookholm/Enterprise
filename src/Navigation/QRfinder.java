package Navigation;

/*
 * 
 * ''''''''''''''IMPORTS'''''''''''''''''''
 */

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import de.yadrone.base.video.ImageListener;
import POI.POI;
import POI.POICircle;
import POI.POIWallPoint;
import Vector.Vector3D;

public class QRfinder {

	/*
	 * 
	 * ''''''''''''''GLOBALE VARIABLER'''''''''''''''''''
	 */

	private final int nord = 0;
	private final int eas = 1;
	private final int syd = 2;
	private final int ves = 3;
	private BufferedImage qrdet;
	private ArrayList<QRPoi> QRFun = new ArrayList<QRPoi>();
	private Mat traces;
	private BufferedImage debuImg;
	private double disToQR;
	private ArrayList<POI> funderQR = new ArrayList<POI>();
	private int POIcounter = 0;

	OpenCVOperations CV;
	/*
	 * 
	 * ''''''''''''''QRFINDEREN********************** INPUT : BILLEDE I MAT
	 * FORM.
	 * 
	 * OUTPUT: POI LISTE SOM INHOLDER ENTEN POICIRCLER ELLER POIWALLPOINTS
	 * '''''''''''''''''''
	 */

	public void findQR(Mat newImage) throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		double lin1, lin2, lin3;

		int polen = 0;

		Mat grey = new Mat(newImage.size(), CvType.makeType(newImage.depth(), 1));
		Mat edges = new Mat(newImage.size(), CvType.makeType(newImage.depth(), 1));
		Mat qr = new Mat();
		Mat qr_raw = new Mat();
		Mat qr_gray = new Mat();
		Mat qr_thres = new Mat();
		traces = new Mat(newImage.size(), CvType.makeType(newImage.depth(), 1));

		int mark;

		if (!newImage.empty()) {

			qr = Mat.zeros(400, 400, CvType.CV_8UC3);
			qr_raw = Mat.zeros(400, 400, CvType.CV_8UC3);
			qr_gray = Mat.zeros(400, 400, CvType.CV_8UC1);
			qr_thres = Mat.zeros(400, 400, CvType.CV_8UC1);

			ArrayList<MatOfPoint> countersFundet = new ArrayList<MatOfPoint>();
			MatOfInt4 heica = new MatOfInt4();

			Imgproc.cvtColor(newImage, grey, Imgproc.COLOR_BGR2GRAY);
			Imgproc.Canny(grey, edges, 145, 150, 3, false);
			Imgproc.findContours(edges, countersFundet, heica, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
			mark = 0;

			Moments[] momm = new Moments[countersFundet.size()];

			int antalF = 0;
			int count = 0;

			ArrayList<Integer> boxEs = new ArrayList<Integer>();

			for (int i = 0; i < heica.rows(); i++) {
				for (int j = 0; j < heica.cols(); j++) {
					double[] fun = heica.get(i, j);
//					 if (fun[0] != -1) {
//					 count = 0;
//					 }
					if (fun[1] != -1) {
						count = 0;
					}

					count++;
					if (count == 5) {
						count = 0;
						antalF = j - 4;

						boxEs.add(antalF);
						mark++;

					}
				}
			}

			Point[] poinF = new Point[countersFundet.size()];
			for (int i = 0; i < countersFundet.size(); i++) {
				momm[i] = Imgproc.moments(countersFundet.get(i), false);

				poinF[i] = new Point((momm[i].get_m10() / momm[i].get_m00()), (momm[i].get_m01() / momm[i].get_m00()));

			}

			List<Point[]> cornerList = new ArrayList<>();
			MatOfPoint2f tempPoints = new MatOfPoint2f();

			for (int i = 0; i < boxEs.size(); i++) {
				tempPoints = corn(countersFundet, boxEs.get(i), 1, tempPoints);
				cornerList.add(tempPoints.toArray());

			}

			
			MatOfPoint2f fin1 = new MatOfPoint2f();
			//
			Mat warp = new Mat();

			List<Point> finP = new ArrayList<Point>(fin1.toList());
			//
			finP.add(new Point(0, 0));
			finP.add(new Point(qr.cols(), 0));
			finP.add(new Point(qr.cols(), qr.rows()));
			finP.add(new Point(0, qr.rows()));
			//
			Point[] toArFin = finP.toArray(new Point[finP.size()]);
			for (int abs = 0; abs < cornerList.size(); abs++) {
				MatOfPoint2f srcT = new MatOfPoint2f(cornerList.get(abs));
				MatOfPoint2f finT = new MatOfPoint2f(toArFin);

				if (srcT.total() == 4 && finT.total() == 4) {
					warp = Imgproc.getPerspectiveTransform(srcT, finT);

					Imgproc.warpPerspective(newImage, qr_raw, warp, new Size(qr.cols(), qr.rows()));

					Imgproc.cvtColor(qr_raw, qr_gray, Imgproc.COLOR_RGB2GRAY);
					Imgproc.threshold(qr_gray, qr_thres, 127, 255, Imgproc.THRESH_BINARY);

					qrdet = new BufferedImage(qr_gray.width(), qr_gray.height(), BufferedImage.TYPE_BYTE_GRAY);

					byte[] data = ((DataBufferByte) qrdet.getRaster().getDataBuffer()).getData();

					qr_gray.get(0, 0, data);
					String result = decode(qrdet);
					if (result != " ") {

						disToQR = (4.45*400*720)/(3.17*distance(cornerList.get(abs)[0], cornerList.get(abs)[3]));
						QRFun.add(new QRPoi(0, 0, 0));
						QRFun.get(POIcounter).setCode(result);
						QRFun.get(POIcounter).setQRimg(qrdet);
						QRFun.get(POIcounter).setDistance(disToQR/10);
						POIcounter++;

					}

				}

				if (boxEs.size() != 0) {

				}

				int debuk = 1;

				if (debuk == 1) {
					Core.circle(newImage, new Point(10, 20), 5, new Scalar(50, 100, 50), -1, 8, 0);

					Imgproc.drawContours(newImage, countersFundet, boxEs.get(abs), new Scalar(100, 50, 255), 3, 8,
							heica, 0, new Point(-1, -1));

				}}
				matToImg switcher = new matToImg();
				sortsQR();

				debuImg = switcher.matToBufferedImage(qr_gray);
				

			
		}

		else {
			throw new Exception("Kunne ikke fÃ¥ billeder fra kamera.");
		}
	}

	/*
	 * 
	 * '''''''''''''' Get metode til listen af POI '''''''''''''''''''
	 */
	public ArrayList<POI> getFunderQR() {
		return funderQR;
	}
	

	/*
	 * 
	 * '''''''''''''' SORTer QR i POIWallpoints og POICircle '''''''''''''''''''
	 */

	private void sortsQR() {
		funderQR.clear();
		for (int j = 0; j < QRFun.size(); j++) {
			if (QRFun.get(j).getCode().startsWith("W")) {
				Point qrcord = QRFun.get(j).getCords();
				funderQR.add(new POIWallPoint(QRFun.get(j).getCode(), QRFun.get(j).getDistance(),
						new Vector3D(qrcord.x, qrcord.y, 150)));
			}

			else if (QRFun.get(j).getCode().startsWith("P")) {
				funderQR.add(new POICircle(QRFun.get(j).getCode(), QRFun.get(j).getDistance()));
			}
		}
	}

	/*
	 * 
	 * ''''''''''''''Metode til at finde hjørnernes punkter af de 3 black boxes
	 * til QR koden output er liste af hjørne punkternes x/y.
	 * '''''''''''''''''''
	 */

	private MatOfPoint2f corn(ArrayList<MatOfPoint> Dj, int punk1, double slop, MatOfPoint2f cd) {

		Rect box;
		box = Imgproc.boundingRect(Dj.get(punk1));

		Point op0 = new Point(), op1 = new Point(), ned0 = new Point(), ned1 = new Point();

		Point A = new Point(0, 0), S = new Point(0, 0), D = new Point(0, 0), F = new Point(0, 0), Z = new Point(0, 0),
				X = new Point(0, 0), C = new Point(0, 0), V = new Point(0, 0);

		A = box.tl();

		S.x = box.br().x;

		S.y = box.tl().y;
		D = box.br();
		F.x = box.tl().x;
		F.y = box.br().y;

		Z.x = (A.x + S.x) / 2;
		Z.y = A.y;

		X.x = S.x;
		X.y = (S.y + D.y) / 2;

		C.x = (D.x + F.x) / 2;
		C.y = D.y;

		V.x = F.x;
		V.y = (F.y + A.y) / 2;

		double[] maksi = new double[4];

		maksi[0] = 0.0;
		maksi[1] = 0.0;
		maksi[2] = 0.0;
		maksi[3] = 0.0;

		double pd1 = 0.0;
		double pd2 = 0.0;

		Point[] sj = Dj.get(punk1).toArray();

		if (slop > 5 || slop < -5) {
			double temp_dist;
			for (int i = 0; i < Dj.get(punk1).total(); i++) {
				pd1 = lineCalc(D, A, sj[i]);
				pd2 = lineCalc(S, F, sj[i]);

				if ((pd1 >= 0.0) && (pd2 > 0.0)) {

					temp_dist = distance(sj[i], Z);
					if (temp_dist > maksi[1]) {
						maksi[1] = temp_dist;
						op1 = sj[i];
					}

				} else if ((pd1 > 0.0) && (pd2 <= 0.0)) {

					temp_dist = distance(sj[i], X);
					if (temp_dist > maksi[2]) {
						maksi[2] = temp_dist;
						ned0 = sj[i];
					}

				} else if ((pd1 <= 0.0) && (pd2 < 0.0)) {

					temp_dist = distance(sj[i], C);
					if (temp_dist > maksi[3]) {
						maksi[3] = temp_dist;
						ned1 = sj[i];
					}

				} else if ((pd1 < 0.0) && (pd2 >= 0.0)) {

					temp_dist = distance(sj[i], V);

					if (temp_dist > maksi[0]) {
						maksi[0] = temp_dist;
						op0 = sj[i];
					}

				} else
					continue;

			}

		}

		else {
			double temp_dist;
			double halfx = (A.x + S.x) / 2;
			double halfy = (A.y + F.y) / 2;

			for (int i = 0; i < sj.length; i++) {

				if ((sj[i].x < halfx) && (sj[i].y <= halfy)) {

					temp_dist = distance(sj[i], D);
					if (temp_dist > maksi[2]) {
						maksi[2] = temp_dist;
						op0 = sj[i];
					}

				} else if ((sj[i].x >= halfx) && (sj[i].y < halfy)) {

					temp_dist = distance(sj[i], F);
					if (temp_dist > maksi[3]) {
						maksi[3] = temp_dist;
						op1 = sj[i];
					}

				} else if ((sj[i].x > halfx) && (sj[i].y >= halfy)) {

					temp_dist = distance(sj[i], A);
					if (temp_dist > maksi[0]) {
						maksi[0] = temp_dist;
						ned0 = sj[i];
					}

				} else if ((sj[i].x <= halfx) && (sj[i].y > halfy)) {

					temp_dist = distance(sj[i], S);
					if (temp_dist > maksi[1]) {
						maksi[1] = temp_dist;
						ned1 = sj[i];
					}

				}
			}

		}

		List<Point> fin = new ArrayList<Point>(cd.toList());

		fin.add(op0);
		fin.add(op1);
		fin.add(ned0);
		fin.add(ned1);

		Point[] finTem = fin.toArray(new Point[fin.size()]);

		MatOfPoint2f fiin = new MatOfPoint2f(finTem);

		cd = fiin;

		return cd;
	}

	/*
	 * 
	 * '''''''''''''' Metode der tjekker QRkodens vinkel, og rette den "op" så
	 * den kan læses. '''''''''''''''''''
	 */

	private MatOfPoint2f update(int nordpolen, MatOfPoint2f inp, MatOfPoint2f outp) {
		Point m0 = new Point(), m1 = new Point(), m2 = new Point(), m3 = new Point();

		Point[] inparray = inp.toArray();
		List<Point> outarray = new ArrayList<Point>(outp.toList());

		if (nordpolen == nord) {
			m0 = inparray[0];
			m1 = inparray[1];
			m2 = inparray[2];
			m3 = inparray[3];
		}
		if (nordpolen == eas) {
			m0 = inparray[1];
			m1 = inparray[2];
			m2 = inparray[3];
			m3 = inparray[0];
			;
		}
		if (nordpolen == syd) {
			m0 = inparray[2];
			m1 = inparray[3];
			m2 = inparray[0];
			m3 = inparray[1];
		}
		if (nordpolen == ves) {
			m0 = inparray[3];
			m1 = inparray[0];
			m2 = inparray[1];
			m3 = inparray[2];
		}

		outarray.add(m0);
		outarray.add(m1);
		outarray.add(m2);
		outarray.add(m3);

		Point[] arrg = outarray.toArray(new Point[outarray.size()]);

		MatOfPoint2f jas = new MatOfPoint2f(arrg);

		outp = jas;

		return outp;
	}

	/*
	 * 
	 * '''''''''''''' Metode der finder det sidste punkt på QR koden, altså
	 * punktet i højre nedre hjørne '''''''''''''''''''
	 */

	private void LastPoint(Point a1, Point a2, Point b1, Point b2) {

		Point p = new Point(a1.x, a1.y);
		Point p1 = new Point(b1.x, b1.y);
		Point r = new Point(a2.x - a1.x, a2.y - a1.y);
		Point s = new Point(b2.x - b1.x, b2.y - b1.y);
		Point o = new Point(p1.x - p.x, p1.y - p.y);

		if (cross(r, s) == 0) {

			return;
		}

		double t = cross(o, s) / cross(r, s);

		Point sd = new Point(t * r.x, t * r.y);

		Point ds = new Point(p.x + sd.x, p.y + sd.y);

		// dj = ds;

	}

	/*
	 * 
	 * ''''''''''''''Matematik der bruges til at dinde hjørnerne
	 * '''''''''''''''''''
	 */

	private double cross(Point d, Point s) {

		return d.x * s.y - d.y * s.x;
	}

	/*
	 * 
	 * '''''''''''''' Matematik. bruges til at finde retningen af QR koden
	 * '''''''''''''''''''
	 */

	private double slope(Point L, Point D) {
		double dy, dx;

		dx = D.x - L.x;
		dy = D.y - L.y;

		if (dy != 0) {

			return (dy / dx);
		} else
			return 0;
	}

	/*
	 * 
	 * '''''''''''''' funktion der finder længden mellem 3 punkter. Også kaldt
	 * Pythagoras :) '''''''''''''''''''
	 */
	private double lineCalc(Point d, Point s, Point t) {
		double a, b, c, pdist;

		a = -((s.y - d.y) / (s.x - d.x));
		b = 1;
		c = (((s.y - d.y) / (s.x - d.x)) * d.x) - d.y;

		pdist = (a * t.x + (b * t.y) + c) / Math.sqrt((a * a) + (b * b));

		return pdist;

	}

	/*
	 * 
	 * '''''''''''''' METODER der finde distance mellem 2 punkter i et billede
	 * OBS. DET I PIXELS '''''''''''''''''''
	 */

	private double distance(Point punkt1, Point punkt2) {

		return Math.sqrt(Math.pow(Math.abs(punkt1.x - punkt2.x), 2) + Math.pow(Math.abs(punkt1.y - punkt2.y), 2));
	}

	/*
	 * 
	 * '''''''''''''' QR kode læseren. input QRkode i buffered image form.
	 * 
	 * output String af kodens tekst. '''''''''''''''''''
	 */
	private String decode(BufferedImage image) throws IOException {

		QRCodeReader reader = new QRCodeReader();

		try {

			LuminanceSource source = new BufferedImageLuminanceSource(image);

			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Result result = reader.decode(bitmap);

			if (result != null) {

				return result.getText();
			}
		} catch (NotFoundException e) {
		} catch (ChecksumException e) {
		} catch (FormatException e) {
		}

		return " ";
	}

	// public List<QRPoi> getQRFun() {
	// return QRFun;
	// }

	//
	// public BufferedImage getQrdet() {
	// return qrdet;
	// }

	/*
	 * 
	 * ''''''''''''''Metode til at finde centrum af QR koderne. DE LIGGER INDE I
	 * QRPOI VIS MAN SKAL BRUGE DEM. '''''''''''''''''''
	 */
	private Point centrumPoint(Point corn1, Point lastCorn) {
		Point cen;

		double len = distance(corn1, lastCorn);
		cen = new Point(corn1.x + (len / 2), corn1.y + (len / 2));

		return cen;
	}

	/*
	 * 
	 * '''''''''''''' BILLEDET HVOR DER ER TEGNET DE DETECTEDE QQRKODER
	 * '''''''''''''''''''
	 */
	public BufferedImage getDebugImg() {
		return debuImg;
	}

	/*
	 * 
	 */
	public ArrayList<QRPoi> getQRFun() {
		return QRFun;
	}

	/*
	 * 
	 * '''''''''''''' METODE TIL AT UDREGNE DRONENS POSITION UD FRA 3 VÆGMÆRKER.
	 * INPUT 3 VÆKMÆRKER OUTPUT POSITION I X/Y '''''''''''''''''''
	 */

	public Point currentDronePosition(QRPoi wallmark1, QRPoi wallmark2, QRPoi wallmark3) {
		Point postion;
		double x, y;

		double a1 = wallmark1.getX();
		double b1 = wallmark2.getX();
		double c1 = wallmark3.getX();
		double a2 = wallmark1.getY();
		double b2 = wallmark2.getY();
		double c2 = wallmark3.getY();
		double r1 = wallmark1.getDistance();
		double r2 = wallmark2.getDistance();
		double r3 = wallmark3.getDistance();

		double an1 = a1 * a1, bn1 = b1 * b1, cn1 = c1 * c1, an2 = a2 * a2, bn2 = b2 * b2, cn2 = c2 * c2, rl1 = r1 * r1,
				rl2 = r2 * r2, rl3 = r3 * r3;

		double numeratorY = (b1 - a1) * (cn1 + cn2 - rl3) + (a1 - c1) * (bn1 + bn2 - rl2)
				+ (c1 - b1) * (an1 + an2 - rl1);
		double denumY = 2 * (c2 * (b1 - a1) + b2 * (a1 - c1) + a2 * (c1 - b1));

		y = numeratorY / denumY;

		double numeratorX = rl2 - rl1 + an1 - bn1 + an2 - bn2 - 2 * (a2 - b2) * y;
		double denumX = 2 * (a1 - b1);
		x = numeratorX / denumX;

		postion = new Point(x, y);

		return postion;

	}

}

package Navigation;

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

import POI.POI;
import POI.POIWallPoint;
import Vector.Vector3D;

public class QRfinder {
	private final int nord = 0;
	private final int eas = 1;
	private final int syd = 2;
	private final int ves = 3;
	private BufferedImage qrdet;
	private ArrayList<QRPoi> QRFun = new ArrayList<QRPoi>();
	private Mat traces;
	private BufferedImage debuImg;
	private double disToQR;

	private static Point dj = new Point();

	public ArrayList<POIWallPoint> findQR(Mat newImage) throws Exception {
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
			Imgproc.Canny(newImage, edges, 100, 200, 3, false);
			Imgproc.findContours(edges, countersFundet, heica, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
			mark = 0;

			Moments[] momm = new Moments[countersFundet.size()];

			int antalF = 0;
			int count = 0;

			ArrayList<Integer> boxEs = new ArrayList<Integer>();

			for (int i = 0; i < heica.rows(); i++) {
				for (int j = 0; j < heica.cols(); j++) {
					double[] fun = heica.get(0, j);
					if (fun[0] != -1) {
						count = 0;
					}
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

			List<MatOfPoint2f> conn = new ArrayList<MatOfPoint2f>(boxEs.size());

			for (int i = 0; i < boxEs.size(); i++) {

				MatOfPoint2f tempp = new MatOfPoint2f();

				tempp = corn(countersFundet, boxEs.get(i), 1, tempp);

				conn.add(tempp);
			}

			List<Point[]> punktCorn = new ArrayList<Point[]>(boxEs.size());
			List<Integer> removeP = new ArrayList<Integer>(3);

			for (int i = 0; i < boxEs.size(); i++) {

				punktCorn.add(conn.get(i).toArray());
			}

			List<String> DecodeQR = new ArrayList<String>();

			int check = 0;

			List<Integer> taken = new ArrayList<>(3);
			int tjek;

			if (boxEs.size() > 2) {

				for (tjek = 0; tjek < boxEs.size(); tjek++) {
					if (boxEs.size() > 2) {
						Point p1 = new Point();
						Point p2 = new Point();
						Point pn1 = new Point();
						Point[] pjA;
						Point[] pjB;
						if (tjek + 1 < punktCorn.size()) {

							pjA = punktCorn.get(tjek);
							p1 = pjA[0];
							p2 = pjA[1];
							double dist1 = distance(p1, p2);
							double maksV = (dist1 * 3) + (dist1 / 2);
							double minV = (dist1 * 2) - (dist1 / 2);

							for (int j = tjek + 1; j < boxEs.size(); j++) {

								pjB = punktCorn.get(j);
								pn1 = pjB[0];
								double dist2p = distance(p1, pn1);
								if (dist2p < maksV && dist2p > minV) {
									if (check == 0) {
										int deal = boxEs.get(tjek);
										taken.add(deal);

										removeP.add(tjek);
										check++;
									}
									int real = boxEs.get(j);
									taken.add(real);
									removeP.add(j);
									if (taken.size() == 3) {

										QRFun.add(new QRPoi(taken.get(0), taken.get(1), taken.get(2)));
										taken.clear();
										;

										int j1 = removeP.get(0);
										int j2 = removeP.get(1);
										int j3 = removeP.get(2);

										boxEs.remove(j3);
										boxEs.remove(j2);
										boxEs.remove(j1);
										punktCorn.remove(j3);
										punktCorn.remove(j2);
										punktCorn.remove(j1);

										removeP.clear();
										check = 0;
										tjek = -1;

										j = j + 10000;

									}
								}
							}

						}
					}
				}

			}

			for (int i = 0; i < QRFun.size(); i++) {
				int top = 0, mid = 0, bot = 0;

				if (mark >= 3) {
					lin1 = distance(poinF[QRFun.get(i).getTop()], poinF[QRFun.get(i).getMid()]);
					lin2 = distance(poinF[QRFun.get(i).getTop()], poinF[QRFun.get(i).getBot()]);
					lin3 = distance(poinF[QRFun.get(i).getMid()], poinF[QRFun.get(i).getBot()]);

					if (lin1 > lin2 && lin1 > lin3) {
						top = QRFun.get(i).getBot();
						mid = QRFun.get(i).getTop();
						bot = QRFun.get(i).getMid();

					}
					if (lin2 > lin1 && lin2 > lin3) {
						top = QRFun.get(i).getMid();
						mid = QRFun.get(i).getTop();
						bot = QRFun.get(i).getBot();
					}
					if (lin3 > lin2 && lin3 > lin1) {
						top = QRFun.get(i).getTop();
						mid = QRFun.get(i).getMid();
						bot = QRFun.get(i).getBot();

					}
					int top1 = top;
					int mid1 = 0;
					int bot1 = 0;

					double slo = slope(poinF[mid], poinF[bot]);

					double dist = lineCalc(poinF[mid], poinF[bot], poinF[top]);

					if (slo == 0) {
						mid1 = mid;
						bot1 = bot;

					}

					else if (slo < 0 && dist < 0) {
						mid1 = mid;
						bot1 = bot;
						polen = nord;

					}

					else if (slo > 0 && dist < 0) {
						mid1 = bot;
						bot1 = mid;
						polen = eas;
					} else if (slo < 0 && dist > 0) {
						mid1 = bot;
						bot1 = mid;
						polen = syd;
					} else if (slo > 0 && dist > 0) {
						mid1 = mid;
						bot1 = bot;
						polen = ves;
					}

					if (top1 < countersFundet.size() && mid1 < countersFundet.size() && bot1 < countersFundet.size()
							&& Imgproc.contourArea(countersFundet.get(top1)) > 10
							&& Imgproc.contourArea(countersFundet.get(mid1)) > 10
							&& Imgproc.contourArea(countersFundet.get(bot1)) > 10) {
						MatOfPoint2f jim = new MatOfPoint2f(), dim = new MatOfPoint2f(), tim = new MatOfPoint2f(),
								jimTemp = new MatOfPoint2f(), dimTemp = new MatOfPoint2f(),
								timTemp = new MatOfPoint2f();

						MatOfPoint2f src1 = new MatOfPoint2f();
						MatOfPoint2f fin1 = new MatOfPoint2f();

						Mat warp = new Mat();

						jimTemp = corn(countersFundet, top1, slo, jimTemp);
						timTemp = corn(countersFundet, mid1, slo, timTemp);
						dimTemp = corn(countersFundet, bot1, slo, dimTemp);

						jim = update(polen, jimTemp, jim);
						tim = update(polen, timTemp, tim);
						dim = update(polen, dimTemp, dim);

						Point[] m = tim.toArray();
						Point[] n = dim.toArray();
						Point[] o = jim.toArray();

						List<Point> srcP = new ArrayList<Point>(src1.toList());

						double x1 = (220.9319 / distance(o[0], n[1]));

						disToQR = Math.pow(x1, 1 / 0.9583);

						LastPoint(n[1], n[2], m[3], m[2]);

						QRFun.get(i).setLP(dj);

						Point cen = centrumPoint(o[0], n[1]);

						QRFun.get(i).setCentrum(cen);

						srcP.add(o[0]);
						srcP.add(n[1]);
						srcP.add(dj);
						srcP.add(m[3]);

						List<Point> finP = new ArrayList<Point>(fin1.toList());

						finP.add(new Point(0, 0));
						finP.add(new Point(qr.cols(), 0));
						finP.add(new Point(qr.cols(), qr.rows()));
						finP.add(new Point(0, qr.rows()));

						Point[] toArSrc = srcP.toArray(new Point[srcP.size()]);
						Point[] toArFin = finP.toArray(new Point[finP.size()]);

						MatOfPoint2f srcT = new MatOfPoint2f(toArSrc);
						MatOfPoint2f finT = new MatOfPoint2f(toArFin);

						if (srcT.total() == 4 && finT.total() == 4) {
							warp = Imgproc.getPerspectiveTransform(srcT, finT);

							Imgproc.warpPerspective(newImage, qr_raw, warp, new Size(qr.cols(), qr.rows()));
							Imgproc.copyMakeBorder(qr_raw, qr, 10, 10, 10, 10, Imgproc.BORDER_CONSTANT,
									new Scalar(255, 255, 255));

							Imgproc.cvtColor(qr, qr_gray, Imgproc.COLOR_RGB2GRAY);
							Imgproc.threshold(qr_gray, qr_thres, 127, 255, Imgproc.THRESH_BINARY);

							qrdet = new BufferedImage(qr_gray.width(), qr_gray.height(), BufferedImage.TYPE_BYTE_GRAY);

							byte[] data = ((DataBufferByte) qrdet.getRaster().getDataBuffer()).getData();

							qr_thres.get(0, 0, data);
							String result = decode(qrdet);

							if (result != " ") {
								QRFun.get(i).setCode(result);
								QRFun.get(i).setQRimg(qrdet);
								QRFun.get(i).setDistance(disToQR);
							}
							if (result == " ") {
								for (int lysloop = 0; lysloop < 10; lysloop++) {
									double alpha = 0.15 * lysloop;
									double beta = 8 * lysloop;
									qr_gray.convertTo(qr_gray, -1, alpha, beta);
									qr_gray.get(0, 0, data);
									result = decode(qrdet);
									if (result != " ") {
										QRFun.get(i).setCode(result);
										QRFun.get(i).setQRimg(qrdet);
										QRFun.get(i).setDistance(disToQR);
										lysloop = 15;
									}
								}
							}
							ArrayList<Integer> rigtigeQR = new ArrayList<>();
							int qrFuncount = 0;
							for (int v = 0; v < QRFun.size(); v++) {
								if (QRFun.get(v).getCode() != " ") {
									rigtigeQR.add(v);
									qrFuncount++;
								}


							}

						}

						int debuk = 1;

						if (debuk == 1) {
							if (slo > 5)
								Core.circle(newImage, new Point(10, 20), 5, new Scalar(150, 0, 50), -1, 8, 0);
							else if (slo < -5)
								Core.circle(newImage, new Point(10, 20), 5, new Scalar(50, 100, 50), -1, 8, 0);

							Imgproc.drawContours(newImage, countersFundet, top1, new Scalar(100, 50, 255), 3, 8, heica, 0,
									new Point(-1, -1));
							Imgproc.drawContours(newImage, countersFundet, mid1, new Scalar(100, 50, 255), 3, 8, heica, 0,
									new Point(-1, -1));
							Imgproc.drawContours(newImage, countersFundet, bot1, new Scalar(100, 50, 255), 3, 8, heica, 0,
									new Point(-1, -1));

							Core.circle(newImage, o[0], 10, new Scalar(0, 100, 0), 4, 8, 0);
							Core.circle(newImage, o[1], 10, new Scalar(0, 100, 0), 4, 8, 0);
							Core.circle(newImage, o[2], 10, new Scalar(0, 100, 0), 4, 8, 0);
							Core.circle(newImage, o[3], 10, new Scalar(0, 100, 0), 4, 8, 0);

							Core.circle(newImage, n[0], 10, new Scalar(0, 100, 0), 4, 8, 0);
							Core.circle(newImage, n[1], 10, new Scalar(0, 0, 0), 4, 8, 0);
							Core.circle(newImage, n[2], 10, new Scalar(0, 100, 0), 4, 8, 0);
							Core.circle(newImage, n[3], 10, new Scalar(0, 100, 0), 4, 8, 0);

							Core.circle(newImage, m[0], 10, new Scalar(0, 100, 0), 4, 8, 0);
							Core.circle(newImage, m[1], 10, new Scalar(0, 0, 255), 4, 8, 0);
							Core.circle(newImage, m[2], 10, new Scalar(0, 100, 0), 4, 8, 0);
							Core.circle(newImage, m[3], 10, new Scalar(0, 100, 0), 4, 8, 0);

							Core.circle(newImage, dj, 20, new Scalar(255, 100, 0), 4, 8, 0);

							Core.line(newImage, m[3], dj, new Scalar(255, 100, 0), 10, 8, 0);
							Core.line(newImage, n[2], dj, new Scalar(255, 100, 0), 10, 8, 0);

							if (polen == nord)
								Core.putText(traces, "den er OP", new Point(50, 50), Core.FONT_HERSHEY_PLAIN, 4,
										new Scalar(255, 100, 0));

							else if (polen == eas)
								Core.putText(traces, "den er HØJRE", new Point(50, 50), Core.FONT_HERSHEY_PLAIN, 4,
										new Scalar(255, 100, 0));

							else if (polen == syd)
								Core.putText(traces, "den er NED", new Point(50, 50), Core.FONT_HERSHEY_PLAIN, 4,
										new Scalar(255, 100, 0));

							else if (polen == ves)
								Core.putText(traces, "den er VENSTER", new Point(50, 50), Core.FONT_HERSHEY_PLAIN, 4,
										new Scalar(255, 100, 0));

							debuImg = new BufferedImage(newImage.width(), newImage.height(),
									BufferedImage.TYPE_BYTE_GRAY);

							byte[] data1 = ((DataBufferByte) debuImg.getRaster().getDataBuffer()).getData();

							newImage.get(0, 0, data1);

						}
						ArrayList<POIWallPoint> QRFound = new ArrayList<>();
						for (int j = 0; j < QRFun.size(); j++) {
							QRFound.add(
									new POIWallPoint(QRFun.get(j).getCode(),QRFun.get(j).getDistance()));
						}
						return QRFound;
					}

				} else {
					throw new Exception("Kunne ikke fÃ¥ billeder fra kamera.");
				}

			}
		}
		return null;
	}

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

		dj = ds;

	}

	private double cross(Point d, Point s) {

		return d.x * s.y - d.y * s.x;
	}

	private double slope(Point L, Point D) {
		double dy, dx;

		dx = D.x - L.x;
		dy = D.y - L.y;

		if (dy != 0) {

			return (dy / dx);
		} else
			return 0;
	}

	private double lineCalc(Point d, Point s, Point t) {
		double a, b, c, pdist;

		a = -((s.y - d.y) / (s.x - d.x));
		b = 1;
		c = (((s.y - d.y) / (s.x - d.x)) * d.x) - d.y;

		pdist = (a * t.x + (b * t.y) + c) / Math.sqrt((a * a) + (b * b));

		return pdist;

	}

	private double distance(Point punkt1, Point punkt2) {

		return Math.sqrt(Math.pow(Math.abs(punkt1.x - punkt2.x), 2) + Math.pow(Math.abs(punkt1.y - punkt2.y), 2));
	}

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
	public BufferedImage getQrdet() {
		return qrdet;
	}

	private Point centrumPoint(Point corn1, Point lastCorn) {
		Point cen;

		double len = distance(corn1, lastCorn);
		cen = new Point(corn1.x + (len / 2), corn1.y + (len / 2));

		return cen;
	}

	public BufferedImage getDebuImg() {
		return debuImg;
	}

	public ArrayList<QRPoi> getQRFun() {
		return QRFun;
	}

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

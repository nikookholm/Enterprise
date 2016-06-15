package Navigation;

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import de.yadrone.base.command.VideoCodec;
import de.yadrone.base.video.*;
import Common.Drone;
import GUI.iDroneGUI;
import POI.POI;
import POI.POICircle;
import POI.POIWallPoint;
import Vector.Vector3D;

public class DroneVision implements iDroneVision {
	/******************global variables**************/
	private Drone drone;

	ArrayList<POI> poi = new ArrayList<POI>();
	ArrayList<POI> poiDrone = new ArrayList<POI>();
	BufferedImage lastImage;
	BufferedImage currImage;
	QRfinder qrfind;
	ArrayList<POI> tempPoI = new ArrayList<POI>();
	OpenCVOperations CVOp;
	VectorDistance VD;

	/********************constructor*****************/
	public DroneVision(Drone drone) {
		this.drone = drone;
		qrfind = new QRfinder();
		CVOp = new OpenCVOperations();
		VD = new VectorDistance();
		drone.getCommandManager().setVideoCodec(VideoCodec.H264_720P).doFor(200);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}


	/************************************************/
	/******************public methods****************/
	/************************************************/

	/*********Scan qr-codes while moving*************/
	@Override
	public ArrayList<POI> scanQR( Condition condition) {

		int i = 0;
		int wallPoints = 0;
		int circlePoints = 0;
		switch(condition){
		case Initial:
			//Find 2 QR codes
			System.out.println("<<<<<<<<<<<< 2");
			while(wallPoints<2){
				System.out.println("<<<<<<<<< 3 , sker der");
				//ONLY finds QR codes
				tempPoI = CVOp.findQR(currImage);
				tempPoI.removeAll(poi);
				poi.addAll(tempPoI);
				while(i<poi.size()){
					if(poi.get(i) instanceof POIWallPoint){
						wallPoints++;
					}
					i++;
				}
			}
			break;

		case CircleQR:
			i = 0;
			circlePoints = 0;
			while(circlePoints<1){
				tempPoI = CVOp.findObjects(null, null, null, 0); //check with PAWURHAUZ
				tempPoI.removeAll(poi);
				poi.addAll(tempPoI);
				while(i<poi.size()){
					if(poi.get(i) instanceof POICircle){
						circlePoints++;
					}
					i++;
				}
			}
			break;
		default:
			//should not be used
			break;
		}
		return poi;
	}

	/***********Calibrate the drone in front of circle********/
	@Override
	public Movement calibrateToCircle(Vector3D dronepos) {
		Movement action = null ;

		if(dronepos.getXCoord() == 0 && dronepos.getYCoord() < 0){
			action = Movement.Up;
		} else 
			if(dronepos.getXCoord() < 0 && dronepos.getYCoord() < 0){
				action = Movement.RightUp;
			} else
				if(dronepos.getXCoord() < 0 && dronepos.getYCoord() == 0){
					action = Movement.Right;
				} else 
					if(dronepos.getXCoord() < 0 && dronepos.getYCoord() > 0){
						action = Movement.RightDown;
					} else
						if(dronepos.getXCoord() == 0 && dronepos.getYCoord() > 0){
							action = Movement.Down;
						} else 
							if (dronepos.getXCoord() > 0 && dronepos.getYCoord() > 0) {
								action = Movement.LeftDown;					
							} else 
								if(dronepos.getXCoord() > 0 && dronepos.getYCoord() == 0){
									action = Movement.Left;
								} else 
									if(dronepos.getXCoord() > 0 && dronepos.getYCoord() < 0){
										action = Movement.LeftUp;
									} 



		return action;
	}	

	/***********Get drone position from wallmarks*************/
	public Vector3D dronePosition(boolean firstTime){

		if(firstTime){
			System.out.println("<<<<<< 1");
			poiDrone = scanQR(Condition.Initial);
		} else {
			poiDrone = scanQR(Condition.Flying);
		}

		System.out.println(VD.getDronePosTwoPoints(((POIWallPoint)poiDrone.get(0)).getCoordinates(),
				((POIWallPoint)poiDrone.get(0)).getDistance(), ((POIWallPoint)poiDrone.get(1)).getCoordinates(), 
				((POIWallPoint)poiDrone.get(1)).getDistance()).getXCoord() + ", " + VD.getDronePosTwoPoints(((POIWallPoint)poiDrone.get(0)).getCoordinates(),
						((POIWallPoint)poiDrone.get(0)).getDistance(), ((POIWallPoint)poiDrone.get(1)).getCoordinates(), 
						((POIWallPoint)poiDrone.get(1)).getDistance()).getYCoord());

		return VD.getDronePosTwoPoints(((POIWallPoint)poiDrone.get(0)).getCoordinates(), 
				((POIWallPoint)poiDrone.get(0)).getDistance(), ((POIWallPoint)poiDrone.get(1)).getCoordinates(), 
				((POIWallPoint)poiDrone.get(1)).getDistance());
	}	

	/************************************************/
	/******************listeners*********************/
	/************************************************/

	@Override
	public ImageListener getImageListener() {
		// TODO Auto-generated method stub
		return new ImageListener(){

			@Override
			public void imageUpdated(BufferedImage arg0) {

				updateGUIImage(arg0);

			}
		};
	}

	BufferedImage oldImage = null;

	public void updateGUIImage(BufferedImage img){
		if(oldImage == null){
			BufferedImage procImage = findQR(img);
			oldImage = procImage;
			drone.getMain().getGUI().updateCorrectedImage(procImage);
		}else{
			BufferedImage procImage = findQR(img);
			oldImage = procImage;
			BufferedImage completeProcImage = CVOp.drawCircles(procImage);
			drone.getMain().getGUI().updateCorrectedImage(completeProcImage);	
		}
	}

	private BufferedImage findQR(BufferedImage image) {

		boolean imgTjek = false;

		iDroneGUI droneGui = drone.getMain().getGUI();
		List<QRPoi> im;

		DecimalFormat numberFormat = new DecimalFormat("0.00");

		Mat imageMat = new Mat();
		imageMat = new HoughCircles().bufferedImageToMat(image);
		qrfind = new QRfinder();

		try {
			qrfind.findQR(imageMat);
		} catch (Exception e) {				
		}

		im = qrfind.getQRFun();
		for(int i= 0; i< im.size(); i++){
			if(im.get(i).getCode() != null){


				droneGui.getLog().add("QRcode:  " + im.get(i).getCode());
				droneGui.getLog().add("Distance:  " + numberFormat.format(im.get(i).getDistance()/2) +"mm");


				System.out.println("new qr " +  im.get(i).getCode() + " Distance er i M: " + im.get(i).getDistance()/2);
			}
		}
		if(imgTjek == true){
			image = qrfind.getDebugImg();
		}
		im.clear();

		return image;

	}


	public QRfinder getQrfind() {
		return qrfind;
	}
}
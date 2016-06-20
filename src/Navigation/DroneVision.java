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

	ArrayList<POI> poi;
	ArrayList<POI> poiDrone;
	BufferedImage lastImage;
	BufferedImage currImage;
	QRfinder qrfind;
	ArrayList<POI> tempPoI;
	OpenCVOperations CVOp;
	VectorDistance VD;
	List<QRPoi> im;
	ArrayList<POICircle> circlesFound;

	/********************constructor*****************/
	public DroneVision(Drone drone) {
		this.drone = drone;
		qrfind = new QRfinder();
		CVOp = new OpenCVOperations();
		VD = new VectorDistance();
		tempPoI = new ArrayList<POI>();
		poiDrone = new ArrayList<POI>();
		poi = new ArrayList<POI>();
		drone.getCommandManager().setMaxVideoBitrate(3000);
		drone.getCommandManager().setVideoBitrate(3000);
		drone.getCommandManager().setVideoCodec(VideoCodec.H264_360P);
		drone.getCommandManager().setVideoCodecFps(20);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}


	/************************************************/
	/******************public methods****************/
	/************************************************/

	/*********Scan qr-codes while moving*************/
	@Override
	public ArrayList<POI> scanQR(Condition condition) {

		boolean isThere = false;
		int i = 0;
		int wallPoints = 0;
		int circlePoints = 0;
		switch(condition){
		case Initial:
			poi.clear();
			//Find 2 QR codes
			while(wallPoints<2){
				System.out.println("wallPoints: " + wallPoints + ", size of poi: " + poi.size());
				//ONLY finds QR codes
				tempPoI = CVOp.findQR(currImage);
				for(int j =0 ; j<tempPoI.size();j++){
					String tempPoICode = tempPoI.get(j).getCode();
					for(int k =0 ; k<poi.size();k++){
						String poiCode = poi.get(k).getCode();
						if(tempPoICode.equals(poiCode)){
							isThere = true;
						}
					}
					if(isThere){
						isThere =false;
					} else {
						poi.add(tempPoI.get(j));
					}
				}
				
				while(i<poi.size()){
					if(poi.get(i) instanceof POIWallPoint){
						wallPoints++;
						System.out.println("wallPoints: " + wallPoints + ", size of poi: " + poi.size());
						
					}
					i++;
				}
			}
			tempPoI.clear();
			break;

		case CircleQR:
			i = 0;
			circlePoints = 0;
			while(circlePoints<1){
				tempPoI = CVOp.findQR(currImage);
				for(int j =0 ; j<tempPoI.size();j++){
					String tempPoICode = tempPoI.get(j).getCode();
					for(int k =0 ; k<poi.size();k++){
						String poiCode = poi.get(k).getCode();
						if(tempPoICode.equals(poiCode)){
							isThere = true;
						}
					}
					if(isThere){
						isThere =false;
					} else {
						poi.add(tempPoI.get(j));
					}
				}	while(i<poi.size()){
					if(poi.get(i) instanceof POICircle){
						circlePoints++;
					}
					i++;
				}
			}
			break;
		case Flying:
			tempPoI = CVOp.findQR(currImage);
			for(int j =0 ; j<tempPoI.size();j++){
				String tempPoICode = tempPoI.get(j).getCode();
				for(int k =0 ; k<poi.size();k++){
					String poiCode = poi.get(k).getCode();
					if(tempPoICode.equals(poiCode)){
						isThere = true;
					}
				}
				if(isThere){
					isThere =false;
				} else {
					poi.add(tempPoI.get(j));
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
									else 
										if(dronepos.getXCoord() == 0 && dronepos.getYCoord() == 0 ){
											
											action = Movement.Forward;
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



	public void updateGUIImage(BufferedImage img){
		if(lastImage == null){
			currImage = findQR(img);
			lastImage = currImage;
			drone.getMain().getGUI().updateCorrectedImage(currImage);
		}else{
			currImage = findQR(img);
			lastImage = CVOp.drawCircles(currImage);
			drone.getMain().getGUI().updateCorrectedImage(lastImage);	
		}
	}

	private BufferedImage findQR(BufferedImage image) {

		boolean imgTjek = false;

		iDroneGUI droneGui = drone.getMain().getGUI();


		DecimalFormat numberFormat = new DecimalFormat("0.00");

		Mat imageMat = new Mat();
		imageMat = new HoughCircles().bufferedImageToMat(image);
		qrfind = new QRfinder();

		try {
			qrfind.findQR(imageMat);
			circlesFound = CVOp.findCircle(imageMat);
					//new Vector3D(0, 0,0));
			
			
		} catch (Exception e) {				
		}

		im = qrfind.getQRFun();
		for(int i= 0; i< im.size(); i++){
			if(im.get(i).getCode() != null){


				droneGui.getLog().add("QRcode:  " + im.get(i).getCode());
				droneGui.getLog().add("Distance:  " + numberFormat.format(im.get(i).getDistance()) +"mm");


				System.out.println("new qr " +  im.get(i).getCode() + " Distance er i M: " + im.get(i).getDistance());
			}
		}
		if(imgTjek == true){
			image = qrfind.getDebugImg();
		}
		im.clear();

		return image;

	}
	public ArrayList<POICircle> getCirclesFound() {
		return circlesFound;
	}
	public List<QRPoi> getIm() {
		return im;
	}


	public QRfinder getQrfind() {
		return qrfind;
	}
	public BufferedImage getCurrImage() {
		return currImage;
	}


	public void search() {
		
		
	}
}
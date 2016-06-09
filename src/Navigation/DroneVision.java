package Navigation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import de.yadrone.base.video.*;
import Common.Drone;
import Movements.DroneMovementThread;
import POI.POI;
import POI.POICircle;
import POI.POIWallPoint;

import Vector.Vector3D;

public class DroneVision implements iDroneVision {
	/******************global variables**************/
	private Drone drone;
	
	ArrayList<POI> poi = new ArrayList<POI>();
	BufferedImage lastImage;
	BufferedImage currImage;
	

	ArrayList<POI> tempPoI = new ArrayList<POI>();
	OpenCVOperations CVOp;
	vectorDistance VD;

	/********************constructor*****************/
	public DroneVision(Drone drone) {
		this.drone = drone;
		CVOp = new OpenCVOperations();
		VD = new vectorDistance();
	}
	

	/************************************************/
	/******************public methods****************/
	/************************************************/
	
	/*********Scan qr-codes while moving*************/
	@Override
	public ArrayList<POI> scanQR(Movement movement, Condition condition) {
		
		DroneMovementThread movementThread = new DroneMovementThread(movement, drone);
		movementThread.run();

		int i = 0;
		int wallPoints = 0;
		int circlePoints = 0;
		switch(condition){
			case Initial:
				//Find 3 QR codes
				while(wallPoints<3){
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
				movementThread.abort();
				break;
				
			case CircleQR:
				i = 0;
				circlePoints = 0;
				while(circlePoints<5){
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
	public Movement scanCircle() {
		Movement up = Movement.Up;
		
		return up;
	}	

	/***********Calibrate the drone to a standard pos*********/
	@SuppressWarnings("null")
	public Vector3D calibrate(){
		
		ArrayList<POI> poi = scanQR(Movement.Forward, Condition.Initial);
		ArrayList<POI> wallPoints = null;
		int wallPointsFound = 0;
		for(int i = 0; i < poi.size(); i++){
			if(poi.get(i) instanceof POIWallPoint){
				wallPoints.add(poi.get(i));
				wallPointsFound++;
			}
		}
		if(wallPointsFound == 2){
		return VD.getDronePosTwoPoints(wallPoints.get(0).getCoordinates(), 0/*dist to 1. qr*/, wallPoints.get(1).getCoordinates(),0/*dist to 1. qr*/);
		}
		return null;
		
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
				if (currImage != null) {
					lastImage = currImage;
					currImage = arg0;
				}else{
					currImage = arg0;
					
				}
			}
		};
	}
}

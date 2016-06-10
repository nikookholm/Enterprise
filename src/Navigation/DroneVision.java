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
	ArrayList<POI> poiDrone = new ArrayList<POI>();
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
				//Find 2 QR codes
				while(wallPoints<2){
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
		movementThread.abort();
		return poi;
	}
	
	/***********Calibrate the drone in front of circle********/
	@Override
	public Movement calibrateToCircle(Vector3D dronepos) {
		Movement up = Movement.Up;
		
		return up;
	}	

	/***********Get drone position from wallmarks*************/
	public Vector3D dronePosition(boolean firstTime){
		if(firstTime){
			poiDrone = scanQR(Movement.DownRight, Condition.Initial);
		} else {
			poiDrone = scanQR(Movement.Left, Condition.Flying);
		}
		
		System.out.println(VD.getDronePosTwoPoints(((POIWallPoint)poiDrone.get(0)).getCoordinates(), ((POIWallPoint)poiDrone.get(0)).getDistance(), ((POIWallPoint)poiDrone.get(1)).getCoordinates(), ((POIWallPoint)poiDrone.get(1)).getDistance()).getXCoord() + ", " + VD.getDronePosTwoPoints(((POIWallPoint)poiDrone.get(0)).getCoordinates(), ((POIWallPoint)poiDrone.get(0)).getDistance(), ((POIWallPoint)poiDrone.get(1)).getCoordinates(), ((POIWallPoint)poiDrone.get(1)).getDistance()).getYCoord());
		return VD.getDronePosTwoPoints(((POIWallPoint)poiDrone.get(0)).getCoordinates(), ((POIWallPoint)poiDrone.get(0)).getDistance(), ((POIWallPoint)poiDrone.get(1)).getCoordinates(), ((POIWallPoint)poiDrone.get(1)).getDistance());
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
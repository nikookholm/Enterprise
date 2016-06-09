package Navigation;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Marshaller.Listener;

import de.yadrone.base.video.*;
import Common.Drone;
import Movements.DroneMovementThread;
import POI.POI;
import POI.POICircle;
import POI.POIWallPoint;

import org.opencv.core.CvType;
import org.opencv.core.Mat;


import Vector.Vector3D;


public class DroneVision implements iDroneVision {

	private Drone drone;
	

	ArrayList<POI> poi = new ArrayList<POI>();
	BufferedImage lastImage;
	BufferedImage currImage;
	

	ArrayList<POI> tempPoI = new ArrayList<POI>();
	OpenCVOperations CVOp;
	vectorDistance VD;
	
	public DroneVision(Drone drone) {
		this.drone = drone;
		CVOp = new OpenCVOperations();
		VD = new vectorDistance();
	}

	@Override
	public ArrayList<POI> scan(Movement movement, Condition condition) {
		
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
	
	public Vector3D calibrate(){
		Vector3D dronepos;
		
		ArrayList<POI> poi = scan(Movement.Forward, Condition.Initial);
		ArrayList<POI> Wallpoints = null;
		int wallpointsFound = 0;
		for(int i = 0; i < poi.size(); i++){
			if(poi.get(i) instanceof POIWallPoint){
				if((((POIWallPoint)poi.get(i)).getQRString() == "W00.00") | (((POIWallPoint)poi.get(i)).getQRString() == "W00.01") | (((POIWallPoint)poi.get(i)).getQRString() == "W00.02")){
					Wallpoints.add(poi.get(i));
					wallpointsFound++;
				}
			}
		}
		if(wallpointsFound == 3){
		//	VD.calcDronepos(Wallpoints.get(0).getCoordinates(), Wallpoints.get(1).getCoordinates(), Wallpoints.get(2).getCoordinates());
		}
		return null;//return dronepos;
		
	}	
	
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

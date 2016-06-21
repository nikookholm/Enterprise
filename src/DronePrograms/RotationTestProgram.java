package DronePrograms;

import java.awt.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Common.Drone;
import Main.DroneProgram;
import Movements.DroneMovement;
import Movements.iDroneMovement;
import de.yadrone.apps.controlcenter.plugins.keyboard.KeyboardCommandManager;
import de.yadrone.apps.paperchase.controller.PaperChaseKeyboardController;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.navdata.Altitude;
import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.AttitudeListener;
import de.yadrone.base.navdata.ControlState;
import de.yadrone.base.navdata.DroneState;
import de.yadrone.base.navdata.KalmanPressureData;
import de.yadrone.base.navdata.Pressure;
import de.yadrone.base.navdata.PressureListener;
import de.yadrone.base.navdata.StateListener;
import Navigation.DroneVision;
import Navigation.OpenCVOperations;
import Navigation.QRPoi;
import POI.POICircle;
import Vector.Vector3D;;

public class RotationTestProgram extends DroneProgram {
	int altitude;
	OpenCVOperations opCV;
	private BufferedImage currentImage;

	@Override
	public void abort() {
		// TODO Auto-generated method stub
		getDrone().landing();

	}

	@Override
	public String getProgramName() {
		return "Spinn'";
	}

	@Override
	public void run() {

		DroneVision DV = getDrone().getNavigation().getVision();
		iDroneMovement DM = getDrone().getMovement();
		
//		DM.start();
//		DM.goUp(50, 0);
		getDrone().getCommandManager().takeOff().doFor(5000).hover().doFor(5000).up(8).doFor(9000);
//		get
		//.up(10).doFor(8000);
		System.out.println(" >ZZZZZZZZZZ<<<<<<<<<<<<<<ZZZZZZZZZZZZZZZZZZZZZZZZZ<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>");
		getDrone().getMovement().flyThroughRing();
		System.out.println(" <<<<<<<<<<<<<<<<ZZZZZZZZZZZZZZZZZZZZZZZZZZ<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		Vector3D differen = opCV.findCircle(currentImage);
//		DV.calibrateToCircle(differen);
//		getDrone().getMovement().flyForwardConstant(5, 0);
//		DM.flyThroughRing();
		DM.landing();
		System.out.println("laaaander nuuu >>>>>>>>>>>>>>>>>>>>>>>");
		}
	}
	


//	@Override
//	public void run() {		
//		getDrone().getMovement().start();
//		int Width;
//		int Height;
//		
//		getDrone().getNavDataManager().addAltitudeListener(new AltitudeListener() {
//			
//			@Override
//			public void receivedExtendedAltitude(Altitude arg0) {
//			}
//			
//			@Override
//			public void receivedAltitude(int arg0) {
//				altitude = arg0;				
//			}
//		});
//		int farMargin = 50;
//		int closeMargin = 30;
//		getDrone().getCommandManager().setMaxAltitude(1500).doFor(50);
//		Height = getDrone().getNavigation().getVision().getCurrImage().getHeight();
//		Width = getDrone().getNavigation().getVision().getCurrImage().getWidth();
//		int CenterX = Width/2;
//		int CenterY = Height/2;
//		boolean run = true;
//		POICircle currentPoit;
//		boolean flyForward = false;
//		while(run){
//
//			while(!flyForward){
//				getDrone().getCommandManager().hover();
//				if(getDrone().getNavigation().getVision().getIm().size() > 0){
//			for(int i = 0; i<getDrone().getNavigation().getVision().getIm().size(); i++){
//				if(getDrone().getNavigation().getVision().getIm().get(i).getCode().contains("P")){
//					System.out.println("FOUND QR, GOING UP");
//					getDrone().getCommandManager().setMinAltitude(1450);
//					while(altitude < 1500){
//						getDrone().getCommandManager().up(20).doFor(50);
//						System.out.println(altitude);
//						
//					}
//					getDrone().hover();
//					
//					if(altitude > 1400){
//						flyForward = true;
//					}
//					
//					
//				}
//			}
//			}
//			}
//			System.out.println("TRYING TO FIND CIRCLE");
//		if(flyForward){
//			System.out.println("hej");
//			if(getDrone().getNavigation().getVision().getCirclesFound().size() > 0){
//				System.out.println("ONMYWAY");
//				int circleX =(int) getDrone().getNavigation().getVision().getCirclesFound().get(0).getCentrumX();
//				System.out.println(circleX + " <<<<<<<<<<-------CIRCLE x");
//				double distToCircle = getDrone().getNavigation().getVision().getCirclesFound().get(0).getDistance();
//				System.out.println(distToCircle + " <<<<<<<<<<-------DIIST ");
//
//				boolean ready = getDrone().getMovement().calibrateToCircle(CenterX, circleX, closeMargin, farMargin, distToCircle);
//				System.out.println(ready);
//				if(ready){
//					getDrone().getMovement().flyForwardConstant(300, 200);
//					try {
//						Thread.sleep(2000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					getDrone().landing();
//					run = false;
//				}
//			}
//			
//		}
//
//			
//		}
//		
//		
//
//		
//	}

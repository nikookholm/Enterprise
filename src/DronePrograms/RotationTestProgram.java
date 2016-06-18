package DronePrograms;

import java.awt.List;
import java.util.ArrayList;

import Common.Drone;
import Main.DroneProgram;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.command.VideoCodec;
import de.yadrone.base.navdata.AttitudeListener;
import de.yadrone.base.navdata.ControlState;
import de.yadrone.base.navdata.DroneState;
import de.yadrone.base.navdata.StateListener;
import Navigation.DroneVision;
import Navigation.QRPoi;
import POI.POICircle;;

public class RotationTestProgram extends DroneProgram {

	@Override
	public void abort() {
		// TODO Auto-generated method stub
		getDrone().reset();

	}

	@Override
	public String getProgramName() {
		return "Spinn'";
	}

	@Override
	public void run() {
		getDrone().getMovement().start();
		int Width;
		int Height;
		int farMargin = 50;
		int closeMargin = 30;
		Height = getDrone().getNavigation().getVision().getCurrImage().getHeight();
		Width = getDrone().getNavigation().getVision().getCurrImage().getWidth();
		int CenterX = Width/2;
		int CenterY = Height/2;
		boolean run = true;
		POICircle currentPoit;
		boolean flyForward = false;
		while(run){
			while(!flyForward){
			for(int i = 0; i<getDrone().getNavigation().getVision().getIm().size(); i++){
				if(getDrone().getNavigation().getVision().getIm().get(i).getCode().contains("P")){
					while(getDrone().getMovement().getAltitude() < 1700){
						getDrone().getMovement().hoverTo(1700);
						
					}
					
					if(getDrone().getMovement().getAltitude() > 1600){
						flyForward = true;
					}
					
					
				}
			}
			}
		if(flyForward){
			System.out.println("Circle QR Found. Distance =" + getDrone().getNavigation().getVision().getCirclesFound().get(0).getDistance());
			if(getDrone().getNavigation().getVision().getCirclesFound().get(0) != null){
				
			}
			
		}

			
		}
		
		

		
	}




}

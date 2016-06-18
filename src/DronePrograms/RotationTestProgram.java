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
import Navigation.QRPoi;;

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
		
		getDrone().getMovement().flyForwardConstant(150, 2000);
		
		getDrone().getMovement().hover();
		
		getDrone().getMovement().landing();
		
	}




}

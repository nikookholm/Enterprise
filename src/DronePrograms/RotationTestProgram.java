package DronePrograms;

import java.awt.List;
import java.util.ArrayList;

import Common.Drone;
import Main.DroneProgram;
import de.yadrone.base.command.CommandManager;
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

		CommandManager com = getDrone().getCommandManager();
		com.setMaxAltitude(2000);
		
		boolean objectFound = false;
		com.takeOff().doFor(5000);
		com.up(20).doFor(1000);
		ArrayList<QRPoi> pois = null;
		int fundetID = 0;
		while (true) {
			for (int i = 0; i < 50; i++) {
				com.freeze().doFor(20);
			}
			com.hover().doFor(50);

			if (!objectFound) {
				pois = getDrone().getNavigation().getVision().getQrfind().getQRFun();
				if (pois != null)
					for (int i = 0; i < pois.size(); i++) {

						if (pois.get(i).getCode().startsWith("W")) {
							objectFound = true;
							fundetID = i;
						}
					}
				if (!objectFound) {
					for (int i = 0; i < 30; i++) {
						com.spinLeft(60).doFor(30);
					}
					com.spinRight(30).doFor(100);
					com.hover().doFor(40);
				}

			}
			if (objectFound) {

				for (int i = 0; i < 10; i++) {
					com.forward(30).doFor(30);
				}
				com.backward(40).doFor(100);
				com.hover().doFor(30);
				if (!(pois.get(fundetID).getDistance() > 1500)) {
					objectFound = false;
					com.landing().doFor(3000);
				}
			}

		}
	}

	private void spinRight() {
		
		for (int i = 0; i < 100; i++) {
			getDrone().getCommandManager().spinLeft(30).doFor(50);
			getDrone().getCommandManager().spinRight(30).doFor(60);
		}
	}

}

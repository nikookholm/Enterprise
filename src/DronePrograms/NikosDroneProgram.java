package DronePrograms;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

import Main.DroneProgram;
import de.yadrone.base.command.FlightAnimation;
import de.yadrone.base.command.FlyingMode;
import de.yadrone.base.command.VideoBitRateMode;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.command.VideoCodec;

public class NikosDroneProgram extends DroneProgram {
	
	static LocalDateTime date = LocalDateTime.now();
	
	@Override
	public void run() {
		int speed = 2;
		
		getDrone().getCommandManager().setVideoChannel(VideoChannel.HORI);
		getDrone().getCommandManager().setVideoCodec(VideoCodec.H264_720P);
		getDrone().getCommandManager().setVideoCodecFps(30);
		getDrone().getCommandManager().setVideoBitrate(4000);
		getDrone().getCommandManager().setVideoBitrateControl(VideoBitRateMode.MANUAL);
		
//		System.out.println("test1");
//		getDrone().start();
//		getDrone().takeOff();
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			
//		}
//
//		System.out.println("test2");
//		Scanner scan = new Scanner(System.in);
//		System.out.println("test3");
//		
//		
//		
//			while(true){
//				System.out.println("test4");
//				int test = scan.nextInt();
//				System.out.println("test5");
//			switch(test){
//			case 1:
//				System.out.println("test6");
//				getDrone().getCommandManager().spinRight(5).doFor((int)500);
//				break;
//			case 2:
//				getDrone().getCommandManager().spinLeft(5).doFor((int)500);
//				break;
//			case 3:
//				getDrone().landing();
//				break;
//			default:
//				break;
//			}
//			}
		
		
		
	}

	@Override
	public void abort() {
		getDrone().getCommandManager().landing().doFor(2000);

	}

	@Override
	public String getProgramName() {
		return "Anwar test program";
	}

}

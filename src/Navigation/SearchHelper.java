package Navigation;

import java.util.ArrayList;

import Common.Drone;
import POI.POI;
import Vector.Vector3D;

public class SearchHelper {
	
	RoomSquare[][] room;
	Drone 		   drone;
	ArrayList<POI> poiList;

	public SearchHelper(ArrayList<POI> poiList, Drone drone)
	{
		initialize(poiList, drone);
		setupRoom();
	}
	
	private void initialize(ArrayList<POI> poiList, Drone drone)
	{
		this.poiList = poiList;
		this.drone   = drone;
	}

	public void search(POI lookFor)
	{
		// flyv til en "plads" vi ikke har skannet endnu
		// Returner hvad den finder, og marker som afsøgt
		
		
	}
	
	private void setupRoom()
	{
		int roomWidth  = 6;
		int roomHeight = 7;
		
		int interval   = 150;
		int margin     = 75;
		
		room = new RoomSquare[roomWidth][roomHeight];
		
		for (int i = 0; i < roomHeight; i++)
		{
			for (int j = 0; j < roomWidth; j++)
			{
				
				room[j][i] = new RoomSquare((j*150) + margin, (i*150) + margin);
				System.out.println("room[" + j + "][" + i + "] har center i (" + room[j][i].getCenter().getXCoord() + "," + room[j][i].getCenter().getYCoord() + ")");
			}
		}
	}
	
	private class RoomSquare
	{
		private boolean  searched = false;
		private Vector3D center;
		
		public RoomSquare(int centerX, int centerY) {

			center = new Vector3D(centerX, centerY, 0); // 0 skal skiftes til dronens z position

		}
		
		public Vector3D getCenter()
		{
			return center;
		}
	}

}
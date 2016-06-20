package Navigation;

import java.util.ArrayList;

import Common.Drone;
import POI.POI;
import Vector.Vector3D;

public class SearchHelper {
	
	int roomSquaresWidth  = 6;
	int roomSquaresHeight = 7;
	
	int interval   = 150;
	int margin     = 75;
	
	boolean squaresLeftToSearch = true;
	boolean POIFound 		    = false;
	
	RoomSquare[][] rooms;
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
		do
		{
			// flyv til en "plads" vi ikke har skannet endnu
			flyToClosestUnsearchedSquare();
			// Returner hvad den finder, og marker som afsøgt
			searchSquare();
			// Gør dette indtil alle firkanter, er søgt eller POI er fundet
			
		} while (POINotFoundOrNoMoreSquares(lookFor)); 
		
	}
	
	private boolean POINotFoundOrNoMoreSquares(POI lookFor) {

		// Check if POI is in POIList
		boolean POIIsInList = false;
		
		for (POI p : poiList)
		{
			if (p.getCode().equals(lookFor.getCode()))
			{
				POIIsInList = true;
			}
		}
		
		return (POIIsInList || !squaresLeftToSearch);
		
	}
	
	private void searchSquare() {

		// Spin 360
		// scan for QR
		
	}

	private void flyToClosestUnsearchedSquare() {
		
		RoomSquare room;
		RoomSquare flyToRoom = null;
		int distanceToClosestSquare = 10000000;
		int newDistance;	
		
		for (int x = 0; x < roomSquaresWidth; x++)
		{
			for (int y = 0; y < roomSquaresHeight; y++)
			{
				room = rooms[x][y];
				newDistance = (int) Math.sqrt( Math.pow((Math.abs(drone.getCoordX()-room.getCenter().getXCoord())), 2) + Math.pow((Math.abs(drone.getCoordY()-room.getCenter().getYCoord())),2)); 
				if (newDistance < distanceToClosestSquare)
				{
					distanceToClosestSquare = newDistance;
					flyToRoom = room;
				}
				
				if (!room.isSearch())
				{
					squaresLeftToSearch = true;
				}
				
			}	
		}
		
		drone.getMovement().flyTo(new POI(flyToRoom.getCenter(), 0));
	}

	private void setupRoom()
	{

		rooms = new RoomSquare[roomSquaresWidth][roomSquaresHeight];
		
		for (int i = 0; i < roomSquaresHeight; i++)
		{
			for (int j = 0; j < roomSquaresWidth; j++)
			{
				
				rooms[j][i] = new RoomSquare((j*150) + margin, (i*150) + margin);
				System.out.println("room[" + j + "][" + i + "] har center i (" + rooms[j][i].getCenter().getXCoord() + "," + rooms[j][i].getCenter().getYCoord() + ")");
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
		
		public boolean isSearch()
		{
			return searched;
		}
	}

}
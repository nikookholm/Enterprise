package GUI.TopDown;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;
import Vector.Vector3D;
import Common.Drone;

public class TopDown implements Runnable {
	
	private ArrayList<Movestamp> moves = new ArrayList<Movestamp>();
	private Drone  drone;
	private JPanel target;
	
	public TopDown(JPanel targetPanel, Drone drone)
	{
		this.target = targetPanel;
		this.drone  = drone;
		paint();
	}
	
	private void addIcon()
	{
		
	}
	
	private void addMovestamp(Vector3D coordsNow)
	{
		moves.add(new Movestamp(coordsNow));		
	}
	
	private void paint()
	{
		target.setBackground(Color.black);		
	}
	

	@Override
	public void run() {

		do
		{
			paint();
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				System.err.println("Fejl ved topdown-loop");
				e.printStackTrace();
			}
			
		} while (true);
		
	}
	
	private class Movestamp
	{
		
		Vector3D position;
		long	 time;
		
		public Movestamp(Vector3D coordsNow)
		{
			position = coordsNow;
			time	 = System.currentTimeMillis();
		}
	}
	
}

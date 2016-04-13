package Navigation;

import org.opencv.core.Point;

public class QRPoi {

	private int top;
	private int mid;
	private int bot;
	private Point lastPoint;
	private double xpos;
	private double ypos;
	private String message;

	public void setX(double k){
		xpos = k;
	}
	public void setY(double k){
		ypos = k;
	}
	
	public void setCode(String s){
		message = s;
	}
	
	public String getCode(){
		return message;
	}
	
	public double getX(){
		return xpos;
	}
	public double getY(){
		return ypos;
	}
	
	
	public void setTop(int i){
		top = i;
	}
	public void setMid(int i){
		mid = i;
	}
	public void setBot(int i)
	{
		bot = i;
	}
	public void setLP(Point p){
		lastPoint = p;
	}
	
	public int getTop(){
		return top;
	}
	public int getMid(){
		return mid;
	}
	public int getBot(){
		return bot;
	}
	public Point getLP(){
		return lastPoint;
	}
	
	public QRPoi(int top1, int mid1 , int bot1){
		this.top = top1;
		this.mid = mid1;
		this.bot = bot1;
	}
	
	
	
	
	
}

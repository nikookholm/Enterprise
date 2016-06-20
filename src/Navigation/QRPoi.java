package Navigation;

import java.awt.image.BufferedImage;

import org.opencv.core.Point;

public class QRPoi {

	private int top;
	private int mid;
	private int bot;
	private Point lastPoint;
	private double xpos;
	private double ypos;
	private String message;
	private BufferedImage QRimg;
	private Point centrum;
	private double distance;
	private Point cords;
	private double distVenstre;
	private double distHøjre;

	public void setX(double k){
		xpos = k;
	}
	public void setY(double k){
		ypos = k;
	}
	
	public void setCode(String s){
		message = s;
		if(message.startsWith("W")){
			setCords();
		}
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
	public void setDistHøjre(double distHøjre) {
		this.distHøjre = distHøjre;
	}
	public void setDistVenstre(double distVenstre) {
		this.distVenstre = distVenstre;
	}
	public double getDistHøjre() {
		return distHøjre;
	}
	public double getDistVenstre() {
		return distVenstre;
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
	public Point getCords() {
		return cords;
	}
	
	public QRPoi(int top1, int mid1 , int bot1){
		this.top = top1;
		this.mid = mid1;
		this.bot = bot1;
	}
	public void setQRimg(BufferedImage qRimg) {
		QRimg = qRimg;
	}
	public BufferedImage getQRimg() {
		return QRimg;
	}
	
	public void setCentrum(Point centrum) {
		this.centrum = centrum;
	}
	public Point getCentrum() {
		return centrum;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getDistance() {
		return distance;
	}
	
	private void setCords(){
		switch(this.message){
		
		case "W00.00":
			this.cords = new Point(188,1055);
			break;
		case "W00.01":
			this.cords = new Point(338,1060);
			
			break;
		case "W00.02":
			this.cords = new Point(515,1055);
			
			break;
		case "W00.03":
			this.cords = new Point(694,1060);
			
			break;
		case "W00.04":
			this.cords = new Point(840,1055);
			
			break;
		case "W01.00":
			this.cords = new Point(926,904);
			
			break;
		case "W01.01":
			this.cords = new Point(926,721);
			
			break;
		case "W01.02":
			this.cords = new Point(926,566);
			
			break;
		case "W01.03":
			this.cords = new Point(926,324);
			
			break;
		case "W01.04":
			this.cords = new Point(926,115);
			
			break;
		case "W02.00":
			this.cords = new Point(847,-10);
			
			break;
		case "W02.01":
			this.cords = new Point(656,-77);
			
			break;
		case "W02.02":
			this.cords = new Point(420,0);
			
			break;
		case "W02.03":
			this.cords = new Point(350,0);
			
			break;
		case "W02.04":
			this.cords = new Point(150,0);
			
			break;
		case "W03.00":
			this.cords = new Point(0,108);
			
			break;
		case "W03.01":
			this.cords = new Point(0,357);
			
			break;
		case "W03.02":
			this.cords = new Point(0,561);
			
			break;
		case "W03.03":
			this.cords = new Point(0,740);
			
			break;
		case "W03.04":
			this.cords = new Point(0,997);
			
			break;
			
			default:
				break;
			
			
		
		
		}
	}
	
	
	
	
	
}

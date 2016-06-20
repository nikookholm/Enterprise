package POI;

import Vector.Vector3D;

public class POICircle extends POI {

	private int radius;
	private boolean QR;
	private double distance;
	private double QRDistance;
	private double centrumX;
	private double centrumY;
	private double QrLeftDist;
	private double QrRigtDist;
	private double qrCentrum;

	
	public POICircle(Vector3D coordinates/*, Vector3D dronepos*/, int radius, double distancee, double cenX, double cenY, double distVenstre, double distHøjre) {
		super(coordinates, null);
		this.radius = radius;
		this.distance = distancee;
		this.centrumX = cenX;
		this.centrumY = cenY;
		this.QrLeftDist = distVenstre;
		this.QrRigtDist = distHøjre;
		
	}
	
	public POICircle(String Code, double distance,double qrcenX){
		super(null,null,0,Code);
		this.QRDistance = distance;
		this.qrCentrum = qrcenX;

	}
	
	public double getDistance() {
		return distance;
	}
	
	public int getRadius(){
		return radius;
	}
	public double getCentrumX() {
		return centrumX;
	}
	public double getCentrumY() {
		return centrumY;
	}
	public double getQrLeftDist() {
		return QrLeftDist;
	}
	public double getQrRigtDist() {
		return QrRigtDist;
	}
	public double getQrCentrum() {
		return qrCentrum;
	}
}

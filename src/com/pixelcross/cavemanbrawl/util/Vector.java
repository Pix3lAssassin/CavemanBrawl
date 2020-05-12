package com.pixelcross.cavemanbrawl.util;

/**
 * @author Justin Schreiber
 *
 */
public class Vector {

	private double x, y, magnitude;
	
	/**
	 * Creates a vector that stores magnitude and x and y unit vector information
	 * 
	 * @param x (Distance traveled in the x direction)
	 * @param y (Distance traveled in the y direction)
	 */
	public Vector(double x, double y) {
		this.magnitude = Math.sqrt(x*x + y*y);

		if(magnitude>0)
		{
			this.x = x/magnitude;
			this.y = y/magnitude;
		}
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
}

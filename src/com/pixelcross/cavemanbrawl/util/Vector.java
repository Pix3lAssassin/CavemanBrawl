package com.pixelcross.cavemanbrawl.util;

public class Vector {

	private double x, y, magnitude;
	
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

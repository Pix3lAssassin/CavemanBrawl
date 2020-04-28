package com.pixelcross.cavemanbrawl.levels;

import java.awt.Point;

/**
 * @author Justin Schreiber
 *
 * A Point with a step value for distance calculations
 */
public class FloodPoint extends Point {
	
	public final int step;
	
	/**
	 * A Point with a step value for distance calculations
	 * 
	 * @param x
	 * @param y
	 * @param step
	 */
	public FloodPoint(int x, int y, int step) {
		super(x, y);
		this.step = step;
	}

	public int getStep() {
		return step;
	}
}

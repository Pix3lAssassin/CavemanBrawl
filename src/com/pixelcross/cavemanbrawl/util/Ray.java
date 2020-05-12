package com.pixelcross.cavemanbrawl.util;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

import com.pixelcross.cavemanbrawl.entities.Entity;
import com.pixelcross.cavemanbrawl.levels.Room;

public class Ray {

	private Point2D.Double origin;
	private Vector direction;
	
	public Ray(Point2D.Double origin, Vector direction, double distance) {
		this.origin = origin;
		this.direction = direction;
		direction.setMagnitude(distance);
	}
	
	public RaycastInfo cast(Room room) {
		for (Entity e : room.getEntities()) {
			Rectangle bounds = e.getBounds();
			
		}
		
		return new RaycastInfo(0, null);
	}
}

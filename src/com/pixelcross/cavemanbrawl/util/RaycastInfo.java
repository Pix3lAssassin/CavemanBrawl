package com.pixelcross.cavemanbrawl.util;

import com.pixelcross.cavemanbrawl.entities.Entity;

public class RaycastInfo {

	private double distance;
	private Entity entityHit;
	
	public RaycastInfo(double distance, Entity entityHit) {
		this.distance = distance;
		this.entityHit = entityHit;
	}

	public double getDistance() {
		return distance;
	}

	public Entity getEntityHit() {
		return entityHit;
	}
	
}

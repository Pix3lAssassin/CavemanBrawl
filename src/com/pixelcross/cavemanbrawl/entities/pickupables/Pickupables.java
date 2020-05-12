package com.pixelcross.cavemanbrawl.entities.pickupables;

import java.util.ArrayList;
import java.util.List;

import com.pixelcross.cavemanbrawl.entities.Entity;

/**
 * @author Colin Kugler
 *
 */
public abstract class Pickupables extends Entity {

	protected boolean isPickupable; // check to see if the item is able to be picked up.

	public Pickupables(double x, double y, int width, int height) {
		super(x, y, width, height); // dimensions of Pickupable object
		// this.bounds = new Rectangle(0,0,width,height);
		isPickupable = true;
		bounds.x = 12;
		bounds.y = 16;
		bounds.height = 24;
		bounds.width = 32;
	}

	public abstract <T extends Pickupables> T pickup();
	// stubbed methods

	public List<Entity> getCollidingEntities(Entity entity, double offsetX, double offsetY) {
		// stubbed
		items.add(entity);
		return items;
	}

	public void setPickupable(boolean pickup) {
		isPickupable = pickup;
	}

}

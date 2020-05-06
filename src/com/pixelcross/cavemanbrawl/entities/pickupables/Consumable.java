package com.pixelcross.cavemanbrawl.entities.pickupables;

import com.pixelcross.cavemanbrawl.entities.creatures.Creature;

/**
 * @author Colin Kugler
 *
 */
public abstract class Consumable extends Pickupables {	

	public Consumable(double x, double y, int width, int height) {
		super(x, y, width, height);
	}
	
	public abstract void consume(Creature entity);	
	
}

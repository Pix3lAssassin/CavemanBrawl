package com.pixelcross.cavemanbrawl.entities.pickupables;

import com.pixelcross.cavemanbrawl.entities.creatures.Creature;
import com.pixelcross.cavemanbrawl.levels.Level;

/**
 * @author Colin Kugler
 *
 */
public abstract class Consumable extends Pickupables {	

	public Consumable(Level level, double x, double y, int width, int height) {
		super(level, x, y, width, height);
	}
	
	/**
	 * Consumes this Consumable
	 * 
	 * @param entity (The entity that will consume this consumable)
	 */
	public abstract void consume(Creature entity);	
	
}

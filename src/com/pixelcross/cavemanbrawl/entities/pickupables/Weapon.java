package com.pixelcross.cavemanbrawl.entities.pickupables;

import com.pixelcross.cavemanbrawl.levels.Level;

/**
 * @author Colin Kugler
 *
 *	Weapons class that implements Pickupables
 *	will be used for weapons classes for the players character to be able to attack
 */
public abstract class Weapon extends Pickupables {	// abstract
	protected int weapon_health;	  // how much health does the weapon have(how long until it breaks)
	protected int weapon_strength; // how strong is the weapon (how much damage the weapon deals)

	public Weapon(Level level, double x, double y, int width, int height, int health, int strength) {
		super(level, x, y, width, height);
		this.weapon_health = health;
		this.weapon_strength = strength;
	}
		
	public int getAttackDamage() {
		return weapon_strength;
	}	

}

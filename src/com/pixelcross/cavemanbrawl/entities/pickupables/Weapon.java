package com.pixelcross.cavemanbrawl.entities.pickupables;

/**
 * @author Colin Kugler
 *
 */
public abstract class Weapon extends Pickupables {	// abstract
	protected double weapon_health;	  // how much health does the weapon have(how long until it breaks)
	protected double weapon_strength; // how strong is the weapon (how much damage the weapon deals)

	public Weapon(double x, double y, int width, int height, double health, double strength) {
		super(x, y, width, height);
		this.weapon_health = health;
		this.weapon_strength = strength;
	}
		
	public double getAttackDamage() {
		return weapon_strength;
	}	

}

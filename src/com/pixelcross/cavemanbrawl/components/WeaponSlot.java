package com.pixelcross.cavemanbrawl.components;

import java.awt.geom.Point2D;

import com.pixelcross.cavemanbrawl.entities.Entity;
import com.pixelcross.cavemanbrawl.entities.creatures.Creature;
import com.pixelcross.cavemanbrawl.entities.pickupables.Weapon;
import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.Level;

import javafx.scene.canvas.GraphicsContext;

/**
 * @author Justin Schreiber
 *
 * Allows an entity to "hold" a weapon
 */
public class WeaponSlot implements Component {

	private Weapon weapon;
	private Level level;
	private Entity parent;
	
	/**
	 * Creates a slot to hold a weapon that can be attached to an entity
	 * 
	 * @param parent (The entity that the WeaponSlot is attached to)
	 * @param level (The level that the entity is currently in)
	 */
	public WeaponSlot(Entity parent, Level level) {
		this.parent = parent;
		this.level = level;
	}
	
	/**
	 * Equips a weapon
	 * 
	 * @param weapon (Weapon to equip)
	 */
	public void equip(Weapon weapon) {
		this.weapon = weapon;
	}
	
	/**
	 * Drops the weapon that the weapon slot is carrying
	 */
	public void drop() {
		Point2D.Double parentPos = parent.getCenterPos();
		weapon.setPos(parentPos.x, parentPos.y);
		level.getCurrentRoom().addEntity(weapon);
		weapon = null;
	}
	
	/**
	 * Use the weapon to attack the creature specified
	 * 
	 * @param creature (The creature the weapon should target)
	 */
	public void attack(Creature creature) {
		creature.doDamage(weapon.getAttackDamage());
	}
	
	@Override
	public void update() {
		Point2D.Double parentPos = parent.getCenterPos();
		weapon.setPos(parentPos.x, parentPos.y);
	}

	/**
	 * Renders the weapon to the screen
	 * 
	 * @param gc
	 * @param interpolation
	 * @param camera
	 */
	public void render(GraphicsContext gc, double interpolation, GameCamera camera) {
		weapon.render(gc, interpolation, camera);
	}
	
}

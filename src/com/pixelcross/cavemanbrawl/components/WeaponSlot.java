package com.pixelcross.cavemanbrawl.components;

import java.awt.geom.Point2D;

import com.pixelcross.cavemanbrawl.entities.Entity;
import com.pixelcross.cavemanbrawl.entities.creatures.Creature;
import com.pixelcross.cavemanbrawl.entities.pickupables.Weapon;
import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.Level;

import javafx.scene.canvas.GraphicsContext;

public class WeaponSlot implements Component {

	private Weapon weapon;
	private Level level;
	private Entity parent;
	
	public WeaponSlot(Entity parent, Level level) {
		this.parent = parent;
		this.level = level;
	}
	
	public void equip(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public void drop() {
		Point2D.Double parentPos = parent.getCenterPos();
		weapon.setPos(parentPos.x, parentPos.y);
		level.getCurrentRoom().addEntity(weapon);
		weapon = null;
	}
	
	public void attack(Creature creature) {
		creature.doDamage(weapon.getAttackDamage());
	}
	
	@Override
	public void update() {
		Point2D.Double parentPos = parent.getCenterPos();
		weapon.setPos(parentPos.x, parentPos.y);
	}

	public void render(GraphicsContext gc, double interpolation, GameCamera camera) {
		weapon.render(gc, interpolation, camera);
	}
	
}

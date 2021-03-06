package com.pixelcross.cavemanbrawl.entities.creatures;

import com.pixelcross.cavemanbrawl.components.RaccoonAnimationController;
import com.pixelcross.cavemanbrawl.components.WeaponSlot;
import com.pixelcross.cavemanbrawl.entities.Entity;
import com.pixelcross.cavemanbrawl.entities.pickupables.weapons.Claw;
import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.Level;
import com.pixelcross.cavemanbrawl.levels.tiles.Tile;
import com.pixelcross.cavemanbrawl.util.Vector;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Justin Schreiber
 *
 * A raccoon enemy
 */
public class Raccoon extends Creature {

	private Entity target;
	private boolean attacking;
	private long attackStart; 
	private double attackTime;
	
	/**
	 * @param level
	 * @param x
	 * @param y
	 */
	public Raccoon(Level level, double x, double y) {
		super(level, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
		
		//Initialize bounds for the raccoon
		bounds.x = 8;
		bounds.y = 24;
		bounds.width = 48;
		bounds.height = 40;
		
		speed = 5;
		target = null;
		
		//Add a weapon slot with a claw weapon and an animation controller for it
		WeaponSlot ws = new WeaponSlot(this, level);
		ws.equip(new Claw(level));
		components.add(ws);
		components.add(new RaccoonAnimationController(this));
		
		attacking = false;
		attackTime = .5;
	}

	@Override
	public void update() {
		evaluateTarget();
		if (!attacking) {
			move();
		}
		xMove *= 0.7;
		yMove *= 0.7;
	}

	@Override
	public void render(GraphicsContext gc, double interpolation, GameCamera camera) {
		gc.drawImage(getComponent(RaccoonAnimationController.class).getFrame(), x - camera.getxOffset(), y - camera.getyOffset(), width, height);

//		gc.setStroke(Color.RED);
//		gc.strokeRect(x + bounds.x - camera.getxOffset(), y + bounds.y - camera.getyOffset(), bounds.width, bounds.height);
	}

	/**
	 * Evaluate where the current target is an take specific actions
	 */
	private void evaluateTarget() {
		if (target != null) {
			//Get the distance to the target
			Vector toTarget = getVectorTo(target.getCenterPos());
			double dist = toTarget.getMagnitude();
			//Check if the distance is less than 2 tiles and if true enter attacking state
			if (dist < 2*Tile.TILEHEIGHT) {
				if (!attacking) {
					attacking = true;
					attackStart = System.nanoTime();
				}
			} else if (dist < 8*Tile.TILEHEIGHT) { // Move towards the target if it is within 8 tiles
				if (Math.sqrt(xMove*xMove + yMove*yMove) < speed) {
					xMove += toTarget.getX()*(speed/3);
					yMove += toTarget.getY()*(speed/3);
				}
			}
			//Attack the target if it is within range and the delay has been passed
			if (attacking && System.nanoTime() > attackStart + (long)(attackTime * 1000000000)) {
				attacking = false;
				if (dist < 2*Tile.TILEHEIGHT) {
					try {
						Creature targetCreature = (Creature) target;
						getComponent(WeaponSlot.class).attack(targetCreature);
					} catch(ClassCastException cce) {}
				}
			}
		}
	}
	
	public void setTarget(Entity e) {
		target = e;
	}

	public boolean isAttacking() {
		return attacking;
	}
}

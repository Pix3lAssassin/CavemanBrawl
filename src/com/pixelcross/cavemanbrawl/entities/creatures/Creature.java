package com.pixelcross.cavemanbrawl.entities.creatures;

import com.pixelcross.cavemanbrawl.entities.Entity;
import com.pixelcross.cavemanbrawl.levels.Level;
import com.pixelcross.cavemanbrawl.levels.tiles.Tile;

/**
 * @author Justin Schreiber
 * 
 * @see https://www.youtube.com/playlist?list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 *
 * Defines a creature that can move around in the world with limited health
 *
 */
public abstract class Creature extends Entity {

	public static final int DEFAULT_HEALTH = 10;
	public static final int DEFAULT_SPEED = 6;
	public static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;
	
	protected int health;
	protected double speed;
	protected double xMove, yMove;
	protected Level currentLevel;

	/**
	 * Defines a creature that can move around in the world with limited health
	 * 
	 * @param level (The current level)
	 * @param x (The starting x position of the Creature)
	 * @param y (The starting y position of the Creature)
	 * @param width (The width of the Creature)
	 * @param height (The height of the Creature)
	 */
	public Creature(Level level, double x, double y, int width, int height) {
		super(x, y, width, height);
		health = DEFAULT_HEALTH;
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		currentLevel = level;
	}

	/**
	 * Move the creature
	 */
	public void move() {
		moveX();
		moveY();
	}
	
	/**
	 * Handles movement in the X direction and world collisions
	 */
	protected void moveX() {
		if(xMove > 0) {//Moving right			
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			}		
		} else if(xMove < 0) {//Moving left
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
			}
		}
	}
	
	/**
	 * Handles movement in the Y direction and world collisions
	 */
	protected void moveY() {
		if(yMove > 0) {//Moving down			
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
			}	
			
		} else if(yMove < 0) {//Moving up
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			}
		}
	}
	
	/**
	 * @param x
	 * @param y
	 * @return true if the Creature collided with a solid tile
	 */
	protected boolean collisionWithTile(int x, int y) {
		Tile tile = currentLevel.getCurrentRoom().getTile(1, x, y);
		if (tile != null) {
			return tile.isSolid();
		} else {
			return false;
		}
	}	
		
	/**
	 * @return X Movement
	 */
	public double getxMove() {
		return xMove;
	}

	/**
	 * @return Y Movement
	 */
	public double getyMove() {
		return yMove;
	}

	/**
	 * @return current health
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * @param health
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return speed
	 */
	public double getSpeed() {
		return speed;
	}
	
}

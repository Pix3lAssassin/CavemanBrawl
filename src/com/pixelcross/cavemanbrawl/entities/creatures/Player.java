package com.pixelcross.cavemanbrawl.entities.creatures;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.pixelcross.cavemanbrawl.components.PlayerAnimationController;
import com.pixelcross.cavemanbrawl.entities.Entity;
import com.pixelcross.cavemanbrawl.entities.LevelListener;
import com.pixelcross.cavemanbrawl.entities.Trigger;
import com.pixelcross.cavemanbrawl.gfx.Assets;
import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.Level;
import com.pixelcross.cavemanbrawl.levels.tiles.Tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Justin Schreiber
 *
 * @see https://www.youtube.com/playlist?list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 * 
 * <br><br>
 * Controls the player via keyboard and mouse inputs and renders the player to the screen
 */
public class Player extends Creature implements LevelListener {

	private ArrayList<String> input;
	
	/**
	 * @param currentLevel (The current level)
	 * @param input (List of keyboard inputs being pressed)
	 * @param x (The starting x position of the Player)
	 * @param y (The starting y position of the Player)
	 */
	public Player(Level currentLevel, ArrayList<String> input, double x, double y) {
		super(currentLevel, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		bounds.x = 16;
		bounds.y = 24;
		bounds.width = 32;
		bounds.height = 40;
		
		health = 100;
		
		this.input = input;
		PlayerAnimationController playerAnimation = new PlayerAnimationController(this);
		components.add(playerAnimation);
		List<Entity> entities = currentLevel.getCurrentRoom().getEntities();
		for (Entity e : entities) {
			try {
				Raccoon r = (Raccoon) e;
				r.setTarget(this);
			} catch(ClassCastException cce) {}
		}
	}

	/* (non-Javadoc)
	 * @see com.pixelcross.cavemanbrawl.entities.Entity#update()
	 */
	@Override
	public void update() {
		getInput();
		move();
		checkForTriggers();
		yMove *= 0.7;
		xMove *= 0.7;
	}

	private void checkForTriggers() {
		int[] collisionCorners = new int[4];
		collisionCorners[0] = (int) (x + bounds.x) / Tile.TILEWIDTH;
		collisionCorners[1] = (int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH;
		collisionCorners[2] = (int) (y + bounds.y) / Tile.TILEHEIGHT;
		collisionCorners[3] = (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT;
		int collisionWidth = collisionCorners[1] - collisionCorners[0]+1;
		int collisionHeight = collisionCorners[3] - collisionCorners[2]+1;

		ArrayList<Tile> collidingTiles = new ArrayList<Tile>();
		for (int tileY = collisionCorners[2]; tileY < collisionCorners[2]+collisionHeight; tileY++) {
			for (int tileX = collisionCorners[0]; tileX < collisionCorners[0]+collisionWidth; tileX++) {
				collidingTiles.add(currentLevel.getCurrentRoom().getTile(1, tileX, tileY));
			}
		}
		for (Tile t : collidingTiles) {
			if (t != null && t.isTrigger()) {
				Trigger tile = (Trigger) t;
				tile.trigger();
				break;
			}
		}
	}
	
	/**
	 * Handle player inputs
	 */
	private void getInput() {
		double speedChange = speed/3;
		if (Math.sqrt(xMove*xMove + yMove*yMove) < speed) {
			if(input.contains("UP"))
				yMove -= speedChange;
			if(input.contains("DOWN"))
				yMove += speedChange;
			if(input.contains("LEFT"))
				xMove -= speedChange;
			if(input.contains("RIGHT"))
				xMove += speedChange;	
		}
	}
	
	/* (non-Javadoc)
	 * @see com.pixelcross.cavemanbrawl.entities.Entity#render(javafx.scene.canvas.GraphicsContext, double, com.pixelcross.cavemanbrawl.gfx.GameCamera)
	 */
	@Override
	public void render(GraphicsContext gc, double interpolation, GameCamera camera) {
		gc.drawImage(getComponent(PlayerAnimationController.class).getFrame(), x - camera.getxOffset(), y - camera.getyOffset(), width, height);

//		gc.setStroke(Color.RED);
//		gc.strokeRect(x + bounds.x - camera.getxOffset(), y + bounds.y - camera.getyOffset(), bounds.width, bounds.height);
	}

	public void setCurrentLevel(Level level) {
		currentLevel = level;
	}

	@Override
	public void onRoomChange() {
		//TODO
		Point startingPoint = currentLevel.getCurrentRoom().getStartPos();
		xMove = 0;
		yMove = 0;
		setPos(startingPoint.x, startingPoint.y);
		List<Entity> entities = currentLevel.getCurrentRoom().getEntities();
		for (Entity e : entities) {
			try {
				Raccoon r = (Raccoon) e;
				r.setTarget(this);
			} catch(ClassCastException cce) {}
		}
	}
}

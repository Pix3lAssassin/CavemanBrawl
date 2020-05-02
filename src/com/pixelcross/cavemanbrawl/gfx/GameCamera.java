package com.pixelcross.cavemanbrawl.gfx;

import com.pixelcross.cavemanbrawl.entities.Entity;
import com.pixelcross.cavemanbrawl.levels.tiles.Tile;
import com.pixelcross.cavemanbrawl.main.CavemanBrawlApp;
import com.pixelcross.cavemanbrawl.states.GameState;

/**
 * @author Justin Schreiber
 *
 * @see https://www.youtube.com/playlist?list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 */
public class GameCamera {

	private GameState gs;
	private double xOffset, yOffset;
	private int screenWidth, screenHeight;
	
	
	/**
	 * 
	 * Creates a camera for the game to use to show the world
	 * 
	 * @param gs (The GameState that the camera is operating within)
	 * @param xOffset (The camera offset within the world in the x direction)
	 * @param yOffset (The camera offset within the world in the y direction)
	 * @param screenWidth (The width of the game window)
	 * @param screenHeight (The height of the game window)
	 */
	public GameCamera(GameState gs, double xOffset, double yOffset, int screenWidth, int screenHeight) {
		this.gs = gs;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	/**
	 * Checks if the camera is looking outside the scope of the room/world
	 */
	private void checkBlankSpace() {
		if(xOffset < 2 * Tile.TILEWIDTH) {
			xOffset = 2 * Tile.TILEWIDTH;
		} else if(xOffset > gs.getLevel().getCurrentRoom().getWidth() * Tile.TILEWIDTH - screenWidth + Tile.TILEWIDTH*2) {
			xOffset = gs.getLevel().getCurrentRoom().getWidth() * Tile.TILEWIDTH - screenWidth + Tile.TILEWIDTH*2;
		}
		
		if(yOffset < 2 * Tile.TILEHEIGHT) {
			yOffset = 2 * Tile.TILEHEIGHT;
		} else if (yOffset > gs.getLevel().getCurrentRoom().getHeight() * Tile.TILEHEIGHT - screenHeight + Tile.TILEHEIGHT*2) {
			yOffset = gs.getLevel().getCurrentRoom().getHeight() * Tile.TILEHEIGHT - screenHeight + Tile.TILEHEIGHT*2;
		}
		//Used to see outside of the visible game region
//		if(xOffset < 0) {
//			xOffset = 0;
//		} else if(xOffset > gs.getLevel().getCurrentRoom().getWidth() * Tile.TILEWIDTH - screenWidth + Tile.TILEWIDTH*4) {
//			xOffset = gs.getLevel().getCurrentRoom().getWidth() * Tile.TILEWIDTH - screenWidth + Tile.TILEWIDTH*4;
//		}
//		
//		if(yOffset < 0) {
//			yOffset = 0;
//		} else if (yOffset > gs.getLevel().getCurrentRoom().getHeight() * Tile.TILEHEIGHT - screenHeight + Tile.TILEHEIGHT *4) {
//			yOffset = gs.getLevel().getCurrentRoom().getHeight() * Tile.TILEHEIGHT - screenHeight + Tile.TILEHEIGHT*4;
//		}
	}
	
	/**
	 * Centers the camera on the entity specified
	 * 
	 * @param e (The entity the camera should center on)
	 */
	public void centerOnEntity(Entity e) {
		xOffset = e.getX() - screenWidth /2 + e.getWidth() /2;
		yOffset = e.getY() - screenHeight /2 + e.getHeight() /2;
		checkBlankSpace();
	}
	
	/**
	 * Moves the camera by the specified amount
	 * 
	 * @param xAmt
	 * @param yAmt
	 */
	public void move(double xAmt, double yAmt) {
		xOffset += xAmt;
		yOffset += yAmt;
		checkBlankSpace();
	}
	
	public double getxOffset() {
		return xOffset;
	}

	public double getyOffset() {
		return yOffset;
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}


}

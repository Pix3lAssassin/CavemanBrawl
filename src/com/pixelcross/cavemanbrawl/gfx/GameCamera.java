package com.pixelcross.cavemanbrawl.gfx;

import com.pixelcross.cavemanbrawl.entities.Entity;
import com.pixelcross.cavemanbrawl.levels.tiles.Tile;
import com.pixelcross.cavemanbrawl.main.CavemanBrawlApp;
import com.pixelcross.cavemanbrawl.states.GameState;

public class GameCamera {

	private GameState gs;
	private double xOffset, yOffset;
	private int screenWidth, screenHeight;
	
	public GameCamera(GameState gs, double xOffset, double yOffset, int screenWidth, int screenHeight) {
		this.gs = gs;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	public void checkBlankSpace() {
		if(xOffset < 0) {
			xOffset = 0;
		} else if(xOffset > gs.getLevel().getCurrentRoom().getWidth() * Tile.TILEWIDTH - screenWidth) {
			xOffset = gs.getLevel().getCurrentRoom().getWidth() * Tile.TILEWIDTH - screenWidth;
		}
		
		if(yOffset < 0) {
			yOffset = 0;
		} else if (yOffset > gs.getLevel().getCurrentRoom().getHeight() * Tile.TILEHEIGHT - screenHeight) {
			yOffset = gs.getLevel().getCurrentRoom().getHeight() * Tile.TILEHEIGHT - screenHeight;
		}
	}
	
	public void centerOnEntity(Entity e) {
		xOffset = e.getX() - screenWidth /2 + e.getWidth() /2;
		yOffset = e.getY() - screenHeight /2 + e.getHeight() /2;
		checkBlankSpace();
	}
	
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

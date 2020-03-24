package com.pixelcross.cavemanbrawl.gfx;

import com.pixelcross.cavemanbrawl.entities.Entity;
import com.pixelcross.cavemanbrawl.levels.tiles.Tile;
import com.pixelcross.cavemanbrawl.main.CavemanBrawlApp;
import com.pixelcross.cavemanbrawl.states.GameState;

public class GameCamera {

	private GameState gs;
	private double xOffset, yOffset;
	
	public GameCamera(GameState gs, double xOffset, double yOffset) {
		this.gs = gs;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void checkBlankSpace() {
		if(xOffset < 0) {
			xOffset = 0;
		} else if(xOffset > gs.getLevel().getCurrentRoom().getWidth() * Tile.TILEWIDTH - CavemanBrawlApp.WIDTH) {
			xOffset = gs.getLevel().getCurrentRoom().getWidth() * Tile.TILEWIDTH - CavemanBrawlApp.WIDTH;
		}
		
		if(yOffset < 0) {
			yOffset = 0;
		} else if (yOffset > gs.getLevel().getCurrentRoom().getHeight() * Tile.TILEHEIGHT - CavemanBrawlApp.HEIGHT) {
			yOffset = gs.getLevel().getCurrentRoom().getHeight() * Tile.TILEHEIGHT - CavemanBrawlApp.HEIGHT;
		}
	}
	
	public void centerOnEntity(Entity e) {
		xOffset = e.getX() - CavemanBrawlApp.WIDTH /2 + e.getWidth() /2;
		yOffset = e.getY() - CavemanBrawlApp.HEIGHT /2 + e.getHeight() /2;
		checkBlankSpace();
	}
	
	public void move(float xAmt, float yAmt) {
		xOffset += xAmt;
		yOffset += yAmt;
		checkBlankSpace();
	}
	
	public double getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public double getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
	
}

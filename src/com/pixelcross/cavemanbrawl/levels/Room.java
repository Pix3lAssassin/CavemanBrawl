package com.pixelcross.cavemanbrawl.levels;

import com.pixelcross.cavemanbrawl.levels.tiles.Tile;

public class Room {

	private int width, height;
	private Tile[][] tiles;
	
	public Room(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
}

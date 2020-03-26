package com.pixelcross.cavemanbrawl.levels.tiles;

public class TileMap {

	private Tile[][] tiles;
	private int width, height;
	
	public TileMap(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	public void setTile(int x, int y, Tile newTile) {
		tiles[x][y] = newTile;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}

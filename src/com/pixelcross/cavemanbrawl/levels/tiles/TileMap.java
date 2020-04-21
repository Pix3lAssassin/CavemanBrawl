package com.pixelcross.cavemanbrawl.levels.tiles;

/**
 * @author Justin Schreiber
 *
 * Defines an map of tiles using a double array
 */
public class TileMap {

	private Tile[][] tiles;
	private int width, height;
	
	/**
	 * Creates a TileMap
	 * 
	 * @param width (The width of the TileMap in number of tiles)
	 * @param height (The height of the TileMap in number of tiles)
	 */
	public TileMap(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
	}

	/**
	 * Gets the tile at the x and y position
	 * 
	 * @param x
	 * @param y
	 * @return The tile at the x and y position
	 */
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	/**
	 * Sets the tile at the x and y position to a new Tile
	 * 
	 * @param x
	 * @param y
	 * @param newTile (The tile that will override the old tile)
	 */
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

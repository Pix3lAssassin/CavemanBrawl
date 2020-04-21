package com.pixelcross.cavemanbrawl.levels.tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Justin Schreiber
 *
 * @see https://www.youtube.com/playlist?list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 * 
 */
public class Tile {

	//STATIC
	
	public static Tile[] tiles = new Tile[256];
	public static Tile grassTile = new GrassTile(0);
	public static Tile rockTile = new RockTile(1);
	
	//CLASS
	public static final int TILEWIDTH = 32, TILEHEIGHT = 32;
	
	protected Image texture;
	protected final int id;
	
	/**
	 * Creates a tile with an texture and id (Possible rework)
	 * 
	 * @param texture (Image texture)
	 * @param id
	 */
	public Tile(Image texture, int id) {
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	
	/**
	 * Updates the tile
	 */
	public void update() {}
	
	/**
	 * Renders the tile to the screens
	 * 
	 * @param gc (The object used to draw the image to the canvas)
	 * @param x (Where to draw the tile in the x direction)
	 * @param y (Where to draw the tile in the y direction)
	 */
	public void render(GraphicsContext gc, int x, int y) {
		gc.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT);
	}
	
	/**
	 * @return true if the tile is solid (default false)
	 */
	public boolean isSolid() {
		return false;
	}
	
	public int getID() {
		return id;
	}
	
}

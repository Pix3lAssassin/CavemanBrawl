package com.pixelcross.cavemanbrawl.levels.tiles;

import com.pixelcross.cavemanbrawl.gfx.Assets;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Justin Schreiber
 *
 * @see https://www.youtube.com/playlist?list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 * 
 */
public class Tile {
	
	public static Tile[] tiles;
	public static Tile groundTile;
	public static Tile wallTile;
	
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
		
//		tiles[id] = this;
	}
	
	public static void init() {
		tiles = new Tile[256];
		groundTile = new GroundTile(Assets.grass[0], 0);
		wallTile = new WallTile(Assets.walls[6], 0);
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
	
	/**
	 * @return true if the tile is a trigger (default false)
	 */
	public boolean isTrigger() {
		return false;
	}
	
	public int getID() {
		return id;
	}
	
}

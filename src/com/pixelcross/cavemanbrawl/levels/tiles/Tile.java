package com.pixelcross.cavemanbrawl.levels.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile {

	//STATIC
	
	public static Tile[] tiles = new Tile[256];
	public static Tile grassTile = new GrassTile(0);
	public static Tile rockTile = new RockTile(1);
	
	//CLASS
	public static final int TILEWIDTH = 64, TILEHEIGHT = 64;
	
	protected Image texture;
	protected final int id;
	
	public Tile(Image texture, int id) {
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void update() {}
	
	public void render(GraphicsContext gc, int x, int y) {
		gc.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT);
	}
	
	public boolean isSolid() {
		return false;
	}
	
	public int getID() {
		return id;
	}
	
}

package com.pixelcross.cavemanbrawl.levels;

import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.tiles.GrassTile;
import com.pixelcross.cavemanbrawl.levels.tiles.RockTile;
import com.pixelcross.cavemanbrawl.levels.tiles.Tile;
import com.pixelcross.cavemanbrawl.levels.tiles.TileMap;

import javafx.scene.canvas.GraphicsContext;

public class Room {

	private int width, height;
	private TileMap[] tileLayers;
	
	public Room(int width, int height) {
		this.width = width;
		this.height = height;
		this.tileLayers = new TileMap[4];
		for (int i = 0; i < tileLayers.length; i++) {
			tileLayers[i] = new TileMap(width, height);
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Tile getTile(int layer, int x, int y) {
		return tileLayers[layer].getTile(x, y);
	}
	
	public void generateRoom() {
		generateBackground();
		generateForeground();
	}
	
	private void generateBackground() {
		for (int x = 0; x < width; x ++) {
			for (int y = 0; y < height; y ++) {
				tileLayers[0].setTile(x, y, new GrassTile(0));
			}
		}
	}
	
	private void generateForeground() {
		for (int x = 0; x < width; x ++) {
			for (int y = 0; y < height; y ++) {
				if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
					tileLayers[1].setTile(x, y, new RockTile(1));
				}
			}
		}
	}
	
	public void update() {
		
	}
	
	public void render(GraphicsContext gc, double interpolation, GameCamera camera) {
		int xStart = (int) Math.max(0, camera.getxOffset() / Tile.TILEWIDTH);
		int xEnd = (int) Math.min(width, (camera.getxOffset() + camera.getScreenWidth()) / Tile.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, camera.getyOffset() / Tile.TILEHEIGHT);
		int yEnd = (int) Math.min(height, (camera.getyOffset() + camera.getScreenWidth()) / Tile.TILEHEIGHT + 1);
		
		for (int layer = 0; layer < 4; layer++) {
			for(int y = yStart; y < yEnd;y++) {
				for(int x = xStart; x < xEnd;x++) {
					Tile tile = getTile(layer, x, y);
					if (tile != null) {
						tile.render(gc, (int) (x * Tile.TILEWIDTH - camera.getxOffset()), (int) (y * Tile.TILEHEIGHT - camera.getyOffset()));
					}
				}
			}
		}
	}

}

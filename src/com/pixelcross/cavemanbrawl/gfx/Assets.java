package com.pixelcross.cavemanbrawl.gfx;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * @author Justin Schreiber
 *
 * @see https://www.youtube.com/playlist?list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 *
 * Loads and stores all the assets needed for the game
 */
public class Assets {

	private static final int width = 16, height = 16;
	
	public static Image player;
	public static Image[] player_down, player_up, player_right, player_left;
	public static Image[] grass, walls;
	
	/**
	 * Initializes all the assets should be called before first game loop
	 */
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/town_tiles.png"));
		SpriteSheet grassSheet = new SpriteSheet(ImageLoader.loadImage("/textures/CavemanBrawlGrassTiles.png"));
		SpriteSheet wallSheet = new SpriteSheet(ImageLoader.loadImage("/textures/CavemanBrawlWallTiles.png"));
		
		grass = new Image[8];
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 4; x++) {
				grass[y*4+x] = ImageLoader.convertToFxImage(grassSheet.crop(x*width/2, y*width/2, width/2, height/2), 4);
			}
		}
		walls = new Image[25];
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				walls[y*5+x] = ImageLoader.convertToFxImage(wallSheet.crop(x*width/2, y*width/2, width/2, height/2), 4);
			}
		}
		
		player_down = new Image[3];
		
		player_down[0] = ImageLoader.convertToFxImage(sheet.crop(0, 0, width, height), 1);
		
		player = ImageLoader.convertToFxImage(sheet.crop(0, 16, 16, 16), 4);
	}
	
}

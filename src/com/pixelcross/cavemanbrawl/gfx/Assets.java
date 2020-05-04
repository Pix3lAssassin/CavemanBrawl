package com.pixelcross.cavemanbrawl.gfx;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * @author Justin Schreiber and Ben Wilkin
 *
 * @see https://www.youtube.com/playlist?list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 *
 * Loads and stores all the assets needed for the game
 */
public class Assets {

	private static final int width = 16, height = 16;
	
	public static Image grass, wall, rock;
	public static Image[] player_down, player_up, player_right, player_left;
	
	/**
	 * Initializes all the assets should be called before first game loop
	 */
	public static void init() {
		SpriteSheet playerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Caveman Paul (1).png"));
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/town_tiles.png"));
		
		for(int i = 0; i<4;i++)
		{
		player_down[i] = ImageLoader.convertToFxImage(playerSheet.crop(i*16, 0, width, height), 1);
		}
		for(int i = 0; i<4;i++)
		{
		player_up[i] = ImageLoader.convertToFxImage(playerSheet.crop(i*16, 16, width, height), 1);
		}
		for(int i = 0; i<4;i++)
		{
		player_right[i] = ImageLoader.convertToFxImage(playerSheet.crop(i*16, 32, width, height), 1);
		}
		for(int i = 0; i<4;i++)
		{
		player_left[i] = ImageLoader.convertToFxImage(playerSheet.crop(i*16, 48, width, height), 1);
		}
		
		grass = ImageLoader.convertToFxImage(sheet.crop(0, 16, 16, 16), 4);
		rock = ImageLoader.convertToFxImage(sheet.crop(0, 0, 16, 16), 4);
	}
	
}

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
	
	public static Image placeHolder;
	public static Image[] player_down, player_up, player_right, player_left;
	public static Image[] raccoon_down, raccoon_up, raccoon_right, raccoon_left, 
	raccoon_down_idle, raccoon_up_idle, raccoon_right_idle, raccoon_left_idle,
	raccoon_down_attacking, raccoon_up_attacking, raccoon_right_attacking, raccoon_left_attacking,
	claw;
	public static Image[] grass, walls;
	
	/**
	 * Initializes all the assets should be called before first game loop
	 */
	public static void init() {
		SpriteSheet playerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Caveman Paul (1).png"));
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/town_tiles.png"));
		SpriteSheet grassSheet = new SpriteSheet(ImageLoader.loadImage("/textures/CavemanBrawlGrassTiles.png"));
		SpriteSheet wallSheet = new SpriteSheet(ImageLoader.loadImage("/textures/CavemanBrawlWallTiles.png"));
		
		player_down = new Image[4];
		player_up = new Image[4];
		player_right = new Image[4];
		player_left = new Image[4];
		for(int i = 0; i<4;i++)
		{
		player_down[i] = ImageLoader.convertToFxImage(playerSheet.crop(i*16, 0, width, height), 4);
		}
		for(int i = 0; i<4;i++)
		{
		player_up[i] = ImageLoader.convertToFxImage(playerSheet.crop(i*16, 16, width, height), 4);
		}
		for(int i = 0; i<4;i++)
		{
		player_right[i] = ImageLoader.convertToFxImage(playerSheet.crop(i*16, 32, width, height), 4);
		}
		for(int i = 0; i<4;i++)
		{
		player_left[i] = ImageLoader.convertToFxImage(playerSheet.crop(i*16, 48, width, height), 4);
		}
		
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
		
		placeHolder = ImageLoader.convertToFxImage(sheet.crop(32, 16, width, height), 4);
		loadRaccoon();
	}
	
	private static void loadRaccoon() {
		SpriteSheet raccoonSheet = new SpriteSheet(ImageLoader.loadImage("/textures/PixelRaccoon.png"));
		raccoon_down = new Image[4];
		raccoon_up = new Image[4];
		raccoon_right = new Image[4];
		raccoon_left = new Image[4];
		raccoon_down_idle = new Image[2];
		raccoon_up_idle = new Image[2];
		raccoon_right_idle = new Image[2];
		raccoon_left_idle = new Image[2];
		raccoon_down_attacking = new Image[2];
		raccoon_up_attacking = new Image[2];
		raccoon_right_attacking = new Image[2];
		raccoon_left_attacking = new Image[2];
		claw = new Image[2];
		for(int i = 0; i<4;i++)
		{
		raccoon_left[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16, 0, width, height), 4);
		}
		for(int i = 0; i<4;i++)
		{
		raccoon_right[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16, 16, width, height), 4);
		}
		for(int i = 0; i<4;i++)
		{
		raccoon_up[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16, 32, width, height), 4);
		}
		for(int i = 0; i<4;i++)
		{
		raccoon_down[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16, 48, width, height), 4);
		}

		for(int i = 0; i<2;i++)
		{
		raccoon_down_attacking[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16, 64, width, height), 4);
		}
		for(int i = 0; i<2;i++)
		{
		raccoon_up_attacking[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16, 80, width, height), 4);
		}
		for(int i = 0; i<2;i++)
		{
		raccoon_left_attacking[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16, 96, width, height), 4);
		}
		for(int i = 0; i<2;i++)
		{
		raccoon_right_attacking[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16, 112, width, height), 4);
		}

		for(int i = 0; i<2;i++)
		{
		raccoon_down_idle[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16+32, 64, width, height), 4);
		}
		for(int i = 0; i<2;i++)
		{
		raccoon_up_idle[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16+32, 80, width, height), 4);
		}
		for(int i = 0; i<2;i++)
		{
		raccoon_left_idle[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16+32, 96, width, height), 4);
		}
		for(int i = 0; i<2;i++)
		{
		raccoon_right_idle[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16+32, 112, width, height), 4);
		}

		for(int i = 0; i<2;i++)
		{
		claw[i] = ImageLoader.convertToFxImage(raccoonSheet.crop(i*16, 128, width, height), 4);
		}
	}
	
}

package com.pixelcross.cavemanbrawl.gfx;

import java.awt.image.BufferedImage;

import javafx.scene.image.Image;

public class Assets {

	private static final int width = 16, height = 16;
	
	public static Image grass, wall, rock;
	public static Image[] player_down, player_up, player_right, player_left;
	
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/town_tiles.png"));
		
		player_down = new Image[3];
		
		player_down[0] = ImageLoader.convertToFxImage(sheet.crop(0, 0, width, height));
		
		grass = ImageLoader.convertToFxImage(sheet.crop(0, 16, 16, 16));
		rock = ImageLoader.convertToFxImage(sheet.crop(0, 0, 16, 16));
	}
	
}

package com.pixelcross.cavemanbrawl.gfx;

import java.awt.image.BufferedImage;

/**
 * @author Justin Schreiber
 *
 * @see https://www.youtube.com/playlist?list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 * 
 * Provides functionality for getting sub images from a loaded image
 */
public class SpriteSheet {

	private BufferedImage sheet;
	
	/**
	 * Creates a new spritesheet
	 * 
	 * @param sheet
	 */
	public SpriteSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}
	
	/**
	 * Crops the image to a specific sprite on the sheet
	 * 
	 * @param x (The starting x position for the sprite)
	 * @param y (The starting y position for the sprite)
	 * @param width (The width of the sprite)
	 * @param height (The height of the sprite)
	 * @return A cropped sprite
	 */
	public BufferedImage crop(int x, int y, int width, int height) {
		return sheet.getSubimage(x, y, width, height);
	}
	
}

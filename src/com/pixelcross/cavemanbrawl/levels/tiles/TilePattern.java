package com.pixelcross.cavemanbrawl.levels.tiles;

import javafx.scene.image.Image;

/**
 * @author Justin Schreiber
 *
 * Defines how to display image patterns within the tile maps
 */
public interface TilePattern {

	/**
	 * @param surroundingTiles (The tiles surrounding the current tile)
	 * @return the correct image based on the surrounding tiles
	 */
	public Image getTileTexture(int[] surroundingTiles);
	
}

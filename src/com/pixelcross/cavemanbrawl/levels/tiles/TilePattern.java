package com.pixelcross.cavemanbrawl.levels.tiles;

import javafx.scene.image.Image;

/**
 * @author Justin Schreiber
 *
 * Defines how to display image patterns within the tile maps
 */
public interface TilePattern {

	public Image getTileTexture(int[] surroundingTiles);
	
}

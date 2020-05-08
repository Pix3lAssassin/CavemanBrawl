package com.pixelcross.cavemanbrawl.levels.tiles;

import com.pixelcross.cavemanbrawl.gfx.Assets;

import javafx.scene.image.Image;

public class GroundTilePattern implements TilePattern {

	Image[] grass;
	
	public GroundTilePattern() {
		this.grass = Assets.grass;
	}
	
	@Override
	public Image getTileTexture(int[] surroundingTiles) {
		return grass[(int)(Math.random()*grass.length)];
	}

}

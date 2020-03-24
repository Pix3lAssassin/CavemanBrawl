package com.pixelcross.cavemanbrawl.levels.tiles;

import java.awt.image.BufferedImage;

import com.pixelcross.cavemanbrawl.gfx.Assets;

public class RockTile extends Tile {

	public RockTile(int id) {
		super(Assets.rock, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
	
}

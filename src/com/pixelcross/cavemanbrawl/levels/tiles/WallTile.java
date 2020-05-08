package com.pixelcross.cavemanbrawl.levels.tiles;

import com.pixelcross.cavemanbrawl.gfx.Assets;

import javafx.scene.image.Image;

public class WallTile extends Tile {

	public WallTile(Image texture, int id) {
		super(texture, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
	
}

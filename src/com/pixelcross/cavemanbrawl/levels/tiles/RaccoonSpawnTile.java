package com.pixelcross.cavemanbrawl.levels.tiles;

import com.pixelcross.cavemanbrawl.entities.creatures.Raccoon;
import com.pixelcross.cavemanbrawl.levels.Level;

public class RaccoonSpawnTile extends SpawnTile {

	public RaccoonSpawnTile(Level level, int x, int y) {
		super(level, x, y);
	}

	@Override
	public void spawn() {
		level.getCurrentRoom().addEntity(new Raccoon(level, x*Tile.TILEWIDTH, y*Tile.TILEHEIGHT));
	}

}

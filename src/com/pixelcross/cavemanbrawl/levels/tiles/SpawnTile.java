package com.pixelcross.cavemanbrawl.levels.tiles;

import com.pixelcross.cavemanbrawl.entities.Entity;
import com.pixelcross.cavemanbrawl.entities.Spawner;
import com.pixelcross.cavemanbrawl.levels.Level;
import com.pixelcross.cavemanbrawl.levels.Room;

import javafx.scene.image.Image;

public abstract class SpawnTile extends Tile implements Spawner {

	protected Level level;
	protected int x, y;
	
	public SpawnTile(Level level, int x, int y) {
		super(null, 0);
		this.level = level;
		this.x = x;
		this.y = y;
	}

}

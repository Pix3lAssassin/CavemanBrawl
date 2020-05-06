package com.pixelcross.cavemanbrawl.levels.tiles;

import com.pixelcross.cavemanbrawl.entities.Trigger;
import com.pixelcross.cavemanbrawl.gfx.Assets;
import com.pixelcross.cavemanbrawl.levels.Level;

/**
 * @author Justin Schreiber
 *
 * Sends a player to the room specified on the tile
 */
public class NextRoomTile extends Tile implements Trigger {

	private Level currentLevel;
	private int roomId;
	
	public NextRoomTile(Level currentLevel, int roomId) {
		super(Assets.player, 0);
		this.currentLevel = currentLevel;
		this.roomId = roomId;
	}

	public boolean isTrigger() {
		return true;
	}

	@Override
	public void trigger() {
		currentLevel.nextRoom(roomId);
	}
		
}

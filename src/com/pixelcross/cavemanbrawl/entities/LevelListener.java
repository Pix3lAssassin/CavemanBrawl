package com.pixelcross.cavemanbrawl.entities;

/**
 * @author Justin Schreiber
 *
 * An inteface that listens for changes/events in the level
 */
public interface LevelListener {

	/**
	 * Called when a room has changed and the new room has been loaded
	 */
	public void onRoomChange();
	
}

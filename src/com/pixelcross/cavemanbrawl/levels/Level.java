package com.pixelcross.cavemanbrawl.levels;

import java.awt.Point;
import java.util.ArrayList;

import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.tiles.Tile;
import com.pixelcross.cavemanbrawl.main.CavemanBrawlApp;

import javafx.scene.canvas.GraphicsContext;

/**
 * @author Justin Schreiber
 *
 * Defined by a list of rooms and a camera that can observe each room
 */
public class Level {

	private Room[] rooms;
	private Room currentRoom;
	private GameCamera camera;
	
	/**
	 * Creates a new level using a camera, the number of rooms, and the room sizes
	 * 
	 * @param camera
	 * @param numOfRooms
	 * @param roomSizeWidth
	 * @param roomSizeHeight
	 */
	public Level(GameCamera camera, int numOfRooms, int roomSizeWidth, int roomSizeHeight) {
		rooms = new Room[numOfRooms];
		for (int i = 0; i < rooms.length; i++) {
			rooms[i] = new Room(roomSizeWidth, roomSizeHeight);
		}
		currentRoom = rooms[0];
		currentRoom.generateRoom(49);
		this.camera = camera;
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}
	
	public Point getPlayerSpawn() {
		return rooms[0].getPlayerSpawn();
	}
	
	/**
	 * Updates the current room in the level
	 */
	public void update() {
		currentRoom.update();
	}
	
	/**
	 * Renders the current room in the level
	 * 
	 * @param gc
	 * @param interpolation
	 * @param camera
	 */
	public void render(GraphicsContext gc, double interpolation, GameCamera camera) {
		currentRoom.render(gc, interpolation, camera);
	}

}

package com.pixelcross.cavemanbrawl.levels;

import java.awt.Point;
import java.util.ArrayList;

import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.tiles.Tile;
import com.pixelcross.cavemanbrawl.main.CavemanBrawlApp;

import javafx.scene.canvas.GraphicsContext;

public class Level {

	Room[] rooms;
	Room currentRoom;
	GameCamera camera;
	
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
	
	public void update() {
		currentRoom.update();
	}
	
	public void render(GraphicsContext gc, double interpolation, GameCamera camera) {
		currentRoom.render(gc, interpolation, camera);
	}

}

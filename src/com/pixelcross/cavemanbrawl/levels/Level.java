package com.pixelcross.cavemanbrawl.levels;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.pixelcross.cavemanbrawl.entities.LevelListener;
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
	private List<LevelListener> levelListeners;
	
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
		int[][] roomDoors = generateLevel(numOfRooms);
		for (int i = 0; i < rooms.length; i++) {
			rooms[i] = new Room(i, this, roomSizeWidth, roomSizeHeight, roomDoors[i]);
		}
		currentRoom = rooms[0];
		currentRoom.generateRoom(49);
		currentRoom.load(-1);
		this.camera = camera;
		this.levelListeners = new ArrayList<LevelListener>();
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}
	
	public Point getPlayerSpawn() {
		return rooms[0].getPlayerSpawn();
	}
	
	public void nextRoom(int roomId) {
		int lastRoomId = currentRoom.getId();
		currentRoom = rooms[roomId];
		if (!currentRoom.isGenerated()) {
			currentRoom.generateRoom(49);
		}
		currentRoom.load(lastRoomId);
		for (LevelListener ll : levelListeners) {
			ll.onRoomChange();
		}
	}
	
	private int[][] generateLevel(int numOfRooms) {
		ArrayList<Point> roomPositions = new ArrayList<Point>();
		int[][] levelDoors = new int[numOfRooms][4];
		boolean invalidLevel = true;
		
		//Loop until rooms don't overlap each other
		while (invalidLevel) {	
			roomPositions.clear();
			roomPositions.add(new Point(0, 0));
			Point currentPoint;
			
			//Loop for the number of rooms and add a room in a new direction
			for (int i = 0; i < numOfRooms-1; i++) {
				//From the newest room check if which rooms adjacent are available
				currentPoint = roomPositions.get(i);
				ArrayList<Point> availableRooms = new ArrayList<Point>();
				ArrayList<Point> adjacentRooms = new ArrayList<Point>();
				adjacentRooms.add(new Point(currentPoint.x-1, currentPoint.y+0));
				adjacentRooms.add(new Point(currentPoint.x+1, currentPoint.y+0));
				adjacentRooms.add(new Point(currentPoint.x+0, currentPoint.y-1));
				adjacentRooms.add(new Point(currentPoint.x+0, currentPoint.y+1));
				for (Point room : adjacentRooms) {
					if (!roomPositions.contains(room)) {
						availableRooms.add(room);
					}
				}
				
				//Add a room randomly from the availableRooms to the list of roomPositions
				if (availableRooms.size() > 0) {
					roomPositions.add(availableRooms.get((int) (Math.random()*availableRooms.size())));
				} else { //If no available rooms restart room position generation
					break;
				}
			}
			//If we have a good level exit room position generation
			if (roomPositions.size() == numOfRooms) {
				invalidLevel = false;
			}
		}
		
		for (int x = 0; x < numOfRooms-1; x++) {
			Point currentRoom = roomPositions.get(x);
			Point[] adjacentRooms = new Point[4];
			adjacentRooms[0] = new Point(currentRoom.x+0, currentRoom.y-1); //North
			adjacentRooms[1] = new Point(currentRoom.x+1, currentRoom.y+0); //East
			adjacentRooms[2] = new Point(currentRoom.x+0, currentRoom.y+1); //South
			adjacentRooms[3] = new Point(currentRoom.x-1, currentRoom.y+0); //West
	
			for (int y = 0; y < 4; y++) {
				int index = roomPositions.indexOf(adjacentRooms[y]);
				levelDoors[x][y] = index;
			}
		}
		for (int y = 0; y < 4; y++) {
			levelDoors[numOfRooms-1][(y+2)%4] = levelDoors[numOfRooms-2][y] == numOfRooms-1 ? numOfRooms-2: -1;
		}
		
		return levelDoors;
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

	public void addLevelListener(LevelListener ll) {
		levelListeners.add(ll);
	}
}

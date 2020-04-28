package com.pixelcross.cavemanbrawl.levels;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Justin Schreiber
 *
 * Generates room walls, spawns and misc items into their respective layers 
 */
/**
 * @author Justin Schreiber
 *
 */
/**
 * @author Justin Schreiber
 *
 */
public class RoomGenerator {

	private int width, height;
	private int[][] foreground, spawns, entities, spawnableArea;
	private List<Point> availableSpawns;
	
	/**
	 * Creates a room generator using the map made by the MapGenerator
	 * 
	 * @param map
	 */
	public RoomGenerator(int[][] map) {
		this.foreground = map;
		width = map.length;
		height = map[0].length;
		availableSpawns = new ArrayList<Point>();
		spawnableArea = new int[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				spawnableArea[x][y] = map[x][y];
				if (map[x][y] == 0 && getSurroundingWallCount(x, y) > 0) {
					spawnableArea[x][y] = 1;
				}
			}
		}
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (checkSpawnSquare(x, y, 2)) {
					availableSpawns.add(new Point(x, y));
				}
			}
		}
		this.spawns = new int[foreground.length][foreground[0].length];
		this.entities = new int[foreground.length][foreground[0].length];
	}
	
	/**
	 * Checks if 2x2 area is available for spawning
	 * 
	 * @param startX
	 * @param startY
	 * @return true if the area is available for spawning
	 */
	private boolean checkSpawnSquare(int startX, int startY, int size) {
		for (int y = startY; y < startY + size; y++) {
			for (int x = startX; x < startX + size; x++) {
				if (x > 0 && x < width - size && y > 0 && y < height - size) {
					if (spawnableArea[x][y] == 1) {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Generates a map for the foreground/walls layer
	 * 
	 * @return a map for the foreground/walls layer
	 */
	public int[][] generateForeground() {
		return foreground;
	}
	
	/**
	 *  Generates a map for the spawns layer
	 * 
	 * @return a map for the spawns layer
	 */
	public int[][] generateSpawns(boolean[] doors) {
		findPlayerSpawn(doors);
		return spawns;
	}
	
	/**
	 * Adds a spawn point for the player
	 */
	private void randomPlayerSpawn() {
		Point spawn = availableSpawns.get((int)(Math.random()*availableSpawns.size()));
		spawns[spawn.x][spawn.y] = 1;
	}
	
	
	/**
	 * Using the doors to other rooms it finds the farthest available spawn position
	 * 
	 * @param doors
	 */
	private void findPlayerSpawn(boolean[] doors) {
		List<Point> doorPoints = new ArrayList<Point>();
		if (doors[0]) {
			doorPoints.add(new Point(width/2, 0));
		}
		if (doors[1]) {
			doorPoints.add(new Point(width-1, height/2));
		}
		if (doors[2]) {
			doorPoints.add(new Point(width/2, height-1));
		}
		if (doors[3]) {
			doorPoints.add(new Point(0, height/2));
		}
		Point[] doorArray = new Point[doorPoints.size()];
		doorPoints.toArray(doorArray);
		Point spawn = findFarthestSpawn(doorArray);
		if (spawn != null) {
			spawns[spawn.x][spawn.y] = 1;
			for (Point p : doorPoints) {
				spawns[p.x][p.y] = -1;
			}
		}
	}
	
	
	/**
	 * Using flood fill get all the tiles within a certain distance, in a specified direction from a starting point
	 * 
	 * @param distance (The distance the flood fill should cover. Use -1 or 0 to ignore distance)
	 * @param direction (The direction the flood fill should travel. 0 - Up, 1 - Right, 2 - Down, 3 - Left, -1 - All Directions)
	 * @param startingPoint 
	 * @return a list of points the flood fill traveled through
	 */
	private List<Point> floodFill(int distance, int direction, Point startingPoint) {
		List<Point> tiles = new ArrayList<Point>();
		int[][] mapFlags = new int[width][height];
		int tileType = 0;
		
		Queue<FloodPoint> queue = new LinkedList<FloodPoint>();
		queue.add(new FloodPoint(startingPoint.x, startingPoint.y, 0));
		mapFlags[startingPoint.x][startingPoint.y] = 1;
		Point[] adjustments = getDirectionAdjustments(direction);
		
		while(!queue.isEmpty()) {
			FloodPoint tile = queue.poll();
			int currentStep = tile.step;
			tiles.add(tile);
		
			if (currentStep < distance || distance < 1) {
				for (int x = tile.x + adjustments[0].x; x <= tile.x + adjustments[0].x; x++) {
					for (int y = tile.y + adjustments[0].y; y <= tile.y + adjustments[0].y; y++) {
						if ((y == tile.y || x == tile.x) && isInMapRange(x, y) && mapFlags[x][y] == 0 && foreground[x][y] == tileType) {
							mapFlags[x][y] = 1;
							queue.add(new FloodPoint(x, y, currentStep+1));
						}
					}
				}
			}
		}
		return tiles;
	}
	
	private Point[] getDirectionAdjustments(int direction) {
		Point[] points = new Point[2];
		if (direction == 0) {
			points[0] = new Point(-1, -1);
			points[1] = new Point(1, 0);
		} else if (direction == 1) {
			points[0] = new Point(0, -1);
			points[1] = new Point(1, 1);
		} else if (direction == 2) {
			points[0] = new Point(-1, 0);
			points[1] = new Point(1, 1);
		} else if (direction == 3) {
			points[0] = new Point(-1, -1);
			points[1] = new Point(0, 1);
		} else {
			points[0] = new Point(-1, -1);
			points[1] = new Point(1, 1);
		}
		return points;
	}
	
	/**
	 * Use flood filling to find the farthest available spawn from a list of points
	 * 
	 * @param startingPoints
	 * @return the spawn point farthest from the startingPoints
	 */
	private Point findFarthestSpawn(Point... startingPoints) {
		int[][] mapFlags = new int[width][height];
		int tileType = 0;
		Point furthestSpawn = null;
		
		Queue<Point> queue = new LinkedList<Point>();
		for (Point p : startingPoints) {
			queue.add(p);
			mapFlags[p.x][p.y] = 1;
		}
		
		while(!queue.isEmpty()) {
			Point tile = queue.poll();
			if (availableSpawns.contains(tile)) {
				furthestSpawn = tile;
			}
		
			for (int x = tile.x - 1; x <= tile.x + 1; x++) {
				for (int y = tile.y - 1; y <= tile.y + 1; y++) {
					if (isInMapRange(x, y) && (y == tile.y || x == tile.x)) {
						if (mapFlags[x][y] == 0 && foreground[x][y] == tileType) {
							mapFlags[x][y] = 1;
							queue.add(new Point(x, y));
						}
					}
				}
			}
		}
		return furthestSpawn;
	}
	
	public int[][] getSpawnableArea() {
		return spawnableArea;
	}
	
	public List<Point> getAvailableSpawns() {
		return availableSpawns;
	}
	
	/**
	 * Check if the point is within the map
	 * 
	 * @param x
	 * @param y
	 * @return true if the point is inside the map
	 */
	private boolean isInMapRange(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	/**
	 * Gets how many walls are around the tile at x, y
	 * 
	 * @param gridX
	 * @param gridY
	 * @return the number of walls surrounding the tile at x, y
	 */
	private int getSurroundingWallCount(int gridX, int gridY) {
		int wallCount = 0;
		for (int neighbourX = gridX - 1; neighbourX <= gridX + 1; neighbourX ++) {
			for (int neighbourY = gridY - 1; neighbourY <= gridY + 1; neighbourY ++) {
				if (neighbourX >= 0 && neighbourX < width && neighbourY >= 0 && neighbourY < height) {
					if (neighbourX != gridX || neighbourY != gridY) {
						wallCount += foreground[neighbourX][neighbourY];
					}
				}
				else {
					wallCount ++;
				}
			}
		}

		return wallCount;
	}

}

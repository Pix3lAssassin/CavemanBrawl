package com.pixelcross.cavemanbrawl.levels;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin Schreiber
 *
 * Generates room walls, spawns and misc items into their respective layers 
 */
public class RoomGenerator {

	private int width, height;
	private int[][] foreground, spawns, entities;
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
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (checkSpawnSquare(x, y)) {
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
	private boolean checkSpawnSquare(int startX, int startY) {
		for (int y = startY; y < startY + 2; y++) {
			for (int x = startX; x < startX + 2; x++) {
				if (x >= 2 && x < width - 2 && y >= 2 && y < height - 2) {
					if (foreground[x][y] == 1) {
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
	public int[][] generateSpawns() {
		addPlayerSpawn();
		return spawns;
	}
	
	/**
	 * Adds a spawn point for the player
	 */
	private void addPlayerSpawn() {
		Point spawn = availableSpawns.get((int)(Math.random()*availableSpawns.size()));
		spawns[spawn.x][spawn.y] = 1;
	}
	
}

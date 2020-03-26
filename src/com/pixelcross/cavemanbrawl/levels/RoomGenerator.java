package com.pixelcross.cavemanbrawl.levels;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class RoomGenerator {

	int width, height;
	int[][] foreground, spawns, entities;
	List<Point> availableSpawns;
	
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
	
	private boolean checkSpawnSquare(int startX, int startY) {
		for (int y = startY; y < startY + 1; y++) {
			for (int x = startX; x < startX + 1; x++) {
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
	
	public int[][] generateForeground() {
		return foreground;
	}
	
	public int[][] generateSpawns() {
		addPlayerSpawn();
		return spawns;
	}
	
	private void addPlayerSpawn() {
		Point spawn = availableSpawns.get((int)(Math.random()*availableSpawns.size()));
		spawns[spawn.x][spawn.y] = 1;
	}
	
}

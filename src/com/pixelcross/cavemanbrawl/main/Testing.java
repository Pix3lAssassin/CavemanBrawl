package com.pixelcross.cavemanbrawl.main;

import com.pixelcross.cavemanbrawl.levels.MapGenerator;

public class Testing {

	static int mapWidth = 80, mapHeight = 60, randomFillPercent = 50, cleaningAmt = 1;

	public static void main(String[] args) {
		
		String seed = "742324545865800";
		
		boolean[] doors = new boolean[4];
		doors[0] = false;
		doors[1] = false;
		doors[2] = false;
		doors[3] = false;
		
		MapGenerator mg = new MapGenerator(mapWidth, mapHeight, randomFillPercent, seed);
		int[][] map = mg.generateMap(doors);
	}
}

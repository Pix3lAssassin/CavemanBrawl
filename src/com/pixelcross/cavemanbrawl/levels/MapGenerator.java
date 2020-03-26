package com.pixelcross.cavemanbrawl.levels;

import java.awt.Point;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.pixelcross.cavemanbrawl.main.GameAdjustment;


public class MapGenerator {

		private int width;
		private int height;
		int borderSize = 2;

		private String seed;
		private boolean useRandomSeed;

		private int randomFillPercent;
		private int smoothingAmt = 4;
		private int cleaningAmt;

		private int[][] map;

		public MapGenerator(int width, int height, int randomFillPercent, int cleaningAmt, String seed) {
			this.width = width - (borderSize * 2);
			this.height = height - (borderSize * 2);
			this.randomFillPercent = randomFillPercent;
			this.seed = seed;
			this.cleaningAmt = cleaningAmt;
			useRandomSeed = false;
		}

		public MapGenerator(int width, int height, int randomFillPercent, int cleaningAmt) {
			this.width = width - (borderSize * 2);
			this.height = height - (borderSize * 2);
			this.randomFillPercent = randomFillPercent;
			this.cleaningAmt = cleaningAmt;
			useRandomSeed = true;
		}

		public void setUseRandomSeed(boolean isRandom) {
			this.useRandomSeed = isRandom;
		}
		
		public int[][] generateMap(boolean[] doors) {
			map = new int[width][height];
			RandomFillMap();

			for (int i = 0; i < smoothingAmt; i ++) {
				map = smoothMap(map);
			}
			
			processMap();
			
			int[][] borderedMap = new int[width + borderSize * 2][height + borderSize * 2];

			for (int x = 0; x < borderedMap.length; x ++) {
				for (int y = 0; y < borderedMap[x].length; y ++) {
					if (x >= borderSize && x < width + borderSize && y >= borderSize && y < height + borderSize) {
						borderedMap[x][y] = map[x-borderSize][y-borderSize];
					}
					else {
						if (y == 0 && x > borderedMap.length/2-2 && x < borderedMap.length/2+2 && doors[0]) {
							borderedMap[x][y] = 0;
						} else if (x == borderedMap.length-1 && y > borderedMap[x].length/2-2 && y < borderedMap[x].length/2+2 && doors[1]) {
							borderedMap[x][y] = 0;
						} else if (y == borderedMap[x].length-1 && x > borderedMap.length/2-2 && x < borderedMap.length/2+2 && doors[2]) {
							borderedMap[x][y] = 0;
						} else if (x == 0 && y > borderedMap[x].length/2-2 && y < borderedMap[x].length/2+2 && doors[3]) {
							borderedMap[x][y] = 0;
						} else {
							borderedMap[x][y] = 1;
						}
					}
				}
			}
			
			map = getMapData(borderedMap);
			width = map.length;
			height = map[0].length;
			List<List<Point>> roomRegions = getRegions(0);
			List<CaveRoom> remainingRooms = new ArrayList<CaveRoom>();
			for (List<Point> roomRegion : roomRegions) {
				remainingRooms.add(new CaveRoom(roomRegion, map));
			}

			remainingRooms.sort(null);
			remainingRooms.get(0).isMainRoom = true;
			remainingRooms.get(0).isAccessibleFromMainRoom = true;
			
			connectClosestRooms(remainingRooms, false);
				
			processMap();
			
			for (int i = 0; i < cleaningAmt; i++) {
				map = cleanMap(map);
			}
			
			width = width - 2;
			height = height - 2;
			for (int x = 0; x < borderedMap.length; x ++) {
				for (int y = 0; y < borderedMap[x].length; y ++) {
					if (x >= 1 && x < width + 1 && y >= 1 && y < height + 1) {
						borderedMap[x][y] = map[x-1][y-1];
					}
					else {
						if (y == 0 && x > borderedMap.length/2-4 && x < borderedMap.length/2+4 && doors[0]) {
							borderedMap[x][y] = 0;
						} else if (x == borderedMap.length-1 && y > borderedMap[x].length/2-4 && y < borderedMap[x].length/2+4 && doors[1]) {
							borderedMap[x][y] = 0;
						} else if (y == borderedMap[x].length-1 && x > borderedMap.length/2-4 && x < borderedMap.length/2+4 && doors[2]) {
							borderedMap[x][y] = 0;
						} else if (x == 0 && y > borderedMap[x].length/2-2 && y < borderedMap[x].length/2+2 && doors[3]) {
							borderedMap[x][y] = 0;
						} else {
							borderedMap[x][y] = 1;
						}
					}
				}
			}
			
			return borderedMap;
		}

		private void addPlayerSpawn() {
			
		}
				
		public void debugCleanMap(CaveRoom room) {
			Point startingTile = room.tiles.get(0);
			CaveRoom fullRoom = new CaveRoom(getRegionTiles(startingTile.x, startingTile.y), map);
			
			int counter = 0;
			for (int i = 0; i < fullRoom.tiles.size()-1; i++) {
				Point tile = fullRoom.tiles.get(i);
				int[][] testMap = new int[width][height];
				for (int x = 0; x < map.length; x ++) {
					for (int y = 0; y < map[x].length; y ++) {
						testMap[x][y] = map[x][y];
					}
				}
				testMap[tile.x][tile.y] = 1;
				
				Point tileStart = fullRoom.tiles.get(i+1);
				List<Point> roomTiles = getRegionTiles(tileStart.x, tileStart.y);
				if (roomTiles.size() + 3 < fullRoom.tiles.size() || roomTiles.size() < 3) {
					counter++;
					drawCircle(tile, 1);
				}
			}
			System.out.printf("Number Fixed: %d\n", counter);
		}
		
		private int[][] getMapData(int[][] dataMap) {
			int[][] recievingMap = new int[dataMap.length][dataMap[0].length];
			for (int x = 0; x < recievingMap.length; x ++) {
				for (int y = 0; y < recievingMap[x].length; y ++) {
					recievingMap[x][y] = dataMap[x][y];
				}
			}
			return recievingMap;
		}
		
		private void processMap() {
			List<List<Point>> wallRegions = getRegions(1);
			int wallThresholdSize = 25;
			for (List<Point> wallRegion : wallRegions) {
				if (wallRegion.size() < wallThresholdSize) {
					for (Point tile : wallRegion) {
						map[tile.x][tile.y] = 0;
					}
				}
			}
			
			List<List<Point>> roomRegions = getRegions(0);
			int roomThresholdSize = 100;
			
			for (List<Point> roomRegion : roomRegions) {
				if (roomRegion.size() < roomThresholdSize) {
					for (Point tile : roomRegion) {
						map[tile.x][tile.y] = 1;
					}
				} 
			}
			
			List<CaveWall> walls = new ArrayList<CaveWall>();
			for (List<Point> wall : wallRegions) {
				walls.add(new CaveWall(wall, map));
			}
			
			walls.sort(null);
			for (CaveWall wall : walls) {
				wall.checkNearbyWalls(3);
			} 
		}
		
		private void connectClosestRooms(List<CaveRoom> allRooms, boolean forceAccessibilityFromMainRoom) {
			
			List<CaveRoom> roomListA = new ArrayList<CaveRoom>();
			List<CaveRoom> roomListB = new ArrayList<CaveRoom>();
			
			if (forceAccessibilityFromMainRoom) {
				for (CaveRoom room : allRooms) {
					if (room.isAccessibleFromMainRoom) {
						roomListB.add(room);
					} else {
						roomListA.add(room);
					}
				}
			} else {
				roomListA = allRooms;
				roomListB = allRooms;
			}
			
			int bestDistance = 0;
			Point bestTileA = new Point();
			Point bestTileB = new Point();
			CaveRoom bestRoomA = new CaveRoom();
			CaveRoom bestRoomB = new CaveRoom();
			boolean possibleConnectionFound = false;
			
			for (CaveRoom roomA : roomListA) {
				if (!forceAccessibilityFromMainRoom) {
					possibleConnectionFound = false;
					if (roomA.connectedRooms.size() > 0) {
						continue;
					}
				}
				for (CaveRoom roomB : roomListB) {
					if (roomA == roomB || roomA.isConnected(roomB)) {
						continue;
					}
					for (int tileIndexA = 0; tileIndexA < roomA.edgeTiles.size(); tileIndexA++) {
						for (int tileIndexB = 0; tileIndexB < roomB.edgeTiles.size(); tileIndexB++) {
							Point tileA = roomA.edgeTiles.get(tileIndexA);
							Point tileB = roomB.edgeTiles.get(tileIndexB);
							int distanceBetweenRooms = (int) (Math.pow(tileA.x-tileB.x, 2) + Math.pow(tileA.y-tileB.y, 2));
							
							if (distanceBetweenRooms < bestDistance || !possibleConnectionFound) {
								bestDistance = distanceBetweenRooms;
								possibleConnectionFound = true;
								bestTileA = tileA;
								bestTileB = tileB;
								bestRoomA = roomA;
								bestRoomB = roomB;
							}
						}
					}
				}
				
				if (possibleConnectionFound && !forceAccessibilityFromMainRoom) {
					createPassage(bestRoomA, bestRoomB, bestTileA, bestTileB);
				}
			} 

			if (possibleConnectionFound && forceAccessibilityFromMainRoom) {
				createPassage(bestRoomA, bestRoomB, bestTileA, bestTileB);
				connectClosestRooms(allRooms, true);
			}
			
			if (!forceAccessibilityFromMainRoom) {
				connectClosestRooms(allRooms, true);
			}
		}
		
		private void createPassage(CaveRoom roomA, CaveRoom roomB, Point tileA, Point tileB) {
			roomA.connectRooms(roomB);
			
			List<Point> line = getLine(tileA, tileB);
			for (Point p : line) {
				drawCircle(p, 3);
			}
			
		}
		
		private void drawCircle (Point p, int r) {
			for (int x = -r; x <= r; x++) {
				for (int y = -r; y <= r; y++) {
					if (x*x + y*y <= r*r) {
						int drawX = p.x + x;
						int drawY = p.y + y;
						if (isInMapRange(drawX, drawY)) {
							map[drawX][drawY] = 0;
						}
					}
				}
			}
		}
		
		private List<Point> getLine(Point from, Point to) {
			List<Point> line = new ArrayList<Point>();
			
			int x = from.x;
			int y = from.y;
			
			int dx = to.x - from.x;
			int dy = to.y - from.y;
			
			boolean inverted = false;
			int step = sign(dx); 
			int gradientStep = sign(dy);
			
			int longest = Math.abs(dx);
			int shortest = Math.abs(dy);
			
			if (longest < shortest) {
				inverted = true;
				longest = Math.abs(dy);
				shortest = Math.abs(dx);
		
				step = sign(dy);
				gradientStep = sign(dx);
			}
			
			int gradientAccumulation = longest / 2;
			for (int i = 0; i < longest; i++) {
				line.add(new Point(x, y));
			
				if (inverted) {
					y += step;
				} else {
					x += step;
				}
				
				gradientAccumulation += shortest;
				if (gradientAccumulation >= longest) {
					if (inverted) {
						x += gradientStep;
					} else {
						y += gradientStep;
					}
					
					gradientAccumulation -= longest;
				}
			}
			
			return line;
		}
		
		private int sign(int i) {
			return i < 0 ? -1 : i > 0 ? 1 : 0;
		}
		
		private List<List<Point>> getRegions(int tileType) {
			List<List<Point>> regions = new ArrayList<List<Point>>();
			int[][] mapFlags = new int[width][height];
			
			for (int x = 0; x < width; x ++) {
				for (int y = 0; y < height; y ++) {
					if (mapFlags[x][y] == 0 && map[x][y] == tileType) {
						List<Point> newRegion = getRegionTiles(x, y);
						regions.add(newRegion);
						
						for(Point tile : newRegion) {
							mapFlags[tile.x][tile.y] = 1;
						}
					}
				}
			}
			
			return regions;
		}
		
		private List<Point> getRegionTiles(int startX, int startY) {
			List<Point> tiles = new ArrayList<Point>();
			int[][] mapFlags = new int[width][height];
			int tileType = map[startX][startY];
			
			Queue<Point> queue = new LinkedList<Point>();
			queue.add(new Point(startX, startY));
			mapFlags[startX][startY] = 1;
			
			while(!queue.isEmpty()) {
				Point tile = queue.poll();
				tiles.add(tile);
			
				for (int x = tile.x - 1; x <= tile.x + 1; x++) {
					for (int y = tile.y - 1; y <= tile.y + 1; y++) {
						if (isInMapRange(x, y) && (y == tile.y || x == tile.x)) {
							if (mapFlags[x][y] == 0 && map[x][y] == tileType) {
								mapFlags[x][y] = 1;
								queue.add(new Point(x, y));
							}
						}
					}
				}
			}
			
			return tiles;
		}

		private boolean isInMapRange(int x, int y) {
			return x >= 0 && x < width && y >= 0 && y < height;
		}
		
		private void RandomFillMap() {
			if (useRandomSeed) {
				seed = System.nanoTime() + "";
			}

			Random pseudoRandom = new Random(seed.hashCode());

			for (int x = 0; x < width; x ++) {
				for (int y = 0; y < height; y ++) {
					if (x == 0 || x == width-1 || y == 0 || y == height -1) {
						map[x][y] = 1;
					}
					else {
						map[x][y] = (pseudoRandom.nextInt(100) < randomFillPercent)? 1: 0;
					}
				}
			}
		}

		public int[][] smoothMap(int[][] map) {
			for (int x = 0; x < width; x ++) {
				for (int y = 0; y < height; y ++) {
					int neighbourWallTiles = GetSurroundingWallCount(x,y);

					if (neighbourWallTiles > 4)
						map[x][y] = 1;
					else if (neighbourWallTiles < 4)
						map[x][y] = 0;

				}
			}
			return getMapData(map);
		}

		private int[][] cleanMap(int[][] map) {
			for (int y = 0; y < height; y ++) {
				for (int x = 0; x < width; x ++) {
					int tileType = map[x][y];
					int neighbourWallTiles = GetSurroundingWallCount(x,y);

					if (tileType == 0) {
						if (neighbourWallTiles > 4) {
							map[x][y] = 1;
						} else if (neighbourWallTiles == 4) {
							int[] surroundingWalls = GetSurroundingWalls(x, y);
							if (surroundingWalls[1] == 1 && surroundingWalls[2] == 1 && surroundingWalls[6] == 1 && surroundingWalls[7] == 1) {
								map[x+0][y-1] = 0;
								map[x+1][y-1] = 0;
							} else if (surroundingWalls[0] == 1 && surroundingWalls[1] == 1 && surroundingWalls[7] == 1 && surroundingWalls[8] == 1) {
								map[x-1][y-1] = 0;
								map[x+0][y-1] = 0;
							} else if (surroundingWalls[0] == 1 && surroundingWalls[3] == 1 && surroundingWalls[5] == 1 && surroundingWalls[8] == 1) {
								map[x-1][y-1] = 0;
								map[x-1][y+0] = 0;
							} else if (surroundingWalls[2] == 1 && surroundingWalls[3] == 1 && surroundingWalls[5] == 1 && surroundingWalls[6] == 1) {
								map[x+1][y-1] = 0;
								map[x+1][y+0] = 0;
							}
						} else if (neighbourWallTiles == 3) {
							int[] surroundingWalls = GetSurroundingWalls(x, y);
							if (surroundingWalls[0] == 1 && surroundingWalls[1] == 1 && surroundingWalls[8] == 1) {
								map[x-1][y-1] = 0;
								map[x+0][y-1] = 0;
							} else if (surroundingWalls[0] == 1 && surroundingWalls[3] == 1 && surroundingWalls[8] == 1) {
								map[x-1][y-1] = 0;
								map[x-1][y+0] = 0;
							} else if (surroundingWalls[0] == 1 && surroundingWalls[5] == 1 && surroundingWalls[8] == 1) {
								map[x+1][y+0] = 0;
								map[x+1][y+1] = 0;
							} else if (surroundingWalls[0] == 1 && surroundingWalls[7] == 1 && surroundingWalls[8] == 1) {
								map[x+1][y+1] = 0;
								map[x+0][y+1] = 0;
							} else if (surroundingWalls[2] == 1 && surroundingWalls[1] == 1 && surroundingWalls[6] == 1) {
								map[x+0][y-1] = 0;
								map[x+1][y-1] = 0;
							} else if (surroundingWalls[2] == 1 && surroundingWalls[5] == 1 && surroundingWalls[6] == 1) {
								map[x+1][y-1] = 0;
								map[x+1][y+0] = 0;
							} else if (surroundingWalls[2] == 1 && surroundingWalls[3] == 1 && surroundingWalls[6] == 1) {
								map[x-1][y+0] = 0;
								map[x-1][y+1] = 0;
							} else if (surroundingWalls[2] == 1 && surroundingWalls[7] == 1 && surroundingWalls[6] == 1) {
								map[x-1][y+1] = 0;
								map[x+0][y+1] = 0;
							}
						} else if (neighbourWallTiles == 2) {
							int[] surroundingWalls = GetSurroundingWalls(x, y);
							if (surroundingWalls[2] == 1 && surroundingWalls[6] == 1) {
								map[x+1][y-1] = 0;
							} else if (surroundingWalls[0] == 1 && surroundingWalls[8] == 1) {
								map[x-1][y-1] = 0;
							}
						}
					} else {
						if (neighbourWallTiles < 4)
							map[x][y] = 0;
					}
					
				}
			}
			return getMapData(map);
		}

		private int GetSurroundingWallCount(int gridX, int gridY) {
			int wallCount = 0;
			for (int neighbourX = gridX - 1; neighbourX <= gridX + 1; neighbourX ++) {
				for (int neighbourY = gridY - 1; neighbourY <= gridY + 1; neighbourY ++) {
					if (neighbourX >= 0 && neighbourX < width && neighbourY >= 0 && neighbourY < height) {
						if (neighbourX != gridX || neighbourY != gridY) {
							wallCount += map[neighbourX][neighbourY];
						}
					}
					else {
						wallCount ++;
					}
				}
			}

			return wallCount;
		}
		
		private int[] GetSurroundingWalls(int gridX, int gridY) {
			int[] surroundingWalls = new int[9];
			int indexCounter = 0;
			for (int neighbourY = gridY - 1; neighbourY <= gridY + 1; neighbourY ++) {
				int indexY = neighbourY - gridY + 1;
				for (int neighbourX = gridX - 1; neighbourX <= gridX + 1; neighbourX ++) {
					int indexX = neighbourX - gridX + 1;
					if (neighbourX >= 0 && neighbourX < width && neighbourY >= 0 && neighbourY < height) {
						surroundingWalls[indexY * 3 + indexX] = map[neighbourX][neighbourY];
					}
					else {
						surroundingWalls[indexY * 3 + indexX] = 1;
					}
				}
			}

			return surroundingWalls;
		}
		
		public String getSeed() {
			return seed;
		}
		
		class CaveRoom implements Comparable<CaveRoom> {
			public List<Point> tiles;
			public List<Point> edgeTiles;
			public List<CaveRoom> connectedRooms;
			public int roomSize;
			public boolean isAccessibleFromMainRoom;
			public boolean isMainRoom;
			
			public CaveRoom() {
				
			}
			
			public CaveRoom(List<Point> roomTiles, int[][] map) {
				tiles = roomTiles;
				roomSize = tiles.size();
				connectedRooms = new ArrayList<CaveRoom>();
				
				edgeTiles = new ArrayList<Point>();
				for (Point tile : tiles) {
					for (int x = tile.x - 1; x <= tile.x + 1; x++) {
						for (int y = tile.y - 1; y <= tile.y + 1; y++) {
							if (isInMapRange(x, y) && map[x][y] == 1 && (y == tile.y || x == tile.x)) {
								edgeTiles.add(tile);
							}
						}
					}
				}
			}
			
			public void setAccessibleFromMainRoom() {
				if (!isAccessibleFromMainRoom) {
					isAccessibleFromMainRoom = true;
					for (CaveRoom connectedRoom : connectedRooms) {
						connectedRoom.setAccessibleFromMainRoom();
					}
				}
			}
			
			public void connectRooms(CaveRoom roomB) {
				if (this.isAccessibleFromMainRoom) {
					roomB.setAccessibleFromMainRoom();
				} else if (roomB.isAccessibleFromMainRoom) {
					this.setAccessibleFromMainRoom();
				}
				connectedRooms.add(roomB);
				roomB.connectedRooms.add(this);
			}
			
			public boolean isConnected(CaveRoom otherRoom) {
				return connectedRooms.contains(otherRoom);
			}

			@Override
			public int compareTo(CaveRoom ocr) {
				return ocr.roomSize - roomSize;
			}
		}
		
		class CaveWall implements Comparable<CaveWall> {
			public List<Point> tiles;
			public List<Point> edgeTiles;
			public int wallSize;
			
			public CaveWall() {	}
			
			public CaveWall(List<Point> wallTiles, int[][] map) {
				tiles = wallTiles;
				wallSize = tiles.size();
				
				edgeTiles = new ArrayList<Point>();
				for (Point tile : tiles) {
					for (int x = tile.x - 1; x <= tile.x + 1; x++) {
						for (int y = tile.y - 1; y <= tile.y + 1; y++) {
							if (isInMapRange(x, y) && map[x][y] == 1 && (y == tile.y || x == tile.x)) {
								edgeTiles.add(tile);
							}
						}
					}
				}
			}
			
			private void updateEdgeTiles() {
				edgeTiles = new ArrayList<Point>();
				for (Point tile : tiles) {
					for (int x = tile.x - 1; x <= tile.x + 1; x++) {
						for (int y = tile.y - 1; y <= tile.y + 1; y++) {
							if (isInMapRange(x, y) && map[x][y] == 1 && (y == tile.y || x == tile.x)) {
								edgeTiles.add(tile);
							}
						}
					}
				}
			}
			
			public void checkNearbyWalls(int r) {
				for (Point tile : edgeTiles) {
					for (int x = tile.x - r; x <= tile.x + r; x++) {
						for (int y = tile.y - r; y <= tile.y + r; y++) {
							if (isInMapRange(x, y) && map[x][y] == 1 && !tiles.contains(new Point(x, y))) {
								map[x][y] = 0;
								tiles.remove(tile);
							}
						}
					}
				}
				updateEdgeTiles();
			}
			
			@Override
			public int compareTo(CaveWall ocw) {
				return ocw.wallSize - wallSize;
			}
		}
	
}

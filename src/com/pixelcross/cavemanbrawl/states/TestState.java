package com.pixelcross.cavemanbrawl.states;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import com.pixelcross.cavemanbrawl.gfx.ImageLoader;
import com.pixelcross.cavemanbrawl.levels.MapGenerator;
import com.pixelcross.cavemanbrawl.main.Timer;
import com.pixelcross.cavemanbrawl.neuralnetwork.fullyconnectednetwork.Network;
import com.pixelcross.cavemanbrawl.neuralnetwork.trainset.TrainSet;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class TestState extends State implements Runnable {

	int mapWidth = 80, mapHeight = 60, randomFillPercent = 50, smoothingAmt = 4, scale;
	Image cave;
	Canvas c;
	TextField mapWidthField; 
	TextField mapHeightField; 
	TextField randomFillPercentField; 
	Label seedLabel, trainSetLabel, spawnCoords;
	MapGenerator mg;
	CheckBox n, e, s, w;
	boolean[] doors;
	int[][] currentMap, storedMap;
	Network net;
	TrainSet ts;
	boolean allowTraining;
	Point spawnPoint;
	TextField inputSeedField, smoothingField;
	
	public TestState(Parent root, Canvas c, TextField mapWidthField, TextField mapHeightField, TextField randomFillPercentField, 
			CheckBox n, CheckBox e, CheckBox s, CheckBox w, Label trainSetLabel, Label seedLabel, 
			TextField inputSeedField, TextField smoothingField, Label spawnCoords) {
		super(root);
		
		this.n = n;
		this.e = e;
		this.s = s;
		this.w = w;
		this.c = c;
		this.mapWidthField = mapWidthField;
		this.mapHeightField = mapHeightField;
		this.randomFillPercentField = randomFillPercentField;
		this.seedLabel = seedLabel;
		this.inputSeedField = inputSeedField;
		this.smoothingField = smoothingField;
		this.trainSetLabel = trainSetLabel;
		this.spawnCoords = spawnCoords;
		
		this.mapWidthField.setText(mapWidth + "");;
		this.mapHeightField.setText(mapHeight + "");;
		this.randomFillPercentField.setText(randomFillPercent + "");;
		this.smoothingField.setText(smoothingAmt + "");;

		net = new Network(80*60, 100, 8, 2);
		ts = new TrainSet(80*60, 1);
		
		doors = new boolean[4];
		doors[0] = n.isSelected();
		doors[1] = e.isSelected();
		doors[2] = s.isSelected();
		doors[3] = w.isSelected();
		
		cave = getCaveImage(this.mapWidth, this.mapHeight, this.randomFillPercent, doors);
	}

	public void setAllowTraining(boolean allowTraining) {
		this.allowTraining = allowTraining;
	}
	
	public void addDataSet() {
		double[] input = getInputSet();
		if (spawnPoint != null) {
			ts.addData(input, new double[] {(double) spawnPoint.x/(double) mapWidth, (double) spawnPoint.y/ (double) mapHeight});
			trainSetLabel.setText(String.format("TrainSet Size: %d", ts.size()));
		}
	}
	
	public void getNewCave() {
		try {mapWidth = Integer.parseInt(mapWidthField.getText());} catch(NumberFormatException nfe) { /* ignored */}
		try {mapHeight = Integer.parseInt(mapHeightField.getText());}  catch(NumberFormatException nfe) { /* ignored */}
		try {randomFillPercent = Integer.parseInt(randomFillPercentField.getText());}  catch(NumberFormatException nfe) { /* ignored */}
		try {smoothingAmt = Integer.parseInt(smoothingField.getText());}  catch(NumberFormatException nfe) { /* ignored */}
		
		
		String seed = inputSeedField.getText().trim();
		
		
		doors[0] = n.isSelected();
		doors[1] = this.e.isSelected();
		doors[2] = s.isSelected();
		doors[3] = w.isSelected();
		
		spawnPoint = null;
		
		if (seed.equals("")) {
			cave = getCaveImage(mapWidth, mapHeight, randomFillPercent, doors);
		} else {
			cave = getCaveImage(mapWidth, mapHeight, randomFillPercent, seed, doors);
		}
	}
	
	public void getCurrentSeed() {
		inputSeedField.setText(mg.getSeed());
	}
	
	public void train() {
		if (ts.size() > 0) {
			Thread training = new Thread(this, "Trainer");
			training.start();
		}
	}
	
	public void predictSpawn () {
		double[] input = getInputSet();
		double[] output = net.calculate(input);
		int x = (int) Math.round(output[0]*mapWidth);
		int y = (int) Math.round(output[1]*mapHeight);
		addSpawnPos(x, y);
	}
	
	private double[] getInputSet() {
		double [] map = new double[mapWidth*mapHeight];
		for (int x = 0; x < storedMap.length; x ++) {
			for (int y = 0; y < storedMap[x].length; y ++) {
				map[(y*mapHeight) + x] = storedMap[x][y];
			}
		}
		return map;
	}
	
	public void saveNetwork() {
		try {
			net.saveNetwork("res/network/network1.txt");
			System.out.println("Network saved.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadNetwork() {
		try {
			net = Network.loadNetwork("res/network/network1.txt");
			System.out.println("Network loaded.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveTrainSet() {
		try {
			ts.saveTrainSet("res/network/trainset1.txt");
			System.out.println("Network saved.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadTrainSet() {
		try {
			ts = TrainSet.loadTrainSet("res/network/trainset1.txt");
			System.out.println("Network loaded.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addSpawnPos(int spawnX, int spawnY) {
		currentMap = new int[mapWidth][mapHeight];
		for (int x = 0; x < currentMap.length; x ++) {
			for (int y = 0; y < currentMap[x].length; y ++) {
				if (x == spawnX && y == spawnY) {
					currentMap[x][y] = 2;
				} else {
					currentMap[x][y] = storedMap[x][y];
				}
			}
		}
		spawnCoords.setText(String.format("SpawnPoint: X: %d, Y: %d", spawnX, spawnY));
		spawnPoint = new Point(spawnX, spawnY);
		cave = ImageLoader.convertToFxImage(getImageFromArray(getPixelsFromMap(currentMap), currentMap.length, currentMap[0].length), scale);
	}
		
	@Override
	public void update() {
	}

	@Override
	public void render(GraphicsContext gc, double interpolation) {
		gc.clearRect(0, 0, c.getWidth(), c.getHeight());
		gc.drawImage(cave, 0, 0);
	}
	
	@Override
	protected void mousePressed(MouseEvent e) {
		Point cavePos = new Point((int) ((mouse.x-20)/scale), (int) ((mouse.y-20)/scale));
		if (cavePos.x < mapWidth && cavePos.y < mapHeight) {
			addSpawnPos(cavePos.x, cavePos.y);
		}
	}
	
	@Override
	protected void mouseReleased(MouseEvent e) {
		
	}

	private BufferedImage getImageFromArray(int[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0,0,width,height,pixels);
        image.setData(raster);
        return image;
    }

	private int[] getPixelsFromMap(int[][] map) {
		int[] pixels = new int[map.length * map[0].length * 4];
		
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				PixelColor c = new PixelColor(0, 0, 0, 255);
				if (map[x][y] == 0) {
					c = new PixelColor(200, 200, 200, 255);
				} else if (map[x][y] == 2) {
					c = new PixelColor(255, 0, 0, 255);
				}
				pixels[(y * map.length + x) * 4+0] = c.getColors()[0];
				pixels[(y * map.length + x) * 4+1] = c.getColors()[1];
				pixels[(y * map.length + x) * 4+2] = c.getColors()[2];
				pixels[(y * map.length + x) * 4+3] = c.getColors()[3];
			}
		}
		
		return pixels;
	}

	private Image getCaveImage(int mapWidth, int mapHeight, int randomFillPercent, boolean[] doors) {
		int scaleX = Math.max((int) (c.getWidth()/this.mapWidth), 1);
		int scaleY = Math.max((int) (c.getHeight()/this.mapHeight), 1);
		scale = Math.min(scaleX, scaleY);
		
		mg = new MapGenerator(mapWidth, mapHeight, randomFillPercent);
		storedMap = mg.generateMap(doors);
		String seed = mg.getSeed();
		
		seedLabel.setText(String.format("Seed: %s", seed));
		
		return ImageLoader.convertToFxImage(getImageFromArray(getPixelsFromMap(storedMap), storedMap.length, storedMap[0].length), scale);	
	}
	
	private Image getCaveImage(int mapWidth, int mapHeight, int randomFillPercent, String seed, boolean[] doors) {
		int scaleX = Math.max((int) (c.getWidth()/this.mapWidth), 1);
		int scaleY = Math.max((int) (c.getHeight()/this.mapHeight), 1);
		scale = Math.min(scaleX, scaleY);
		
		mg = new MapGenerator(mapWidth, mapHeight, randomFillPercent, seed);
		storedMap = mg.generateMap(doors);
		
		seedLabel.setText(String.format("Seed: %s", seed));
		
		return ImageLoader.convertToFxImage(getImageFromArray(getPixelsFromMap(storedMap), storedMap.length, storedMap[0].length), scale);	
	}
	
	private class PixelColor {
				
		private int r, g, b, a;
		
		public PixelColor(int r, int g, int b, int a) {
			this.r = r;
			this.g = g;
			this.b = b;
			this.a = a;
		}
		
		public int[] getColors() {
			return new int[]{r, g, b, a};
		}
	}

	
	@Override
	public void run() {
		Timer.start();
		net.train(ts, 1000, ts.size());
		Timer.printSeconds();
	}
}

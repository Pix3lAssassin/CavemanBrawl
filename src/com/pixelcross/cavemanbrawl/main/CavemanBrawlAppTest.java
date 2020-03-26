package com.pixelcross.cavemanbrawl.main;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import com.pixelcross.cavemanbrawl.gfx.Assets;
import com.pixelcross.cavemanbrawl.gfx.ImageLoader;
import com.pixelcross.cavemanbrawl.levels.MapGenerator;
import com.pixelcross.cavemanbrawl.states.GameState;
import com.pixelcross.cavemanbrawl.states.StateManager;
import com.pixelcross.cavemanbrawl.states.TestState;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CavemanBrawlAppTest extends Application {

	public static final int WIDTH = 400, HEIGHT = 300;
	TestState ts;
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Caveman Brawl");
		
		Assets.init();	
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		Button smoothMap = new Button("Smooth Map");
		VBox canvasBox = new VBox(3);
		HBox inputSeedBox = new HBox(3);
		HBox smoothingBox = new HBox(3);
		canvasBox.getChildren().addAll(canvas, inputSeedBox, smoothingBox);
		
		Label seed = new Label("Seed: ");
		Button getSeed = new Button("Use Current Seed");
		Label inputSeed = new Label("Input Seed:");
		TextField inputSeedField = new TextField();
		inputSeedBox.getChildren().addAll(seed, getSeed, inputSeed, inputSeedField);

		Label smoothing = new Label("Cleaning Amount:");
		TextField smoothingField = new TextField();
		Label spawnCoords = new Label("SpawnPoint:");
		smoothingBox.getChildren().addAll(smoothing, smoothingField, spawnCoords);

		HBox layout = new HBox(10);
		VBox form = new VBox(5);
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.getChildren().addAll(canvasBox, form);
		
		Label widthLabel, heightLabel, fillPercentLabel, trainSetLabel;
		TextField mapWidth, mapHeight, randomFillPercent;
		HBox doors = new HBox(3);
		Button newCaveButton, predictButton, trainButton, saveNetworkButton, loadNetworkButton, saveTrainSetButton, loadTrainSetButton;
		HBox allowTraining = new HBox(3);
		HBox saveBox = new HBox(2);
		HBox loadBox = new HBox(2);
		widthLabel = new Label("Width:");
		mapWidth = new TextField();
		heightLabel = new Label("Height:");
		mapHeight = new TextField();
		fillPercentLabel = new Label("Fill Percent:");
		randomFillPercent = new TextField();
		newCaveButton = new Button("New Cave");
		trainButton = new Button("Train AI");
		predictButton = new Button("AI Predict Spawn");
		trainSetLabel = new Label("TrainSet Size: 0");
		form.getChildren().addAll(widthLabel, mapWidth, heightLabel, mapHeight, fillPercentLabel, 
				randomFillPercent, doors, newCaveButton, predictButton, trainButton, trainSetLabel,
				saveBox, loadBox);
		
		saveNetworkButton = new Button("Save Network");
		loadNetworkButton = new Button("Load Network");
		saveTrainSetButton = new Button("Save TrainSet");
		loadTrainSetButton = new Button("Load TrainSet");
		saveBox.getChildren().addAll(saveNetworkButton, saveTrainSetButton);
		loadBox.getChildren().addAll(loadNetworkButton, loadTrainSetButton);
		
		
		Label nDoorLabel, eDoorLabel, sDoorLabel, wDoorLabel;
		CheckBox n, e, s, w;
		nDoorLabel = new Label("N:");
		eDoorLabel = new Label("E:");
		sDoorLabel = new Label("S:");
		wDoorLabel = new Label("W:");
		n = new CheckBox();
		e = new CheckBox();
		s = new CheckBox();
		w = new CheckBox();
		doors.getChildren().addAll(nDoorLabel, n, eDoorLabel, e, sDoorLabel, s, wDoorLabel, w);
		
		Label allowTrainingLabel;
		CheckBox allowTrainingCheck;
		allowTrainingLabel = new Label("Allow Training:");
		allowTrainingCheck = new CheckBox();
		allowTraining.getChildren().addAll(allowTrainingLabel, allowTrainingCheck);
		
		ts = new TestState(layout, canvas, mapWidth, mapHeight, randomFillPercent, 
				n, e, s, w, trainSetLabel, seed, inputSeedField, smoothingField, spawnCoords);
		
		newCaveButton.setOnAction(eh -> {
			ts.addDataSet();
			ts.getNewCave();
		});
		trainButton.setOnAction(eh -> ts.train());
		predictButton.setOnAction(eh -> ts.predictSpawn());
		saveNetworkButton.setOnAction(eh -> ts.saveNetwork());
		loadNetworkButton.setOnAction(eh -> ts.loadNetwork());
		saveTrainSetButton.setOnAction(eh -> ts.saveTrainSet());
		loadTrainSetButton.setOnAction(eh -> ts.loadTrainSet());
		smoothMap.setOnAction(eh -> ts.smoothMap());
		getSeed.setOnAction(eh -> ts.getCurrentSeed());
		
		primaryStage.setScene(ts);

		StateManager sm = new StateManager(ts);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		AnimationTimer timer = new AnimationTimer() {
			int updatesPerSec = 30;
			long timePerUpdate = 1000000000  / updatesPerSec;
			long nextUpdate = System.nanoTime();
			long lastTime = System.nanoTime();
			int debugTimer = 0, ticks = 0, renders = 0;

			public void handle(long currentTime) {
				debugTimer += currentTime - lastTime;
				lastTime = currentTime;
				
				while(nextUpdate < currentTime) {
					update();
					nextUpdate += timePerUpdate;
					ticks++;
				}
				
				double interpolation = (currentTime + timePerUpdate - nextUpdate) / timePerUpdate;
				render(interpolation);
				renders++;
				
//				if (debugTimer >= 1000000000) {
//					System.out.printf("UPS: %d\n", ticks);
//					System.out.printf("FPS: %d\n", renders);
//					ticks = 0;
//					renders = 0;
//					debugTimer = 0;
//				}
			}
			
			private void update() {
				sm.getState().update();
			}
			
			private void render(double interpolation) {
				sm.getState().render(gc, interpolation);
			}
		};
		timer.start();
		
		primaryStage.show();
	}
	
}

package com.pixelcross.cavemanbrawl.main;

import com.pixelcross.cavemanbrawl.gfx.Assets;
import com.pixelcross.cavemanbrawl.states.GameState;
import com.pixelcross.cavemanbrawl.states.StateManager;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class DebugWindow extends Application {
	//Debug width and height
	public static final int WIDTH = 800, HEIGHT = 600;
	private GameState gs;
	
	public DebugWindow(GameState gs) {
		this.gs = gs;
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Debugger");
		
		//Initialize assets and scenes
		Group root = new Group();
		primaryStage.setScene(gs);

		//Handles the current state
		StateManager sm = new StateManager(gs);
		
		//Initialize the canvas and get GraphicsContext object
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		//Game loop
		AnimationTimer timer = new AnimationTimer() {
			int updatesPerSec = 30;
			long timePerUpdate = 1000000000  / updatesPerSec;
			long nextUpdate = System.nanoTime();
			long lastTime = System.nanoTime();
			int debugTimer = 0, ticks = 0, renders = 0;

			public void handle(long currentTime) {
				debugTimer += currentTime - lastTime;
				lastTime = currentTime;
				
				//Update loop will update based on updatesPerSecond
				while(nextUpdate < currentTime) {
					update();
					nextUpdate += timePerUpdate;
					ticks++;
				}
				
				//Calculate interpolation and render the fram
				double interpolation = (currentTime + timePerUpdate - nextUpdate) / timePerUpdate;
				render(interpolation);
				renders++;
				
				//Debug info
				if (debugTimer >= 1000000000) {
					System.out.printf("UPS: %d\n", ticks);
					System.out.printf("FPS: %d\n", renders);
					ticks = 0;
					renders = 0;
					debugTimer = 0;
				}
			}
			
			//Updates current state
			private void update() {
				sm.getState().update();
			}
			
			//Renders current state
			private void render(double interpolation) {
				sm.getState().render(gc, interpolation);
			}
		};
		timer.start();
		
//		gc.setFill( Color.RED );
//	    gc.setStroke( Color.BLACK );
//	    gc.setLineWidth(2);
//	    Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
//	    gc.setFont( theFont );
//	    gc.fillText( "Hello, World!", 60, 50 );
//	    gc.strokeText( "Hello, World!", 60, 50 );
		
		primaryStage.show();
	}

}

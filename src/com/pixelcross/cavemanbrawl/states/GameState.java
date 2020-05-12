package com.pixelcross.cavemanbrawl.states;

import java.awt.Point;
import java.util.ArrayList;

import com.pixelcross.cavemanbrawl.entities.creatures.Player;
import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.Level;
import com.pixelcross.cavemanbrawl.levels.tiles.Tile;
import com.pixelcross.cavemanbrawl.main.CavemanBrawlApp;

import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Justin Schreiber
 *
 * @see https://www.youtube.com/playlist?list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 */
public class GameState extends State {
	
	GameCamera camera;
	Player player;
	Level level;
	
	/**
	 * Creates a game state that handles the level, player, and camera
	 * 
	 * @param root
	 */
	public GameState(Parent root) {
		super(root);
		camera = new GameCamera(this, 0, 0, CavemanBrawlApp.WIDTH, CavemanBrawlApp.HEIGHT);
		level = new Level(camera, 6, 80, 60);
		Point playerSpawn = level.getPlayerSpawn();
		player = new Player(level, input, playerSpawn.x * Tile.TILEWIDTH, playerSpawn.y * Tile.TILEHEIGHT);
		level.addLevelListener(player);
	}

	@Override
	public void update() {
		level.update();
		player.update();
		camera.centerOnEntity(player);
	}

	@Override
	public void render(GraphicsContext gc, double interpolation) {
		level.render(gc, interpolation, camera);
		player.render(gc, interpolation, camera);
		renderUI(gc);
	}

	/**
	 * Renders UI elements to the screen
	 * 
	 * @param gc
	 */
	private void renderUI(GraphicsContext gc) {
		gc.setFill( Color.BLACK );
	    Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 24 );
	    gc.setFont( theFont );
	    gc.fillText( "Health:", 10, 28 );
	    
		gc.setFill( Color.GRAY );
	    gc.fillRect(92, 10, 100, 24);
		gc.setFill( Color.RED );
	    gc.fillRect(92, 10, player.getHealth(), 24);
		gc.setStroke( Color.BLACK );
		gc.setLineWidth(4);
	    gc.strokeRect(92, 10, 100, 24);
	}
	
	public Level getLevel() {
		return level;
	}
	
}

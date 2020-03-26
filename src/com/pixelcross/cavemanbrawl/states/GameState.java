package com.pixelcross.cavemanbrawl.states;

import java.util.ArrayList;

import com.pixelcross.cavemanbrawl.entities.creatures.Player;
import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.Level;
import com.pixelcross.cavemanbrawl.main.CavemanBrawlApp;

import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;

public class GameState extends State {
	
	GameCamera camera;
	Player player;
	Level level;
	
	public GameState(Parent root) {
		super(root);
		camera = new GameCamera(this, 0, 0, CavemanBrawlApp.WIDTH, CavemanBrawlApp.HEIGHT);
		level = new Level(camera, 6, 80, 60);
		player = new Player(level, input, 100, 100);
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
	}

	public Level getLevel() {
		return level;
	}
	
}

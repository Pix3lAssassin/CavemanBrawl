package com.pixelcross.cavemanbrawl.states;

import java.util.ArrayList;

import com.pixelcross.cavemanbrawl.entities.creatures.Player;
import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.Level;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class GameState extends State {
	
	GameCamera camera;
	Player player;
	Level level;
	
	public GameState(Parent root) {
		super(root);
		level = new Level();
		player = new Player(level, input, 100, 100);
		camera = new GameCamera(this, 0, 0);
		camera.centerOnEntity(player);
	}

	@Override
	public void update() {
		player.update();
	}

	@Override
	public void render(GraphicsContext gc, double interpolation) {
		player.render(gc, interpolation, camera);
	}

	public Level getLevel() {
		return level;
	}
	
}

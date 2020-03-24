package com.pixelcross.cavemanbrawl.entities.creatures;

import java.util.ArrayList;

import com.pixelcross.cavemanbrawl.gfx.Assets;
import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.Level;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends Creature {

	private ArrayList<String> input;
	
	public Player(Level currentLevel, ArrayList<String> input, double x, double y) {
		super(currentLevel, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		bounds.x = 16;
		bounds.y = 32;
		bounds.width = 32;
		bounds.height = 32;
		
		this.input = input;
	}

	@Override
	public void update() {
		getInput();
		move();
	}

	private void getInput() {
		xMove = 0;
		yMove = 0;
		
		if(input.contains("UP"))
			yMove = -speed;
		if(input.contains("DOWN"))
			yMove = speed;
		if(input.contains("LEFT"))
			xMove = -speed;
		if(input.contains("RIGHT"))
			xMove = speed;
		
	}
	
	@Override
	public void render(GraphicsContext gc, double interpolation, GameCamera camera) {
		gc.drawImage(Assets.grass, x - camera.getxOffset(), y - camera.getyOffset(), width, height);

		gc.setStroke(Color.RED);
		gc.strokeRect(x + bounds.x - camera.getxOffset(), y + bounds.y - camera.getyOffset(), bounds.width, bounds.height);
	}

	public void setCurrentLevel(Level level) {
		currentLevel = level;
	}
}

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
		bounds.y = 24;
		bounds.width = 32;
		bounds.height = 40;
		
		this.input = input;
	}

	@Override
	public void update() {
		getInput();
		move();
		yMove *= 0.7;
		xMove *= 0.7;
	}

	private void getInput() {
		double speedChange = speed/3;
		if(input.contains("UP") && Math.abs(yMove) < speed)
			yMove -= speedChange;
		if(input.contains("DOWN") && Math.abs(yMove) < speed)
			yMove += speedChange;
		if(input.contains("LEFT") && Math.abs(xMove) < speed)
			xMove -= speedChange;
		if(input.contains("RIGHT") && Math.abs(xMove) < speed)
			xMove += speedChange;	
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

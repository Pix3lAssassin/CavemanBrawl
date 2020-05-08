package com.pixelcross.cavemanbrawl.entities.creatures;

import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.Level;

import javafx.scene.canvas.GraphicsContext;

public class Raccoon extends Creature {

	public Raccoon(Level level, double x, double y, int width, int height) {
		super(level, x, y, width, height);
	}

	@Override
	public void update() {
	}

	@Override
	public void render(GraphicsContext gc, double interpolation, GameCamera camera) {
	}

}

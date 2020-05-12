package com.pixelcross.cavemanbrawl.entities.pickupables.weapons;

import com.pixelcross.cavemanbrawl.entities.pickupables.Pickupables;
import com.pixelcross.cavemanbrawl.entities.pickupables.Weapon;
import com.pixelcross.cavemanbrawl.gfx.Assets;
import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.Level;

import javafx.scene.canvas.GraphicsContext;

public class Claw extends Weapon {

	public Claw(Level level) {
		super(level, 0, 0, 32, 32, 10, 10);
	}

	@Override
	public Claw pickup() {
		return this;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(GraphicsContext gc, double interpolation, GameCamera camera) {
		gc.drawImage(Assets.placeHolder, x - camera.getxOffset(), y - camera.getyOffset(), width, height);
	}

}

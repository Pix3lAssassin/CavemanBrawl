package com.pixelcross.cavemanbrawl.components;

import com.pixelcross.cavemanbrawl.entities.creatures.Raccoon;
import com.pixelcross.cavemanbrawl.gfx.Animation;
import com.pixelcross.cavemanbrawl.gfx.Assets;
import com.pixelcross.cavemanbrawl.util.Direction;

import javafx.scene.image.Image;

public class RaccoonAnimationController extends AnimationController {
	Animation currentAnimation;
	Raccoon r;
	Animation[] raccoonAttacking, raccoonIdle, raccoonWalking;
	Direction d;

	public RaccoonAnimationController(Raccoon r) {
		this.r = r;

		raccoonAttacking = new Animation[4];
		raccoonIdle = new Animation[4];
		raccoonWalking = new Animation[4];
		
		raccoonWalking[0] = new Animation(100, Assets.raccoon_up);
		raccoonWalking[1] = new Animation(100, Assets.raccoon_right);
		raccoonWalking[2] = new Animation(100, Assets.raccoon_down);
		raccoonWalking[3] = new Animation(100, Assets.raccoon_left);
		raccoonIdle[0] = new Animation(150, Assets.raccoon_up_idle);
		raccoonIdle[1] = new Animation(150, Assets.raccoon_right_idle);
		raccoonIdle[2] = new Animation(150, Assets.raccoon_down_idle);
		raccoonIdle[3] = new Animation(150, Assets.raccoon_left_idle);
		raccoonAttacking[0] = new Animation(200, Assets.raccoon_up_attacking);
		raccoonAttacking[1] = new Animation(200, Assets.raccoon_right_attacking);
		raccoonAttacking[2] = new Animation(200, Assets.raccoon_down_attacking);
		raccoonAttacking[3] = new Animation(200, Assets.raccoon_left_attacking);
		currentAnimation = raccoonIdle[0];
		d = Direction.Left;
	}

	@Override
	public Image getFrame() {
		if (r.getxMove() > 0.1 && Math.abs(r.getxMove()) > Math.abs(r.getyMove())) { 
			d = Direction.Right;
		} else if (r.getxMove() < -0.1 && Math.abs(r.getxMove()) > Math.abs(r.getyMove())) { 
			d = Direction.Left;
		} else if (r.getyMove() < -0.1 && Math.abs(r.getyMove()) > Math.abs(r.getxMove())) { 
			d = Direction.Up;
		} else if (r.getyMove() > 0.1 && Math.abs(r.getyMove()) > Math.abs(r.getxMove())) { 
			d = Direction.Down;
		}
		
		if (r.isAttacking()) {
			if (currentAnimation != raccoonAttacking[d.ordinal()]) {
				raccoonAttacking[d.ordinal()].setFrame(0);
				currentAnimation = raccoonAttacking[d.ordinal()];
			}

			currentAnimation.tick();
			return currentAnimation.getCurrentFrame();
		} else if (r.getxMove() > 0.1 || r.getxMove() < -0.1 || r.getyMove() > 0.1 || r.getyMove() < -0.1) {
			if (currentAnimation != raccoonWalking[d.ordinal()]) {
				raccoonWalking[d.ordinal()].setFrame(0);
				currentAnimation = raccoonWalking[d.ordinal()];
			}

			currentAnimation.tick();
			return currentAnimation.getCurrentFrame();
		} else {
			if (currentAnimation != raccoonIdle[d.ordinal()]) {
				raccoonIdle[d.ordinal()].setFrame(0);
				currentAnimation = raccoonIdle[d.ordinal()];
			}

			currentAnimation.tick();
			return currentAnimation.getCurrentFrame();
		}

	}

	@Override
	public void update() {
	}

}

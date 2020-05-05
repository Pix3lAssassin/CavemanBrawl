// Ben Wilkin
package com.pixelcross.cavemanbrawl.components;

import com.pixelcross.cavemanbrawl.entities.creatures.Player;
import com.pixelcross.cavemanbrawl.gfx.Animation;
import com.pixelcross.cavemanbrawl.gfx.Assets;

import javafx.scene.image.Image;

public class PlayerAnimationController  extends AnimationController{
	Animation playerLeft;
	Animation playerRight;
	Animation playerUp;
	Animation playerDown;
	Animation currentAnimation;
	Player p;
	
	public PlayerAnimationController(Player p) {
		this.p = p;
		
			playerRight = new Animation(200, Assets.player_right);
	
			playerLeft = new Animation(200, Assets.player_left);
		
			playerUp = new Animation(200, Assets.player_up);
		
			playerDown = new Animation(200, Assets.player_down);
		
	}
	@Override
	public Image getFrame() {
		if(p.getxMove() > 0.1 && currentAnimation != playerRight) {
			playerRight.setFrame(0);
			currentAnimation = playerRight;
			
			currentAnimation.tick();
			return currentAnimation.getCurrentFrame();
		}
		else if(p.getxMove() < 0.1 && currentAnimation != playerLeft) {
			playerLeft.setFrame(0);
			currentAnimation = playerLeft;
			
			currentAnimation.tick();
			return currentAnimation.getCurrentFrame();
		}
		else if(p.getyMove() > 0.1 && currentAnimation != playerUp) {
			playerUp.setFrame(0);
			currentAnimation = playerUp;
			
			currentAnimation.tick();
			return currentAnimation.getCurrentFrame();
		}
		else if(p.getyMove() < 0.1 && currentAnimation != playerDown) {
			playerDown.setFrame(0);
			currentAnimation = playerDown;
			
			currentAnimation.tick();
			return currentAnimation.getCurrentFrame();
		}
		else return playerDown.getFrame(0);
		
		
	}
	@Override
	public void update() {
		// Doesn't do anything for now.  getFrame handles with we need for now
		
	}
}


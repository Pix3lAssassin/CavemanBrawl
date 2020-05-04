// Ben Wilkin
package com.pixelcross.cavemanbrawl.components;

import com.pixelcross.cavemanbrawl.entities.creatures.Player;
import com.pixelcross.cavemanbrawl.gfx.Animation;
import com.pixelcross.cavemanbrawl.gfx.Assets;

import javafx.scene.image.Image;

public class PlayerAnimationController{
	
	public PlayerAnimationController(Player p) {
		if(p.getxMove() > 0.1){
			Image playerSprite = new Animation(200, Assets.player_right).getCurrentFrame();
		}
		else if(p.getxMove() < -0.1){
			Image playerSprite = new Animation(200, Assets.player_left).getCurrentFrame();
		}
		else if(p.getyMove() > 0.1){
			Image playerSprite = new Animation(200, Assets.player_up).getCurrentFrame();
		}
		else if(p.getyMove() < -0.1){
			Image playerSprite = new Animation(200, Assets.player_down).getCurrentFrame();
		}
	}
}


// portions of code taken from codenmore's tutorial, part 25
package com.pixelcross.cavemanbrawl.gfx;

import javafx.scene.image.Image;

public class Animation 
{
	private long lastTime, timer;
	private int speed, index;
	private Image[] frames;
	// Speed is how many milliseconds between frames
	// frames will be given as an image array of 
	//the broken up sprite sheet
	public Animation(int speed, Image[] frames) {
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	// counts time, when time > speed, show next frame
	// cycle through frames 
	public void tick() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > speed) {
			index++;
			timer = 0;
			if(index >= frames.length)
				index = 0;
		}
	}
	// returns the Image object
	public Image getCurrentFrame() {
		return frames[index];
	}
}

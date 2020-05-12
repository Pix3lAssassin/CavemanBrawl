package com.pixelcross.cavemanbrawl.components;

import javafx.scene.image.Image;

/**
 * @author Ben Wilkin
 *
 * Handles what frame should be displayed to the screen
 */
public abstract class AnimationController implements Component {

	/**
	 * Determines what frame should be displayed to the screen
	 * 
	 * @return the image that should be displayed to the screen
	 */
	public abstract Image getFrame();
	
}

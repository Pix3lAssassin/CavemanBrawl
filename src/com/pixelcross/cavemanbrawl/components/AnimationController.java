package com.pixelcross.cavemanbrawl.components;

import javafx.scene.image.Image;

public abstract class AnimationController implements Component {

	@Override
	public abstract void update();

	public abstract Image getFrame();
}

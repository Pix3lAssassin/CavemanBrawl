package com.pixelcross.cavemanbrawl.entities;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.pixelcross.cavemanbrawl.components.Component;
import com.pixelcross.cavemanbrawl.gfx.GameCamera;

import javafx.scene.canvas.GraphicsContext;

/**
 * @author Justin Schreiber
 *
 * @see https://www.youtube.com/playlist?list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 * <br><br>
 * An interactive object in the game world
 */
public abstract class Entity {

	protected double x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected List<Component> components;
	
	/**
	 * An interactive object in the game world
	 * 
	 * @param x (The starting x position of the Entity)
	 * @param y (The starting y position of the Entity)
	 * @param width (The width of the Entity)
	 * @param height (The height of the Entity)
	 * 
	 */
	public Entity(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		bounds = new Rectangle(0, 0, width, height);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Gets a component based on the components class ex. getComponent(Collider.class)
	 * 
	 * @param componentClass (The class to look for)
	 * @return The first object from the components list that is of the type componentClass
	 */
	public <T extends Component> T getComponent(Class<T> componentClass) {
		for (Component c : components) {
			if (componentClass.isAssignableFrom(c.getClass())) {
				return (T) c;
			}
		}
		return null;
	}
	
	/**
	 * Gets a list of components based on the components class ex getComponents(AudioSource.class)
	 * 
	 * @param componentClass (The class to look for)
	 * @return A list of objects from the components list that are of the type componentClass
	 */
	public <T extends Component> List<T> getComponents(Class<T> componentClass) {
		ArrayList<T> componentList = new ArrayList<T>();
		for (Component c : components) {
			if (componentClass.isAssignableFrom(c.getClass())) {
				componentList.add((T) c);
			}
		}
		return componentList;
	}
	
	/**
	 * Update function called at regular intervals
	 */
	public abstract void update();
	
	/**
	 * Method used to render objects to the canvas according to a cameras position and time adjustment. 
	 * This method is called as fast as possible.
	 * 
	 * @param gc
	 * @param interpolation
	 * @param camera
	 */
	public abstract void render(GraphicsContext gc, double interpolation, GameCamera camera);
	
}

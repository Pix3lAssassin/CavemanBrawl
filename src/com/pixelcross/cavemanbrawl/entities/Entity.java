package com.pixelcross.cavemanbrawl.entities;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.pixelcross.cavemanbrawl.components.Component;
import com.pixelcross.cavemanbrawl.entities.pickupables.Pickupables;
import com.pixelcross.cavemanbrawl.gfx.GameCamera;
import com.pixelcross.cavemanbrawl.levels.Level;
import com.pixelcross.cavemanbrawl.util.Vector;

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
	protected Level currentLevel;
	protected List<Component> components;
	
	/**
	 * An interactive object in the game world
	 * 
	 * @param level (The level that the Entity belongs to)
	 * @param x (The starting x position of the Entity)
	 * @param y (The starting y position of the Entity)
	 * @param width (The width of the Entity)
	 * @param height (The height of the Entity)
	 * 
	 */
	public Entity(Level level, double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		bounds = new Rectangle(0, 0, width, height);
		currentLevel = level;
		components = new ArrayList<Component>();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setPos(double x, double y) {
		this.x = x;
		this.y = y;
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
	 * 	@Author Colin Kugler
	 *  Returns a new rectangle of the area around the rectangle(entity) of an object
	 */
	public Rectangle getCollisionArea(double xOff, double yOff) {
		return new Rectangle((int) (x + bounds.x + xOff), (int) (y + bounds.y + yOff), bounds.width, bounds.height);
	}
	
	/** 
	 * @Author Colin Kugler
	 * 
	 * first checks if the entity is of type pickupable for use in method. if its not it is ignored
	 * method that will check if a collision occurs between two objects and return true or false if not
	 */
	public boolean checkCollisionsForPickup(double xOff, double yOff, int tileX, int tileY) {
		for (Entity en : currentLevel.getCurrentRoom().getEntities()){
			if(en.equals(this)) {
				continue;
			}
			try {
				Pickupables p = (Pickupables) en;
				if (en.getCollisionArea(0,0).intersects(getCollisionArea(xOff,yOff))) 
					return true;
			} catch(ClassCastException cce) {}
			
		}
		return false;
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

	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * @param coords
	 * @return a vector to the specified coords
	 */
	public Vector getVectorTo(Point2D.Double coords) {
		Point2D.Double localCoords = getCenterPos();
		double distX = coords.x - localCoords.x;
		double distY = coords.y - localCoords.y;
		return new Vector(distX, distY);
	}
	
	/**
	 * @return the center of this entity
	 */
	public Point2D.Double getCenterPos() {
		return new Point2D.Double(x + bounds.x + bounds.width/2, y + bounds.y + bounds.height/2);
	}
}

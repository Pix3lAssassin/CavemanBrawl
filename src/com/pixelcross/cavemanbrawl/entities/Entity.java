package com.pixelcross.cavemanbrawl.entities;

import java.awt.Rectangle;

import com.pixelcross.cavemanbrawl.gfx.GameCamera;

import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {

	protected double x, y;
	protected int width, height;
	protected Rectangle bounds;
	
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
	
	public abstract void update();
	
	public abstract void render(GraphicsContext gc, double interpolation, GameCamera camera);
	
}

package com.pixelcross.cavemanbrawl.states;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class State extends Scene {

	protected ArrayList<String> input;
	protected Point2D.Double mouse;
	protected boolean mouseDown;
	
	public State(Parent root) {
		super(root);
		
		input = new ArrayList<String>();
		mouse = new Point2D.Double();
		
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				 
                // only add once... prevent duplicates
                if ( !input.contains(code) )
                    input.add( code );	
                keyPressed(e);
			}
		});
		this.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                input.remove( code );
                keyReleased(e);
			}
		});
		this.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				mouse.x = e.getX();
				mouse.y = e.getY();
			}
		});
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				mouseDown = true;
				mousePressed(e);
			}
		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				mouseDown = false;
				mouseReleased(e);
			}
		});
	}
	
	public abstract void update();
	
	public abstract void render(GraphicsContext gc, double interpolation);

	protected void mousePressed(MouseEvent e) {}
	
	protected void mouseReleased(MouseEvent e) {}
		
	protected void keyPressed(KeyEvent e) {}
	
	protected void keyReleased(KeyEvent e) {}
		
}

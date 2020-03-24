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
			}
		});
		this.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                input.remove( code );
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
				mouse.x = e.getX();
				mouse.y = e.getY();
			}
		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				mouse.x = e.getX();
				mouse.y = e.getY();
			}
		});
	}
	
	public abstract void update();
	
	public abstract void render(GraphicsContext gc, double interpolation);

}

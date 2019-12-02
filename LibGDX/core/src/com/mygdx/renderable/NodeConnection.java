package com.mygdx.renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class NodeConnection {
	
	Sprite line;
	float x1, x2, y1, y2;
	
	Node spreader;
	Node reciever;
	
	public NodeConnection(Node spreader, Node reciever) {
		this.spreader = spreader;
		this.reciever = reciever;
		
		line = new Sprite(new Texture(Gdx.files.local("house/baseLine.png")));
		line.setPosition(spreader.getSprite().getX(), spreader.getSprite().getY());
		
		y2 = reciever.getSprite().getY();
		x2 = reciever.getSprite().getX();
		y1 = spreader.getSprite().getY();
		x1 = spreader.getSprite().getX();
		setWidth();
		setRotationBetweenNodes();
	}
	
	public void setWidth() {
		//line.setRegionWidth((int) initial.dst(destination));

		
		
		float width = (float) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
		System.out.println("Width: " + width);
		//line.setPosition(reciever.getSprite().getX(), reciever.getSprite().getY());
		//line.setScale(1, width/8);
		//line.setRegionWidth((int) width);
		//line.setPosition(line.getX()-line.getHeight(), line.getY()-line.getWidth());
	}
	
	public void setRotationBetweenNodes() {
		float delta_x = x2 - x1;
		float delta_y = y2 - y1;
	    float theta_radians = (float) Math.atan2(delta_y, delta_x);
	    theta_radians = (float) (theta_radians - (0.5f*Math.PI));
	    float angle = (float) Math.toDegrees(theta_radians);
	    line.setRotation(angle);
	}
	
	public Sprite getSprite() {
		return line;
	}

}

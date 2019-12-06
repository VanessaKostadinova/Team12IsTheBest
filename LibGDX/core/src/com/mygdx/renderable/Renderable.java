package com.mygdx.renderable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Renderable {
	
	protected Sprite sprite;
	protected Vector2 coordinates;
	private float rotation;
	private Boolean isVisible;
	
	public Renderable() {
		coordinates = new Vector2();
		rotation = 0;
		isVisible = true;
		sprite = new Sprite();
	}
	
	public void setVisible(Boolean visible) {
		this.isVisible = visible;
	}

	public void setSprite(TextureRegion image, float x, float y) {
		sprite = new Sprite(image); 
		coordinates.add(x, y);
		sprite.setPosition(x, y);
		sprite.setRotation(rotation);
	}
	
	public void setSprite(Texture image, float x, float y) {
		sprite = new Sprite(image); 
		coordinates.add(x, y);
		sprite.setPosition(x, y);
		sprite.setRotation(rotation);
	}
	
	public void updateSprite(float dx, float dy) {
		coordinates.add(coordinates.x + dx, coordinates.y + dy);
		sprite.setPosition(sprite.getX()+dx, sprite.getY()+dy);
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
		sprite.setRotation(rotation);
	}
	
	public float getRotation() {
		return this.rotation;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Vector2 getCoords() {
		return coordinates;
	}
	
	public Vector2 getCentreCoords() {
		return new Vector2(coordinates.x + sprite.getWidth()/2, coordinates.y + sprite.getHeight()/2);
	}
	
	public void draw(SpriteBatch batch) {
		if(isVisible) {
			sprite.draw(batch);
		}
	}
	
    public Boolean pointIsWithinSprite(float x, float y) {
    	if(isVisible) {
	        if(x >= coordinates.x && x <= coordinates.x + sprite.getWidth()) {
	            if(y >= coordinates.y && y <= coordinates.y + sprite.getHeight()) {
	            	System.out.println("HIT");
	                return true;
	            }
	        }	
    	}
        return false;
    }
    
    public void dispose() {
    	sprite.getTexture().dispose();
    }
    
    public void setCoordinates(Vector2 newCoordinates) {
    	coordinates = newCoordinates;
    }

}

package com.mygdx.renderable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Used to represent anything that can be rendered in our game
 * @author Inder, Vanessa
 * @version 1.3
 */
public abstract class Renderable {

	/** The sprite */
	protected Sprite sprite;

	/** The coordinates of the renderable*/
	protected Vector2 coordinates;

	/** The rotation of the renderable */
	private float rotation;

	/** If the renderable is visible */
	private Boolean isVisible;


	public Renderable() {
		coordinates = new Vector2();
		rotation = 0;
		isVisible = true;
		sprite = new Sprite();
	}

	/**
	 * Set the sprite as visible
	 * @param visible set sprite visibility
	 */
	public void setVisible(Boolean visible) {
		this.isVisible = visible;
	}

	/**
	 * Used to intialise the sprite placement using TextureRegion.
	 * @param image TextureRegion of the sprite for the renderable
	 * @param x The x value of the sprite.
	 * @param y The y value of the sprite.
	 */
	public void setSprite(TextureRegion image, float x, float y) {
		sprite = new Sprite(image); 
		coordinates.add(x, y);
		sprite.setPosition(x, y);
		sprite.setRotation(rotation);
	}

	/**
	 * Used to intialise the sprite placement using Texture.
	 * @param image Texture of the sprite for the renderable
	 * @param x The x value of the sprite.
	 * @param y The y value of the sprite.
	 */
	public void setSprite(Texture image, float x, float y) {
		sprite = new Sprite(image); 
		coordinates.add(x, y);
		sprite.setPosition(x, y);
		sprite.setRotation(rotation);
	}

	/**
	 * Update the sprite position
	 * @param dx directional value of the renderable in x axis
	 * @param dy directional value of the renderable in y axis
	 */
	public void updateSprite(float dx, float dy) {
		coordinates.add(coordinates.x + dx, coordinates.y + dy);
		sprite.setPosition(sprite.getX()+dx, sprite.getY()+dy);
	}

	/**
	 * Set the rotation of the sprite
	 * @param rotation float value in radians
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
		sprite.setRotation(rotation);
	}

	/**
	 * Get the rotation value of the sprite
	 * @return The rotation of sprite
	 */
	public float getRotation() {
		return this.rotation;
	}

	/**
	 * Get the sprite of the player
	 * @return the sprite of the player
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * Get the coordinates of the sprite
	 * @return the coordinates of the sprite
	 */
	public Vector2 getCoords() {
		return coordinates;
	}

	/**
	 * Return the centre coordinates of the sprite
	 * @return the Vector variable of the centre of the sprite
	 */
	public Vector2 getCentreCoords() {
		return new Vector2(coordinates.x + sprite.getWidth()/2, coordinates.y + sprite.getHeight()/2);
	}

	/**
	 * Draw the sprite.
	 * @param batch the batch to draw the sprite on
	 */
	public void draw(SpriteBatch batch) {
		if(isVisible) {
			sprite.draw(batch);
		}
	}

	/**
	 * If the point of x and y is within a sprite
	 * @param x X coordinate value
	 * @param y Y coordinate value
	 * @return if point is within sprite.
	 */
    public Boolean pointIsWithinSprite(float x, float y) {
    	if(isVisible) {
	        if(x >= coordinates.x && x <= coordinates.x + sprite.getWidth()) {
				return y >= coordinates.y && y <= coordinates.y + sprite.getHeight();
	        }	
    	}
        return false;
    }

	/**
	 * Dispose of the sprite
	 */
	public void dispose() {
    	sprite.getTexture().dispose();
    }

	/**
	 * Set the coordinates of the sprite
	 * @param newCoordinates
	 */
	public void setCoordinates(Vector2 newCoordinates) {
    	coordinates = newCoordinates;
    }

}

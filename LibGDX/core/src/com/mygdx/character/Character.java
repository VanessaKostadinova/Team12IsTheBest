package com.mygdx.character;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Provides a superclass for each character so methods aren't repeated.
 * @author Team 12
 *
 */
public abstract class Character {
	
	/**
	 * The Character's X Value
	 */
	protected float x;
	/**
	 * The Character's Y Value
	 */
	protected float y;
	/**
	 * The Character's Sprite
	 */
	protected Sprite sprite;
	
	/**
	 * Returns the sprite of the character.
	 * @return Character's Sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}
	
	/**
	 * Sets the sprite of the character.
	 * @param sprite New instance of sprite.
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Sets the position of the sprite, in Cartesian axis.
	 * @param x X-Coordinate of sprite and character.
	 * @param y Y-Coordinate of sprite and character.
	 */
	public void setSpritePosition(float x, float y) {
		sprite.setPosition(x, y);
		this.x = sprite.getX();
		this.y = sprite.getY();
	}
	
	
	/**
	 * Returns the Characters Y Value
	 * @return Character Y Value
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Sets the Characters Y Value
	 * @param y New Y Value.
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Returns the Characters X Value
	 * @return Character X Value
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Sets the Characters X Value
	 * @param x New X Value.
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Update XY is used if player is moved in either direction.
	 * @param x The delta value of X in X-Axis.
	 * @param y The delta value of Y in Y-Axis.
	 */
	public void updateXY(float x, float y) {
		this.x = this.x + x;
		this.y = this.y + y;
		setSpritePosition(this.x,this.y);
	}

}

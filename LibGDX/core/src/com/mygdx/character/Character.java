package com.mygdx.character;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Character {
	
	protected float x;
	protected float y;
	protected float initialX;
	protected float initialY;
	protected Sprite sprite;
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void setSpritePosition(float x, float y) {
		sprite.setPosition(x, y);
		this.x = sprite.getX();
		this.y = sprite.getY();
		this.initialX = this.x;
		this.initialY = this.y;
	}
	
	
	//Getters and Setters for players X coordinates.
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	//Update XY is used if player is moved in either direction.
	public void updateXY(float x, float y) {
		this.x = this.x + x;
		this.y = this.y + y;
		setSpritePosition(this.x,this.y);
	}

}

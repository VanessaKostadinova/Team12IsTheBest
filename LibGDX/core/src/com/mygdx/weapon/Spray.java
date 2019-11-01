package com.mygdx.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.character.NPC;

public abstract class Spray {
	
	protected static final int FRAME_COLS = 2;
	protected static final int FRAME_ROWS = 1;
	protected Sprite sprite;
	protected Animation<TextureRegion> Animation;	
	
	private float x, y;
	private TextureRegion region;
	private Boolean isActive = false;
	
	public Spray(float x, float y, float angle) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void update(float rotation, float OriginX, float OriginY, NPC[] npcs, float height) {
        float angle = (float) Math.toRadians(rotation+11);
        System.out.println(OriginX +" "+ OriginY);

        x = (float) ((32) * Math.cos(angle)) + OriginX;
        y = (float) ((32) * Math.sin(angle)) + OriginY;
        System.out.println(angle);
		sprite.setPosition(x, y);
		System.out.println("Radius: " + angle);

		System.out.println("x: " + x);
		System.out.println("y: " + y);
		collision(npcs);
		System.out.println("");

	}
	
	public void updatePosition(float x, float y) {
		this.x = this.x+ x;
		this.y = this.y+ y;
		sprite.setPosition(this.x, this.y);
	}
	
	public void setPosition(float x, float y) {
		sprite.setPosition(x, y);
		this.x = sprite.getX();
		this.y = sprite.getY();
	}
	
	public void setVisible(Boolean value) {
		if(value) {
			sprite.setRegion(region);
			sprite.rotate90(true);
			setIsActive(true);
	    	Rectangle rect = sprite.getBoundingRectangle().setSize(26, 26);
	    	sprite.getBoundingRectangle().set(rect);

		}
		else {
			Texture texture = new Texture(Gdx.files.internal("spray/empty.png"));
			sprite.setTexture(texture);
			setIsActive(false);
	    	Rectangle rect = sprite.getBoundingRectangle().setSize(24, 24);
	    	sprite.getBoundingRectangle().set(rect);
		}

	}
	
	public void rotate(float rotation) {
		sprite.setRotation(rotation);
	}
	
	public void collision(NPC[] npcs) {
		for(NPC npc : npcs) {
			if(npc.getSprite().getBoundingRectangle().overlaps(sprite.getBoundingRectangle())) {
				if(isActive) {
					System.out.println("HIT");
				}
			}
		}

	}
	
	public float value(float value) {
		if(value <  0) {
			value = value*-1;
		}
		return value;
	}
	
	public Sprite Sprite() {
		return sprite;
	}

	public Animation<TextureRegion> getAnimation() {
		return Animation;
	}

	public void setAnimation(Animation<TextureRegion> walkAnimation) {
		this.Animation = walkAnimation;
	}

	public void setTextureRegion(TextureRegion keyFrame) {
		this.region = keyFrame;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public float getRotation() {
		return sprite.getRotation();
	}
	
}

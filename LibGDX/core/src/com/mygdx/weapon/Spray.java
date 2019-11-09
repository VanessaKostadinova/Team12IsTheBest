package com.mygdx.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.character.NPC;

/**
 * Spray holds the generic information of each spray.
 * @author Team 12
 *
 */
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
	
	
	/**
	 * Gets the X value of this spray sprite.
	 * @return Spray X Value
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Gets the Y value of this spray sprite.
	 * @return Spray Y Value
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Updates the sprite position and checks for collisions.
	 * @param rotation Used in calculations to find out correct X and Y Coordinates
	 * @param OriginX The X Point of the centre of the Player Sprite.
	 * @param OriginY The Y Point of the centre of the Player Sprite
	 * @param npcs The NPC's (Villagers) within the level.
	 * @see Math
	 * @see NPC
	 */
	public void update(float rotation, float OriginX, float OriginY, NPC[] npcs) {
        float angle = (float) Math.toRadians(rotation+11);
        System.out.println(OriginX +" "+ OriginY);

        x = (float) ((32) * Math.cos(angle)) + OriginX;
        y = (float) ((32) * Math.sin(angle)) + OriginY;
        System.out.println(angle);
		sprite.setPosition(x, y);
		System.out.println("Radius: " + angle);

		System.out.println("x: " + x);
		System.out.println("y: " + y);
		System.out.println("");

	}
	
	/**
	 * Updates the position of the spray and the sprite.
	 * @param dx Delta X, the change in the X Value.
	 * @param dy Delta Y, the change in the Y Value.
	 */ 
	public void updatePosition(float dx, float dy) {
		this.x = this.x+ dx;
		this.y = this.y+ dy;
		sprite.setPosition(this.x, this.y);
	}
	
	/**
	 * Sets the position of the spray and the sprite.
	 * @param x The new X Value.
	 * @param y The new Y Value.
	 */ 
	public void setPosition(float x, float y) {
		sprite.setPosition(x, y);
		this.x = sprite.getX();
		this.y = sprite.getY();
	}
	
	/**
	 * This changes the texture of the spray depending on whether or not
	 * the boolean value is true or false.
	 * @param value If Mouse has been clicked.
	 */
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
	
	/**
	 * Set the rotation of the spray's sprite.
	 * @param rotation Rotation of the spray sprite.
	 */
	public void rotate(float rotation) {
		sprite.setRotation(rotation);
	}
	
	/**
	 * Checks for collisions between the spray sprite and NPC Sprite.
	 * @param npcs An array of NPC characters
	 */
	public Boolean collision(NPC[] npcs, int spray) {
		for(NPC npc : npcs) {
			if(npc.getSprite().getBoundingRectangle().overlaps(sprite.getBoundingRectangle())) {
				if(isActive) {
					if(npc.getStatus().equals("Dead") && spray == 1) {
						npc.decreaseHealth(); 
					}
					else if(npc.getStatus().equals("Sick")) {
						if(spray == 0) {
							npc.increaseHealth(); 
							if(npc.getHealed() && !npc.getMoneyGiven()) {
								npc.moneyGiven(true);
								return true;
							}
						}
						else {
							npc.decreaseHealth(); 
						}
					}
				}
			}
		}
		return false;
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

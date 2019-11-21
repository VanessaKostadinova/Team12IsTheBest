package com.mygdx.renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Spray extends Renderable {
	
	private Boolean isActive;
	
	protected static final int FRAME_COLS = 2;
	protected static final int FRAME_ROWS = 1;
	protected Animation<TextureRegion> Animation;	

	private TextureRegion empty;
	private float deltaValue;
	
	
	public Spray(float deltaValue) {
		this.isActive = false;
		this.deltaValue = deltaValue;
	}
	
	public float getValue() {
		return deltaValue;
	}

	/**
	 * This changes the texture of the spray depending on whether or not
	 * the boolean value is true or false.
	 * @param value If Mouse has been clicked.
	 */
	public void setVisible(Boolean value) {
		if(value) {
			getSprite().setRegion(empty);
			getSprite().rotate90(true);
			setIsActive(true);
	    	Rectangle rect = getSprite().getBoundingRectangle().setSize(26, 26);
	    	getSprite().getBoundingRectangle().set(rect);

		}
		else {
			Texture texture = new Texture(Gdx.files.internal("spray/empty.png"));
			getSprite().setTexture(texture);
			setIsActive(false);
	    	Rectangle rect = getSprite().getBoundingRectangle().setSize(24, 24);
	    	getSprite().getBoundingRectangle().set(rect);
		}

	}
	
	public Spray() {
		this.isActive = false;
	}
	
	/**
	 * Checks for collisions between the spray sprite and NPC Sprite.
	 * @param npcs An array of NPC characters
	 */
	public Boolean collision(NPC[] npcs, int spray) {
		/*for(NPC npc : npcs) {
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
		}*/
		return false;
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

        Float x = (float) ((32) * Math.cos(angle)) + OriginX;
        Float y = (float) ((32) * Math.sin(angle)) + OriginY;
        System.out.println(angle);
		getSprite().setPosition(x, y);
		System.out.println("Radius: " + angle);

		System.out.println("x: " + x);
		System.out.println("y: " + y);
		System.out.println("");

	}
	
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}

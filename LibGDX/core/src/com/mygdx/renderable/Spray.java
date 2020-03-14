package com.mygdx.renderable;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Holds the information about the spray and animation of the spray.
 */
public class Spray extends Renderable {

	/** Checks if the spray is currently active */
	private Boolean isActive;
	/** The flame columns of the animation sheet*/
	protected static final int FRAME_COLS = 2;
	/** The flame rows of the animation sheet*/
	protected static final int FRAME_ROWS = 1;
	/** Animation of the spray for the player*/
	protected Animation<TextureRegion> Animation;
	/** The color of the light of the spray for the player.*/
	private Color colorLight;
	/** The damage/heal of the spray */
	private float deltaValue;

	/**
	 * The spray constructor
	 * @param deltaValue The damage/heal the spray is able to do
	 * @param color The color of the spray.
	 */
	public Spray(float deltaValue, Color color) {
		this.isActive = false;
		this.deltaValue = deltaValue;
		System.out.println("DELTA VALUE: " + deltaValue);
		this.colorLight = color;
		start();
	}

	/** Return the delta health value of the spray */
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
			getSprite().rotate90(true);
			getSprite().setAlpha(1);
			setIsActive(true);
	    	Rectangle rect = getSprite().getBoundingRectangle().setSize(26, 26);
	    	getSprite().getBoundingRectangle().set(rect);
	    	isActive = true;

		}
		else {
			setIsActive(false);
			getSprite().setAlpha(0);
	    	Rectangle rect = getSprite().getBoundingRectangle().setSize(24, 24);
	    	getSprite().getBoundingRectangle().set(rect);
	    	isActive = false;

		}

	}


	
	/**
	 * Checks for collisions between the spray sprite and NPC Sprite.
	 * @param list An array of NPC characters
	 */
	public Boolean collision(List<NPC> list, float f, Player p) {
		for(NPC npc : list) {
			System.out.println("IS SPRAY NULL? :" + sprite.getBoundingRectangle() != null);
			if(npc.getRectangle().overlaps(sprite.getBoundingRectangle())) {
				if(isActive) {
					if(npc.getStatus().equals("Dead") && f == 1) {
						npc.setFoodGiven(true);
						npc.changeHealth(deltaValue); 
						npc.setSanity(true);
					}
					else if(npc.getStatus().equals("Sick")) {
						if(f == 0) {
							npc.changeHealth(deltaValue); 
							if(npc.getHealed() && !npc.foodGiven()) {
								p.updateFood(50);
								npc.setFoodGiven(true);
								return true;
							}
						}
						else {
							if(f == 1) {
								npc.setFoodGiven(true);
								npc.changeHealth(deltaValue);
								npc.setSanity(true);
							}
						}
					}
					else {
						if(f == 1) {
							npc.setFoodGiven(true);
							npc.changeHealth(deltaValue);
							if(npc.getStatus().equals("Burnt")) {
								npc.setSanity(false);
							}
						}
					}
				}
				else {
					npc.setSanity(false);
				}
			}
			else {
				npc.setSanity(false);
			}
		}
		return false;
	}


	/**
	 * Sets the animation of the spray. If the deltaValue is less than 0 then it can only be
	 * a flamethrower type spray so the spray type is set to that if they deltaValue is greater
	 * than 0 then it's a cure type spray and will be assigned a normally spray type.
	 */
	public void start() {
		if(deltaValue < 0) {
			Texture walksheet = new Texture(Gdx.files.internal("spray/fire.png"));
	
			TextureRegion[][] tmp = TextureRegion.split(walksheet, 
					walksheet.getWidth() / FRAME_COLS,
					walksheet.getHeight() / FRAME_ROWS);
			
			TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
			int index = 0;
			for (int i = 0; i < FRAME_ROWS; i++) {
				for (int j = 0; j < FRAME_COLS; j++) {
					walkFrames[index++] = tmp[i][j];
				}
			}
			
			Animation = new Animation<TextureRegion>(0.1f, walkFrames);
			super.setSprite(walkFrames[0], 0, 0);

	    	super.getSprite().setAlpha(0);
		}
		else {
			Texture walksheet = new Texture(Gdx.files.internal("spray/spray.png"));
	
			TextureRegion[][] tmp = TextureRegion.split(walksheet, 
					walksheet.getWidth() / FRAME_COLS,
					walksheet.getHeight() / FRAME_ROWS);
			
			TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
			int index = 0;
			for (int i = 0; i < FRAME_ROWS; i++) {
				for (int j = 0; j < FRAME_COLS; j++) {
					walkFrames[index++] = tmp[i][j];
				}
			}
			
			Animation = new Animation<TextureRegion>(0.1f, walkFrames);
			super.setSprite(walkFrames[0], 0, 0);
	    	super.getSprite().setAlpha(0);
		}
	}
	
	/**
	 * Updates the sprite position.
	 * @param rotation Used in calculations to find out correct X and Y Coordinates
	 * @param OriginX The X Point of the centre of the Player Sprite.
	 * @param OriginY The Y Point of the centre of the Player Sprite
	 * @param npcs The NPC's (Villagers) within the level.
	 * @see Math
	 * @see NPC
	 */
	public void update(float rotation, float OriginX, float OriginY, List<NPC> npcs) {
        float angle = (float) Math.toRadians(rotation+11);
        Float x = (float) ((32) * Math.cos(angle)) + OriginX;
        Float y = (float) ((32) * Math.sin(angle)) + OriginY;
		getSprite().setPosition(x, y);
	}

	/**
	 * Get if spray is active
	 * @return Is spray active
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * Sets the spray as active when used
	 * @param isActive spray is active
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Add to the strength of the spray
	 * @param deltaSpray DeltaValue of the spray
	 */
	public void addStrength(float deltaSpray) {
		this.deltaValue = this.deltaValue + deltaSpray;
	}

	/**
	 * The animation of each of the sprays
	 * @return current animation of the spray
	 */
	public Animation<TextureRegion> getAnimation() {
		return Animation;
	}

	/**
	 * Return the color of the spray
	 * @return The color of the spray.
	 */
	public Color getColor() {
		return this.colorLight;
	}
}
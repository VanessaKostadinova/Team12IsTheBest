package com.mygdx.renderable;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Spray extends Renderable {
	
	private Boolean isActive;
	
	protected static final int FRAME_COLS = 2;
	protected static final int FRAME_ROWS = 1;
	protected Animation<TextureRegion> Animation;	
	//private ConeLight light;
	private Color colorLight;
	private float deltaValue;
	
	public Spray(float deltaValue, Color color) {
		this.isActive = false;
		this.deltaValue = deltaValue;
		this.colorLight = color;
		start();
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
	
	public Spray() {
		this.isActive = false;
	}
	
	/**
	 * Checks for collisions between the spray sprite and NPC Sprite.
	 * @param list An array of NPC characters
	 */
	public Boolean collision(List<NPC> list, float f, Player p) {
		for(NPC npc : list) {
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
	
	public Player readPlayer() {
		FileHandle handle = Gdx.files.local("data/player.txt");
		String[] values= handle.readString().split(",");
		Player p = new Player(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6]), Float.parseFloat(values[7]));
		return p;
	}
	
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
	 * Updates the sprite position and checks for collisions.
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
	
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public void addStrength(float deltaSpray) {
		this.deltaValue = this.deltaValue + deltaSpray;
	}

	public Animation<TextureRegion> getAnimation() {
		return Animation;
	}
	
	public Color getColor() {
		return this.colorLight;
	}
}
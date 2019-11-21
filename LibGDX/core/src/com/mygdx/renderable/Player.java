package com.mygdx.renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Renderable implements Living {
	
	private Animation<TextureRegion> walkAnimation;
	private TextureRegion[] walkFrames;
	
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 2;
	@SuppressWarnings("unused")
	private static final float speed = 2f*60f;

	private Spray[] sprays;
	private int sprayIndex;
	
	private float health;
	private int amountOfFood;
	
	private int energy;
	
	
	
	public Player(float x, float y) {
		super();
		loadWalkingAnimation();
		amountOfFood = 0;
		super.setSprite(walkFrames[2], x, y);
		health = 100;
		energy = 3;
	}
	
	public void switchSpray() {
		if(sprayIndex == 0) {
			sprayIndex = 1;
		} else {
			sprayIndex = 0;
		}
	}
	
	public Spray getSpray() {
		return sprays[sprayIndex];
	}
		
	public void loadWalkingAnimation() {
		Texture walksheet = new Texture(Gdx.files.internal("badlogic.jpg"));

		TextureRegion[][] tmp = TextureRegion.split(walksheet,
				walksheet.getWidth() / FRAME_COLS,
				walksheet.getHeight() / FRAME_ROWS);

		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		walkAnimation = new Animation<TextureRegion>(0.3f, walkFrames);
	}
	
	public Animation<TextureRegion> getAnimation() {
		return walkAnimation;
	}
	
	public int getFood() {
		return amountOfFood;
	}
	
	public void setFood(int newFoodValue) {
		amountOfFood =+ newFoodValue;
	}
	
	public int getEnergy() {
		return energy;
	}

	@Override
	public void changeHealth(float deltaHealth) {
		health -= deltaHealth;
	}

	@Override
	public float getHealth() {
		return health;
	}

	@Override
	public boolean isDead() {
		if(health == 0) {
			return true;
		}
		return false;
	}


}

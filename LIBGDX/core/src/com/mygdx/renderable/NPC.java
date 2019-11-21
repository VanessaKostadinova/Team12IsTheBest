package com.mygdx.renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NPC extends Renderable implements Living {
	
	private Animation<TextureRegion> walkAnimation;
	private TextureRegion[] walkFrames;
	private String status;
	
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 2;
	
	private float health;
	@SuppressWarnings("unused")
	private float susceptibility;

	@SuppressWarnings("unused")
	private boolean foodGiven;
	private boolean isHealed;
	
	public NPC(float health) {
		this.health = health;
		this.isHealed = false;
		this.update();
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
	
	/**
	 * Update texture of the villager as it's state changes.
	 */
	public void updateTexture() {
		Texture tempSprite = new Texture(Gdx.files.internal(status + "_NPC.gif"));
		getSprite().setTexture(tempSprite);
	}
	
	public void update() {
		if(health > 100) {
			health = 100;
		}
		
		if(health == 100) {
			status = "Alive";
			isHealed = true;
			updateTexture();
		}
		
		if(health == 0) {
			status = "Dead";
			updateTexture();
		}
		
		if(health == -100) {
			status = "Burnt";
			updateTexture();
		}
		
		if(health < -100) {
			health = -100;
		}
	}

	@Override
	public void changeHealth(float deltaHealth) {
		health -= deltaHealth;
	}
	
	public Boolean getHealed() {
		return this.isHealed;
	}
	
	public String getStatus() {
		return status;
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

package com.mygdx.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Character {
	
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 2;
	private Animation<TextureRegion> walkAnimation;
	private TextureRegion[] walkFrames;

	// General constructor to create player.
	public Player() {
        loadWalkingAnimation();
        sprite = new Sprite(walkFrames[1]);
	}
	
	public void loadWalkingAnimation() {
		Texture walksheet = new Texture(Gdx.files.internal("player/The_Doctor_Walk.png"));
		
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
	
	public void playerReset() {
		sprite.setRegion(walkFrames[1]);
	}
	
	
}

package com.mygdx.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.weapon.Cure;
import com.mygdx.weapon.Fire;
import com.mygdx.weapon.Spray;

public class Player extends Character {
	
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 2;
	private Animation<TextureRegion> walkAnimation;
	private TextureRegion[] walkFrames;
	private Spray[] spray;
	private int currentSpray;

	private int coin;

	// General constructor to create player.
	public Player() {
        loadWalkingAnimation();
        sprite = new Sprite(walkFrames[1]);
        spray = new Spray[2];
		spray[0] = new Cure(x-sprite.getWidth()/2, y+sprite.getHeight(), 90);
		spray[1] = new Fire(x-sprite.getWidth()/2, y+sprite.getHeight(), 90);
		currentSpray = 0;
		coin = 0;
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
	
	public void switchSolution() {
		if(currentSpray == 0) {
			currentSpray = 1;
			spray[1].setPosition(spray[0].getX(), spray[0].getY());
			spray[1].rotate(spray[0].getRotation());
		}
		else {
			currentSpray = 0;
			spray[0].setPosition(spray[1].getX(), spray[1].getY());
			spray[0].rotate(spray[1].getRotation());
		}
	}
	
	
	public int getCoins() {
		return coin;
	}
	
	public void setCoins(int coin) {
		this.coin = coin;
	}
	
	public Spray getSpray() {
		return spray[currentSpray];
	}

	
	public Animation<TextureRegion> getAnimation() {
		return walkAnimation;
	}
	
	public void playerStanding() {
		sprite.setRegion(walkFrames[1]);
	}
	
	public int getSprayType() {
		return currentSpray;
	}
	
	
}

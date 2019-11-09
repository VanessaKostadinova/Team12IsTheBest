package com.mygdx.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.weapon.Cure;
import com.mygdx.weapon.Fire;
import com.mygdx.weapon.Spray;

/**
 * The Class Player, contains all the methods exclusive to player and not used in NPC.
 * 
 * @author Team 12
 */
public class Player extends Character {
	
	/** Constants of Columns Sprite-sheet */
	private static final int FRAME_COLS = 2;
	
	/** Constants of Rows Sprite-sheet */
	private static final int FRAME_ROWS = 2;
	
	/** The walk animation for the player. */
	private Animation<TextureRegion> walkAnimation;
	
	/** The individual walk frames. */
	private TextureRegion[] walkFrames;
	
	/** The sprays that the player has. */
	private Spray[] spray;
	
	/** The current spray which the player is using. */
	private int currentSpray;

	/** The amount of money the player has. */
	private int coin;

	/**
	 * Instantiates a new player.
	 */
	public Player() {
        loadWalkingAnimation();
        sprite = new Sprite(walkFrames[1]);
        spray = new Spray[2];
		spray[0] = new Cure(x-sprite.getWidth()/2, y+sprite.getHeight(), 90);
		spray[1] = new Fire(x-sprite.getWidth()/2, y+sprite.getHeight(), 90);
		currentSpray = 0;
		coin = 0;
	}
	
	/**
	 * Loads the player's walking animation.
	 */
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
	
	/**
	 * Switch solution within the spray.
	 */
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
	
	
	/**
	 * Gets the money the player has.
	 *
	 * @return Player's total money.
	 */
	public int getCoins() {
		return coin;
	}
	
	/**
	 * Sets the total amount of coins the player has.
	 *
	 * @param coin The new amount of coins
	 */
	public void setCoins(int coin) {
		this.coin = coin;
	}
	
	/**
	 * Gets the spray which the player is using.
	 *
	 * @return the spray
	 */
	public Spray getSpray() {
		return spray[currentSpray];
	}

	
	/**
	 * Gets the animation of the player
	 *
	 * @return the player's animation
	 */
	public Animation<TextureRegion> getAnimation() {
		return walkAnimation;
	}
	
	/**
	 * Resets the player, so the player stands when there is no input.
	 */
	public void playerStanding() {
		sprite.setRegion(walkFrames[1]);
	}
	
	/**
	 * Gets the spray type.
	 *
	 * @return the index for the spray.
	 */
	public int getSprayType() {
		return currentSpray;
	}
	
	
}

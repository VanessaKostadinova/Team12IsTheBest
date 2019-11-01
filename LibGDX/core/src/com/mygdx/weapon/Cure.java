package com.mygdx.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A type of spray, which burns the villagers.
 * @author Team 12
 */
public class Cure extends Spray {
	
	/**
	 * Creates an instance of the cure spray.
	 * @param x Float value of the spray's position in the X-Axis.
	 * @param y Float value of the spray's position in the Y-Axis.
	 * @param angle Ensure's that the sprite looks in the correct direction.
	 * @see Spray
	 */
	public Cure(float x, float y, float angle) {
		super(x, y, angle);
        Texture texture = new Texture(Gdx.files.internal("spray/empty.png"));
        sprite = new Sprite(texture);
    	sprite.setPosition(x, y);
    	sprite.setRotation(angle);
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
	}

}

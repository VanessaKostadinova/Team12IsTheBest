package com.mygdx.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Fire extends Spray {

	public Fire(float x, float y, float angle) {
		super(x, y, angle);
        Texture texture = new Texture(Gdx.files.internal("spray/empty.png"));
        sprite = new Sprite(texture);
    	sprite.setPosition(x, y);
    	sprite.setRotation(angle);
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
		
		Animation = new Animation<TextureRegion>(0.1f, walkFrames);	}

}

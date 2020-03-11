package com.mygdx.shop;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.extras.PermanetPlayer;
import com.mygdx.renderable.Renderable;

/**
 * Holds the sprite of the church for map-screen.
 */
public class Church extends Renderable {

	/**
	 * The constructor of the church
	 * @param textureOfHouse The texture of the church.
	 * @param x The x value of the church.
	 * @param y The y value of the church.
	 */
	public Church(Texture textureOfHouse, float x, float y) {
		super.setSprite(textureOfHouse, x, y);
	}

}
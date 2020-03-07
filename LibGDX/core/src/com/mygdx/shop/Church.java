package com.mygdx.shop;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.extras.PermanetPlayer;
import com.mygdx.renderable.Renderable;

public class Church extends Renderable {

	private PermanetPlayer permanetPlayer;
	
	public Church(Texture textureOfHouse, float x, float y) {
		super.setSprite(textureOfHouse, x, y);
		permanetPlayer = PermanetPlayer.getPermanentPlayerInstance();
	}

}
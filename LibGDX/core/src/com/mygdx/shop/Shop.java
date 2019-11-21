package com.mygdx.shop;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.renderable.Renderable;

public class Shop extends Renderable {
	
	Item[] items;
	
	public Shop(Texture textureOfHouse, float x, float y) {
		super.setSprite(textureOfHouse, x, y);
	}

}

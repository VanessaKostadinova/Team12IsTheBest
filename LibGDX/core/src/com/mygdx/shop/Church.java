package com.mygdx.shop;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.renderable.Renderable;

public class Church extends Renderable {
	
	Item[] items;
	
	public Church(Texture textureOfHouse, float x, float y) {
		super.setSprite(textureOfHouse, x, y);
		
		items = new Item[6];
		 
		items[0] = new Item("MOVEMENT SPEED","INCREASE THE PLAYERS SPEED", 0.1f, 50f);
		items[1] = new Item("MASK ABILITY", "INCREASE THE MASK DURABILITY", 60f, 10f);
		items[2] = new Item("FLAME STRENGTH", "INCREASE THE FLAME STRENGTH", 0.05f, 100f);
		items[3] = new Item("CURE STRENGTH", "INCREASE THE CURE STRENGTH", 0.05f, 100f);
		//upgrades[4] = new Upgrade("FLAME AMOUNT", "INCREASE THE FLAME AMOUNT", 0.3f, 100f);
		//upgrades[5] = new Upgrade("CURE AMOUNT", "INCREASE THE CURE AMOUNT", 0.3f, 100f);
	}
	
	public Item[] getItems() {
		return items;
	}
	
	public Item getUpgrade(int index) {
		return items[index];
	}
}

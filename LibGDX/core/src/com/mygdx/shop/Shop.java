package com.mygdx.shop;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.renderable.Renderable;

public class Shop extends Renderable {
	
	Upgrade[] upgrades;
	
	public Shop(Texture textureOfHouse, float x, float y) {
		super.setSprite(textureOfHouse, x, y);
		
		upgrades = new Upgrade[6];
		 
		upgrades[0] = new Upgrade("INCREASE THE PLAYERS MOVEMENT SPEED", 0.3f, 100f);
		upgrades[1] = new Upgrade("INCREASE THE MASK DURABILITY", 0.3f, 100f);
		upgrades[2] = new Upgrade("INCREASE THE FLAME STRENGTH", 0.3f, 100f);
		upgrades[3] = new Upgrade("INCREASE THE CURE STRENGTH", 0.3f, 100f);
		upgrades[4] = new Upgrade("INCREASE THE FLAME AMOUNT", 0.3f, 100f);
		upgrades[5] = new Upgrade("INCREASE THE CURE AMOUNT", 0.3f, 100f);


	}
	

	
	public void increaseMovementSpeed() {
		
	}

	public void increaseMaskAbility() {
		
	}
	
	public void increaseFlameStrength() {
		
	}
	
	public void increaseCureStrength() {
		
	}
	
	public void increaseFlameAmount() {
		
	}
	
	public void increaseCureAmount() {
		
	}
	
	public void readPlayerFile() {
		
	}
}

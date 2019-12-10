package com.mygdx.shop;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.renderable.Renderable;

public class Shop extends Renderable {
	
	Upgrade[] upgrades;
	
	public Shop(Texture textureOfHouse, float x, float y) {
		super.setSprite(textureOfHouse, x, y);
		
		upgrades = new Upgrade[6];
		 
		upgrades[0] = new Upgrade("MOVEMENT SPEED","INCREASE THE PLAYERS SPEED", 0.1f, 50f);
		upgrades[1] = new Upgrade("MASK ABILITY", "INCREASE THE MASK DURABILITY", 60f, 10f);
		upgrades[2] = new Upgrade("FLAME STRENGTH", "INCREASE THE FLAME STRENGTH", 0.05f, 100f);
		upgrades[3] = new Upgrade("CURE STRENGTH", "INCREASE THE CURE STRENGTH", 0.05f, 100f);
		//upgrades[4] = new Upgrade("FLAME AMOUNT", "INCREASE THE FLAME AMOUNT", 0.3f, 100f);
		//upgrades[5] = new Upgrade("CURE AMOUNT", "INCREASE THE CURE AMOUNT", 0.3f, 100f);


	}
	
	public Upgrade[] getUpgrades() {
		return upgrades;
	}
	
	public Upgrade getUpgrade(int index) {
		return upgrades[index];
	}
	

}

package com.mygdx.game.houses;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.mygdx.game.actors.Villager;

public class House {
	
	private float diseaseLevel;
	private float x;
	private float y;
	private ImageTextButton houseImage;
	private List<Villager> villagers;
	
	public House (float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	private float getX() {
		return x;
	}
	
	private float getY() {
		return y;
	}
	
	private float diseaseLevel() {
		return diseaseLevel;
	}
	
	private ImageTextButton getHouse() {
		return houseImage;
	}
	
	private List<Villager> getVillager() {
		return villagers;
	}

}

package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Level {
	
	private Tile[][] level;
	private Sprite wall;
	private Sprite floor;
	private Sprite door;
	
	public Level(int width, int height) {
		level = new Tile[width][height];
	}
	
	public Tile[][] getLevel() {
		return level;
	}

}

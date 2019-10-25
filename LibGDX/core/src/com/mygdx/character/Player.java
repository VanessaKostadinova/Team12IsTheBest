package com.mygdx.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Character {
	
	// General constructor to create player.
	public Player() {
        Texture texture = new Texture(Gdx.files.internal("sprite.png"));
        sprite = new Sprite(texture);
	}
	
	
}

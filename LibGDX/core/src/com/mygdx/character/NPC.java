package com.mygdx.character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class NPC extends Character {
	
	private String status;
	private int health;
	private float susceptibility;
	private int gold;

	public NPC(float coordX, float coordY) {
		Texture tempSprite = new Texture(Gdx.files.internal(status + "_NPC.gif"));
		sprite = new Sprite(tempSprite);
		status = "Alive";
		this.x = coordX;
		this.y = coordY;
	}
	
	public void makeSick() {
		status = "Sick";
		sprite.setTexture(new Texture(Gdx.files.internal("Sick_NPC")));
	}
	
	public void decreaseHealth() {
		health--;
		if(health == 0) {
			status = "Dead";
			sprite.setTexture(new Texture(Gdx.files.internal("Dead_NPC")));
		}
	}
	
	public void cure() {
		if(status == "Dead") {
			throw new IllegalStateException("They're dead!");
		}
		status = "Alive";
		susceptibility += 0.1;
	}
	
	public int getHealth() {
		return health;
	}
	
	public float getSucceptibility() {
		return susceptibility;
	}
	
	public String getStatus() {
		return status;
	}
}

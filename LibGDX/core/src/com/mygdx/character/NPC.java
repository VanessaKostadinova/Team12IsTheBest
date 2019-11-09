package com.mygdx.character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class NPC extends Character {
	
	private String status;
	private int health;
	private float susceptibility;
	private Boolean isHealed;
	private Boolean moneyGiven;

	public NPC(String status, float coordX, float coordY, int health) {
		Texture tempSprite = new Texture(Gdx.files.internal(status + "_NPC.gif"));
		sprite = new Sprite(tempSprite);
		this.x = coordX;
		this.y = coordY;
		setSpritePosition(x, y);
		this.health = health;
		this.status = status;
		
		this.isHealed = false;
		this.moneyGiven = false;
		
	}
	
	public void makeSick() {
		status = "Sick";
		sprite.setTexture(new Texture(Gdx.files.internal("Sick_NPC")));
	}
	
	public void moneyGiven(Boolean given) {
		this.moneyGiven = given;
	}
	
	public void decreaseHealth() {
		health--;
		if(health == 0) {
			status = "Dead";
			updateTexture();
		}
		if(health == -100) {
			status = "Burnt";
			updateTexture();
		}
		
		if(health < -100) {
			health = -100;
		}
	}
	
	public void increaseHealth() {
		health++;
		if(health == 100) {
			status = "Alive";
			isHealed = true;
			updateTexture();
		}
		if(health > 100) {
			health = 100;
		}
	}
	
	public void updateTexture() {
		Texture tempSprite = new Texture(Gdx.files.internal(status + "_NPC.gif"));
		sprite.setTexture(tempSprite);
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
	
	public Boolean getHealed() {
		return this.isHealed;
	}
	
	public Boolean getMoneyGiven() {
		return this.moneyGiven;
	}
}

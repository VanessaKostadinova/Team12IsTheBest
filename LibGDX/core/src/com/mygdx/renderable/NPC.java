package com.mygdx.renderable;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class NPC extends Renderable implements Living {
	
	private Animation<TextureRegion> walkAnimation;
	private TextureRegion[] walkFrames;
	private String status;
	
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 2;
	
	private float health;
	private boolean foodGiven;
	private boolean isHealed;

	private int daysInStatus;

	private boolean sanityAdd;
	
	private Rectangle rectangle;
	
	private Sprite healthBar;

	private Boolean isAggressive = false;
	
	public NPC(float health) {
		super();
		this.health = health;
		this.isHealed = false;
		this.foodGiven = false;
		healthBar = new Sprite(new Texture(Gdx.files.internal("house/UI/HEALTH.png")));
		this.sanityAdd = false;
		this.update();
	}
	
	public NPC(float health, float x, float y) {
		super();
		this.health = health;
		this.isHealed = false;
		this.foodGiven = false;
		healthBar = new Sprite(new Texture(Gdx.files.internal("house/UI/HEALTH.png")));
		this.sanityAdd = false;
		super.updateSprite(x, y);
		this.update();
	}
	
	public void loadWalkingAnimation() {
		Texture walksheet = new Texture(Gdx.files.internal("badlogic.jpg"));

		TextureRegion[][] tmp = TextureRegion.split(walksheet,
				walksheet.getWidth() / FRAME_COLS,
				walksheet.getHeight() / FRAME_ROWS);

		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		walkAnimation = new Animation<TextureRegion>(0.3f, walkFrames);
	}
	
	public Animation<TextureRegion> getAnimation() {
		return walkAnimation;
	}
	
	/**
	 * Update texture of the villager as it's state changes.
	 */
	public void updateTexture() {
		Texture tempSprite = new Texture(Gdx.files.internal("NPC/" + status + "_NPC.gif"));
		super.setSprite(tempSprite,super.getSprite().getX(),super.getSprite().getY());
		changeStatus();
	}

	public void update() {
		if(health > 100) {
			health = 100;
		}
		
		if(health < -100) {
			health = -100;
		}
		
		if(health == -100) {
			status = "Burnt";
			updateTexture();
			healthBar.setAlpha(0);
		}
		
		else if(health <= 0) {
			status = "Dead";
			updateTexture();
			healthBar.setTexture(new Texture(Gdx.files.internal("house/UI/HEALTH.png")));
		}
		
		else if(health < 100 && health > 0) {
			status = "Sick";
			updateTexture();
			healthBar.setTexture(new Texture(Gdx.files.internal("house/UI/HEALTHSICK.png")));
		}
		
		else if(health == 100) {
			status = "Alive";
			isHealed = true;
			updateTexture();
			healthBar.setTexture(new Texture(Gdx.files.internal("house/UI/HEALTHFULL.png")));
		}
	}

	private void changeStatus(){
		daysInStatus = 0;
	}

	//TODO put this in
	private void incrementStatus(){
		daysInStatus += 1;
	}

	@Override
	public void updateSprite(float dx, float dy) {
		coordinates.add(coordinates.x + dx, coordinates.y + dy);
		sprite.setPosition(sprite.getX()+dx, sprite.getY()+dy);
		healthBar.setPosition(sprite.getX()+15, sprite.getY()+sprite.getHeight()+5);
		rectangle = new Rectangle(sprite.getX()+15, sprite.getY()+5, sprite.getWidth()-30, sprite.getHeight()-10);
	}

	@Override
	public void changeHealth(float deltaHealth) {
		health += deltaHealth;
		update();
	}
	
	public Boolean getHealed() {
		return this.isHealed;
	}
	
	public String getStatus() {
		return status;
	}

	@Override
	public float getHealth() {
		return health;
	}

	@Override
	public boolean isDead() {
		if(health == 0) {
			return true;
		}
		return false;
	}
	
	public boolean foodGiven() {
		return foodGiven;
	}
	
	public void setFoodGiven(boolean given) {
		this.foodGiven = given;
	}

	public boolean isSick(){
		if(status == "Sick"){
			return true;
		}
		else {
			return false;
		}
	}

	public boolean getAggressive() {
		return false;
	}

	public Sprite getBar() {
		return healthBar;
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}
	
	public void setSanity(Boolean sanity) {
		this.sanityAdd = sanity;
	}
	
	public Boolean getSanity() {
		return this.sanityAdd;
	}
	
	public Boolean isBurned() {
		if(sanityAdd) {
			return true;
		}
		else {
			return false;
		}
	}

	public void infect() {
		if(status.equals("Alive")){
			status = "Sick";
		}
	}
	
	public void setHealth(float newHealth) {
		this.health = newHealth;
	}
}
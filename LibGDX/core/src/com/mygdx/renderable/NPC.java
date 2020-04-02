package com.mygdx.renderable;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.assets.AssetHandler;

/**
 * The NPCS within each of the houes.
 * @author Inder, Vanessa
 */
public class NPC extends Renderable implements Living {

	/** The potential states of the npc */
	private String status;
	/** The health of the npc */
	private float health;
	/** If the NPC has already given food to the player */
	private boolean foodGiven;
	/** If the NPC is healed*/
	private boolean isHealed;
	/** If the NPC has added to insanity of player */
	private boolean sanityAdd;
	/** The collision rectangle for the player */
	private Rectangle rectangle;
	/** The health bar of the NPC */
	private Sprite healthBar;
	/** Is the NPC aggressive */
	private Boolean isAggressive;
	/** The villager type */
	private int villagerType;

	private Texture healthbar = new Texture(Gdx.files.internal("house/UI/HEALTH.png"));
	private Texture healthbarsick = new Texture(Gdx.files.internal("house/UI/HEALTHSICK.png"));
	private Texture healthbarfull = new Texture(Gdx.files.internal("house/UI/HEALTHFULL.png"));
	
	public NPC(float health) {
		super();
		this.health = health;
		this.isHealed = false;
		this.foodGiven = false;
		healthBar = new Sprite(new Texture(Gdx.files.internal("house/UI/HEALTH.png")));
		this.sanityAdd = false;
		this.villagerType = new Random().nextInt((3 - 1) + 1) + 1;
		this.update();
	}
	
	public NPC(float health, float x, float y, int villagerType) {
		super();
		this.health = health;
		this.isHealed = false;
		this.foodGiven = false;
		healthBar = new Sprite(new Texture(Gdx.files.internal("house/UI/HEALTH.png")));
		this.sanityAdd = false;
		this.villagerType = villagerType;
		this.update();
		this.updateSprite(x, y);

	}

	
	/**
	 * Update texture of the villager as it's state changes.
	 */
	public void updateTexture() {
		Texture tempSprite = ((AssetHandler.MANAGER.get("NPC/" + status + villagerType + ".gif", Texture.class)));
		super.setSprite(tempSprite,super.getSprite().getX(),super.getSprite().getY());
	}

	/** Updating the state of the NPC and health bar
	 *  This includes state changes as well as health changes constraints */
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
			healthBar.setTexture(healthbar);
		}

		else if(health < 100 && health > 0) {
			status = "Sick";
			updateTexture();
			healthBar.setTexture(healthbarsick);
		}

		else if(health == 100) {
			status = "Alive";
			isHealed = true;
			updateTexture();
			healthBar.setTexture(healthbarfull);
		}
	}

	public void dispose() {
		healthbar.dispose();
		getSprite().getTexture().dispose();
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

	/**
	 * Get if the NPC is healed
	 * @return if the NPC is healed
	 */
	public Boolean getHealed() {
		return this.isHealed;
	}

	/**
	 * Get if the NPC status
	 * @return the NPC status
	 */
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

	/**
	 * Return the food given
	 * @return foodGiven
	 */
	public boolean foodGiven() {
		return foodGiven;
	}

	/**
	 * Set if the food was given
	 * @param given if food given
	 */
	public void setFoodGiven(boolean given) {
		this.foodGiven = given;
	}

	/**
	 * Check if the NPC is sick
	 * @return if villager then true else false
	 */
	public boolean isSick(){
		if(status == "Sick"){
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get if the NPC is aggressive
	 * @return if the NPC is aggressive
	 */
	public boolean getAggressive() {
		return false;
	}

	/**
	 * Get the health bar of the NPC
	 * @return sprite healthBar
	 */
	public Sprite getBar() {
		return healthBar;
	}

	/**
	 * Get the NPC rectangle
	 * @return rectangle
	 */
	public Rectangle getRectangle() {
		return rectangle;
	}

	/**
	 * Set the sanity of the NPC
	 * @param sanity if sanity has been added by NPC
	 */
	public void setSanity(Boolean sanity) {
		this.sanityAdd = sanity;
	}
	
	public Boolean isBurned() {
		if(sanityAdd) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * When infected update the state of the villager
	 * Does not reduce there health
	 */
	public void infect() {
		if(status.equals("Alive")){
			status = "Sick";
		}
	}

	/**
	 * Set the health of the villager
	 * @param newHealth new health value
	 */
	public void setHealth(float newHealth) {
		this.health = newHealth;
	}

	/**
	 * Get the type of villager this is. Each type of villager has
	 * a different type of sprite set.
	 * @return a int showing villagerType
	 */
	public int getVillagerType() {return villagerType;}

}
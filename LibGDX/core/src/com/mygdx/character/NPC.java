package com.mygdx.character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * The Class NPC, which will be most of the villagers.
 */
public class NPC extends Character {

	/** The status of each villager and their illness. */
	private String status;

	/** The health of each villager. */
	private int health;

	/** The likelihood of the villager to get diseased again. */
	private float susceptibility;

	/** If the character is healed. */
	private Boolean isHealed;

	/** The villager has given money. */
	private Boolean moneyGiven;

	/**
	 * Instantiates a new npc.
	 *
	 * @param status the status of the villager
	 * @param coordX the X coordinate of the villager
	 * @param coordY the Y coordinate of the villager
	 * @param health the health of the villager
	 */
	public NPC(String status, float coordX, float coordY, int health) {
		Texture tempSprite = new Texture(Gdx.files.internal(status + "_NPC.gif"));
		sprite = new Sprite(tempSprite);
		status = "Alive";
		this.x = coordX;
		this.y = coordY;
		setSpritePosition(x, y);
		this.health = health;
		this.status = status;

		this.isHealed = false;
		this.moneyGiven = false;

	}

	/**
	 * Makes the villager sick.
	 */
	public void makeSick() {
		status = "Sick";
		sprite.setTexture(new Texture(Gdx.files.internal("Sick_NPC")));
	}

	/**
	 * Sets that the villager has give money.
	 *
	 * @param given The value that the villager has given money.
	 */
	public void moneyGiven(Boolean given) {
		this.moneyGiven = given;
	}

	/**
	 * Decrease health of the villager. Also affects the villager's state.
	 */
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

	/**
	 * Increase health of the villager. Also affects the villager's state.
	 */
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

	/**
	 * Update texture of the villager as it's state changes.
	 */
	public void updateTexture() {
		Texture tempSprite = new Texture(Gdx.files.internal(status + "_NPC.gif"));
		sprite.setTexture(tempSprite);
	}

	/**
	 * Gets the health of the villager.
	 *
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Gets the susceptibility of the villager.
	 *
	 * @return the susceptibility
	 */
	public float getSucceptibility() {
		return susceptibility;
	}

	/**
	 * Gets the villager status.
	 *
	 * @return the villager status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Gets if healed.
	 *
	 * @return the healed
	 */
	public Boolean getHealed() {
		return this.isHealed;
	}

	/**
	 * Gets if money given.
	 *
	 * @return if money given has been given
	 */
	public Boolean getMoneyGiven() {
		return this.moneyGiven;
	}
}

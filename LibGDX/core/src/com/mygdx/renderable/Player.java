package com.mygdx.renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.extras.PermanetPlayer;

/**
 * The player class which hold the player information.
 */
public class Player extends Renderable implements Living {

	/** The animation of the player */
	private Animation<TextureRegion> walkAnimation;
	/** The array of the walk frames for the player */
	private TextureRegion[] walkFrames;
	/** Columns in the sprite sheet for player*/
	private static final int FRAME_COLS = 2;
	/** Rows in the sprite sheet for player*/
	private static final int FRAME_ROWS = 2;
	/** Speed of the player */
	private float speed;
	/** Constant sanity factor increase for the player*/
	private static final float sanityFactor = 3.0f;
	/** The mask reduction rate */
	private float maskDurationSeconds;
	/** The sprays which the player has*/
	private Spray[] sprays;
	/** Current spray the player is using*/
	private int sprayIndex;
	/** The current rate of mask durability */
	private float currentMaskDuration;
	/** Number of masks the player has */
	private int numberOfMasks;
	/** Amount of food the player has*/
	private float amountOfFood;
	/** The box2D body of the player*/
	private Body body;

	/** The instance of player */
	private static Player player = null;

	/** Constructor of player
	 * @param masks Number of masks
	 * @param amountOfHealingFluid Amount of fluid on player
	 * @param amountOfBurningFluid Amount of burning fluid on player
	 */
	private Player(int masks, float amountOfHealingFluid, float amountOfBurningFluid) {
		super();
		loadWalkingAnimation();
		setSprite(walkFrames[0], 0, 0);
		this.amountOfFood = 1000;
		this.numberOfMasks = masks;
		try {
			PermanetPlayer.getPermanentPlayerInstance();
		} catch (IllegalStateException e ) {
			PermanetPlayer.createPermanentPlayerInstance(masks, amountOfHealingFluid, amountOfBurningFluid);
		}
		this.speed = 60f + (PermanetPlayer.getPermanentPlayerInstance().getItem(0).getLevel()*PermanetPlayer.getPermanentPlayerInstance().getItem(0).getIncreasingValue());

		this.maskDurationSeconds = 1f;

		float cureSprayStrength = PermanetPlayer.getPermanentPlayerInstance().getItem(2).getLevel();
		float fireSprayStrength = PermanetPlayer.getPermanentPlayerInstance().getItem(3).getLevel()*(-PermanetPlayer.getPermanentPlayerInstance().getItem(3).getIncreasingValue());

		sprays = new Spray[2];
		sprays[0] = new Spray(cureSprayStrength, Color.CYAN);
		sprays[1] = new Spray(fireSprayStrength, Color.ORANGE);

		this.currentMaskDuration = PermanetPlayer.getPermanentPlayerInstance().getItem(1).getLevel() * PermanetPlayer.getPermanentPlayerInstance().getItem(1).getIncreasingValue();
	}

	/**
	 * Create an instance of player using the singleton method
	 * @param masks Initial Number of masks.
	 * @param amountOfHealingFluid Initial amount of healing fluid.
	 * @param amountOfBurningFluid Initial amount of burning fluid.
	 * @return
	 */
	public static Boolean init(int masks, float amountOfHealingFluid, float amountOfBurningFluid) {
		if(player == null) {
			player = new Player(masks, amountOfHealingFluid, amountOfBurningFluid);
			return true;
		}
		return false;
	}

	/**
	 * Get the player instance
	 * @return The player instance
	 */
	public static Player getInstance() {
		if(player != null) {
			return player;
		}
		return null;
	}


	/**
	 * Allow the player to switch between sprays and correctly update the new spray
	 * so that is able to work.
	 */
	public void switchSpray() {
		if(sprayIndex == 0) {
			sprayIndex = 1;
			sprays[sprayIndex].getSprite().setPosition(sprays[0].getSprite().getX(), sprays[0].getSprite().getY());
			sprays[sprayIndex].setRotation(sprays[0].getRotation());
		} else {
			sprayIndex = 0;
			sprays[sprayIndex].getSprite().setPosition(sprays[1].getSprite().getX(), sprays[1].getSprite().getY());
			sprays[sprayIndex].setRotation(sprays[1].getRotation());
		}
	}

	/**
	 * Get the spray that the player is currently using
	 * @return spray
	 */
	public Spray getSpray() {
		return sprays[sprayIndex];
	}

	/**
	 * Load the walking animation and set the walk frames of the player.
	 */
	public void loadWalkingAnimation() {
		Texture walksheet = new Texture(Gdx.files.internal("player/The_Doctor_Walk.png"));

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


	/**
	 * Get the initial mask duration of the mask
	 * @return intital mask duration
	 */
	public float getInitialMaskDuration() { return PermanetPlayer.getPermanentPlayerInstance().getItem(1).getLevel() * PermanetPlayer.getPermanentPlayerInstance().getItem(1).getIncreasingValue(); }

	/**
	 * Get the current mask duration
	 * @return current mask duration
	 */
	public float getCurrentMaskDuration() {
		return currentMaskDuration;
	}

	/**
	 * Set the current mask duration
	 * @param currentMaskDuration new currentmaskduration level
	 */
	public void setCurrentMaskDuration(float currentMaskDuration) {
		this.currentMaskDuration = currentMaskDuration;
		System.out.println("CURRENT DURATION PLAYER: " +currentMaskDuration);
	}

	/**
	 * Reset the mask of the player
	 */
	public void resetMask() {
		this.currentMaskDuration = getInitialMaskDuration();
	}

	/**
	 * Get the animation of the player
	 * @return Animation of the player
	 * @see Animation
	 */
	public Animation<TextureRegion> getAnimation() {
		return walkAnimation;
	}

	/**
	 * Get the amount of food the player has
	 * @return return the amount of food the player has
	 */
	public float getFood() {
		return amountOfFood;
	}

	/**
	 * Get set the amount of food the player has
	 * @param f new food value
	 */
	public void setFood(float f) {
		amountOfFood =+ f;
	}


	@Override
	public void changeHealth(float deltaHealth) {
		currentMaskDuration -= deltaHealth;
	}

	@Override
	public float getHealth() {
		return currentMaskDuration;
	}

	@Override
	public boolean isDead() {
		if(currentMaskDuration == 0 && numberOfMasks == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Get the speed of the player
	 * @return speed of player
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Get the current spray index
	 * @return spray index
	 */
	public float getSprayIndex() {
		return sprayIndex;
	}

	/**
	 * Increase the sanity of the player
	 */
	public void increaseSanity() {
		PermanetPlayer.getPermanentPlayerInstance().changeSanity(sanityFactor);
	}

	/**
	 * Get the number of masks the player has
	 * @return number of masks
	 */
	public int getNumberOfMasks() {
		return numberOfMasks;
	}

	/**
	 * Set the new number of masks the player has
	 * @param numberOfMasks new number of masks
	 */
	public void setNumberOfMasks(int numberOfMasks) {
		this.numberOfMasks = numberOfMasks;
	}

	/**
	 * Reduce the mask durability
	 */
	public void reduceMask() {
		this.currentMaskDuration -= getMaskDurationSeconds();
	}

	/**
	 * Get the total mask duration/
	 * @return mask duration
	 */
	public float getMaskDurationSeconds() {
		return maskDurationSeconds;
	}

	/**
	 * Set the mask duration seconds
	 * @param maskDurationSeconds new number of mask duration
	 */
	public void setMaskDurationSeconds(float maskDurationSeconds) {
		this.maskDurationSeconds = maskDurationSeconds;
	}

	/**
	 * Update the amount of food
	 * @param addFood the new food gained by player
	 */
	public void updateFood(float addFood) {
		this.amountOfFood = this.amountOfFood + addFood;
	}

	/**
	 * Return the sanity type of the player!
	 * @return get the sanity classification of the player.
	 */
	public String getSanityLabel() {
		float sanity = PermanetPlayer.getPermanentPlayerInstance().getSanity();
		if(sanity < 100) {
			return "SANE";
		}
		else if(sanity < 200) {
			return "IMMORAL";
		}
		else if(sanity < 300) {
			return "VEXED";
		}
		else if(sanity < 500) {
			return "RISKY";
		}
		else {
			return "INSANE";
		}
		
	}

	public void dispose() {
		getSprite().getTexture().dispose();
	}

	/**
	 * Set the body of the player
	 * @param world The world value for the house
	 * @return the body of the player
	 */
	public Body setBody(World world) {
		
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("player/player.json"));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        bodyDef.fixedRotation = true;

        body = world.createBody(bodyDef);
        body.setTransform(sprite.getX()+16, sprite.getY()+16, (float)(2*Math.PI/4));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(26/2, 32/2);

        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.density = 1.0f; 
        fixtureDef2.filter.categoryBits = Constants.PLAYER;


        loader.attachFixture(body, "Name", fixtureDef2, 32f);

        return body;
    }

	/**
	 * Update the body position
	 * @param dx delta x value
	 * @param dy delta y value
	 */
	public void updateBody(float dx, float dy) {
		body.setTransform(sprite.getX()+16-dx/1.5f, sprite.getY()+16-dy/1.5f, body.getAngle());
	}

	/**
	 * Update the rotation of the body
	 * @param rotation float value of the body
	 */
	public void updateRotation(float rotation) {
		body.setTransform(sprite.getX()+16, sprite.getY()+16, rotation);
	}

	/**
	 * Get the body of the player
	 * @return body of the player
	 */
	public Body getBody() {
		return body;
	}

}
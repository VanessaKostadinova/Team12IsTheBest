package com.mygdx.renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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

public class Player extends Renderable implements Living {
	
	private Animation<TextureRegion> walkAnimation;
	private TextureRegion[] walkFrames;
	
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 2;
	private float speed;
	private PermanetPlayer permanetPlayer;
	private static final float sanityFactor = 0.2f;
	
	private float maskDurationSeconds;
	
	private Spray[] sprays;
	private int sprayIndex;

	private float amountOfHealingFluid;
	private float amountOfBurningFluid;

	private float currentMaskDuration;
	private int numberOfMasks;
	
	private float amountOfFood;
	
	private Body body;
	//private Body sprayBody;

	private static Player player = null;

	private Player(int masks, float amountOfHealingFluid, float amountOfBurningFluid) {
		super();
		loadWalkingAnimation();
		setSprite(walkFrames[0], 0, 0);
		this.amountOfFood = 1000;
		this.numberOfMasks = masks;
		PermanetPlayer.createInventoryInstance(masks, amountOfHealingFluid, amountOfBurningFluid);
		this.speed = 60f + (PermanetPlayer.getPermanentPlayerInstance().getItem(0).getLevel()*PermanetPlayer.getPermanentPlayerInstance().getItem(0).getIncreasingValue());
		this.amountOfHealingFluid = amountOfHealingFluid;
		this.amountOfBurningFluid = amountOfBurningFluid;

		/**
		 * Why are you trying to figure out the mask decrement?
		 * Since this implementation has already been added.
		 * Don't try to over-complicate things.
		 * -Inder
		 */
		this.maskDurationSeconds = 1f;

		float cureSprayStrength = PermanetPlayer.getPermanentPlayerInstance().getItem(3).getLevel();
		float fireSprayStrength = PermanetPlayer.getPermanentPlayerInstance().getItem(4).getLevel()*(-PermanetPlayer.getPermanentPlayerInstance().getItem(4).getIncreasingValue());

		sprays = new Spray[2];
		sprays[0] = new Spray(cureSprayStrength, Color.CYAN);
		sprays[1] = new Spray(fireSprayStrength, Color.ORANGE);

		this.currentMaskDuration = 20f;
	}

	public static Boolean init(int masks, float amountOfHealingFluid, float amountOfBurningFluid) {
		if(player == null) {
			player = new Player(masks, amountOfHealingFluid, amountOfBurningFluid);
			return true;
		}
		return false;
	}

	public static Player getInstance() {
		if(player != null) {
			return player;
		}
		return null;
	}


	/**
	 * DONT REMOVE THIS METHOD,
	 * IT GOING TO BE VITAL FOR THE RECONSTRUCTION OF THE PLAYER CLASS
	 * AND SHOULDN'T BE REMOVED ANYWAY AS IT ALREADY SERVES ON FUNCTION OF SAVING THE PLAYER ATTRIBUTES
	 * -Inder
	 */
	/*public Player(float gold, float mask, float sanity, float maskDurationSeconds, float cureSprayStrength, float fireSprayStrength, float speed, float energy) {
		super();
	}*/


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
	
	public Spray getSpray() {
		System.out.println(sprayIndex);
		return sprays[sprayIndex];
	}
		
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


	public float getInitialMaskDuration() {
		return 20;
	}

	public float getCurrentMaskDuration() {
		return currentMaskDuration;
	}

	public void resetMask() {
		this.currentMaskDuration = getInitialMaskDuration();
	}


	public Animation<TextureRegion> getAnimation() {
		return walkAnimation;
	}
	
	public float getFood() {
		return amountOfFood;
	}
	
	public void setFood(float f) {
		amountOfFood =+ f;
	}
	
	public float getEnergy() {
		return permanetPlayer.getEnergy();
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
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void updateSpeed(float newSpeed) {
		this.speed = this.speed + newSpeed*60f;
	}
	
	public float getSprayIndex() {
		return sprayIndex;
	}

	public void increaseSanity() {
		//permanetPlayer.changeSanity(sanityFactor);
	}

	public float getSanity() {
		return permanetPlayer.getSanity();
	}

	public float getNumberOfMasks() {
		return numberOfMasks;
	}

	public void reduceMask() {
		this.currentMaskDuration -= getMaskDurationSeconds();
	}
	
	public float getMaskDurationSeconds() {
		return maskDurationSeconds;
	}
	
	public void setMaskDurationSeconds(float maskDurationSeconds) {
		this.maskDurationSeconds = maskDurationSeconds;
	}
	
	public void updateMaskDurationSeconds(float addSeconds) {
		this.maskDurationSeconds = maskDurationSeconds+addSeconds;
	}
	
	public void updateSprays(int index, float strength) {
		sprays[index].addStrength(strength);
	}
	
	public void updateFood(float addFood) {
		this.amountOfFood = this.amountOfFood + addFood;
	}
	
	public String getSanityLabel() {
		float sanity = permanetPlayer.getSanity();
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
	
	/*public Body setSprayBody(World world) {
		
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("spray/spray.json"));

        BodyDef bodyDef3 = new BodyDef();
        bodyDef3.type = BodyType.DynamicBody;
        bodyDef3.position.set(0, 0);
        bodyDef3.fixedRotation = true;

        sprayBody = world.createBody(bodyDef3);
        sprayBody.setTransform(getSprite().getX()+16, getSprite().getY()+16, getSpray().getSprite().getRotation());
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f; 
       
        loader.attachFixture(sprayBody, "Name", fixtureDef, 32f);

        return sprayBody;
    }

    public void updateSpray(float rotation) {
		sprayBody.setTransform(getSprite().getX()+16, getSprite().getY()+16, rotation);
	}
	
	public void updateSprayPosition() {
		sprayBody.setTransform(getSprite().getX()+16, getSprite().getY()+16, sprayBody.getAngle());
	}*/
	
	public void updateBody(float dx, float dy) {
		body.setTransform(sprite.getX()+16-dx/1.5f, sprite.getY()+16-dy/1.5f, body.getAngle());
	}
	
	public void updateRotation(float rotation) {
		body.setTransform(sprite.getX()+16, sprite.getY()+16, rotation);
		//body.setTransform(sprite.getX(), sprite.getY(), body.getAngle());
	}

	public Body getBody() {
		// TODO Auto-generated method stub
		return body;
	}

}
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
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.ConeLight;

public class Player extends Renderable implements Living {
	
	private Animation<TextureRegion> walkAnimation;
	private TextureRegion[] walkFrames;
	
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 2;
	private float speed = 2f*60f;
	private static final float sanityFactor = 0.2f;
	
	private float maskDurationSeconds;
	
	private float sanity;
	
	private Spray[] sprays;
	private int sprayIndex;
	
	private float mask;
	private float initialMask;
	
	private float amountOfFood;
	
	private int energy;
	
	private Body body;
	private Body sprayBody;

	
	
	
	public Player(float gold, float mask, float sanity, float maskDurationSeconds, float cureSprayStrength, float fireSprayStrength, float speed, float energy) {
		super();
		loadWalkingAnimation();
		setSprite(walkFrames[0], 0, 0);
		this.amountOfFood = gold;
		this.initialMask = mask;
		this.sanity = sanity;
		this.speed = speed*60f;
		this.maskDurationSeconds = maskDurationSeconds;

		sprays = new Spray[2];
		sprays[0] = new Spray(cureSprayStrength, Color.CYAN);
		sprays[1] = new Spray(fireSprayStrength, Color.ORANGE);
		
		
		this.mask = mask;
		this.energy = (int) energy;
	}
	
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
		return energy;
	}

	@Override
	public void changeHealth(float deltaHealth) {
		mask -= deltaHealth;
	}

	@Override
	public float getHealth() {
		return mask;
	}

	@Override
	public boolean isDead() {
		if(mask == 0) {
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
		System.out.println(newSpeed);

		System.out.println(newSpeed*60f);
		this.speed = this.speed + newSpeed*60f;
	}
	
	public float getSprayIndex() {
		return sprayIndex;
	}

	public void increaseSanity() {
		sanity = sanity + sanityFactor;
	}
	
	public float getSanity() {
		return sanity;
	}
	
	public void writeToPlayerFile() {
		FileHandle handle = Gdx.files.local("data/player.txt");
		handle.writeString(amountOfFood+","+initialMask+","+sanity+","+maskDurationSeconds+","+sprays[0].getValue()+","+sprays[1].getValue()+","+speed/60f+","+energy, false);
	}
	
	public float getInitialMask() {
		return initialMask;
	}
	
	public void reduceMask() {
		this.mask = this.mask - (this.initialMask/maskDurationSeconds);
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
	
	public Body setSprayBody(World world) {
		
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("spray/spray.json"));

        BodyDef bodyDef3 = new BodyDef();
        bodyDef3.type = BodyType.StaticBody;
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
	}
	
	public void updateBody() {
		body.setTransform(sprite.getX()+16, sprite.getY()+16, body.getAngle());
	}
	
	public void updateRotation(float rotation) {
		body.setTransform(sprite.getX()+16, sprite.getY()+16, rotation);
		//body.setTransform(sprite.getX(), sprite.getY(), body.getAngle());
	}

	public Body getBody() {
		// TODO Auto-generated method stub
		return body;
	}
	
	public Body getSprayBody() {
		// TODO Auto-generated method stub
		return sprayBody;
	}
	
	public void deltaEnergy(int removeEnergy) {
		this.energy = this.energy - removeEnergy;
	}
	
	public void resetEnergy() {
		this.energy = 100;
	}


}

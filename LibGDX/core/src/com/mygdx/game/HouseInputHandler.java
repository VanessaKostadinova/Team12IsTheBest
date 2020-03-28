package com.mygdx.game;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.camera.Camera;
import com.mygdx.renderable.MyContactListener;
import com.mygdx.renderable.NPC;
import com.mygdx.renderable.Player;

/**
 * Used to handle the input within the house.
 * @author Inder, Vanessa
 * @version 1.3
 */
public class HouseInputHandler implements InputProcessor {

	/** The player's width. */
	private float playerWidth;
	
	/** The player's height. */
	private float playerHeight;
	
	/** The camera of the scene. */
	private Camera camera;
	
	/** The level. */
	private int[][] level;
	
	/** The speed of player. */
	private float speed;
	
	/** If menu is opened. */
	private Boolean isPaused;

	/** If Inventory is opened */
	private Boolean inventoryOpened;
	
	/** Pause menu. */
	private Window pause;

	/** If mouse pressed. */
	private boolean mousePressed;

	/** The paragraph  for each of the notes */
	private Label paragraph;

	/** The Image for the actual letter texture. */
	private Image letter;

	/** The npcs. */
	private List<NPC> npcs;

	/** The image icon to leave the house */
	private Image icon;
	
	/** Contact listener for bodies */
	private MyContactListener listener;

	/** Used to store the string of the last input of the user */
	private String lastInput;

	/** The boolean variable to store whether or not cutscene is active */
	private Boolean cutsceneActive;

	/** A number of sound effects for the game */
	private Music walkingSoundEffect, cureSoundEffect, fireSoundEffect, leatherAndJeansEffect;

	/** A potential background music for the game*/
	private Music backgroundSound1, backgroundSound2, backgroundSound3;

	/** The input value for moving the character up */
	private int UP = Input.Keys.W;
	/** The input value for moving the character left */
	private int LEFT = Input.Keys.A;
	/** The input value for moving the character right */
	private int RIGHT = Input.Keys.D;
	/** The input value for moving the character down */
	private int DOWN = Input.Keys.S;

	/** The state time of the input handler. - How long has the class been running for. */
	private float stateTime;


	/**
	 * The constructor for the HouseInputHandler.
	 * @param camera Camera of the house from SpriteBatch
	 * @param level The current Level layout for the game
	 * @param pause The pause window in the HouseScreen
	 * @param npcs The NPC's Within a house.
	 * @param paragraph The paragraph of text for a note.
	 * @param letter The letter texture for a note.
	 * @param icon The Icon to leave the house
	 * @param world The Body2D world information (Bodies, Lights etc).
	 */
	public HouseInputHandler(Camera camera, int[][] level, Window pause, List<NPC> npcs, Label paragraph, Image letter, Image icon, World world) {
		this.camera = camera;
		this.level = level;
		this.playerWidth = Player.getInstance().getSprite().getWidth();
		this.playerHeight = Player.getInstance().getSprite().getHeight();
		this.mousePressed = false;
		this.speed = Player.getInstance().getSpeed();
		this.isPaused = false;
		this.inventoryOpened = false;
		this.pause = pause;
		this.npcs = npcs;
		this.icon = icon;
		this.paragraph = paragraph;
		this.letter = letter;
		this.walkingSoundEffect = Gdx.audio.newMusic(Gdx.files.internal("soundeffects/footstep-on-wooden-floor.wav"));
		this.cureSoundEffect = Gdx.audio.newMusic(Gdx.files.internal("soundeffects/healing-spray.wav"));
		this.fireSoundEffect = Gdx.audio.newMusic(Gdx.files.internal("soundeffects/flame-thrower-fire-with-air.wav"));
		this.leatherAndJeansEffect = Gdx.audio.newMusic(Gdx.files.internal(	"soundeffects/leathers-and-jeans-moving.wav"));
		this.stateTime = 0f;
		this.backgroundSound1 = Gdx.audio.newMusic(Gdx.files.internal("soundeffects/big-creepy-sound.wav"));
		this.backgroundSound2 = Gdx.audio.newMusic(Gdx.files.internal("soundeffects/creepy-sound-one.wav"));
		this.backgroundSound3 = Gdx.audio.newMusic(Gdx.files.internal("soundeffects/creepy-sound-two.wav"));

		this.backgroundSound1.setVolume(0.01f);
		this.backgroundSound2.setVolume(0.01f);
		this.backgroundSound3.setVolume(0.01f);

		listener = new MyContactListener();
		this.cutsceneActive = false;
		world.setContactListener(listener);
		lastInput = "";
	}

	/**
	 * Play random background music while in a house.
	 */
	public void playBackgroundMusic() {
		if(!backgroundSound1.isPlaying() && !backgroundSound1.isPlaying() && !backgroundSound1.isPlaying()) {
			Random r = new Random();
			int value = r.nextInt((2 - 0) + 1) + 0;
			switch(value) {
				case 0: backgroundSound1.play();
				case 1: backgroundSound2.play();
				case 2: backgroundSound3.play();
			}

		}
	}

	/**
	 * Stop and dispose of any music when you leave the screen.
	 */
	public void stopAllMusicAndDispose() {
		backgroundSound1.stop();
		backgroundSound2.stop();
		backgroundSound3.stop();
		walkingSoundEffect.stop();
		cureSoundEffect.stop();
		fireSoundEffect.stop();
		leatherAndJeansEffect.stop();

		backgroundSound1.dispose();
		backgroundSound2.dispose();
		backgroundSound3.dispose();
		walkingSoundEffect.dispose();
		cureSoundEffect.dispose();
		fireSoundEffect.dispose();
		leatherAndJeansEffect.dispose();
	}


	/**
	 * Used to handle the movement of the player itself and update all of the bodies attached to it.
	 * @param region The current texture of the player
	 * @param delta The delta of the player to ensure lag catchup occurs if it does occur.
	 */
	public void movement(TextureRegion region, float delta) {
		float playerX = Player.getInstance().getSprite().getX();
		float playerY = Player.getInstance().getSprite().getY();
		System.out.println(playerX+","+playerY+","+100);
		stateTime = stateTime + delta;
		if(Player.getInstance().getSanityLabel()=="RISKY") {
			if(stateTime % 10 < 1) {
				UP = Input.Keys.S;
				DOWN = Input.Keys.W;
				LEFT = Input.Keys.D;
				RIGHT = Input.Keys.A;
			}
			else {
				UP = Input.Keys.W;
				DOWN = Input.Keys.S;
				LEFT = Input.Keys.A;
				RIGHT = Input.Keys.D;
			}
		}

		if(Player.getInstance().getSanityLabel()=="INSANE") {
			if(stateTime % 5 < 1) {
				UP = Input.Keys.S;
				DOWN = Input.Keys.W;
				LEFT = Input.Keys.D;
				RIGHT = Input.Keys.A;
			}
			else {
				UP = Input.Keys.W;
				DOWN = Input.Keys.S;
				LEFT = Input.Keys.A;
				RIGHT = Input.Keys.D;
			}
		}

		if(lastInput.length() > 2) {
			lastInput = "";
		}

		/*
		 *
		 * In W and S I took away speed from the second arguments to make sure that the player is able to pass through the gaps.
		 *
		 * In S and A I also took away speed from Y and X respectively to make sure that the collision happens on the outside not within.
		 *
		 * Otherwise the camera and player both move at the same pace.
		 */
		if(!isPaused && !cutsceneActive) {
			playBackgroundMusic();
			if(Gdx.input.isKeyPressed(UP)) {
				if(!collision(playerX, playerY + playerHeight) && !collision(playerX + playerWidth - speed*delta -2, playerY + playerHeight)) {
					camera.getCamera().translate(0f, speed* delta);
					Player.getInstance().updateSprite(0, speed* delta);
					Player.getInstance().updateBody(0, speed* delta);
					camera.updateCamera();
					Player.getInstance().getSprite().setRegion(region);
					Player.getInstance().getSpray().updateSprite(0f, speed*delta);
					if(shouldExit(playerX, playerY + playerHeight) && shouldExit(playerX-2 + playerWidth - speed*delta, playerY + playerHeight)) {
						icon.setVisible(true);
					}
					else {
						icon.setVisible(false);
					}
					lastInput = lastInput + "W";
				}
			}
			if(Gdx.input.isKeyPressed(LEFT)) {
				if(!collision(playerX - speed*delta, playerY+2) && !collision(playerX - speed*delta, playerY + playerHeight - 4)) {
					camera.getCamera().translate(-speed*delta, 0f);
					camera.updateCamera();
					Player.getInstance().updateSprite(-speed*delta, 0);
					Player.getInstance().updateBody(-speed*delta, 0f);
					Player.getInstance().getSprite().setRegion(region);
					Player.getInstance().getSpray().updateSprite(-speed*delta, 0);
					if(shouldExit(playerX - speed*delta, playerY) && shouldExit(playerX - speed*delta, playerY + playerHeight - 4)) {
						icon.setVisible(true);
					}
					else {
						icon.setVisible(false);
					}
					lastInput = lastInput + "A";
				}
			}
			
			if(Gdx.input.isKeyPressed(DOWN)) {
				if(!collision(playerX+2, playerY - speed*delta) && !collision(playerX + playerWidth - speed*delta - 2, playerY - speed*delta)) {
					camera.getCamera().translate(0f, -speed*delta);
					Player.getInstance().updateSprite(0,-speed*delta);
					Player.getInstance().updateBody(0f, -speed*delta);
					camera.updateCamera();
					Player.getInstance().getSprite().setRegion(region);
					Player.getInstance().getSpray().updateSprite(0f, -speed*delta);
					if(shouldExit(playerX+2, playerY - speed*delta) && shouldExit(playerX-2 + playerWidth - speed*delta, playerY - speed*delta)) {
						icon.setVisible(true);
					}
					else {
						icon.setVisible(false);
					}
					lastInput = lastInput + "S";
				}
			}
			
			if(Gdx.input.isKeyPressed(RIGHT)) {
				if(!collision(playerX + playerWidth, playerY+2) && !collision(playerX + playerWidth, playerY + playerHeight - 4)) {
					Player.getInstance().updateSprite(speed* delta, 0);
					camera.getCamera().translate(speed*delta, 0);
					camera.updateCamera();
					Player.getInstance().updateBody(speed*delta, 0);
					Player.getInstance().getSprite().setRegion(region);
					Player.getInstance().getSpray().updateSprite(speed*delta, 0f);
					if(shouldExit(playerX + playerWidth, playerY) && shouldExit(playerX + playerWidth, playerY + playerHeight - 4)) {
						icon.setVisible(true);
					}
					else {
						icon.setVisible(false);
					}
					lastInput = lastInput + "D";
				}
			}

			if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D)) {
				if(!walkingSoundEffect.isPlaying()) {
					walkingSoundEffect.setVolume(0.3f);
					walkingSoundEffect.play();
				}
			}
			else {
				walkingSoundEffect.stop();
			}
			leatherAndJeansEffect.stop();


		}
		

		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			if(letter.isVisible()) {
				this.isPaused = false;
				letter.setVisible(false);;
				paragraph.setVisible(false);
			} 
			else {
				if(!isPaused) {
					pause.setVisible(true);
					togglePaused();
				}
				else {
					pause.setVisible(false);
					togglePaused();
				}
			}
		}
	}

	/**
	 * Handler for the spray which the player has
	 * @param value The amount of spray the player has of that specific spray
	 * @return the new amount of spray the player has.
	 */
	public float spray(float value) {
		if(!isPaused && !cutsceneActive) {
			Player.getInstance().getSpray().setVisible(mousePressed);
			if(Player.getInstance().getSpray().getIsActive()) {
				if((int)value < 1) {
					value = 0;
					Player.getInstance().getSpray().setIsActive(false);
					Player.getInstance().getSpray().setVisible(false);
				}
				else {
					value = value - 0.025f;
					if(Player.getInstance().getSprayIndex() == 0) {
						cureSoundEffect.play();
						cureSoundEffect.setVolume(0.2f);
					}
					else {
						fireSoundEffect.play();
						fireSoundEffect.setVolume(0.1f);

					}
				}
			}
			else {
				cureSoundEffect.stop();
				fireSoundEffect.stop();
			}
		}
		return value;
	}
	
	/**
	 * Collision.
	 *
	 * @param x the x coordinate of the character
	 * @param y the y coordinate of the character
	 * @return true, if collision occurs
	 */
	public boolean collision(float x, float y) {
		/*                        
		 * Each tile is 32*32
		 * Hence we divide the coordinates by 32 and round down.
		 * This will be = to the tile the player is interacting with.
		 * If the tile is 1 in the array (a wall) return collision as true
		 */
	    int arrayX = 0;
	    int arrayY = 0;

	    if(x > 30) {
	    	arrayX = (int) (x/32);
	    }
	    if(y > 30) {
	    	arrayY = (int) (y/32);
	    }

	    try {
			if (level[arrayY][arrayX] == 1) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException exception) {
	    	return false;
		}
	    
	    if(y < 0 || x < 0) {
	    	return true;
	    }
	    
	    
	    return false;

	}

	/**
	 * If there is a door present for the player to access and leave the house with.
	 * @param x The X value of the player
	 * @param y The Y value of the player
	 * @return If the player is next to a door and can leave
	 */
	private boolean shouldExit(float x, float y) {
		/*                        
		 * Each tile is 32*32
		 * Hence we divide the coordinates by 32 and round down.
		 * This will be = to the tile the player is interacting with.
		 * If the tile is 1 in the array (a wall) return collision as true
		 */
	    int arrayX = 0;
	    int arrayY = 0;

	    if(x > 30) {
	    	arrayX = (int) (x/32);
	    }
	    if(y > 30) {
	    	arrayY = (int) (y/32);
	    }

	    if(level[arrayY][arrayX] == 2) {
	    	return true;
	    }
	    else {
	    	return false;
	    }
	    

	}


	/**
	 * Event to check if the player has switched his spray
	 * @param keycode What key has been pressed
	 * @return false
	 */
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.Q) {
			Player.getInstance().switchSpray();
			System.out.println(Player.getInstance().getSprayIndex());
		}
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		mousePressed = true;
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		mousePressed = false;
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		spriteRotations(screenX, screenY);
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		spriteRotations(screenX, screenY);


		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Checking if spray has collided with a villager
	 * @param list
	 * @return
	 */
	public boolean sprayWithVillagerCollision(List<NPC> list) {
		Boolean value = Player.getInstance().getSpray().collision(list, Player.getInstance().getSprayIndex(), Player.getInstance());
		return value;
	}

	/**
	 * Used to handle the rotation of the player to follow the mouse
	 * @param screenX The X position of the mouse on screen.
	 * @param screenY The Y position of the mouse on screen.
	 */
	private void spriteRotations(int screenX, int screenY) {
		/*
		 * The intital calculations are to make sure the initial coordinates are from the centre.
		 * Then we unproject the mouse coordinate to get the coordinates according to the camera rather than the UI.
		 *
		 * We then implement atan2 which gives us the angle. Then if the rotation is a negative value we add 360 to make it positive.
		 * Then we set the sprites rotation.
		 */
		float initialRotation = Player.getInstance().getSprite().getRotation();
		if(!isPaused ) {
			float spriteX = Player.getInstance().getSprite().getX()+Player.getInstance().getSprite().getWidth()/2;
			float spriteY = Player.getInstance().getSprite().getY()+Player.getInstance().getSprite().getHeight()/2;
		
			Vector3 mouse = camera.getCamera().unproject(new Vector3(screenX, screenY, 0));
		
		    float rotation = (float) MathUtils.radiansToDegrees * MathUtils.atan2(mouse.y - spriteY, mouse.x - spriteX);
		    if (rotation < 0) rotation += 360;
			Player.getInstance().getSprite().setRotation(rotation);
			Player.getInstance().getSpray().update(rotation, spriteX-Player.getInstance().getSprite().getWidth()/2, spriteY-Player.getInstance().getSprite().getHeight()/2, npcs);
			Player.getInstance().getSpray().setRotation(rotation-90f);

			Player.getInstance().updateRotation(rotation * (float)(Math.PI/180) );
			rotation = rotation - 90f;
			//player.updateSpray(rotation * (float)(Math.PI/180));

		}

	}


	/**
	 * Sets paused variable
	 * @param isPaused if paused or unpaused
	 */
	public void setPaused(Boolean isPaused) {
		this.isPaused = isPaused;
	}

	/** Switches between paused and unpaused without setting*/
	public void togglePaused() {
		if(isPaused) {
			isPaused = false;
		}
		else {
			isPaused = true;
		}		
	}

	/** Sets the cutscene variable to active */
	public void setCutsceneActive(Boolean cutsceneActive) {
		this.cutsceneActive = cutsceneActive;
	}

	public Boolean getCutsceneActive() {
		return cutsceneActive;
	}

	public Boolean getPaused() {
		return this.isPaused;
	}
	
	public Boolean getPressed() {
		return mousePressed;
	}

}

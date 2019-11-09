package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.camera.Camera;
import com.mygdx.character.NPC;
import com.mygdx.character.Player;

/**
 * The Class InputHandler, which controls the input of the player.
 * @author Team 12
 */
public class InputHandler implements InputProcessor {

	/** The player. */
	private Player player;
	
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
	
	/** Pause menu. */
	private Window pause;
	
	/** If mouse pressed. */
	private boolean mousePressed;
	
	/** The npcs. */
	private NPC[] npcs;

	/**
	 * Instantiates a new input handler.
	 *
	 * @param player the player
	 * @param camera the camera
	 * @param level the level
	 * @param pause the pause
	 * @param npcs the npcs
	 */
	public InputHandler(Player player, Camera camera, int[][] level, Window pause, NPC[] npcs) {
		this.player = player;
		this.camera = camera;
		this.level = level;
		this.playerWidth = player.getSprite().getWidth();
		this.playerHeight = player.getSprite().getHeight();
		this.mousePressed = false;
		this.speed = 2f*60f;
		this.isPaused = false;
		this.pause = pause;
		this.npcs = npcs;
	}

	/**
	 * Movement.
	 *
	 * @param region The animation frame of the doctor
	 * @param delta Delta of the current scene.
	 */
	public void movement(TextureRegion region, float delta) {

		float playerX = player.getX();
		float playerY = player.getY();

		/*
		 *
		 * In W and S I took away speed from the second arguments to make sure that the player is able to pass through the gaps.
		 *
		 * In S and A I also took away speed from Y and X respectively to make sure that the collision happens on the outside not within.
		 *
		 * Otherwise the camera and player both move at the same pace.
		 */
		if(!isPaused) {
			if(Gdx.input.isKeyPressed(Input.Keys.W)) {
				if(!collision(playerX, playerY + playerHeight) && !collision(playerX + playerWidth - speed*delta, playerY + playerHeight)) {
					camera.getCamera().translate(0f, speed* delta);
					player.updateXY(0, speed* delta);
					player.getSpray().updatePosition(0f, speed*delta);
					camera.updateCamera();
					player.getSprite().setRegion(region);
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				if(!collision(playerX - speed*delta, playerY) && !collision(playerX - speed*delta, playerY + playerHeight - 2)) {
					camera.getCamera().translate(-speed*delta, 0f);
					camera.updateCamera();
					player.updateXY(-speed*delta, 0);
					player.getSpray().updatePosition(-speed*delta, 0f);
					player.getSprite().setRegion(region);
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {
				if(!collision(playerX, playerY - speed*delta) && !collision(playerX + playerWidth - speed*delta, playerY - speed*delta)) {
					camera.getCamera().translate(0f, -speed*delta);
					player.updateXY(0, -speed*delta);
					player.getSpray().updatePosition(0f, -speed*delta);
					camera.updateCamera();
					player.getSprite().setRegion(region);
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)) {
				if(!collision(playerX + playerWidth, playerY) && !collision(playerX + playerWidth, playerY + playerHeight - 2)) {
					camera.getCamera().translate(speed*delta, 0f);
					camera.updateCamera();
					player.updateXY(+speed* delta, 0);
					player.getSpray().updatePosition(speed*delta, 0f);
					player.getSprite().setRegion(region);
				}
			}
		}
	}
	
	/**
	 * Used to check collisions between the NPC and Spray.
	 *
	 * @param npc All of the npcs within a level.
	 */
	public void sprayWithVillagerCollision(NPC[] npc) {
		Boolean value = player.getSpray().collision(npc, player.getSprayType());
		if(value) {
			player.setCoins(player.getCoins() + 5);
		}
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		//If escape pressed show/hide menu.
		if(keycode == Keys.ESCAPE) {
			if(!isPaused) {
				pause.setVisible(true);
				togglePaused();
			}
			else {
				pause.setVisible(false);
				togglePaused();
			}
		}
		
		if(keycode == Keys.Q) {
			player.switchSolution();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		player.playerStanding();
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		mousePressed = true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		mousePressed = false;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		spriteRotations(screenX, screenY);
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		spriteRotations(screenX, screenY);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Starts the spray animation.
	 */
	public void spray() {
		if(!isPaused) {
			player.getSpray().setVisible(mousePressed);
		}
	}

	/**
	 * Sprite rotations.
	 *
	 * @param screenX the coordinates of the mouse on screen in X
	 * @param screenY the coordinates of the mouse on screen in Y
	 */

	private void spriteRotations(int screenX, int screenY) {
		/*
		 * The intital calculations are to make sure the initial coordinates are from the centre.
		 * Then we unproject the mouse coordinate to get the coordinates according to the camera rather than the UI.
		 *
		 * We then implement atan2 which gives us the angle. Then if the rotation is a negative value we add 360 to make it positive.
		 * Then we set the sprites rotation.
		 */
		if(!isPaused) {
			float spriteX = player.getX()+player.getSprite().getWidth()/2;
			float spriteY = player.getY()+player.getSprite().getHeight()/2;
	
			Vector3 mouse = camera.getCamera().unproject(new Vector3(screenX, screenY, 0));
	
		    float rotation = (float)MathUtils.radiansToDegrees * MathUtils.atan2(mouse.y - spriteY, mouse.x - spriteX);
		    if (rotation < 0) rotation += 360;
		    player.getSprite().setRotation(rotation);
		    player.getSpray().update(rotation, spriteX-player.getSprite().getWidth()/2, spriteY-player.getSprite().getHeight()/2, npcs);
		    player.getSpray().rotate(rotation);

		}
	}

	/**
	 * Collision.
	 *
	 * @param x the x coordinate of the character
	 * @param y the y coordinate of the character
	 * @return true, if collision occurs
	 */
	private boolean collision(float x, float y) {
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

	    if(level[arrayY][arrayX] == 1) {
	    	return true;
	    }
	    return false;

	}

	/**
	 * Toggle paused boolean value.
	 */
	public void togglePaused() {
		if(isPaused) {
			isPaused = false;
		}
		else {
			isPaused = true;
		}
	}
}

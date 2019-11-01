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
import com.mygdx.character.Player;

public class InputHandler implements InputProcessor {

	private Player player;
	private float playerWidth;
	private float playerHeight;
	private Camera camera;
	private int[][] level;
	private float speed;
	private Boolean isPaused;
	private Window pause;


	public InputHandler(Player player, Camera camera, int[][] level, Window pause) {
		this.player = player;
		this.camera = camera;
		this.level = level;
		this.playerWidth = player.getSprite().getWidth();
		this.playerHeight = player.getSprite().getHeight();

		this.speed = 2f*60f;
		this.isPaused = false;
		this.pause = pause;
	}

	public void movement(TextureRegion region, float delta) {

		float playerX = player.getX();
		float playerY = player.getY();

		/**
		 * To Vanessa:
		 *
		 * I ended up using Polling over events since they were essentially doing the same thing. We probably want to use delta to move the character.
		 * However this is the first implementation so far.
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
					camera.updateCamera();
					player.getSprite().setRegion(region);
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				if(!collision(playerX - speed*delta, playerY) && !collision(playerX - speed*delta, playerY + playerHeight - 2)) {
					camera.getCamera().translate(-speed*delta, 0f);
					camera.updateCamera();
					player.updateXY(-speed*delta, 0);
					player.getSprite().setRegion(region);
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {
				if(!collision(playerX, playerY - speed*delta) && !collision(playerX + playerWidth - speed*delta, playerY - speed*delta)) {
					camera.getCamera().translate(0f, -speed*delta);
					player.updateXY(0, -speed*delta);
					camera.updateCamera();
					player.getSprite().setRegion(region);
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)) {
				if(!collision(playerX + playerWidth, playerY) && !collision(playerX + playerWidth, playerY + playerHeight - 2)) {
					camera.getCamera().translate(speed*delta, 0f);
					camera.updateCamera();
					player.updateXY(+speed* delta, 0);
					player.getSprite().setRegion(region);
				}
			}
		}
	}

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
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		player.playerReset();
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if(!isPaused) {
			spriteRotations(screenX, screenY);
		}
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * Explanation of Maths behind this is within the discord group.
	 * However, this essentially uses polar coordinates to get the rotation of the sprite to face the pointer.
	 *
	 * The intital calculations are to make sure the initial coordinates are from the centre.
	 * Then we unproject the mouse coordinate to get the coordinates according to the camera rather than the UI.
	 *
	 * We then implement atan2 which gives us the angle. Then if the rotation is a negative value we add 360 to make it positive.
	 * Then we set the sprites rotation.
	 */
	private void spriteRotations(int screenX, int screenY) {
		float spriteX = player.getX()+player.getSprite().getWidth()/2;
		float spriteY = player.getY()+player.getSprite().getHeight()/2;

		Vector3 mouse = camera.getCamera().unproject(new Vector3(screenX, screenY, 0));

	    float rotation = (float)MathUtils.radiansToDegrees * MathUtils.atan2(mouse.y - spriteY, mouse.x - spriteX);
	    if (rotation < 0) rotation += 360;
	    player.getSprite().setRotation(rotation);
	}

	/*
	 * Each tile is 32*32
	 * Hence we divide the coordinates by 32 and round down.
	 * This will be = to the tile the player is interacting with.
	 * If the tile is 1 in the array (a wall) return collision as true
	 */
	private boolean collision(float x, float y) {
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

	/*
	 * Toggles if paused or not. Rather than setting true/false.
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

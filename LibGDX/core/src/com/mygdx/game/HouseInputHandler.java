package com.mygdx.game;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

public class HouseInputHandler implements InputProcessor {
	
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
	
	private Label paragraph;
	
	private Image letter;
	
	private Boolean Up, Down, Left, Right;
	
	
	/** The npcs. */
	private List<NPC> npcs;

	private Image icon;
	
	private World world;
	
	private MyContactListener listener;

	private String lastInput;
	
	public HouseInputHandler(Player player, Camera camera, int[][] level, Window pause, List<NPC> npcs, Label paragraph, Image letter, Image icon, World world) {
		this.player = player;
		this.camera = camera;
		this.level = level;
		this.playerWidth = player.getSprite().getWidth();
		this.playerHeight = player.getSprite().getHeight();
		this.mousePressed = false;
		this.speed = player.getSpeed();
		this.isPaused = false;
		this.pause = pause;
		this.npcs = npcs;
		this.icon = icon;
		this.paragraph = paragraph;
		this.letter = letter;
		this.world = world;
		listener = new MyContactListener();
		world.setContactListener(listener);
		lastInput = "";
		
		
		Up = false;
		Down = false;
		Left = false;
		Right = false;
	}
	
	
	public void movement(TextureRegion region, float delta) {
		float playerX = player.getSprite().getX();
		float playerY = player.getSprite().getY();
		
		
		
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
		if(!isPaused) {
			if(Gdx.input.isKeyPressed(Input.Keys.W)) {
				if(!collision(playerX, playerY + playerHeight) && !collision(playerX + playerWidth - speed*delta, playerY + playerHeight)) {
					camera.getCamera().translate(0f, speed* delta);
					player.updateSprite(0, speed* delta);
					//player.updateSprayPosition();
					player.updateBody(0, speed* delta);
					camera.updateCamera();
					player.getSprite().setRegion(region);
					player.getSpray().updateSprite(0f, speed*delta);
					if(shouldExit(playerX, playerY + playerHeight) && shouldExit(playerX-2 + playerWidth - speed*delta, playerY + playerHeight)) {
						icon.setVisible(true);
					}
					else {
						icon.setVisible(false);
					}
					lastInput = lastInput + "W";
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				if(!collision(playerX - speed*delta, playerY+2) && !collision(playerX - speed*delta, playerY + playerHeight - 2)) {
					camera.getCamera().translate(-speed*delta, 0f);
					camera.updateCamera();
					player.updateSprite(-speed*delta, 0);
					//player.updateSprayPosition();
					player.updateBody(-speed*delta, 0f);
					player.getSprite().setRegion(region);
					player.getSpray().updateSprite(-speed*delta, 0);
					if(shouldExit(playerX - speed*delta, playerY) && shouldExit(playerX - speed*delta, playerY + playerHeight - 4)) {
						icon.setVisible(true);
					}
					else {
						icon.setVisible(false);
					}
					lastInput = lastInput + "A";
				}
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {
				if(!collision(playerX+2, playerY - speed*delta) && !collision(playerX + playerWidth - speed*delta, playerY - speed*delta)) {
					camera.getCamera().translate(0f, -speed*delta);
					player.updateSprite(0,-speed*delta);
					//player.updateSprayPosition();
					player.updateBody(0f, -speed*delta);
					camera.updateCamera();
					player.getSprite().setRegion(region);
					player.getSpray().updateSprite(0f, -speed*delta);
					if(shouldExit(playerX+2, playerY - speed*delta) && shouldExit(playerX-2 + playerWidth - speed*delta, playerY - speed*delta)) {
						icon.setVisible(true);
					}
					else {
						icon.setVisible(false);
					}
					lastInput = lastInput + "S";
				}
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.D)) {
				if(!collision(playerX + playerWidth, playerY+2) && !collision(playerX + playerWidth, playerY + playerHeight - 2)) {
					player.updateSprite(speed* delta, 0);
					camera.getCamera().translate(speed*delta, 0);
					camera.updateCamera();
					//player.updateSprayPosition();
					player.updateBody(speed*delta, 0);
					player.getSprite().setRegion(region);
					player.getSpray().updateSprite(speed*delta, 0f);
					if(shouldExit(playerX + playerWidth, playerY) && shouldExit(playerX + playerWidth, playerY + playerHeight - 4)) {
						icon.setVisible(true);
					}
					else {
						icon.setVisible(false);
					}
					lastInput = lastInput + "D";
				}
			}
			
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
	
	
	
	

	
	public void spray() {
		if(!isPaused) {
			player.getSpray().setVisible(mousePressed);
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
	    
	    if(y < 0 || x < 0) {
	    	return true;
	    }
	    
	    
	    return false;

	}
	
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



	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.Q) {
			player.switchSpray();
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
	
	public void sprayWithVillagerCollision(List<NPC> list) {
		Boolean value = player.getSpray().collision(list, player.getSprayIndex(), player);
	}
	
	private void spriteRotations(int screenX, int screenY) {
		/*
		 * The intital calculations are to make sure the initial coordinates are from the centre.
		 * Then we unproject the mouse coordinate to get the coordinates according to the camera rather than the UI.
		 *
		 * We then implement atan2 which gives us the angle. Then if the rotation is a negative value we add 360 to make it positive.
		 * Then we set the sprites rotation.
		 */
		if(!isPaused ) {
			float spriteX = player.getSprite().getX()+player.getSprite().getWidth()/2;
			float spriteY = player.getSprite().getY()+player.getSprite().getHeight()/2;
		
			Vector3 mouse = camera.getCamera().unproject(new Vector3(screenX, screenY, 0));
		
		    float rotation = (float)MathUtils.radiansToDegrees * MathUtils.atan2(mouse.y - spriteY, mouse.x - spriteX);
		    if (rotation < 0) rotation += 360;
			player.getSprite().setRotation(rotation);
			player.getSpray().update(rotation, spriteX-player.getSprite().getWidth()/2, spriteY-player.getSprite().getHeight()/2, npcs);
		    player.getSpray().setRotation(rotation-90f);
			    
			player.updateRotation(rotation * (float)(Math.PI/180) );
		    
			rotation = rotation - 90f;
			//player.updateSpray(rotation * (float)(Math.PI/180));

		}
	}
	
	public void setPaused(Boolean isPaused) {
		this.isPaused = isPaused;
	}


	public void togglePaused() {
		if(isPaused) {
			isPaused = false;
		}
		else {
			isPaused = true;
		}		
	}
	
	public Boolean getPaused() {
		return this.isPaused;
	}
	
	public Boolean getPressed() {
		return mousePressed;
	}

}

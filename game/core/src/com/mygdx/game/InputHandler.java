package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class InputHandler extends PlagueDoctor implements InputProcessor { 
	
	private float runningSpeed = 5f;
	private float border = 20f;
	
	
	private float mouseCursorWidth = 0f;
	private float mouseCursorHeight = 0f;
	
	public InputHandler(float width, float height) {
		this.mouseCursorWidth = width;
		this.mouseCursorHeight = height;
	}
	
	public void doctorMovement(Sprite sprite, TextureRegion walkingAnimation) {
		if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			sprite.translateX(-runningSpeed);
			Gdx.input.setCursorPosition((int) (Gdx.input.getX() - runningSpeed), Gdx.input.getY());
			sprite.setRegion(walkingAnimation);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			sprite.translateX(runningSpeed);
			Gdx.input.setCursorPosition((int) (Gdx.input.getX() + runningSpeed), Gdx.input.getY());
			sprite.setRegion(walkingAnimation);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
			sprite.translateY(runningSpeed);
			Gdx.input.setCursorPosition(Gdx.input.getX(), (int) (Gdx.input.getY()-runningSpeed));
			sprite.setRegion(walkingAnimation);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			sprite.translateY(-runningSpeed);
			Gdx.input.setCursorPosition(Gdx.input.getX(), (int) (Gdx.input.getY()+runningSpeed));
			sprite.setRegion(walkingAnimation);
		}
		
		else if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
		
			/* Test this below however you will see this wouldn't work as it
			 * is using the UI Coordinates not the Cartesian Coordinates used by
			 * Box2d. 
			 * 
			 * sprite.setPosition(Gdx.input.getX(), Gdx.input.getY());*/
		
			  sprite.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
		}
	}
	
	
	public void setRotations(Sprite sprite) {
		float spriteX = sprite.getX();
		float spriteY = sprite.getY();
		
		float mouseX = (float) Gdx.input.getX();
		float mouseY = (float) Gdx.graphics.getHeight() - Gdx.input.getY();
		
		float characterRotations = 0f;
		float lengthX = 0f;
		float lengthY = 0f;
		float angle = 0f;
		
		if(mouseX > spriteX && mouseY > spriteY) {
			characterRotations = 270f;
			lengthX = mouseX - spriteX;
			lengthY = mouseY - spriteY;
			angle = (float) (Math.atan(lengthY/lengthX)/(2* Math.PI) * 360);
			sprite.setRotation(characterRotations+angle);
		}
		else if(mouseX < spriteX && mouseY > spriteY) {
			lengthX = spriteX - mouseX;
			lengthY = mouseY - spriteY;
			angle = (float) (Math.atan(lengthY/lengthX)/(2* Math.PI) * 360);
			angle = 90 - angle;
			sprite.setRotation(angle);
		}
		else if(mouseX < spriteX && mouseY < spriteY) {
			characterRotations = characterRotations + 90f;
			lengthX = spriteX - mouseX;
			lengthY = spriteY - mouseY;
			angle = (float) (Math.atan(lengthY/lengthX)/(2* Math.PI) * 360);
			sprite.setRotation(characterRotations + angle);
		}
		else if(mouseX > spriteX && mouseY < spriteY) {
			characterRotations = characterRotations + 180f;
			lengthX = mouseX - spriteX;
			lengthY = spriteY - mouseY;
			angle = 90 - (float) (Math.atan(lengthY/lengthX)/(2* Math.PI) * 360);
			sprite.setRotation(characterRotations + angle);
		}
	}
	
	
	public void mouseHandler(float mouseCursorWidth2, float mouseCursorHeight2) {
		float x = Gdx.input.getX();
		float y = Gdx.input.getY();
		
		if(x < border) {
			Gdx.input.setCursorPosition((int) border, Gdx.input.getY());
		}
		else if(x > Gdx.graphics.getWidth() - border-mouseCursorWidth2) {
			Gdx.input.setCursorPosition((int) (Gdx.graphics.getWidth()-border-mouseCursorWidth2), Gdx.input.getY());
		}
		
		if(y < border) {
			Gdx.input.setCursorPosition(Gdx.input.getX(), (int) border);
		}
		
		else if(y > Gdx.graphics.getHeight() - border-mouseCursorWidth2) {
			Gdx.input.setCursorPosition(Gdx.input.getX(), (int) (Gdx.graphics.getHeight()-border-mouseCursorWidth2));
		}
	}


	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		System.out.println("X: " + screenX + " | " + "Y: " + screenY);
		mouseHandler(mouseCursorWidth, mouseCursorHeight);
		return true;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	

}

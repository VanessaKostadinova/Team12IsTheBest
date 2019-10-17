package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class InsideHouseScreen implements Screen {
	
	MainScreen screen;
	Texture img;
	Sprite sprite;
	
	public InsideHouseScreen(MainScreen mainScreen) {
		this.screen = mainScreen;
		img = new Texture((Gdx.files.internal("main_menu_assets/mask.png")));
		sprite = new Sprite();
		sprite.setTexture(img);
		sprite.setPosition(0, 0);
		//sprite.scale(0.1f);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		screen.batch.begin();
		//screen.batch.draw(head.getTexture(), 0, 0);
		screen.batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getWidth()/2, sprite.getHeight()/2, sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
		screen.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		img.dispose();		
	}

}

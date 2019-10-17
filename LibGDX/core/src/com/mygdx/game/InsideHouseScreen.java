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
		sprite = new Sprite(img);
		//sprite.setBounds(0, 0, img.getWidth(), img.getHeight());
		//sprite.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		sprite.setScale(0.5f);
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
		//sprite.draw(screen.batch);
		screen.batch.draw(sprite, 0, 0, 0, 0, sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
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

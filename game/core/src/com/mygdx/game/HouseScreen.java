package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HouseScreen implements Screen {

	MainScreen screen;
	Table t;
	Skin skin;
	
	public HouseScreen(MainScreen mainScreen) {
		this.screen = mainScreen;
		this.t = new Table();
		this.t.setFillParent(true);
		screen.ui.addActor(t);
		t.setDebug(true);
		
		this.skin = new Skin();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		screen.ui.act(Gdx.graphics.getDeltaTime());
		screen.ui.draw();
	}

	@Override
	public void resize(int width, int height) {
		screen.ui.getViewport().update(width, height, true);

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
		screen.ui.clear();
	}

}
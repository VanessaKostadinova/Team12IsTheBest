package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class MainMenu implements Screen {

	Main main;
	public MainMenu(Main main) {
		this.main = main;
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}
	
	public void playGame() {
		main.setScreen(new MapScreen(main));
	}

}

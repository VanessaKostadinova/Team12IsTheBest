package com.mygdx.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Main;
import com.mygdx.game.MainMenu;
import com.mygdx.game.MapScreen;

public class LoadingScreen implements Screen {
	
	private Main main;
	private Sprite loading;
	
	public LoadingScreen(Main main) {
		this.main = main;
		loading = new Sprite(new Texture(Gdx.files.internal("loading/loading.png")));
		loading.setPosition(Gdx.graphics.getWidth()/2-loading.getWidth()/2, Gdx.graphics.getHeight()/2-loading.getHeight()/2);
		main.assets.load();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		main.batch.begin();
			loading.draw(main.batch);
		main.batch.end();
		
		if (main.assets.manager.update()) {
			main.setScreen(new MapScreen(main));
		}
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

}

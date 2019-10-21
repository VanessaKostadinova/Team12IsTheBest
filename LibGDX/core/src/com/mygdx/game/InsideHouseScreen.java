package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class InsideHouseScreen implements Screen {
	
	MainScreen screen;
	Texture img;
	Level testLevel;
	
	public InsideHouseScreen(MainScreen mainScreen) {
		this.screen = mainScreen;
		//img = new Texture("Wooden_Floor.png");
		testLevel = new Level(25,25,"test.csv");
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		screen.batch.begin();
		renderMap();
		//screen.batch.draw(img, 0, 0);
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
	
	//renders map
	private void renderMap() {
		int[][] workingArray = new int[25][25];
		workingArray = testLevel.getLevel();
		int yCoord = 0;
		
		for(int[] i : workingArray) {
			int xCoord = 0;
			
			for(int r : i) {
				screen.batch.draw(testLevel.getTexture(r), xCoord, yCoord);
				xCoord += 32;
			}
			yCoord += 32;
		}
		
	}


}

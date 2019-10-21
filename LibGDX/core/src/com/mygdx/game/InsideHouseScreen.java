package com.mygdx.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InsideHouseScreen implements Screen {

	MainScreen screen;
	Texture img;

	String fileLocation;
	BufferedReader reader;
	String line;
	final String splitter = ",";

	Texture floor;
	Texture wall;

	public InsideHouseScreen(MainScreen mainScreen) {
		this.screen = mainScreen;
		img = new Texture("badlogic.jpg");
		fileLocation = "C:\\Users\\vanes\\Desktop\\test.csv";
		screen.batch = new SpriteBatch();
		floor = new Texture("Wooden_Floor.gif");
		wall = new Texture("Floor_Stone.gif");
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
		csvParse();
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

	private void csvParse() {
		try {
			//load reader
			reader = new BufferedReader(new FileReader(fileLocation));
			//stores the texture's y coordinate
			int textureY = 0;
			//check if there is next line
			while((line = reader.readLine()) != null) {
				//creates an array with every element in the current line
				String[] currentLine = line.split(splitter);
				//stores the x coord of the texture
				int textureX = 0;
				//for every element in the current line
				for(String i:currentLine) {
					//draw a wall if the csv reads 1
					if(i.equals("1")) {
						screen.batch.draw(wall, textureX, textureY);
					}

					//draw floor if the csv reads 0
					if(i.equals("0")) {
						screen.batch.draw(floor, textureX, textureY);
					}
					//increment X
					textureX +=32;
				}
				//increment Y
				textureY += 32;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

}

package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainScreen extends Game  {

    public SpriteBatch batch;
    public Stage ui;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		ui = new Stage();
		setScreen(new InsideHouseScreen(this));
	}
		
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
        batch.dispose();
	}
	

}

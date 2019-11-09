package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * The Class MainScreen, is the main game window and entry point for the game.
 * @author Team 12
 */
public class MainScreen extends Game  {

    /** The sprite batch. */
    public SpriteBatch batch;
    
    /** The stage for the ui. */
    public Stage ui;
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		ui = new Stage();
		setScreen(new HouseScreen(this));
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Game#render()
	 */
	@Override
	public void render() {
		super.render();
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Game#dispose()
	 */
	@Override
	public void dispose() {
        batch.dispose();
	}
	

}

package com.mygdx.game;

import java.io.Serializable;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.assets.AssetHandler;
import com.mygdx.camera.Camera;

public class Main extends Game implements Serializable {
	
    /** The sprite batch. */
    public SpriteBatch batch;
    
    /** The stage for the ui. */
    public Stage ui;
    
    public AssetHandler assets;
    
    private Camera camera;

	@Override
	public void create() {
		batch = new SpriteBatch();
		
		camera = new Camera(2160f, 1080f, 1920f);
		camera.getCamera().position.set(
				camera.getCamera().viewportWidth / 2f , 
				camera.getCamera().viewportHeight / 2f, 0);
		ui = new Stage(camera.getViewport());

		
		assets = new AssetHandler();

		
		assets.load();
		assets.manager.finishLoading();
		setScreen(new MapScreen(this));		
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

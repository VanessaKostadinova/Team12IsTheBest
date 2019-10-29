package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.camera.Camera;
import com.mygdx.character.Player;

public class InsideHouseScreen implements Screen {
	
	MainScreen screen;
	Texture img;
	Level testLevel;
	
	private Camera camera;
	private int viewWidth;
	private Player player;
	private InputHandler handler;

	
	public InsideHouseScreen(MainScreen mainScreen) {
		this.screen = mainScreen;
		//img = new Texture("Wooden_Floor.png");
		testLevel = new Level("testLevel.txt");
		
		/**
		 * To Vanessa:
		 * 
		 * Added some basic implementation of the camera
		 * The constructor of the camera is ViewPointWidth and ViewPointHeight currently set it at 256x(256*scale)
		 * (If you set it at 32 you should only see a single tile)
		 * 
		 * Scale is there to make sure that all tiles appear as squares no matter the resolution
		 *    | 
		 * 	  L> The w and h are initialised before as they are needed to be float otherwise
		 * 		  the camera will not be created correctly. (We will get a red background).
		 * 		  Gdx.graphics.getWidth()/.getHeight() are both integers.
		 * 
		 * The Camera also makes it so that (0,0) of the TileMap is at the centre 
		 * of the screen, not at the bottom left. So i took the viewport of the camera
		 * and half both width and height to to make TileMap start at (0,0)
		 * 
		 * Updating the Camera is vital, none of the changes will show otherwise.
		 * 
		 */
		
		this.viewWidth = 256;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new Camera(viewWidth, h, w);
		
		//Used to centre the TileMap, to the bottom left.
		camera.getCamera().position.set(camera.getCamera().viewportWidth / 2f , camera.getCamera().viewportHeight / 2f, 0);

		player = new Player();
		player.setSpritePosition(camera.getViewport().getWorldWidth()/2-player.getSprite().getWidth()/2, camera.getViewport().getWorldHeight()/2-player.getSprite().getHeight()/2);
		
		handler = new InputHandler(player, camera, testLevel.getLevel());
		Gdx.input.setInputProcessor(handler);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Allows player to move.
		handler.movement();

	    screen.batch.setProjectionMatrix(camera.getCamera().combined);
		screen.batch.begin();
		renderMap();
		player.getSprite().draw(screen.batch);
		screen.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		/**
		 * To Vanessa:
		 * 
		 * Similar to what we have done above, however this just resizes the viewport
		 * when the window is resized. Then of course updates, the camera.
		 * 
		 */
		camera.getViewport().update(width, height);
		camera.getCamera().viewportHeight = viewWidth*((float)height/(float) width);
		camera.getViewport().apply();
		camera.updateCamera();
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
	
	/**
	 * To Inder:
	 * This doesn't reopen the csv every time it's called.
	 */
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

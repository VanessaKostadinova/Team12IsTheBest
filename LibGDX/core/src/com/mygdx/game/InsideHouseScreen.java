package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class InsideHouseScreen implements Screen {
	
	MainScreen screen;
	Texture img;
	Level testLevel;
	
	private OrthographicCamera camera;
	private int viewWidth;
	
	public InsideHouseScreen(MainScreen mainScreen) {
		this.screen = mainScreen;
		//img = new Texture("Wooden_Floor.png");
		testLevel = new Level(25,25,"test.csv");
		
		/**
		 * To Vanessa:
		 * 
		 * Added some basic implementation of the camera
		 * The constructor of the camera is ViewPointWidth and ViewPointHeight currently set it at 256x(256*scale)
		 * (If you set it at 32x32 you should only see a single tile)
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
		 * Have a look at this, since this is just the starting point for now.
		 * 
		 */
		
		this.viewWidth = 256;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(viewWidth,viewWidth*(h/w) );
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
		renderMap();
		//screen.batch.draw(img, 0, 0);
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
		camera.viewportHeight = viewWidth*((float)height/(float) width);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
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

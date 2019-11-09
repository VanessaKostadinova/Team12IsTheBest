package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.camera.Camera;
import com.mygdx.character.NPC;
import com.mygdx.character.Player;

public class InsideHouseScreen implements Screen {

	MainScreen screen;
	Level testLevel;

	private Camera camera;
	private int viewWidth;
	private Player player;
	private InputHandler handler;
	private InputMultiplexer input;
	private Window pause;
	private Skin skin;
	private float stateTime;
	

	public InsideHouseScreen(MainScreen mainScreen) {
		this.screen = mainScreen;
		//img = new Texture("Wooden_Floor.png");
		testLevel = new Level("testLevel.txt");
		stateTime = 0f;
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
		skin = new Skin(Gdx.files.internal("skin/terra-mother-ui.json"));
		pauseGame();
		handler = new InputHandler(player, camera, testLevel.getLevel(), pause, testLevel.getAllNPCS());

		/*
		 * This allows us to use two inputhandlers, in this case:
		 * One is for the UI
		 * One is for the Movement.
		 */
		input = new InputMultiplexer();
		input.addProcessor(handler);
		input.addProcessor(screen.ui);
        Gdx.input.setInputProcessor(input);

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
		handler.movement(player.getAnimation().getKeyFrame(stateTime, true), delta);
		player.getSpray().setTextureRegion(player.getSpray().getAnimation().getKeyFrame(stateTime, true));
		stateTime = stateTime + delta;
		handler.sprayWithVillagerCollision(testLevel.getAllNPCS());

	    screen.batch.setProjectionMatrix(camera.getCamera().combined);
		screen.batch.begin();
		renderMap();
		drawNPCs();
		handler.spray();
		player.getSpray().Sprite().draw(screen.batch);
		player.getSprite().draw(screen.batch);
		screen.batch.end();

		//Draws the UI parts of house.
		screen.ui.draw();

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
		screen.ui.getViewport().update(width, height);
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

	private void drawNPCs() {
		for(int i = 0; i < testLevel.getNumNPCs(); i++) {
			testLevel.getNPC(i).getSprite().draw(screen.batch);
		}
	}

    public void pauseGame() {
    	
    	/*
    	 * This method is used to create the window elements for the pause menu
    	 *
    	 * Creates a window and then has two different text buttons within
    	 *
    	 *  - RESUME (hides window when clicked and allows movement)
    	 *  - EXIT (exits the game)
    	 *
    	 * This is done through add listeners to each of the the buttons
    	 *
    	 * The window containing all the values is called pause
    	 */
    	
        float windowWidth = 200, windowHeight = 200;
        pause = new Window("", skin);
        pause.setMovable(false); //So the user can't move the window
        final TextButton button1 = new TextButton("Resume", skin);
        button1.getLabel().setFontScale(windowHeight/200, windowHeight/200 );
        button1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handler.togglePaused();
                pause.setVisible(false);
            }
        });
        TextButton button2 = new TextButton("Exit", skin);
        button2.getLabel().setFontScale(windowHeight/200, windowHeight/200 );

        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        pause.add(button1).row();
        pause.row();
        pause.add(button2).row();
        pause.pack(); //Important! Correctly scales the window after adding new elements

        //Centre window on screen.
        pause.setBounds((Gdx.graphics.getWidth() - windowWidth  ) / 2,
        (Gdx.graphics.getHeight() - windowHeight) / 2, windowWidth  , windowHeight );
        //Sets the menu as invisible
        pause.setVisible(false);
        //Adds it to the UI Screen.
        screen.ui.addActor(pause);
    }

}

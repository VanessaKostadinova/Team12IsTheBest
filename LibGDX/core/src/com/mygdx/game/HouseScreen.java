package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

/**
 * The Class HouseScreen.
 */
public class HouseScreen implements Screen {

	/** The main screen. */
	MainScreen screen;
	
	/** The table for the ui. */
	Table t;
	
	/** The ui skin. */
	Skin skin;
	
	/** The window pause. */
	Window pause;
	
	/** If pause menu is active. */
	Boolean isPaused;
	
	/**
	 * Instantiates a new house screen.
	 *
	 * @param mainScreen the main screen
	 */
	public HouseScreen(MainScreen mainScreen) {
		
		float width = Gdx.graphics.getWidth();
		float scaleItem = width/1920;
		
		
		this.isPaused = false;
		this.screen = mainScreen;
		this.skin = new Skin(Gdx.files.internal("skin/terra-mother-ui.json"));
		this.t = new Table();
		this.t.setFillParent(true);
		
		/*
		 * Sets up the shop, and the background of the shop.
		 */
		Image mask = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("houses/background.png")))));
		mask.setWidth(Gdx.graphics.getWidth());
		mask.setHeight(Gdx.graphics.getHeight());
		mask.setWidth(Gdx.graphics.getWidth());
		mask.setHeight(Gdx.graphics.getHeight());
		t.addActor(mask);
		
		
		setHouse(scaleItem, Gdx.graphics.getWidth()/2 - 245/2- 100f*scaleItem, Gdx.graphics.getHeight()/2 - 344/2);

		/*
		 * Sets up the shop, and the interaction with the shop.
		 */
		final Image shop = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("houses/SHOP.png")))));
		shop.setScaling(Scaling.fit);
		shop.setSize(shop.getWidth()*scaleItem, shop.getHeight()*scaleItem);
		shop.setPosition(Gdx.graphics.getWidth()/2 - 245/2 + shop.getWidth(), Gdx.graphics.getHeight()/2 - 344/2);
		shop.addListener(new ClickListener(){
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		    	if(!isPaused) {
		    		dispose();
		    		screen.setScreen(new ShopScreen(screen));
		    	}
		    }
		    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		    	if(!isPaused) {
		    		TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("houses/SHOPMOUSE.png"))));
			    	shop.setDrawable(t);
		    	}
		    }
		    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
			    if(!isPaused) {
			    	TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("houses/SHOP.png"))));
			    	shop.setDrawable(t);
			    }
		    }
		});
		
		t.addActor(shop);
		
		
		pauseGame();
		t.addActor(pause);
		Gdx.input.setInputProcessor(screen.ui);

		screen.ui.addActor(t);

	}
	
	/**
	 * Sets the house.
	 *
	 * @param scaleItem the scale item to resolution
	 * @param x the x coordinate of the house
	 * @param y the y coordinate of the house
	 */
	public void setHouse(float scaleItem, float x, float y) {
		/*
		 * Adds the houses onto the screen as well as adding the ability for each to clicked independantly.
		 */
		final Image house = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("houses/HOUSE.png")))));
		house.setScaling(Scaling.fit);
		house.setSize(house.getWidth()*scaleItem, house.getHeight()*scaleItem);
		house.setPosition(x, y);
		house.addListener(new ClickListener(){
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		    	if(!isPaused) {
			    	dispose();
			    	screen.setScreen(new InsideHouseScreen(screen));
		    	}
		    }
		    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		    	if(!isPaused) {
		    		TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("houses/HOUSEMOUSE.png"))));
			    	house.setDrawable(t);
		    	}
		    }
		    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
			    if(!isPaused) {
			    	TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("houses/HOUSE.png"))));
			    	house.setDrawable(t);
			    }
		    }
		});
		t.addActor(house);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		screen.ui.act(Gdx.graphics.getDeltaTime());
		screen.ui.draw();
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if(pause.isVisible()) {
				pause.setVisible(false);
				togglePaused();
			} else {
				pause.setVisible(true);
				togglePaused();
			}
		}
	}
	
	
	/**
	 * Toggle isPaused variable.
	 */
	public void togglePaused() {
		if(isPaused) {
			isPaused = false;
		}
		else {
			isPaused = true;
		}
	}
	
	
    /**
     * Pause game.
     * @see InsideHouseScreen
     */
    public void pauseGame() {
        float windowWidth = 200, windowHeight = 200;
        pause = new Window("", skin);
        pause.setMovable(false); //So the user can't move the window
        final TextButton button1 = new TextButton("Resume", skin);
        button1.getLabel().setFontScale(windowHeight/200, windowHeight/200 );
        button1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause.setVisible(false);
                togglePaused();
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

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		screen.ui.getViewport().update(width, height, true);

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		screen.ui.clear();
	}

}
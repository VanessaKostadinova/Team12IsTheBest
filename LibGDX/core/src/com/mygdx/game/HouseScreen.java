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

public class HouseScreen implements Screen {

	MainScreen screen;
	Table t;
	Skin skin;
	Window pause;
	
	Boolean isPaused;
	
	public HouseScreen(MainScreen mainScreen) {
		
		float width = Gdx.graphics.getWidth();
		float scaleItem = width/1920;
		
		
		this.isPaused = false;
		this.screen = mainScreen;
		this.skin = new Skin(Gdx.files.internal("skin/terra-mother-ui.json"));
		this.t = new Table();
		this.t.setFillParent(true);
		Image mask = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("houses/background.png")))));
		mask.setWidth(Gdx.graphics.getWidth());
		mask.setHeight(Gdx.graphics.getHeight());
		mask.setWidth(Gdx.graphics.getWidth());
		mask.setHeight(Gdx.graphics.getHeight());
		t.addActor(mask);
		
		final Image house = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("houses/HOUSE.png")))));
		house.setScaling(Scaling.fit);
		house.setSize(house.getWidth()*scaleItem, house.getHeight()*scaleItem);
		house.setPosition(Gdx.graphics.getWidth()/2 - house.getWidth()/2- 100f*scaleItem, Gdx.graphics.getHeight()/2 - house.getHeight()/2);
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
		
		final Image shop = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("houses/SHOP.png")))));
		shop.setScaling(Scaling.fit);
		shop.setSize(shop.getWidth()*scaleItem, shop.getHeight()*scaleItem);
		shop.setPosition(Gdx.graphics.getWidth()/2 - house.getWidth()/2 + shop.getWidth(), Gdx.graphics.getHeight()/2 - house.getHeight()/2);
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

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

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
	
	
	public void togglePaused() {
		if(isPaused) {
			isPaused = false;
		}
		else {
			isPaused = true;
		}
	}
	
	
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

	@Override
	public void resize(int width, int height) {
		screen.ui.getViewport().update(width, height, true);

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
		screen.ui.clear();
	}

}
package com.mygdx.game;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.camera.Camera;
import com.mygdx.shop.Shop;

public class ShopScreen implements Screen {

	
	private boolean isPaused = false;
	private Main main;
	private Shop shop;
	private Camera cameraUI;
	
	private Table t = new Table();
	private float scaleItem;
	private Boolean isClicked;
	private int XArray = -1;
	private int YArray = -1;
	private Image[][] item;
	
	/** The pause window. */
	private Window pause;
	
	/** The ui skin. */
	private Skin skin;
	
	public ShopScreen(final Main main, Shop shop, final MapScreen mapScreen) {
		System.out.println(main.ui.getViewport().getScreenWidth());
		this.main = main;
		this.shop = shop;
		this.skin = new Skin(Gdx.files.internal("skin/terra-mother-ui.json"));

		this.t = new Table();
		this.t.setFillParent(true);
		
		scaleItem = 1080f/(float)Gdx.graphics.getWidth();
		if(scaleItem < 1) {
			scaleItem = 1;
		}
		
		final Image Title = new Image(new TextureRegionDrawable(new TextureRegion(main.assets.manager.get("shop/screen/SHOP.png", Texture.class))));
		Title.setScaling(Scaling.fit);
		Title.setPosition(50f, main.ui.getHeight()- Title.getHeight() - 50f);
		Title.setSize(Title.getWidth(), Title.getHeight());
		t.addActor(Title);
		
		isClicked = false;
		item = new Image[5][5];

		createItemSlots();

		final Image Leave = new Image(new TextureRegionDrawable(new TextureRegion(new TextureRegion(main.assets.manager.get("shop/screen/LEAVE.png", Texture.class)))));
		Leave.setScaling(Scaling.fit);
		Leave.setPosition((main.ui.getWidth())-40f-Leave.getWidth(),40f);
		Leave.setSize(Leave.getWidth(), Leave.getHeight());
		Leave.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					dispose();
					main.ui.clear();
					mapScreen.pauseGame();
					main.setScreen(mapScreen);
				}
		    }
		    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if(!isPaused) {
					TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(main.assets.manager.get("shop/screen/LEAVEMOUSE.png", Texture.class))));
					Leave.setDrawable(t);
				}
		    }
		    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if(!isPaused) {
					TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(main.assets.manager.get("shop/screen/LEAVE.png", Texture.class))));
					Leave.setDrawable(t);
				}
		    }
		});
		t.addActor(Leave);
		

		final Image Buy = new Image(new TextureRegionDrawable((new TextureRegion(main.assets.manager.get("shop/screen/BUY.png", Texture.class)))));
		Buy.setScaling(Scaling.fit);
		Buy.setPosition((main.ui.getWidth())-40f-Buy.getWidth(),40f*2f + Leave.getHeight());
		Buy.setSize(Buy.getWidth(), Buy.getHeight());
		Buy.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {

				}
		    }
		    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if(!isPaused) {
					TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(main.assets.manager.get("shop/screen/BUYMOUSE.png", Texture.class))));
					Buy.setDrawable(t);
				}
		    }
		    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if(!isPaused) {
					TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(main.assets.manager.get("shop/screen/BUY.png", Texture.class))));
					Buy.setDrawable(t);
				}
		    }
		});
		t.addActor(Buy);

		Gdx.input.setInputProcessor(main.ui);
		main.ui.addActor(t);
		pauseGame();
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		inputHandler();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		main.ui.act(delta);
		main.ui.draw();
	}
	
	public void inputHandler() {
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			togglePaused();
			pause.setVisible(isPaused);
		}
	}

	@Override
	public void resize(int width, int height) {
		main.ui.getViewport().update(width, height);
		main.ui.getViewport().apply();
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
		// TODO Auto-generated method stub
		
	}
	
	public void createItemSlots() {
		for(int y = 0; y < 5; y++) {
			for(int x = 0; x < 5; x++) {
				final int X = x;
				final int Y = y;
				
				
				final Image ItemSlot1 = new Image(new TextureRegionDrawable((new TextureRegion(main.assets.manager.get("shop/screen/ITEM.png", Texture.class)))));
				ItemSlot1.setScaling(Scaling.fit);
				ItemSlot1.setPosition(50f + ItemSlot1.getWidth()*x, 
						(main.ui.getHeight() - main.ui.getHeight()/9f) - ItemSlot1.getHeight() - 200f - ItemSlot1.getHeight()*y);
				ItemSlot1.setSize(ItemSlot1.getWidth(), ItemSlot1.getHeight());
				
				ItemSlot1.addListener(new ClickListener(){
					@Override
				    public void clicked(InputEvent event, float x, float y) {
						if(!isPaused) {
				    		TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(main.assets.manager.get("shop/screen/ITEMHOVER.png", Texture.class))));
				    		if(isClicked) {
					    		TextureRegionDrawable t2 = new TextureRegionDrawable((new TextureRegion(main.assets.manager.get("shop/screen/ITEM.png", Texture.class))));
				    			item[YArray][XArray].setDrawable(t2);
				    			YArray = Y;
				    			XArray = X;
				    		}
				    		else {
					    		isClicked = true;
				    			YArray = Y;
				    			XArray = X;
				    		}
				    		ItemSlot1.setDrawable(t);
						}
				    }
				    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
						if(!isPaused) {
							TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(main.assets.manager.get("shop/screen/ITEMHOVER.png", Texture.class))));
							ItemSlot1.setDrawable(t);
						}
				    }
				    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
						if(!isPaused) {
					    	if(!(X == XArray) || !(Y == YArray)) {
					    		TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(main.assets.manager.get("shop/screen/ITEM.png", Texture.class))));
				    			ItemSlot1.setDrawable(t);
					    	}
						}
				    }
				});
				item[Y][X] = ItemSlot1;
				t.addActor(ItemSlot1);
			}
		}
	}
	
	
    /**
     * Holds the window for the pause menu.
     */
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
    	System.out.println("");
    	System.out.println(scaleItem);
        float windowWidth = 200*scaleItem, windowHeight = 200*scaleItem;
        pause = new Window("", skin);
        pause.setMovable(false); //So the user can't move the window
        final TextButton button1 = new TextButton("Resume", skin);
        button1.getLabel().setFontScale((windowHeight/200)*scaleItem, (windowHeight/200)*scaleItem );
        button1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePaused();
                pause.setVisible(false);
            }
        });
        TextButton button2 = new TextButton("Exit", skin);
        button2.getLabel().setFontScale((windowHeight/200)*scaleItem, (windowHeight/200)*scaleItem );
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
        pause.setBounds(((main.ui.getWidth() - windowWidth*scaleItem  ) / 2),
        (main.ui.getHeight() - windowHeight*scaleItem) / 2, windowWidth  , windowHeight );
        //Sets the menu as invisible
        isPaused = false;
        pause.setVisible(false);
        
        pause.setSize(pause.getWidth()*scaleItem, pause.getHeight()*scaleItem);
        //Adds it to the UI Screen.
        main.ui.addActor(pause);
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
}
package com.mygdx.game;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.camera.Camera;
import com.mygdx.renderable.Player;
import com.mygdx.shop.Shop;
import com.mygdx.shop.Upgrade;

public class ShopScreen implements Screen {

	
	private boolean isPaused = false;
	private Main main;
	private Shop shop;
	
	private Table t = new Table();
	private float scaleItem;

	/** The pause window. */
	private Window pause;
	
	/** The ui skin. */
	private Skin skin;
	
	private List<Label> items;
	
	private LabelStyle unClicked;
	private LabelStyle clicked;
	
	private Label information;
	private Label description;
	private Label scaleFactor;
	private Label sf;
	private Label titleLevel;
	private Label level;
	private Label titleCost;
	private Label cost;
	
	private Label playerGold;
	
	
	private Image Leave;
	private Image Buy;
	
	private Upgrade clickedShop;
	private float alphaUI = 0f;


	
	public ShopScreen(final Main main, Shop shop, final MapScreen mapScreen) {
		this.main = main;
		this.shop = shop;
		
		clickedShop = null;
		
		unClicked = createLabelStyleWithBackground(Color.WHITE);
		clicked = createLabelStyleWithBackground(Color.CYAN);
		
		this.skin = new Skin(Gdx.files.internal("skin/terra-mother-ui.json"));
		this.items = new ArrayList<>();
		this.t = new Table();
		this.t.setFillParent(true);
		
		scaleItem = 1080f/(float)Gdx.graphics.getWidth();
		if(scaleItem < 1) {
			scaleItem = 1;
		}
		
		setCatagories();
		
		final Image Title = new Image(new TextureRegionDrawable(new TextureRegion(main.assets.manager.get("shop/screen/SHOP.png", Texture.class))));
		Title.setScaling(Scaling.fit);
		Title.setPosition(50f, main.ui.getHeight()- Title.getHeight() - 50f);
		Title.setSize(Title.getWidth(), Title.getHeight());
		t.addActor(Title);
		
		playerGold = new Label("PLAYER FOOD: " + readPlayer().getFood(), unClicked);
		playerGold .setPosition(50f, Title.getY() - 100f);
		main.ui.addActor(playerGold);

		Leave = new Image(new TextureRegionDrawable(new TextureRegion(new TextureRegion(main.assets.manager.get("shop/screen/LEAVE.png", Texture.class)))));
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
		

		Buy = new Image(new TextureRegionDrawable((new TextureRegion(main.assets.manager.get("shop/screen/BUY.png", Texture.class)))));
		Buy.setScaling(Scaling.fit);
		Buy.setPosition((main.ui.getWidth())-40f-Buy.getWidth(),40f*2f + Leave.getHeight());
		Buy.setSize(Buy.getWidth(), Buy.getHeight());
		Buy.setVisible(false);
		Buy.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					Player p = readPlayer();
					if(clickedShop.getName().equals("MASK ABILITY") && p.getFood() >= clickedShop.getCost()) {
						p.updateMaskDurationSeconds(clickedShop.getIncreasingValue());
						p.setFood(p.getFood()-clickedShop.getCost());
						p.writeToPlayerFile();
						clickedShop.update();
					}
					if(clickedShop.getName().equals("MOVEMENT SPEED") && p.getFood() >= clickedShop.getCost()) {
						p.updateSpeed(clickedShop.getIncreasingValue());
						p.setFood(p.getFood()-clickedShop.getCost());
						p.writeToPlayerFile();
						clickedShop.update();
					}
					if(clickedShop.getName().equals("FLAME STRENGTH") && p.getFood() >= clickedShop.getCost()) {
						p.updateSprays(1, -clickedShop.getIncreasingValue());
						p.setFood(p.getFood()-clickedShop.getCost());
						p.writeToPlayerFile();
						clickedShop.update();
					}
					if(clickedShop.getName().equals("CURE STRENGTH") && p.getFood() >= clickedShop.getCost()) {
						p.updateSprays(0, clickedShop.getIncreasingValue());
						p.setFood(p.getFood()-clickedShop.getCost());
						p.writeToPlayerFile();
						clickedShop.update();
					}
					setLabels(clickedShop);
					playerGold.setText("PLAYER FOOD: " + readPlayer().getFood());
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
		
		information = new Label("DESCRIPTION:", unClicked);
		information.setPosition((main.ui.getWidth())-40f-Leave.getWidth(), Title.getY());
		information.setVisible(false);
		main.ui.addActor(information);
		
		description = new Label("VOID", unClicked);
		description.setFontScale(0.5f);
		description.setPosition((main.ui.getWidth())-40f-Leave.getWidth(), information.getY() - 60f);
		description.setVisible(false);
		main.ui.addActor(description);
		
		sf = new Label("SCALE FACTOR:", unClicked);
		sf.setPosition((main.ui.getWidth())-40f-Leave.getWidth(), description.getY() - 100f);
		sf.setVisible(false);
		main.ui.addActor(sf);
		
		scaleFactor= new Label("VOID", unClicked);
		scaleFactor.setFontScale(0.5f);
		scaleFactor.setPosition((main.ui.getWidth())-40f-Leave.getWidth(), sf.getY() - 60f);
		scaleFactor.setVisible(false);
		main.ui.addActor(scaleFactor);
		
		titleCost = new Label("COST OF ITEM:", unClicked);
		titleCost .setPosition((main.ui.getWidth())-40f-Leave.getWidth(), scaleFactor.getY() - 100f);
		titleCost .setVisible(false);
		main.ui.addActor(titleCost);
		
		cost= new Label("VOID", unClicked);
		cost.setFontScale(0.5f);
		cost.setPosition((main.ui.getWidth())-40f-Leave.getWidth(), titleCost.getY() - 60f);
		cost.setVisible(false);
		main.ui.addActor(cost);
		
		titleLevel = new Label("LEVEL OF ITEM:", unClicked);
		titleLevel .setPosition((main.ui.getWidth())-40f-Leave.getWidth(), cost.getY() - 100f);
		titleLevel .setVisible(false);
		main.ui.addActor(titleLevel);
		
		level= new Label("VOID", unClicked);
		level.setFontScale(0.5f);
		level.setPosition((main.ui.getWidth())-40f-Leave.getWidth(), titleLevel.getY() - 60f);
		level.setVisible(false);
		main.ui.addActor(level);
		

		

		Gdx.input.setInputProcessor(main.ui);
		main.ui.addActor(t);
		pauseGame();
	}
	
	public Player readPlayer() {
		FileHandle handle = Gdx.files.local("data/player.txt");
		String[] values= handle.readString().split(",");
		Player p = new Player(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6]), Float.parseFloat(values[7]));
		return p;
	}
	
	
	public void setCatagories() {
		float spacing = 80f;
		
		final Label movement = new Label("MOVEMENT SPEED", unClicked);
		movement.setPosition(50, main.ui.getHeight() - 400);
		movement.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					if(movement.getStyle().equals(unClicked)) {
						resetLabel();
						movement.setStyle(clicked);
						setLabels(shop.getUpgrade(0));
					}
					else {
						movement.setStyle(unClicked);
						updateUI(false);
					}
				}
		    }
		});
		items.add(movement);
		
		final Label maskAbility = new Label("MASK ABILITY", unClicked);
		maskAbility.setPosition(50, items.get(items.size()-1).getY() - spacing);
		maskAbility.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					if(maskAbility.getStyle().equals(unClicked)) {
						resetLabel();
						maskAbility.setStyle(clicked);
						setLabels(shop.getUpgrade(1));
					}
					else {
						maskAbility.setStyle(unClicked);
						updateUI(false);
					}
				}
		    }
		});
		items.add(maskAbility);

		final Label flameStrength = new Label("FLAME STRENGTH", unClicked);
		flameStrength.setPosition(50, items.get(items.size()-1).getY() - spacing);
		flameStrength.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					if(flameStrength.getStyle().equals(unClicked)) {
						resetLabel();
						flameStrength.setStyle(clicked);
						setLabels(shop.getUpgrade(2));
					}
					else {
						flameStrength.setStyle(unClicked);
						updateUI(false);
					}
				}
		    }
		});
		items.add(flameStrength);
		
		final Label cureStrength = new Label("CURE STRENGTH", unClicked);
		cureStrength.setPosition(50, items.get(items.size()-1).getY() - spacing);
		cureStrength.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					if(cureStrength.getStyle().equals(unClicked)) {
						resetLabel();
						cureStrength.setStyle(clicked);
						setLabels(shop.getUpgrade(3));
					}
					else {
						cureStrength.setStyle(unClicked);
						updateUI(false);
					}
				}
		    }
		});
		items.add(cureStrength);
		
		/*final Label flameAmount = new Label("FLAME AMOUNT", unClicked);
		flameAmount.setPosition(50, items.get(items.size()-1).getY() - spacing);
		flameAmount.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					if(flameAmount.getStyle().equals(unClicked)) {
						resetLabel();
						flameAmount.setStyle(clicked);
						setLabels(shop.getUpgrade(4));
					}
					else {
						flameAmount.setStyle(unClicked);
						updateUI(false);
					}
				}
		    }
		});
		items.add(flameAmount);
		
		final Label cureAmount = new Label("CURE AMOUNT", unClicked);
		cureAmount.setPosition(50, items.get(items.size()-1).getY() - spacing);
		cureAmount.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					if(cureAmount.getStyle().equals(unClicked)) {
						resetLabel();
						cureAmount.setStyle(clicked);
						setLabels(shop.getUpgrade(5));
					}
					else {
						cureAmount.setStyle(unClicked);
						updateUI(false);
					}
				}
		    }
		});
		items.add(cureAmount);*/
		
		
		for(Label label : items) {
			t.addActor(label);
		}

	
	}
	
	public void setLabels(Upgrade upgrade) {
		clickedShop = upgrade;
		description.setText(upgrade.getDescription());
		scaleFactor.setText("INCREASING BY " + upgrade.getIncreasingValue());
		cost.setText(upgrade.getCost()+" FOOD");
		level.setText("Lvl. " +upgrade.getLevel());
		updateUI(true);
	}
	
	
	public void resetLabel() {
		for(Label label : items) {
			label.setStyle(unClicked);
		}
		updateUI(false);
		clickedShop = null;
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

		float windowWidth = 200*scaleItem, windowHeight = 200*scaleItem;
		pause = new Window("", skin);
		pause.setMovable(false); //So the user can't move the window
		//final TextButton button1 = new TextButton("Resume", skin);
		final Label button1 = new Label("RESUME", createLabelStyleWithBackground(Color.WHITE));
		button1.setFontScale(24f/60f);
		button1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				togglePaused();
				pause.setVisible(false);
	        }
		});
		Label button2 = new Label("EXIT", createLabelStyleWithBackground(Color.WHITE));
		button2.setFontScale(24f/60f);
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
    
    private LabelStyle createLabelStyleWithBackground(Color color) {
    	///core/assets/font/Pixel.ttf
    	FileHandle fontFile = Gdx.files.internal("font/Pixel.ttf");
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 60;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        labelStyle.fontColor = color;
        return labelStyle;
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
	
    public void updateUI(Boolean hit) {
    	information.setVisible(hit);
    	description.setVisible(hit);
    	scaleFactor.setVisible(hit);
    	sf.setVisible(hit);
    	Buy.setVisible(hit);
    	titleCost.setVisible(hit);
    	cost.setVisible(hit);
    	titleLevel.setVisible(hit);
    	level.setVisible(hit);
    }
	
	

}
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.assets.AssetHandler;
import com.mygdx.extras.PermanetPlayer;
import com.mygdx.renderable.Player;
import com.mygdx.shop.Church;
import com.mygdx.shop.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Church screen so the player is able to buy more perishables.
 * Similar to the shop screen.
 * @see ShopScreen
 * @author Inder
 */

public class ChurchScreen implements Screen {

	/** Whether screen is paused. */
	private boolean isPaused = false;
	/** The main screen
	 * @see Main
	 */
	private Main main;
	/** Holds the information about the church */
	private Church church;
	/** Table to hold some of the UI Scene2D Components */
	private Table t;
	/** The change of scale in item from one scale to another */
	private float scaleItem;
	/** The pause window. */
	private Window pause;
	/** The ui skin. */
	private Skin skin;
	/** Holds the list of selectable labels/buttons you can choose from */
	private List<Label> items;
	/** The style of items which haven't been clicked */
	private LabelStyle unClicked;
	/** The style of items which have clicked */
	private LabelStyle clicked;
	/** Holds description title */
	private Label information;
	/** Holds description about selected perishable */
	private Label description;
	/** Holds scale factor title */
	private Label scaleFactor;
	/** Holds scale factor information about selected perishable */
	private Label sf;
	/** Holds the level title */
	private Label titleLevel;
	/** Holds the current level of the item */
	private Label level;
	/** Holds the title cost */
	private Label titleCost;
	/** Holds the cost of buying the perishable*/
	private Label cost;
	/** How much currency the player has. */
	private Label playerGold;
	
	
	private Image Leave;
	private Image Buy;
	
	private Item clickedShop;


	/**
	 * Initialise and set up the Church Screen.
	 * @param main The main screen.
	 * @param church The Church Class
	 * @param mapScreen The mapScreen, used to return to map. Also is a final variable.
	 */
	public ChurchScreen(final Main main, Church church, final MapScreen mapScreen) {
		this.main = main;
		this.church = church;
		this.t = new Table();
		clickedShop = null;
		
		unClicked = AssetHandler.FONT_SIZE_60_SUBTITLES_WHITE;
		clicked = AssetHandler.FONT_SIZE_60_SUBTITLES_CYAN;
		
		this.skin = AssetHandler.SKIN_UI;
		this.items = new ArrayList<>();
		this.t = new Table();
		this.t.setFillParent(true);
		
		scaleItem = 1080f/(float)Gdx.graphics.getWidth();
		if(scaleItem < 1) {
			scaleItem = 1;
		}
		
		setCatagories();
		
		final Image Title = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("shop/church/CHURCHLOGO.png"))));
		Title.setScaling(Scaling.fit);
		Title.setPosition(50f, main.ui.getHeight()- Title.getHeight() - 50f);
		Title.setSize(Title.getWidth(), Title.getHeight());
		t.addActor(Title);
		
		playerGold = new Label("PLAYER FOOD: " + Player.getInstance().getFood(), unClicked);
		playerGold .setPosition(50f, Title.getY() - 100f);
		main.ui.addActor(playerGold);

		Leave = new Image(new TextureRegionDrawable(new TextureRegion(new TextureRegion(AssetHandler.MANAGER.get("shop/screen/LEAVE.png", Texture.class)))));
		Leave.setScaling(Scaling.fit);
		Leave.setPosition((main.ui.getWidth())-40f-Leave.getWidth(),40f);
		Leave.setSize(Leave.getWidth(), Leave.getHeight());
		Leave.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					dispose();
					main.ui.clear();
					mapScreen.createUI();
					mapScreen.pauseGame();
					mapScreen.inventory();
					main.setScreen(mapScreen);
				}
		    }
		    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if(!isPaused) {
					TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(AssetHandler.MANAGER.get("shop/screen/LEAVEMOUSE.png", Texture.class))));
					Leave.setDrawable(t);
				}
		    }
		    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if(!isPaused) {
					TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(AssetHandler.MANAGER.get("shop/screen/LEAVE.png", Texture.class))));
					Leave.setDrawable(t);
				}
		    }
		});
		t.addActor(Leave);
		

		Buy = new Image(new TextureRegionDrawable((new TextureRegion(AssetHandler.MANAGER.get("shop/screen/BUY.png", Texture.class)))));
		Buy.setScaling(Scaling.fit);
		Buy.setPosition((main.ui.getWidth())-40f-Buy.getWidth(),40f*2f + Leave.getHeight());
		Buy.setSize(Buy.getWidth(), Buy.getHeight());
		Buy.setVisible(false);
		Buy.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				System.out.println(clickedShop.getName());
				if(clickedShop.getName().equals("MASKS") && Player.getInstance().getFood() >= clickedShop.getCost()) {
					Player.getInstance().setFood(Player.getInstance().getFood()-clickedShop.getCost());
					PermanetPlayer.getPermanentPlayerInstance().getItem(1).upgrade();
					setLabels(PermanetPlayer.getPermanentPlayerInstance().getItem(1));
				}
				else if(clickedShop.getName().equals("BOOTS") && Player.getInstance().getFood() >= clickedShop.getCost()) {
					Player.getInstance().setFood(Player.getInstance().getFood()-clickedShop.getCost());
					PermanetPlayer.getPermanentPlayerInstance().getItem(0).upgrade();
					setLabels(PermanetPlayer.getPermanentPlayerInstance().getItem(0));
				}
				else if(clickedShop.getName().equals("BURNING STRENGTH") && Player.getInstance().getFood() >= clickedShop.getCost()) {
					Player.getInstance().setFood(Player.getInstance().getFood()-clickedShop.getCost());
					PermanetPlayer.getPermanentPlayerInstance().getItem(3).upgrade();
					setLabels(PermanetPlayer.getPermanentPlayerInstance().getItem(3));
				}
				else if(clickedShop.getName().equals("HEALING STRENGTH") && Player.getInstance().getFood() >= clickedShop.getCost()) {
					Player.getInstance().setFood(Player.getInstance().getFood()-clickedShop.getCost());
					PermanetPlayer.getPermanentPlayerInstance().getItem(2).upgrade();
					setLabels(PermanetPlayer.getPermanentPlayerInstance().getItem(2));
				}
				playerGold.setText("PLAYER FOOD: " + Player.getInstance().getFood());

			}
		    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if(!isPaused) {
					TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(AssetHandler.MANAGER.get("shop/screen/BUYMOUSE.png", Texture.class))));
					Buy.setDrawable(t);
				}
		    }
		    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if(!isPaused) {
					TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(AssetHandler.MANAGER.get("shop/screen/BUY.png", Texture.class))));
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


	/**
	 * Set the selectable catagories which the player is able to choose from.
	 */
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
						setLabels(PermanetPlayer.getPermanentPlayerInstance().getItem(0));
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
						setLabels(PermanetPlayer.getPermanentPlayerInstance().getItem(1));
					}
					else {
						maskAbility.setStyle(unClicked);
						updateUI(false);
					}
				}
		    }
		});
		items.add(maskAbility);

		//TODO add mask eyes option N?A

		final Label flameStrength = new Label("FLAME STRENGTH", unClicked);
		flameStrength.setPosition(50, items.get(items.size()-1).getY() - spacing);
		flameStrength.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					if(flameStrength.getStyle().equals(unClicked)) {
						resetLabel();
						flameStrength.setStyle(clicked);
						setLabels(PermanetPlayer.getPermanentPlayerInstance().getItem(3));
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
						setLabels(PermanetPlayer.getPermanentPlayerInstance().getItem(2));
					}
					else {
						cureStrength.setStyle(unClicked);
						updateUI(false);
					}
				}
		    }
		});
		items.add(cureStrength);
		
		for(Label label : items) {
			t.addActor(label);
		}

	
	}

	/**
	 * Set the labels when a specific item is selected.
	 * @param item The item selected
	 */
	public void setLabels(Item item) {
		clickedShop = item;
		description.setText(item.getDescription());
		scaleFactor.setText("INCREASING BY " + item.getIncreasingValue());
		cost.setText(item.getCost()+" FOOD");
		level.setText("Lvl. " + item.getLevel());
		updateUI(true);
	}


	/**
	 * Reset the labels to show no information once the item isn't selected.
	 */
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


	/**
	 * Creation of the pause menu.
	 * @see Main
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

		float windowWidth = 200*scaleItem, windowHeight = 200*scaleItem;
		pause = new Window("", skin);
		pause.setMovable(false); //So the user can't move the window
		//final TextButton button1 = new TextButton("Resume", skin);
		final Label button1 = new Label("RESUME", AssetHandler.FONT_SIZE_60_SUBTITLES_WHITE);
		button1.setFontScale(24f/60f);
		button1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				togglePaused();
				pause.setVisible(false);
	        }
		});
		Label button2 = new Label("EXIT", AssetHandler.FONT_SIZE_60_SUBTITLES_WHITE);
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

	/**
	 * Toggle isPaused variable.
	 */
	private void togglePaused() {
		isPaused = !isPaused;
	}

	/**
	 * Update the User Interface so that it shows/hides the items.
	 * @param show show/hide the items
	 */
    public void updateUI(Boolean show) {
    	information.setVisible(show);
    	description.setVisible(show);
    	scaleFactor.setVisible(show);
    	sf.setVisible(show);
    	Buy.setVisible(show);
    	titleCost.setVisible(show);
    	cost.setVisible(show);
    	titleLevel.setVisible(show);
    	level.setVisible(show);
    }
	
	

}
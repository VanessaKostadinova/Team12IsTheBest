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
import com.mygdx.shop.Shop;

import java.util.List;

/**
 * Shop Screen to allow player to get more items.
 * @author Inder, Vanessa
 */
public class ShopScreen implements Screen {

	/** If the Screen is Paused */
	private boolean isPaused = false;
	/** An instance of main */
	private Main main;
	/** An instance of shop */
	private Shop shop;
	/** A table to store the UI element */
	private Table t;
	/** Used to scale items by window size */
	private float scaleItem;
	/** The pause window. */
	private Window pause;
	/** The ui skin. */
	private Skin skin;
	/** List of label items */
	private List<Label> items;
	/** The labelstyle for any labels which are unclicked */
	private LabelStyle unClicked;
	/** The labelstyle for any labels which are clicked */
	private LabelStyle clicked;
	/** The Label to hold the item information */
	private Label information;
	/** The Label to hold the item description */
	private Label description;
	/** The Label to hold the item amount */
	private Label amount;
	/** The Label to hold the item amount on player */
	private Label amountOnPlayer;
	/** The Label title cost */
	private Label titleCost;
	/** The label holds the total cost */
	private Label cost;
	/** The label which holds the amount of burn fluids */
	private Label burnFluid;
	/** The label which holds the amount of cure fluids */
	private Label cureFluid;
	/** The label which holds the amount of player masks*/
	private Label playerMasks;
	/** The label which holds the amount of player food */
	private Label playerGold;
	/** The Image button to leave */
	private Image Leave;
	/** The Image button to buy */
	private Image Buy;
	/** The String to hold current equipment clicked*/
	private String clickedEquipment;


	public ShopScreen(final Main main, final Shop shop, final MapScreen mapScreen) {
		this.main = main;
		this.shop = shop;
		this.t = new Table();
		clickedEquipment = "";
		
		unClicked = AssetHandler.fontSize60SubtitlesWhite;
		clicked = AssetHandler.fontSize60SubtitlesCyan;
		
		this.skin = AssetHandler.skinUI;
		this.t = new Table();
		this.t.setFillParent(true);
		
		scaleItem = 1080f/(float)Gdx.graphics.getWidth();
		if(scaleItem < 1) {
			scaleItem = 1;
		}

		final Image Title = new Image(new TextureRegionDrawable(new TextureRegion(AssetHandler.manager.get("shop/screen/SHOP.png", Texture.class))));
		Title.setScaling(Scaling.fit);
		Title.setPosition(50f, main.ui.getHeight()- Title.getHeight() - 50f);
		Title.setSize(Title.getWidth(), Title.getHeight());
		t.addActor(Title);
		
		playerGold = new Label("PLAYER FOOD: " + Player.getInstance().getFood(), unClicked);
		playerGold .setPosition(50f, Title.getY() - 100f);
		main.ui.addActor(playerGold);

		setCatagories();

		Leave = new Image(new TextureRegionDrawable(new TextureRegion(new TextureRegion(AssetHandler.manager.get("shop/screen/LEAVE.png", Texture.class)))));
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
					TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(AssetHandler.manager.get("shop/screen/LEAVEMOUSE.png", Texture.class))));
					Leave.setDrawable(t);
				}
		    }
		    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if(!isPaused) {
					TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(AssetHandler.manager.get("shop/screen/LEAVE.png", Texture.class))));
					Leave.setDrawable(t);
				}
		    }
		});
		t.addActor(Leave);
		

		Buy = new Image(new TextureRegionDrawable((new TextureRegion(AssetHandler.manager.get("shop/screen/BUY.png", Texture.class)))));
		Buy.setScaling(Scaling.fit);
		Buy.setPosition((main.ui.getWidth())-40f-Buy.getWidth(),40f*2f + Leave.getHeight());
		Buy.setSize(Buy.getWidth(), Buy.getHeight());
		Buy.setVisible(false);
		Buy.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(clickedEquipment.equals("PLAYER MASKS") && Player.getInstance().getFood() >= shop.COST_PER_MASK) {
					Player.getInstance().setFood(Player.getInstance().getFood()-shop.COST_PER_MASK);
					PermanetPlayer.getPermanentPlayerInstance().reduceNumberOfMasks(-shop.MASK_PER_PURCHASE);
					setLabels(new String[] {
							clickedEquipment,
							"AMOUNT OF MASKS.",
							PermanetPlayer.getPermanentPlayerInstance().getNumberOfMasks()+"",
							shop.COST_PER_MASK+"",
					});
				}
				else if(clickedEquipment.equals("BURN FLUID") && Player.getInstance().getFood() >= shop.COST_PER_FLUID) {
					Player.getInstance().setFood(Player.getInstance().getFood()-shop.COST_PER_FLUID);
					PermanetPlayer.getPermanentPlayerInstance().reduceBurnSpray( (int) -shop.FLUID_PER_PURCHASE);
					setLabels(new String[] {
							clickedEquipment,
							"AMOUNT OF BURN FLUID.",
							PermanetPlayer.getPermanentPlayerInstance().getBurningFluid()+"",
							shop.COST_PER_FLUID+"",
					});
				}
				else if(clickedEquipment.equals("CURE FLUID") && Player.getInstance().getFood() >= shop.COST_PER_FLUID) {
					Player.getInstance().setFood(Player.getInstance().getFood()-shop.COST_PER_FLUID);
					PermanetPlayer.getPermanentPlayerInstance().reduceCureSpray( (int) -shop.FLUID_PER_PURCHASE);
					setLabels(new String[] {
							clickedEquipment,
							"AMOUNT OF CURE FLUID.",
							PermanetPlayer.getPermanentPlayerInstance().getHealingFluid()+"",
							shop.COST_PER_FLUID+"",
					});
				}
				playerGold.setText("PLAYER FOOD: " + Player.getInstance().getFood());
			}
		    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if(!isPaused) {
					TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(AssetHandler.manager.get("shop/screen/BUYMOUSE.png", Texture.class))));
					Buy.setDrawable(t);
				}
		    }
		    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if(!isPaused) {
					TextureRegionDrawable t = new TextureRegionDrawable((new TextureRegion(AssetHandler.manager.get("shop/screen/BUY.png", Texture.class))));
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
		
		amountOnPlayer = new Label("AMOUNT ON PLAYER:", unClicked);
		amountOnPlayer.setPosition((main.ui.getWidth())-40f-Leave.getWidth(), description.getY() - 100f);
		amountOnPlayer.setVisible(false);
		main.ui.addActor(amountOnPlayer);
		
		amount = new Label("VOID", unClicked);
		amount.setFontScale(0.5f);
		amount.setPosition((main.ui.getWidth())-40f-Leave.getWidth(), amountOnPlayer.getY() - 60f);
		amount.setVisible(false);

		main.ui.addActor(amount);
		
		titleCost = new Label("COST OF ITEM:", unClicked);
		titleCost .setPosition((main.ui.getWidth())-40f-Leave.getWidth(), amount.getY() - 100f);
		titleCost .setVisible(false);
		main.ui.addActor(titleCost);
		
		cost= new Label("VOID", unClicked);
		cost.setFontScale(0.5f);
		cost.setPosition((main.ui.getWidth())-40f-Leave.getWidth(), titleCost.getY() - 60f);
		cost.setVisible(false);
		main.ui.addActor(cost);

		Gdx.input.setInputProcessor(main.ui);
		main.ui.addActor(t);
		pauseGame();
	}


	/**
	 * Set the categories of each of the potential items in the shop.
	 */
	public void setCatagories() {
		float spacing = 80f;

		burnFluid = new Label("BURN FLUID", unClicked);
		burnFluid.setPosition(50,  playerGold.getY() - 200);
		burnFluid.setWidth(burnFluid.getWidth() + burnFluid.getWidth());

		cureFluid = new Label("CURE FLUID", unClicked);
		cureFluid.setPosition(50, burnFluid.getY() - spacing);
		cureFluid.setWidth(cureFluid.getWidth() + cureFluid.getWidth());

		playerMasks = new Label("PLAYER MASKS", unClicked);
		playerMasks.setWidth(playerMasks.getWidth() + playerMasks.getWidth());
		playerMasks.setPosition(50, cureFluid.getY() - spacing);


		burnFluid.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					if(burnFluid.getStyle().equals(unClicked)) {
						resetLabel();
						burnFluid.setStyle(clicked);
						setLabels(new String[] {
								burnFluid.getText().toString(),
								"AMOUNT OF BURN FLUID.",
								PermanetPlayer.getPermanentPlayerInstance().getBurningFluid()+"",
								shop.COST_PER_FLUID+"",
						});
					}
					else {
						burnFluid.setStyle(unClicked);
						updateUI(false);
					}
				}
			}
		});

		cureFluid.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					if(cureFluid.getStyle().equals(unClicked)) {
						resetLabel();
						cureFluid.setStyle(clicked);
						setLabels(new String[] {
								cureFluid.getText().toString(),
								"AMOUNT OF CURE FLUID.",
								PermanetPlayer.getPermanentPlayerInstance().getHealingFluid()+"",
								shop.COST_PER_FLUID+"",
						});
					}
					else {
						cureFluid.setStyle(unClicked);
						updateUI(false);
					}
				}
			}
		});


		playerMasks.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				if(!isPaused) {
					if(playerMasks.getStyle().equals(unClicked)) {
						resetLabel();
						playerMasks.setStyle(clicked);
						setLabels(new String[] {
								playerMasks.getText().toString(),
								"AMOUNT OF MASKS.",
								PermanetPlayer.getPermanentPlayerInstance().getNumberOfMasks()+"",
								shop.COST_PER_MASK+"",
						});
					}
					else {
						playerMasks.setStyle(unClicked);
						updateUI(false);
					}
				}
		    }
		});

		main.ui.addActor(burnFluid);
		main.ui.addActor(cureFluid);
		main.ui.addActor(playerMasks);


	
	}

	/**
	 * Reset the labels and make them invisible
	 */
	public void resetLabel() {
		burnFluid.setStyle(unClicked);
		cureFluid.setStyle(unClicked);
		playerMasks.setStyle(unClicked);
		updateUI(false);
		clickedEquipment = "";
	}


	/**
	 * Set the labels of the menu.
	 * @param values an array of string values
	 */
	public void setLabels(String[] values) {
		clickedEquipment = values[0];
		description.setText(values[1]);
		amount.setText("AMOUNT:  " + values[2]);
		cost.setText(values[3]+" FOOD");
		updateUI(true);
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

	/**
	 * InputHandler for the shop screen to allow exit.
	 */
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
	 * The window for the pause game.
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
		final Label button1 = new Label("RESUME", AssetHandler.fontSize60SubtitlesWhite);
		button1.setFontScale(24f/60f);
		button1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				togglePaused();
				pause.setVisible(false);
	        }
		});
		Label button2 = new Label("EXIT", AssetHandler.fontSize60SubtitlesWhite);
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
	 * Update all the UI elements
	 * @param hit if a ui element has been hit.
	 */
    public void updateUI(Boolean hit) {
    	information.setVisible(hit);
    	description.setVisible(hit);
    	amount.setVisible(hit);
    	amountOnPlayer.setVisible(hit);
    	Buy.setVisible(hit);
    	titleCost.setVisible(hit);
    	cost.setVisible(hit);
    }
	
	

}
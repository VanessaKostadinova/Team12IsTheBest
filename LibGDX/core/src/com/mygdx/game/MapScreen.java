package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.map.Disease;
import com.mygdx.map.Map;
import com.mygdx.renderable.Node;
import com.mygdx.renderable.Player;
import com.mygdx.shop.Shop;

import box2dLight.RayHandler;

import com.mygdx.camera.Camera;;

public class MapScreen implements Screen {

	private final int WORLD_WIDTH = 1920*2;
	private final int WORLD_HEIGHT = 1080*2;
	
	private float stateTime;
	
	private Main main;
	private Map map;
	private float viewWidth;
	private Camera cameraMap;
	private Camera cameraUI;
	
	protected Sprite background;
	protected Sprite behindBackground;
	private Sprite pointer;
	
	private Sprite shopText;
	private Sprite enterShop;
	
	private Sprite houseText;
	private Sprite enterHouse;
	private Sprite inspectHouse;
	private Sprite inspectDialog;
	
	private Sprite baseUI;
	private Sprite foodLabel;
	private Sprite forwardButton;
	
	private float movement =(12000f/60f);
	private float houseAlpha = 0;
	private float shopAlpha = 0;
	
	private Boolean houseHit = false;
	private Boolean shopHit = false;
	private Boolean enterBuilding = false;
	
	private float scaleItem;
	private Window pause;
	private Boolean isPaused;
	private Skin skin;
	
	private World world;
	private RayHandler rayHandler;
	
	private float darkness;
	private boolean darken;
	
	private int day;
	private Label dayLabel;
	private float dayAnimationTime;
	
	private boolean initialDone;
	
	private int getIndex;
	private Label energy;
	private Player p;
	private Disease disease;
	
	private Node nodeHit;
	private Label numberOfCharacter;
	private Label numberOfcharacterTitle;
	private Label numberOfcharacterSickTitle;
	private Label numberOfCharacterSick;
	private Label numberOfcharacterDiseasedTitle;
	private Label numberOfCharacterDiseased;
	
	private Node hoverNode;
	
	
	public MapScreen(Main main) {	
		this.viewWidth = 256;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		isPaused = false;
		cameraMap = new Camera(viewWidth, 1080, 1920);
		cameraMap.getCamera().position.set(
				cameraMap.getCamera().viewportWidth / 2f , 
				cameraMap.getCamera().viewportHeight / 2f, 0);
		cameraMap.setMaxValues(WORLD_WIDTH, WORLD_HEIGHT);
		
		cameraUI = new Camera(viewWidth, 1080, 1920);
		cameraUI.getCamera().position.set(
				cameraUI.getCamera().viewportWidth / 2f , 
				cameraUI.getCamera().viewportHeight / 2f, 0);
		cameraUI.setMaxValues(WORLD_WIDTH, WORLD_HEIGHT);

		background = new Sprite(main.assets.manager.get("house/background.png", Texture.class));
		background.setPosition(0, 0);
		
		behindBackground = new Sprite(main.assets.manager.get("house/water.png", Texture.class));
		behindBackground.setPosition(0, 0);
		behindBackground.scale(2f);
		//background.setScale(WORLD_WIDTH/background.getWidth(), WORLD_HEIGHT/background.getHeight());
		
		
		this.darkness = 0f;
		this.world = new World(new Vector2(0,0), false);
		this.rayHandler = new RayHandler(world);
		this.rayHandler.setAmbientLight(darkness);
		this.darken = false;
		this.main = main;
		this.map = new Map(main.assets);
		this.disease = map.getDisease();
		this.initialDone = false;
		this.getIndex = -1;
		createUIElements();
		stateTime = 0;
		dayAnimationTime = 0;
		
				
	}
	

	public void createUIElements() {
		scaleItem = 1080f/(float)Gdx.graphics.getWidth();
		if(scaleItem < 1) {
			scaleItem = 1;
		}
		
		this.skin = new Skin(Gdx.files.internal("skin/terra-mother-ui.json"));
		
		pauseGame();
		
		this.pointer = new Sprite(main.assets.manager.get("house/aim.png", Texture.class));
		pointer.setPosition(
				cameraMap.getViewport().getWorldWidth()/2-pointer.getWidth()/2, 
				cameraMap.getViewport().getWorldHeight()/2-pointer.getHeight()/2);
		
		this.enterHouse = new Sprite(main.assets.manager.get("house/MAP_ENTERHOUSE.png", Texture.class));
		enterHouse.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));;
		enterHouse.setPosition(-72.5f, -80);
		enterHouse.setAlpha(houseAlpha);
		
		this.inspectHouse = new Sprite(main.assets.manager.get("house/MAP_INSPECT.png", Texture.class));
		inspectHouse.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));;
		inspectHouse.setPosition(-240, -80);
		inspectHouse.setAlpha(houseAlpha);
		
		this.inspectDialog = new Sprite(new Texture(Gdx.files.internal("player/MAPUI/dialog.png")));
		inspectDialog.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));;
		inspectDialog.setScale(0.125f);
		inspectDialog.setScale(0.128f, 0.132f);
		inspectDialog.setPosition(-252, -290);
		inspectDialog.setAlpha(houseAlpha);
		
		this.houseText = new Sprite(main.assets.manager.get("house/MAP_HOUSE.png", Texture.class));
		houseText.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));;
		houseText.setPosition(-66f, 72f);
		houseText.setAlpha(houseAlpha);

		this.enterShop = new Sprite(main.assets.manager.get("shop/ENTER_SHOP.png", Texture.class));
		enterShop.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));;
		enterShop.setPosition(-72.5f, -80);
		enterShop.setAlpha(shopAlpha);
		
		this.shopText = new Sprite(main.assets.manager.get("shop/SHOP.png", Texture.class));
		shopText.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));;
		shopText.setPosition(-64.5f, 72.5f);
		shopText.setAlpha(shopAlpha);
		
		this.baseUI = new Sprite(main.assets.manager.get("player/MAPUI/BaseUI.png", Texture.class));
		baseUI.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));;
		baseUI.setPosition(-5, 30);
		
		this.foodLabel = new Sprite(main.assets.manager.get("player/MAPUI/NextLabel.png", Texture.class));
		foodLabel.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));;
		foodLabel.setPosition(47, 74.5f);
	
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stateTime = stateTime + delta;
		fadeAnimationHouseUI();
		fadeAnimationShopUI();
		initalSceneTransitions(delta);
		
		localInputHandler(delta);
	    main.shape.setProjectionMatrix(cameraMap.getCamera().combined);
		checkIfClicked(pointer.getX(), pointer.getY());

	    main.batch.setProjectionMatrix(cameraMap.getCamera().combined);
		main.batch.begin();
			behindBackground.draw(main.batch);
			background.draw(main.batch);
			map.getShop().draw(main.batch);
	   main.batch.end();
	   checkIfClicked(pointer.getX(), pointer.getY());

	   main.batch.begin();
			for(Node node: this.map.getNodes()) {
				if(node != null) {
					node.draw(main.batch);
					node.updateHouseDiseased();
				}
			}
			pointer.draw(main.batch);
			
		    main.batch.setProjectionMatrix(cameraUI.getCamera().combined);

			enterHouse.draw(main.batch);
			inspectHouse.draw(main.batch);
			inspectDialog.draw(main.batch);
			houseText.draw(main.batch);
			
			shopText.draw(main.batch);
			enterShop.draw(main.batch);
			
			baseUI.draw(main.batch);
			foodLabel.draw(main.batch);
			
		main.batch.end();
		


		
		makeSceneDark();
		rayHandler.render();
		
		main.ui.draw();

		
	}
	
	
	public void localInputHandler(float delta) {
		if(Gdx.input.isKeyPressed(Keys.W) && !isPaused) {
			if(pointer.getY()+pointer.getHeight()+movement*delta < background.getHeight()) {
				cameraMap.updateCameraPosition(0 , movement*delta);
				pointer.setPosition(pointer.getX(), pointer.getY() + movement*delta);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.S) && !isPaused) {
			if(pointer.getY()-movement*delta > 0) {
				cameraMap.updateCameraPosition(0 , -movement*delta);
				pointer.setPosition(pointer.getX(), pointer.getY() - movement*delta);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.D) && !isPaused) {
			if(pointer.getX()+pointer.getWidth()+movement*delta < background.getWidth()) {
				cameraMap.updateCameraPosition(movement*delta, 0);
				pointer.setPosition(pointer.getX()+ movement*delta, pointer.getY());
			}
		}
		if(Gdx.input.isKeyPressed(Keys.A) && !isPaused) {
			if(pointer.getX()-movement*delta > 0) {
				cameraMap.updateCameraPosition(-movement*delta , 0);
				pointer.setPosition(pointer.getX()- movement*delta, pointer.getY());
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.Z) && !isPaused) {
			cameraMap.zoomIn(1);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.C) && !isPaused) {
			cameraMap.zoomIn(-1);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER) && !isPaused) {
			enterBuilding = true;
		} else {
			enterBuilding = false;
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			togglePaused();
			pause.setVisible(isPaused);
		}
		
		if(Gdx.input.isKeyPressed(Keys.N) && !isPaused) {
			darken = true;
		}

		if(Gdx.input.isKeyJustPressed(Keys.E) && !isPaused) {
			p = readPlayer();
			try {
				if(p.getEnergy() >=  5 && !hoverNode.reachedMaxLevel()) {
					hoverNode.upgradeLevelKnown();
					p.deltaEnergy(5);
					p.writeToPlayerFile();
				}
			} catch(NullPointerException e) {
				Gdx.app.log("E pressed outside of house", "Caught exception outside of the house.");
			}
		}
		
	}
	
	public void makeSceneDark() {
		
		if(initialDone) {
			if (darken) {
				energy.setVisible(false);
				numberOfCharacter.setVisible(false);
				numberOfcharacterTitle.setVisible(false);
				numberOfcharacterSickTitle.setVisible(false);
				numberOfCharacterSick.setVisible(false);
				numberOfcharacterDiseasedTitle.setVisible(false);
				numberOfCharacterDiseased.setVisible(false);
				if(darkness >= 0) {
					darkness-= (1f/60f);
					if(darkness <= 0) {
						Player p = readPlayer();
						p.resetEnergy();
						energy.setText(p.getEnergy()+"");
						disease.diseaseSpread(map.getNodes());
						p.writeToPlayerFile();
						initialDone = false;
						darken = false;
						day++;
						dayLabel.setText("DAY " + day);

					}
				}
				else {
					numberOfCharacter.setVisible(false);
					numberOfcharacterTitle.setVisible(false);
					numberOfcharacterSickTitle.setVisible(false);
					numberOfCharacterSick.setVisible(false);
					numberOfcharacterDiseasedTitle.setVisible(false);
					numberOfCharacterDiseased.setVisible(false);
				}
			}
			else {
				energy.setVisible(true);				
			}
		}
		this.rayHandler.setAmbientLight(darkness);
	}
	
	
	
	public void updateText(Node n, Player p) {
		energy.setText(p.getEnergy()+"");
		if(n.getLevel1()) {
			numberOfCharacter.setText(n.getNumberOfResidents());
		} else {
			numberOfCharacter.setText("NOT KNOWN");
		}
		
		if(n.getLevel2()) {
			numberOfCharacterSick.setText(n.getNumberOfSick());
		} else {
			numberOfCharacterSick.setText("NOT KNOWN");
		}
		
		if(n.getLevel3()) {
			numberOfCharacterDiseased.setText(n.getNumberOfInfected());
		} else {
			numberOfCharacterDiseased.setText("NOT KNOWN");
		}

	}
	
	public void resetLabels() {
		numberOfCharacter.setText("NOT KNOWN");
		numberOfCharacter.setText("NOT KNOWN");
		numberOfCharacter.setText("NOT KNOWN");
	}
	
	public void initalSceneTransitions(float delta) {
		if(!initialDone) {
			dayLabel.setVisible(true);
			
			numberOfCharacter.setVisible(false);
			numberOfcharacterTitle.setVisible(false);
			numberOfcharacterSickTitle.setVisible(false);
			numberOfCharacterSick.setVisible(false);
			numberOfcharacterDiseasedTitle.setVisible(false);
			numberOfCharacterDiseased.setVisible(false);
			
			Boolean fade = false;
			dayAnimationTime = dayAnimationTime + delta;
			
			if(dayAnimationTime > 2) {
				if(darkness < 1) {
					dayLabel.setVisible(false);
					darkness+= (1f/60f);
					this.rayHandler.setAmbientLight(darkness);
					numberOfCharacter.setVisible(false);
					numberOfcharacterTitle.setVisible(false);
					numberOfcharacterSickTitle.setVisible(false);
					numberOfCharacterSick.setVisible(false);
					numberOfcharacterDiseasedTitle.setVisible(false);
					numberOfCharacterDiseased.setVisible(false);
				}
				if(darkness >= 1 && !initialDone) {
					initialDone = true;
					dayAnimationTime = 0;
					numberOfCharacter.setVisible(false);
					numberOfcharacterTitle.setVisible(false);
					numberOfcharacterSickTitle.setVisible(false);
					numberOfCharacterSick.setVisible(false);
					numberOfcharacterDiseasedTitle.setVisible(false);
					numberOfCharacterDiseased.setVisible(false);
				}
			}
			
		}
	}

	@Override
	public void resize(int width, int height) {	
		
		cameraMap.getViewport().update(width, height);
		cameraMap.getCamera().viewportHeight = viewWidth*((float)height/(float) width);
		cameraMap.getViewport().apply();
		cameraMap.updateCamera();
		
		cameraUI.getViewport().update(width, height);
		cameraUI.getCamera().viewportHeight = viewWidth*((float)height/(float) width);
		cameraUI.getViewport().apply();
		cameraUI.updateCamera();
		
		main.ui.getViewport().update(width, height);

	}
	

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}
	
	public void fadeAnimationHouseUI() {
		if(houseHit) { 
			if(houseAlpha < 1) { 
				houseAlpha += (1f/10f);
				enterHouse.setAlpha(houseAlpha);
				inspectHouse.setAlpha(houseAlpha);
				inspectDialog.setAlpha(houseAlpha);
				houseText.setAlpha(houseAlpha);
			}
			else {
				enterHouse.setAlpha(1);
				inspectHouse.setAlpha(1);
				inspectDialog.setAlpha(1);
				houseText.setAlpha(1);
				
				
				numberOfCharacter.setVisible(true);
				numberOfcharacterTitle.setVisible(true);
				numberOfcharacterSickTitle.setVisible(true);
				numberOfCharacterSick.setVisible(true);
				numberOfcharacterDiseasedTitle.setVisible(true);
				numberOfCharacterDiseased.setVisible(true);
			}
		}
		else { 
			

			
			if(houseAlpha > 0.1) { 
				houseAlpha -= (1f/10f);
				enterHouse.setAlpha(houseAlpha);
				inspectHouse.setAlpha(houseAlpha);
				inspectDialog.setAlpha(houseAlpha);
				houseText.setAlpha(houseAlpha);
			}
			else {
				enterHouse.setAlpha(0);
				inspectHouse.setAlpha(0);
				inspectDialog.setAlpha(0);
				houseText.setAlpha(0);
				
				numberOfCharacter.setVisible(false);
				numberOfcharacterTitle.setVisible(false);
				numberOfcharacterSickTitle.setVisible(false);
				numberOfCharacterSick.setVisible(false);
				numberOfcharacterDiseasedTitle.setVisible(false);
				numberOfCharacterDiseased.setVisible(false);
			}
		}
	}
	
	public void fadeAnimationShopUI() {
		if(shopHit) {
			if(shopAlpha < 1) { 
				shopAlpha += (1f/10f);
				shopText.setAlpha(shopAlpha);
				enterShop.setAlpha(shopAlpha);
			}
			else {
				shopText.setAlpha(1);
				enterShop.setAlpha(1);
			}
		}
		else {
			if(shopAlpha > 0.1) { 
				shopAlpha -= (1f/10f);
				shopText.setAlpha(shopAlpha);
				enterShop.setAlpha(shopAlpha);
			}
			else {
				shopText.setAlpha(0);
				enterShop.setAlpha(0);
			}
		}
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
		main.dispose();
	}
	
	public void enterHouse(Node node) {
		houseHit = true;
		Player p = readPlayer();
		updateText(node, p);
		hoverNode = node;
		
		if(enterBuilding && p.getEnergy() >= 30) {
			main.ui.clear();
			enterBuilding = false;
			node.serializeVillagers();
			p.deltaEnergy(30);
			p.writeToPlayerFile();
			main.setScreen(new HouseScreen(main, node, this));
		}	
	}
	
	public void enterShop(Shop shop) {
		shopHit = true;
		if(enterBuilding) {
			main.ui.clear();
			enterBuilding = false;
			main.setScreen(new ShopScreen(main, shop, this));
		}
	}
	
	public void checkIfClicked(float x, float y) {
		int value = 0;
		for(Node node : map.getNodes()) {
			if(node != null) {
				if(node.pointIsWithinSprite(x, y)) {
					getIndex = value;
					enterHouse(node);
					disease.draw(map.getNodes(), node, main.shape);
					break;
				}
				else {
					if(houseHit) {
						houseHit = false;
						node = null;
					}
					//disease.clear();
				}
				value++;
			}
		}
		
		if(map.getShop().pointIsWithinSprite(x, y)) {
			enterShop(map.getShop());
		}
		else {
			shopHit = false;
		}
	}
	
	public float getStateTime() {
		return stateTime;
	}
	
	public Camera getMapCamera() {
		return cameraMap;
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
        float windowWidth = 200*scaleItem, windowHeight = 200*scaleItem;
        pause = new Window("", skin);
        pause.setMovable(false); //So the user can't move the window
        //final TextButton button1 = new TextButton("Resume", skin);
        final Label button1 = new Label("RESUME", createLabelStyleWithBackground());
        button1.setFontScale((windowHeight/200)*scaleItem, (windowHeight/200)*scaleItem );
        button1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePaused();
                pause.setVisible(false);
            }
        });
        Label button2 = new Label("EXIT", createLabelStyleWithBackground());
        button2.setFontScale((windowHeight/200)*scaleItem, (windowHeight/200)*scaleItem );
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
        
		dayLabel = new Label("DAY " + day , createLabelStyleWithBackgroundFont48());
		dayLabel.setPosition(main.ui.getWidth()/2-dayLabel.getWidth()/2, main.ui.getHeight()/2-dayLabel.getHeight()/2);
		dayLabel.setVisible(false);
		main.ui.addActor(dayLabel);

		
		energy = new Label(readPlayer().getEnergy()+"", createLabelStyleWithBackground());
		energy.setWidth(450f);
		energy.setFontScale(2.5f);
		energy.setAlignment(Align.center);
		energy.setPosition(main.ui.getWidth()-energy.getWidth()-80f, main.ui.getHeight()-energy.getHeight()-200f);
		energy.setVisible(false);
		//energy.setVisible(false);
		main.ui.addActor(energy);
		
		numberOfcharacterTitle = new Label("NUMBER OF CHARACTERS: ", createLabelStyleWithBackground());
		numberOfcharacterTitle.setWidth(500f);
		numberOfcharacterTitle.setFontScale(1.3f);
		numberOfcharacterTitle.setPosition(90, main.ui.getHeight() - 150);
		numberOfcharacterTitle.setVisible(false);
		main.ui.addActor(numberOfcharacterTitle);
		
		numberOfCharacter = new Label("NOT KNOWN", createLabelStyleWithBackground());
		numberOfCharacter.setWidth(500f);
		numberOfCharacter.setPosition(90, main.ui.getHeight() - 150 -numberOfcharacterTitle.getHeight());
		numberOfCharacter.setVisible(false);
		main.ui.addActor(numberOfCharacter);
		
		
		numberOfcharacterSickTitle = new Label("NUMBER OF SICK: ", createLabelStyleWithBackground());
		numberOfcharacterSickTitle.setWidth(500f);
		numberOfcharacterSickTitle.setFontScale(1.3f);
		numberOfcharacterSickTitle.setPosition(90, main.ui.getHeight() - 150 -numberOfCharacter.getHeight()-numberOfcharacterTitle.getHeight());
		numberOfcharacterSickTitle.setVisible(false);
		main.ui.addActor(numberOfcharacterSickTitle);
		
		numberOfCharacterSick = new Label("NOT KNOWN", createLabelStyleWithBackground());
		numberOfCharacterSick.setWidth(500f);
		numberOfCharacterSick.setPosition(90, main.ui.getHeight() - 150 -numberOfcharacterSickTitle.getHeight()-numberOfCharacter.getHeight()-numberOfcharacterTitle.getHeight());
		numberOfCharacterSick.setVisible(false);
		main.ui.addActor(numberOfCharacterSick);
		
		numberOfcharacterDiseasedTitle = new Label("NUMBER OF DISEASED: ", createLabelStyleWithBackground());
		numberOfcharacterDiseasedTitle.setWidth(500f);
		numberOfcharacterDiseasedTitle.setFontScale(1.3f);
		numberOfcharacterDiseasedTitle.setPosition(90, main.ui.getHeight() - 150 -numberOfCharacterSick.getHeight()-numberOfcharacterSickTitle.getHeight()-numberOfCharacter.getHeight()-numberOfcharacterTitle.getHeight());
		numberOfcharacterDiseasedTitle.setVisible(false);
		main.ui.addActor(numberOfcharacterDiseasedTitle);
		
		numberOfCharacterDiseased = new Label("NOT KNOWN", createLabelStyleWithBackground());
		numberOfCharacterDiseased.setWidth(500f);
		numberOfCharacterDiseased.setPosition(90, main.ui.getHeight() - 150 -numberOfcharacterDiseasedTitle.getHeight() -numberOfCharacterSick.getHeight()-numberOfcharacterSickTitle.getHeight()-numberOfCharacter.getHeight()-numberOfcharacterTitle.getHeight());
		numberOfCharacterDiseased.setVisible(false);
		main.ui.addActor(numberOfCharacterDiseased);
		
		
		Gdx.input.setInputProcessor(main.ui);

    }
    
    
    private LabelStyle createLabelStyleWithBackground() {
    	///core/assets/font/Pixel.ttf
    	FileHandle fontFile = Gdx.files.internal("font/Pixel.ttf");
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 24;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        labelStyle.fontColor = Color.WHITE;
        return labelStyle;
    }
    
    
    private LabelStyle createLabelStyleWithBackgroundFont48() {
    	///core/assets/font/Pixel.ttf
    	FileHandle fontFile = Gdx.files.internal("font/Pixel.ttf");
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 48;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        labelStyle.fontColor = Color.WHITE;
        return labelStyle;
    }
	/**
	 * Toggle isPaused variable.
	 */
	public void togglePaused() {
		if(!isPaused) {
			isPaused = true;
		}
		else {
			isPaused = false;
		}
	}
	
	public Player readPlayer() {
		FileHandle handle = Gdx.files.local("data/player.txt");
		String[] values= handle.readString().split(",");
		Player p = new Player(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6]), Float.parseFloat(values[7]));
		return p;
	}
	
	
}

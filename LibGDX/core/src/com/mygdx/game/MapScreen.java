package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.map.Map;
import com.mygdx.renderable.Node;
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
	
	private Sprite baseUI;
	private Sprite foodLabel;
	private Sprite forwardButton;
	
	private Sprite energyBar1;
	private Sprite energyBar2;
	private Sprite energyBar3;
	
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

	
	public MapScreen(Main main) {	
		this.viewWidth = 256;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		cameraMap = new Camera(viewWidth, h, w);
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
		this.initialDone = false;
		createUIElements();
		stateTime = 0;
		dayAnimationTime = 0;
		
		Gdx.input.setInputProcessor(main.ui);
				
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
		
		this.foodLabel = new Sprite(main.assets.manager.get("player/MAPUI/FoodLabel.png", Texture.class));
		foodLabel.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));;
		foodLabel.setPosition(47, 74.5f);
		
		this.energyBar1 = new Sprite(main.assets.manager.get("player/MAPUI/HealthBar.png", Texture.class));
		energyBar1.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));;
		energyBar1.setPosition(190.3f, 90.3f);
		
		this.energyBar2 = new Sprite(main.assets.manager.get("player/MAPUI/HealthBar.png", Texture.class));
		energyBar2.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));;
		energyBar2.setPosition(190.3f+2f, 90.3f);
		
		this.energyBar3 = new Sprite(main.assets.manager.get("player/MAPUI/HealthBar.png", Texture.class));
		energyBar3.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));;
		energyBar3.setPosition(190.3f+4f, 90.3f);
		
		dayLabel = new Label("Day " + day, skin);
		dayLabel.setPosition(main.ui.getWidth()/2, main.ui.getHeight()/2);
		dayLabel.setVisible(false);
		
		main.ui.addActor(dayLabel);
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
		
	    main.batch.setProjectionMatrix(cameraMap.getCamera().combined);
		main.batch.begin();
			behindBackground.draw(main.batch);
			background.draw(main.batch);
			for(Node node: this.map.getNodes()) {
				if(node != null) {
					node.draw(main.batch);
				}
			}
			map.getShop().draw(main.batch);
			
			pointer.draw(main.batch);
			
		    main.batch.setProjectionMatrix(cameraUI.getCamera().combined);

			enterHouse.draw(main.batch);
			inspectHouse.draw(main.batch);
			houseText.draw(main.batch);
			
			shopText.draw(main.batch);
			enterShop.draw(main.batch);
			
			baseUI.draw(main.batch);
			foodLabel.draw(main.batch);
			
			energyBar1.draw(main.batch);
			energyBar2.draw(main.batch);
			energyBar3.draw(main.batch);
		main.batch.end();
		
		makeSceneDark();
		rayHandler.render();
		checkIfClicked(pointer.getX(), pointer.getY());
		
		main.ui.draw();
		
		
	}
	
	public void localInputHandler(float delta) {
		if(Gdx.input.isKeyPressed(Keys.W)) {
			if(pointer.getY()+pointer.getHeight()+movement*delta < background.getHeight()) {
				cameraMap.updateCameraPosition(0 , movement*delta);
				pointer.setPosition(pointer.getX(), pointer.getY() + movement*delta);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			if(pointer.getY()-movement*delta > 0) {
				cameraMap.updateCameraPosition(0 , -movement*delta);
				pointer.setPosition(pointer.getX(), pointer.getY() - movement*delta);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			if(pointer.getX()+pointer.getWidth()+movement*delta < background.getWidth()) {
				cameraMap.updateCameraPosition(movement*delta, 0);
				pointer.setPosition(pointer.getX()+ movement*delta, pointer.getY());
			}
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			if(pointer.getX()-movement*delta > 0) {
				cameraMap.updateCameraPosition(-movement*delta , 0);
				pointer.setPosition(pointer.getX()- movement*delta, pointer.getY());
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.Z)) {
			cameraMap.zoomIn(1);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.C)) {
			cameraMap.zoomIn(-1);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			enterBuilding = true;
		} else {
			enterBuilding = false;
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			togglePaused();
			pause.setVisible(isPaused);
		}
		
		if(Gdx.input.isKeyPressed(Keys.N)) {
			darken = true;
		}

		
	}
	
	public void makeSceneDark() {
		
		if(initialDone) {
			if (darken) {
				if(darkness >= 0) {
					darkness-= (1f/60f);
					if(darkness <= 0) {
						initialDone = false;
						darken = false;
						day++;
						dayLabel.setText("Day " + day);
						
					}
				}
			}
		}
		this.rayHandler.setAmbientLight(darkness);
	}
	
	
	public void initalSceneTransitions(float delta) {
		if(!initialDone) {
			dayLabel.setVisible(true);
			Boolean fade = false;
			dayAnimationTime = dayAnimationTime + delta;
			System.out.println(delta);
			
			if(dayAnimationTime > 2) {
				if(darkness < 1) {
					dayLabel.setVisible(false);
					darkness+= (1f/60f);
					this.rayHandler.setAmbientLight(darkness);
					System.out.println(darkness);
				}
				if(darkness >= 1 && !initialDone) {
					initialDone = true;
					dayAnimationTime = 0;
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
				houseText.setAlpha(houseAlpha);
			}
			else {
				enterHouse.setAlpha(1);
				inspectHouse.setAlpha(1);
				houseText.setAlpha(1);
			}
		}
		else { 
			if(houseAlpha > 0.1) { 
				houseAlpha -= (1f/10f);
				enterHouse.setAlpha(houseAlpha);
				inspectHouse.setAlpha(houseAlpha);
				houseText.setAlpha(houseAlpha);
			}
			else {
				enterHouse.setAlpha(0);
				inspectHouse.setAlpha(0);
				houseText.setAlpha(0);
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
		//main.setScreen(new HouseScreen(main, node));
	}
	
	public void enterShop(Shop shop) {
		shopHit = true;
		if(enterBuilding) {
			main.ui.clear();
			main.setScreen(new ShopScreen(main, shop, this));
		}
	}
	
	public void checkIfClicked(float x, float y) {
		for(Node node : map.getNodes()) {
			if(node != null) {
				if(node.pointIsWithinSprite(x, y)) {
					enterHouse(node);
					break;
				}
				else {
					if(houseHit) {
						houseHit = false;
					}
				}
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
		if(!isPaused) {
			isPaused = true;
		}
		else {
			isPaused = false;
		}
	}
	
}

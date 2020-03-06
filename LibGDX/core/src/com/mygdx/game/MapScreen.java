package com.mygdx.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.extras.PermanetPlayer;
import com.mygdx.map.Disease;
import com.mygdx.map.Map;
import com.mygdx.renderable.NPC;
import com.mygdx.renderable.Node;
import com.mygdx.renderable.Player;
import com.mygdx.shop.Church;
import box2dLight.RayHandler;
import com.mygdx.assets.AssetHandler;
import com.mygdx.camera.Camera;
import com.mygdx.story.Note;
import com.mygdx.story.StoryHandler;

public class MapScreen implements Screen {

	public final int ENERGY_FOR_RESEARCH = 5;
	public final int ENERGY_FOR_ENTER_HOUSE = 25;

	private float stateTime;
	
	private Main main;
	private Map map;
	private float viewWidth;
	private Camera cameraMap;
	private Camera cameraUI;
	
	protected Sprite background;
	//protected Sprite behindBackground;
	private Sprite pointer;
	
	private Sprite shopText;
	private Sprite enterShop;
	
	private Sprite houseText;
	private Sprite enterHouse;
	private Sprite inspectHouse;
	private Sprite inspectDialog;
	
	private Sprite baseUI;
	private Sprite foodLabel;

	private float houseAlpha = 0;
	private float shopAlpha = 0;
	
	private Boolean houseHit = false;
	private Boolean shopHit = false;
	private Boolean enterBuilding = false;
	
	private float scaleItem;
	private Window pause;
	private Window beforeEntry;
	private Boolean isPaused;
	private Boolean cutsceneActive;
	private Skin skin;
	private RayHandler rayHandler;
	private float darkness;
	private boolean darken;
	/**
	 * Don't store the instance of Permanent Player breaks the point of having a
	 * singleton as this instance and the actual instance of PermanetPlayer.getInstance()
	 * can be out of sync. Furthermore, there is no way to sync these without breaking Singleton
	 * so PermanentPlayer.getInstance() should be used to retrieve the instance of the player.
	 */
	//private PermanetPlayer permanetPlayer;
	
	private int day;
	private Label dayLabel;
	private float dayAnimationTime;
	
	private boolean initialDone;

	private Label energy;
	private Disease disease;

	private Label numberOfCharacter;
	private Label numberOfcharacterTitle;
	private Label numberOfcharacterSickTitle;
	private Label numberOfCharacterSick;
	private Label numberOfcharacterDiseasedTitle;
	private Label numberOfCharacterDiseased;

	private Label Amount1;
	
	private Node hoverNode;

	private Image overlayCutscene;
	private Image dialogCutscene;
	private Image speakerImage;
	private Label personToSpeak;
	private Label setDescriptionOfText;
	private Image noteBackground;

	private List<String> currentCutsceneQuotes;
	private List<String> currentCutscenePerson;
	private List<String> currentCutsceneDuration;

	/** Inventory menu. */
	private Window inventory;

	private List<String> checkForKC;
	private boolean itemsSelected;
	private int cutsceneSequence;
	private Window notesInventory;


	private Label noteTitle, note1, note2, note3, note4, note5, note6, note7, note8, note9, note10, note11, note12, note13, note14, note15, note16, note17, note18, note19, note20;
	private Image letter;
	private Label paragraph;

	/**
	 * Create the map screen, and handle the input and movements around the map.
	 * @author Inder, Vanessa, Leon
	 * @param main The main class and shouldn't be null.
	 */
	public MapScreen(Main main) {
		itemsSelected = false;
		cutsceneActive = false;
		this.viewWidth = 256;
		Player.init(5, 100, 100);
		isPaused = false;
		cameraMap = new Camera(viewWidth, -1080, -1920);
		cameraMap.getCamera().position.set(
				cameraMap.getCamera().viewportWidth / 2f , 
				cameraMap.getCamera().viewportHeight / 2f, 0);
		int WORLD_WIDTH = 1920 * 2;
		int WORLD_HEIGHT = 1080 * 2;
		cameraMap.setMaxValues(WORLD_WIDTH, WORLD_HEIGHT);


		cameraUI = new Camera(viewWidth, 1080, 1920);
		cameraUI.getCamera().position.set(
				cameraUI.getCamera().viewportWidth / 2f , 
				cameraUI.getCamera().viewportHeight / 2f, 0);
		cameraUI.setMaxValues(WORLD_WIDTH, WORLD_HEIGHT);

		background = new Sprite(AssetHandler.manager.get("house/background.png", Texture.class));
		//background.setScale(1920/1080);
		background.setPosition(0, 0);

		currentCutsceneQuotes = new LinkedList<>();
		currentCutscenePerson = new LinkedList<>();
		currentCutsceneDuration = new LinkedList<>();


		this.darkness = 0f;
		World world = new World(new Vector2(0, 0), false);
		this.rayHandler = new RayHandler(world);
		this.rayHandler.setAmbientLight(darkness);
		this.darken = false;
		this.main = main;
		this.map = new Map();
		this.disease = map.getDisease();
		this.initialDone = false;

		this.checkForKC = new ArrayList<>();
		createUIElements();
		stateTime = 0;
		dayAnimationTime = 0;
		
		cameraMap.updateCameraPosition(500, 500);
		cameraMap.getCamera().zoom = 7f;

		pointer.setPosition(pointer.getX()+500, pointer.getY()+500);
		createUI();
		inventory();
		pauseGame();
		if(!StoryHandler.introductionPart1) {
			startCreatingCutscene("cutscene/ingame/scripts/Scene1.csv");
			StoryHandler.introductionPart1 = true;
		}

	}

	/**
	 * Used to draw and create the cutscene items on stream.
	 */
	public void createInGameCutscene() {
		Sprite s = new Sprite(new Texture(Gdx.files.internal("cutscene/ingame/cutsceneOverlay.png")));
		s.setAlpha(0.9f);
		cutsceneSequence = 0;
		overlayCutscene = new Image(new SpriteDrawable(s));
		overlayCutscene.setPosition(main.ui.getWidth()/2 - overlayCutscene.getWidth()/2 - 130, main.ui.getHeight()/2 - overlayCutscene.getHeight()/2 - 100);
		overlayCutscene.setScale(2f);
		overlayCutscene.setVisible(false);

		Sprite s3 = new Sprite(new Texture(Gdx.files.internal("cutscene/ingame/characterImages/templateCutsceneSpeaker.png")));
		speakerImage = new Image(new SpriteDrawable(s3));
		speakerImage.setPosition(1080-speakerImage.getWidth()/2-150, 430);
		speakerImage.setScale(2f);
		speakerImage.setVisible(false);

		Sprite s2 = new Sprite(AssetHandler.manager.get("player/MAPUI/dialog.png", Texture.class));
		dialogCutscene = new Image(new SpriteDrawable(s2));
		dialogCutscene.setPosition(50, 50);
		dialogCutscene.setScaleY(0.5f);
		dialogCutscene.setScaleX(3.45f);
		dialogCutscene.setVisible(false);

		personToSpeak = new Label("YOU:", AssetHandler.fontSize32);
		personToSpeak.setPosition(90, 350F);
		personToSpeak.setVisible(false);

		setDescriptionOfText = new Label("YNSERT TEXT HERE PLEASE! ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss", AssetHandler.fontSize32);
		setDescriptionOfText.setAlignment(Align.topLeft);
		setDescriptionOfText.setWidth(1950);
		setDescriptionOfText.setWrap(true);
		setDescriptionOfText.setPosition(90, 300f);
		setDescriptionOfText.setVisible(false);

		main.ui.addActor(overlayCutscene);
		main.ui.addActor(dialogCutscene);
		main.ui.addActor(speakerImage);
		main.ui.addActor(personToSpeak);
		main.ui.addActor(setDescriptionOfText);
	}

	/**
	 * Updates visibility of cutscene
	 * @param value True or False value depending if want to be visible.
	 */
	public void updateInGameCutscene(boolean value) {
		this.overlayCutscene.setVisible(value);
		this.speakerImage.setVisible(value);
		this.dialogCutscene.setVisible(value);
		this.personToSpeak.setVisible(value);
		this.setDescriptionOfText.setVisible(value);
		this.cutsceneActive = value;
	}

	/**
	 * Updates the text of script
	 * @param person Person's name.
	 * @param text What the person is saying.
	 */
	public void updateInGameCutscene(String person, String text) {
		this.personToSpeak.setText(person);
		this.setDescriptionOfText.setText(text);
	}

	/**
	 * Intialising the new cutscene based of a text file.
	 * @param file The file of the .csv file for the cutscene.
	 */
	public void startCreatingCutscene(String file) {
		System.out.println("HIT1");
		FileHandle n = Gdx.files.internal(file);
		System.out.println("HIT2");
		cutsceneSequence = 0;
		String textFile = n.readString();
		String lines[] = textFile.split("\\r?\\n");
		for(int i = 1; i < lines.length; i++) {
			String line = lines[i];
			String[] data = line.split(",");
			/**
			 * Data - ARRAY:
			 * index 0 = Duration of line (potentially for voice acting)
			 * index 1 = Person name
			 * index 2 = Quote of what the person is saying.
			 */
			currentCutsceneDuration.add(data[0]);
			currentCutscenePerson.add(data[1]);
			currentCutsceneQuotes.add(data[2]);
		}
		updateInGameCutscene(true);
		updateInGameCutscene(currentCutscenePerson.get(cutsceneSequence), currentCutsceneQuotes.get(cutsceneSequence));
	}

	/**
	 * Handles the switching of the cutscene.
	 */
	public void sequenceOfCutscene() {
		if(cutsceneActive) {
			if(Gdx.input.isKeyJustPressed(Keys.ENTER) && !isPaused) {
				cutsceneSequence++;
				if(cutsceneSequence == currentCutsceneDuration.size()) {
					cutsceneSequence = 0;
					updateInGameCutscene(false);
				}
				else {
					updateInGameCutscene(currentCutscenePerson.get(cutsceneSequence), currentCutsceneQuotes.get(cutsceneSequence));
				}
			}
		}
	}
	

	public void createUIElements() {
		scaleItem = 1080f/(float)Gdx.graphics.getWidth();
		if(scaleItem < 1) {
			scaleItem = 1;
		}
		this.skin = AssetHandler.skinUI;
		

		this.pointer = new Sprite(AssetHandler.manager.get("house/aim.png", Texture.class));
		pointer.setPosition(
				cameraMap.getViewport().getWorldWidth()/2-pointer.getWidth()/2, 
				cameraMap.getViewport().getWorldHeight()/2-pointer.getHeight()/2);
		
		this.enterHouse = new Sprite(AssetHandler.manager.get("house/MAP_ENTERHOUSE.png", Texture.class));
		enterHouse.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));
		enterHouse.setPosition(-72.5f, -80);
		enterHouse.setAlpha(houseAlpha);
		
		this.inspectHouse = new Sprite(AssetHandler.manager.get("house/MAP_INSPECT.png", Texture.class));
		inspectHouse.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));
		inspectHouse.setPosition(-240, -80);
		inspectHouse.setAlpha(houseAlpha);
		
		this.inspectDialog = new Sprite(AssetHandler.manager.get("player/MAPUI/dialog.png", Texture.class));
		inspectDialog.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));
		inspectDialog.setScale(0.125f);
		inspectDialog.setScale(0.128f, 0.053f);
		inspectDialog.setPosition(-252, -260);
		inspectDialog.setAlpha(houseAlpha);
		
		this.houseText = new Sprite(AssetHandler.manager.get("house/MAP_HOUSE.png", Texture.class));
		houseText.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));
		houseText.setPosition(-66f, 72f);
		houseText.setAlpha(houseAlpha);

		this.enterShop = new Sprite(AssetHandler.manager.get("shop/ENTER_SHOP.png", Texture.class));
		enterShop.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));
		enterShop.setPosition(-72.5f, -80);
		enterShop.setAlpha(shopAlpha);
		
		this.shopText = new Sprite(AssetHandler.manager.get("shop/SHOP.png", Texture.class));
		shopText.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));
		shopText.setPosition(-64.5f, 72.5f);
		shopText.setAlpha(shopAlpha);
		
		this.baseUI = new Sprite(AssetHandler.manager.get("player/MAPUI/BaseUI.png", Texture.class));
		baseUI.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));
		baseUI.setPosition(-5, 30);
		
		this.foodLabel = new Sprite(AssetHandler.manager.get("player/MAPUI/NextLabel.png", Texture.class));
		foodLabel.setScale((cameraUI.getCamera().viewportWidth/1920), (cameraUI.getCamera().viewportHeight/1080));
		foodLabel.setPosition(47, 74.5f);

	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(124/256f, 189/256f, 239/256f, 1);
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
			//behindBackground.draw(main.batch);
			background.draw(main.batch);
			map.getChurch().draw(main.batch);
	   main.batch.end();
	   checkIfClicked(pointer.getX(), pointer.getY());

	   main.batch.begin();
			for(Node node: this.map.getNodes()) {
				if(node != null) {
					node.draw(main.batch);
					disease.calculateHouseIllness(node);
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
		
		checkKC();

		
		makeSceneDark();
		rayHandler.render();
		
		main.ui.draw();
		sequenceOfCutscene();
		checkEndGame();
		showNotes();
		
	}
	
	public void checkEndGame() {
		boolean endGame = true;
		for(Node n : map.getNodes()) {
			if(!n.shouldGameEnd()) {
				endGame = false;
			}
		}
		
		if(endGame) {
			//boolean allDead = false;
			/*for(Node n : map.getNodes()) {
				if(n.areAllDead()) {
					//allDead = true;
				}
			}*/
			
			main.ui.clear();
			main.setScreen(new EndGame(main,false));
		}
	}
	
	
	public void localInputHandler(float delta) {
		float movement = (12000f / 60f);
		if(Gdx.input.isKeyPressed(Keys.W) && !isPaused && !cutsceneActive) {
			if(pointer.getY()+pointer.getHeight()+ movement *delta < background.getHeight()) {
				cameraMap.updateCameraPosition(0 , movement *delta);
				pointer.setPosition(pointer.getX(), pointer.getY() + movement *delta);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.S) && !isPaused && !cutsceneActive) {
			if(pointer.getY()- movement *delta > 0) {
				cameraMap.updateCameraPosition(0 , -movement *delta);
				pointer.setPosition(pointer.getX(), pointer.getY() - movement *delta);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.D) && !isPaused && !cutsceneActive) {
			if(pointer.getX()+pointer.getWidth()+ movement *delta < background.getWidth()) {
				cameraMap.updateCameraPosition(movement *delta, 0);
				pointer.setPosition(pointer.getX()+ movement *delta, pointer.getY());
			}
		}
		if(Gdx.input.isKeyPressed(Keys.A) && !isPaused && !cutsceneActive) {
			if(pointer.getX()- movement *delta > 0) {
				cameraMap.updateCameraPosition(-movement *delta , 0);
				pointer.setPosition(pointer.getX()- movement *delta, pointer.getY());
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.Z) && !isPaused && !cutsceneActive) {
			cameraMap.zoomIn(1);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.C) && !isPaused && !cutsceneActive) {
			cameraMap.zoomIn(-1);
		}

		if(Gdx.input.isKeyJustPressed(Keys.I)) {
			System.out.println("HIT");
			if(!isPaused) {
				System.out.println("ACTIVATED");

				if(noteBackground.isVisible()) {
					System.out.println("ACTIVATED");
					inventoryVisible(false);
				}
				else {
					System.out.println("ACTIVATED");
					inventoryVisible(true);
				}
			}
		}



		enterBuilding = Gdx.input.isKeyJustPressed(Keys.ENTER) && !isPaused && !cutsceneActive;



		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if(letter.isVisible()) {
				this.isPaused = false;
				letter.setVisible(false);;
				paragraph.setVisible(false);
			}
			else {
				togglePaused();
				pause.setVisible(isPaused);
			}
		}
		
		if(Gdx.input.isKeyPressed(Keys.N) && !isPaused && !cutsceneActive) {
			darken = true;
		}

		if(Gdx.input.isKeyJustPressed(Keys.E) && !isPaused && !cutsceneActive) {
			try {
				if(PermanetPlayer.getPermanentPlayerInstance().getEnergy() >=  ENERGY_FOR_RESEARCH && !hoverNode.reachedMaxLevel()) {
					hoverNode.upgradeLevelKnown();
					PermanetPlayer.getPermanentPlayerInstance().changeEnergy(-ENERGY_FOR_RESEARCH);
				}
			} catch(NullPointerException e) {
				Gdx.app.log("E pressed outside of house", "Caught exception outside of the house.");
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.UP)) {
			checkForKC.add("UP");
		}
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			checkForKC.add("DOWN");
		}
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			checkForKC.add("LEFT");
		}
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			checkForKC.add("RIGHT");
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
					darkness -= (1f/60f);
					if(darkness <= 0) {
						PermanetPlayer.getPermanentPlayerInstance().resetEnergy();
						energy.setText(PermanetPlayer.getPermanentPlayerInstance().getEnergy() + "");
						for(Node house : map.getNodes()){
							diseaseHandler(house);
						}
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
	
	public void diseaseHandler(Node house) {
		disease.calculateHouseIllness(house);
		disease.infectResidents(house);
	}
	
	public void checkKC() {
		StringBuilder value = new StringBuilder();

		if(checkForKC.size() == 9) {
			for(String key : checkForKC) {
				value.append(key);
			}

			if(value.toString().equals("UPUPDOWNDOWNLEFTRIGHTLEFTRIGHTRIGHT")) {
				for(Node n : map.getNodes()) {
					for(NPC v : n.getResidents()) {
						v.setHealth(100);
						v.update();
					}
				}
				checkForKC.clear();
			}

			else if(value.toString().equals("UPUPDOWNDOWNLEFTRIGHTLEFTRIGHTLEFT")) {
				for(Node n : map.getNodes()) {
					for(NPC v : n.getResidents()) {
						v.setHealth(-100);
						v.update();
					}
				}
				checkForKC.clear();
			}

			else {
				checkForKC.clear();
			}
		}
	}

	public void updateText(Node n) {
		energy.setText(PermanetPlayer.getPermanentPlayerInstance().getEnergy()+"");
		if(n.getLevel1()) {
			numberOfCharacter.setText(n.getNumberOfAlive());
		} else {
			numberOfCharacter.setText("NOT KNOWN");
		}
		
		if(n.getLevel2()) {
			numberOfCharacterSick.setText(n.getNumberOfSick());
		} else {
			numberOfCharacterSick.setText("NOT KNOWN");
		}
		
		if(n.getLevel3()) {
			numberOfCharacterDiseased.setText(n.getNumberOfDead());
		} else {
			numberOfCharacterDiseased.setText("NOT KNOWN");
		}
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
			if (houseAlpha > 0.1) {
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

	/*
	TODO Fix this
		-player is used in update text
		-need to either replace with permenant player or pass it the required parameters
	 */
	public void enterHouse(Node node) {
		houseHit = true;
		updateText(node);
		hoverNode = node;
		
		if(enterBuilding && (PermanetPlayer.getPermanentPlayerInstance().getEnergy() >= ENERGY_FOR_ENTER_HOUSE)) {
			beforeEntry.setVisible(true);
		}	
	}
	
	public void enterShop(Church church) {
		shopHit = true;
		if(enterBuilding) {
			main.ui.clear();
			enterBuilding = false;
			main.setScreen(new ChurchScreen(main, church, this));
		}
	}
	
	public void checkIfClicked(float x, float y) {
		for(Node node : map.getNodes()) {
			if(node != null) {
				if(node.pointIsWithinSprite(x, y)) {
					enterHouse(node);
					disease.draw(map.getNodes(), node, main.shape);
					break;
				}
				else {
					if(houseHit) {
						houseHit = false;
						//node = null;
					}
					//disease.clear();
				}
			}
		}
		
		if(map.getChurch().pointIsWithinSprite(x, y)) {
			enterShop(map.getChurch());
		}
		else {
			shopHit = false;
		}
	}

	
    /**
     * Holds the window for the pause menu.
     */
    public void createUI() {


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
		beforeEntry();

        //Adds it to the UI Screen.

		dayLabel = new Label("DAY " + day , AssetHandler.fontSize48);
		dayLabel.setPosition(main.ui.getWidth()/2 - dayLabel.getWidth()/2, main.ui.getHeight()/2 - dayLabel.getHeight()/2);
		dayLabel.setVisible(false);
		main.ui.addActor(dayLabel);

		energy = new Label(PermanetPlayer.getPermanentPlayerInstance().getEnergy() + "", AssetHandler.fontSize24);
		energy.setWidth(450f);
		energy.setFontScale(2.5f);
		energy.setAlignment(Align.center);
		energy.setPosition(main.ui.getWidth()-energy.getWidth()-80f, main.ui.getHeight()-energy.getHeight()-200f);
		energy.setVisible(false);
		//energy.setVisible(false);
		main.ui.addActor(energy);
		
		numberOfcharacterTitle = new Label("NUMBER OF ALIVE: ", AssetHandler.fontSize24);
		numberOfcharacterTitle.setWidth(500f);
		numberOfcharacterTitle.setFontScale(1.3f);
		numberOfcharacterTitle.setPosition(90, main.ui.getHeight() - 150);
		numberOfcharacterTitle.setVisible(false);
		main.ui.addActor(numberOfcharacterTitle);
		
		numberOfCharacter = new Label("NOT KNOWN", AssetHandler.fontSize24);
		numberOfCharacter.setWidth(500f);
		numberOfCharacter.setPosition(90, main.ui.getHeight() - 150 -numberOfcharacterTitle.getHeight());
		numberOfCharacter.setVisible(false);
		main.ui.addActor(numberOfCharacter);
		
		numberOfcharacterSickTitle = new Label("NUMBER OF SICK: ", AssetHandler.fontSize24);
		numberOfcharacterSickTitle.setWidth(500f);
		numberOfcharacterSickTitle.setFontScale(1.3f);
		numberOfcharacterSickTitle.setPosition(90, main.ui.getHeight() - 150 -numberOfCharacter.getHeight()-numberOfcharacterTitle.getHeight());
		numberOfcharacterSickTitle.setVisible(false);
		main.ui.addActor(numberOfcharacterSickTitle);
		
		numberOfCharacterSick = new Label("NOT KNOWN", AssetHandler.fontSize24);
		numberOfCharacterSick.setWidth(500f);
		numberOfCharacterSick.setPosition(90, main.ui.getHeight() - 150 -numberOfcharacterSickTitle.getHeight()-numberOfCharacter.getHeight()-numberOfcharacterTitle.getHeight());
		numberOfCharacterSick.setVisible(false);
		main.ui.addActor(numberOfCharacterSick);
		
		numberOfcharacterDiseasedTitle = new Label("NUMBER OF DEAD: ", AssetHandler.fontSize24);
		numberOfcharacterDiseasedTitle.setWidth(500f);
		numberOfcharacterDiseasedTitle.setFontScale(1.3f);
		numberOfcharacterDiseasedTitle.setPosition(90, main.ui.getHeight() - 150 -numberOfCharacterSick.getHeight()-numberOfcharacterSickTitle.getHeight()-numberOfCharacter.getHeight()-numberOfcharacterTitle.getHeight());
		numberOfcharacterDiseasedTitle.setVisible(false);
		main.ui.addActor(numberOfcharacterDiseasedTitle);
		
		numberOfCharacterDiseased = new Label("NOT KNOWN", AssetHandler.fontSize24);
		numberOfCharacterDiseased.setWidth(500f);
		numberOfCharacterDiseased.setPosition(90, main.ui.getHeight() - 150 -numberOfcharacterDiseasedTitle.getHeight() -numberOfCharacterSick.getHeight()-numberOfcharacterSickTitle.getHeight()-numberOfCharacter.getHeight()-numberOfcharacterTitle.getHeight());
		numberOfCharacterDiseased.setVisible(false);

		main.ui.addActor(numberOfCharacterDiseased);

		createInGameCutscene();

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(main.ui);
		Gdx.input.setInputProcessor(multiplexer);
    }

    public void pauseGame() {
		float windowWidth = 200 * scaleItem, windowHeight = 200 * scaleItem;
		pause = new Window("", skin);
		pause.setMovable(false); //So the user can't move the window
		//final TextButton button1 = new TextButton("Resume", skin);

		final Label button1 = new Label("RESUME", AssetHandler.fontSize24);
		button1.setFontScale((windowHeight/200) * scaleItem, (windowHeight/200) * scaleItem);
		button1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				togglePaused();
				pause.setVisible(false);
			}
		});

		Label button2 = new Label("EXIT", AssetHandler.fontSize24);
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

		pause.setSize(pause.getWidth() * scaleItem, pause.getHeight()*scaleItem);
		main.ui.addActor(pause);
	}

	public void beforeEntry() {

		float windowWidth = 400, windowHeight = 600;
		beforeEntry = new Window("", skin);
		beforeEntry.setMovable(false); //So the user can't move the window

		Label Title1 = new Label("SELECT", AssetHandler.fontSize32);
		Label Title2= new Label("GEAR", AssetHandler.fontSize32);

		Table table = new Table();
		table.add(Title1).center().row();
		table.add(Title2).center().row();

		beforeEntry.add(table).center().top().row();

		Table table2 = new Table();


		final Image image1 = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("temp64x64.png")))));
		final Label amount1= new Label("0", AssetHandler.fontSize24);
		amount1.setWidth(40);
		amount1.setHeight(40);
		final Label plus1= new Label("+", AssetHandler.fontSize48);
		plus1.setWidth(40);
		plus1.setHeight(40);
		final Label remove1= new Label("-", AssetHandler.fontSize48);
		remove1.setWidth(40);
		remove1.setHeight(40);

		plus1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("HIT!");
				int amount = Integer.parseInt(amount1.getText().toString());
				amount++;
				amount1.setText(amount);
				if(amount == PermanetPlayer.getPermanentPlayerInstance().getNumberOfMasks()) {
					plus1.setVisible(false);
				}

				if(amount > 0) {
					remove1.setVisible(true);
				}
			}
		});

		remove1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				int amount = Integer.parseInt(amount1.getText().toString());
				amount--;
				amount1.setText(amount);
				if(amount == 0) {
					remove1.setVisible(false);
				}
				if(amount < PermanetPlayer.getPermanentPlayerInstance().getNumberOfMasks()) {
					plus1.setVisible(true);
				}

			}
		});

		if(Integer.parseInt(amount1.getText().toString()) == 0) {
			remove1.setVisible(false);
		}

		final Image image2 = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("temp64x64.png")))));
		final Label amount2= new Label("0", AssetHandler.fontSize24);
		amount2.setWidth(40);
		amount2.setHeight(40);
		final Label plus2= new Label("+", AssetHandler.fontSize48);
		plus2.setWidth(40);
		plus2.setHeight(40);
		final Label remove2= new Label("-", AssetHandler.fontSize48);
		remove2.setWidth(40);
		remove2.setHeight(40);

		plus2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("HIT!");
				int amount = Integer.parseInt(amount2.getText().toString());
				amount++;
				amount2.setText(amount);
				if(amount == PermanetPlayer.getPermanentPlayerInstance().getHealingFluid()) {
					plus2.setVisible(false);
				}

				if(amount > 0) {
					remove2.setVisible(true);
				}
			}
		});

		remove2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				int amount = Integer.parseInt(amount2.getText().toString());
				amount--;
				amount2.setText(amount);
				if(amount == 0) {
					remove2.setVisible(false);
				}
				if(amount < PermanetPlayer.getPermanentPlayerInstance().getHealingFluid()) {
					plus2.setVisible(true);
				}

			}
		});

		if(Integer.parseInt(amount2.getText().toString()) == 0) {
			remove2.setVisible(false);
		}

		final Image image3 = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("temp64x64.png")))));
		final Label amount3= new Label("0", AssetHandler.fontSize24);
		amount3.setWidth(40);
		amount3.setHeight(40);
		final Label plus3= new Label("+", AssetHandler.fontSize48);
		plus3.setWidth(40);
		plus3.setHeight(40);
		final Label remove3= new Label("-", AssetHandler.fontSize48);
		remove3.setWidth(40);
		remove3.setHeight(40);

		plus3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("HIT!");
				int amount = Integer.parseInt(amount3.getText().toString());
				amount++;
				amount3.setText(amount);
				if(amount == PermanetPlayer.getPermanentPlayerInstance().getBurningFluid()) {
					plus3.setVisible(false);
				}

				if(amount > 0) {
					remove3.setVisible(true);
				}
			}
		});

		remove3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				int amount = Integer.parseInt(amount3.getText().toString());
				amount--;
				amount3.setText(amount);
				if(amount == 0) {
					remove3.setVisible(false);
				}
				if(amount < PermanetPlayer.getPermanentPlayerInstance().getBurningFluid()) {
					plus3.setVisible(true);
				}

			}
		});

		if(Integer.parseInt(amount3.getText().toString()) == 0) {
			remove3.setVisible(false);
		}


		table2.add(image1).pad(64).pad(10);
		table2.add(amount1).width(20).pad(10);
		table2.add(plus1).width(40).height(40);
		table2.add(remove1).width(40).height(40).row();

		table2.add(image2).pad(64).pad(10);
		table2.add(amount2).width(20).pad(10);
		table2.add(plus2).width(40).height(40);
		table2.add(remove2).width(40).height(40).row();

		table2.add(image3).pad(64).pad(10);
		table2.add(amount3).width(20).pad(10);
		table2.add(plus3).width(40).height(40);
		table2.add(remove3).width(40).height(40).row();


		beforeEntry.add(table2).center().row();

		Table table3 = new Table();

		Label exit = new Label("EXIT", AssetHandler.fontSize32);
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				beforeEntry.setVisible(false);
			}
		});

		Label enter = new Label("ENTER", AssetHandler.fontSize32);
		enter.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				enterHouse();
			}
		});
		table3.add(exit).width(exit.getWidth()).pad(20);
		table3.add(enter).width(enter.getWidth()).row();

		beforeEntry.add(table3).center().row();

		beforeEntry.pack(); //Important! Correctly scales the window after adding new elements

		//Centre window on screen.
		beforeEntry.setBounds(((main.ui.getWidth() - windowWidth  ) / 2),
				(main.ui.getHeight() - windowHeight) / 2, windowWidth  , windowHeight );
		//Sets the menu as invisible
		beforeEntry.setVisible(false);

		beforeEntry.setSize(beforeEntry.getWidth()*scaleItem, beforeEntry.getHeight()*scaleItem);
		main.ui.addActor(beforeEntry);
	}

	public final void enterHouse() {
		main.ui.clear();
		enterBuilding = false;
		hoverNode.serializeVillagers();
		PermanetPlayer.getPermanentPlayerInstance().changeEnergy(-ENERGY_FOR_ENTER_HOUSE);
		main.setScreen(new HouseScreen(main, hoverNode, this));

	}


	/**
	 * Toggle isPaused variable.
	 */
	public void togglePaused() {
		isPaused = !isPaused;
	}
	
	/*public Player newPlayer(int masksSelected, float healingFluidSelected, float burningFluidSelected) {
		return new Player(masksSelected, healingFluidSelected, burningFluidSelected);
	}*/

	/**
	 * Holds the window for the inventory.
	 */

	public void showNotes() {
		if(noteBackground.isVisible()) {
			int notes = PermanetPlayer.getPermanentPlayerInstance().getNotes().size();
			System.out.println("NUMBER OF NOTES: " + notes);
			if (notes >= 1) {
				note1.setVisible(true);
			}
			else {
				note1.setVisible(false);
			}
			if (notes >= 2) {
				note2.setVisible(true);
			}
			else {
				note2.setVisible(false);
			}
			if (notes >= 3) {
				note3.setVisible(true);
			}
			else {
				note3.setVisible(false);
			}
			if (notes >= 4) {
				note4.setVisible(true);
			}
			else {
				note4.setVisible(false);
			}
			if (notes >= 5) {
				note5.setVisible(true);
			}
			else {
				note5.setVisible(false);
			}
			if (notes >= 6) {
				note6.setVisible(true);
			}
			else {
				note6.setVisible(false);
			}
			if (notes >= 7) {
				note7.setVisible(true);
			}
			else {
				note7.setVisible(false);
			}
			if (notes >= 8) {
				note8.setVisible(true);
			}
			else {
				note8.setVisible(false);
			}
			if (notes >= 9) {
				note9.setVisible(true);
			}
			else {
				note9.setVisible(false);
			}
			if (notes >= 10) {
				note10.setVisible(true);
			}
			else {
				note10.setVisible(false);
			}
			if (notes >= 11) {
				note11.setVisible(true);
			}
			else {
				note11.setVisible(false);
			}
			if (notes >= 12) {
				note12.setVisible(true);
			}
			else {
				note12.setVisible(false);
			}
			if (notes >= 13) {
				note13.setVisible(true);
			}
			else {
				note13.setVisible(false);
			}
			if (notes >= 14) {
				note14.setVisible(true);
			}
			else {
				note14.setVisible(false);
			}
			if (notes >= 15) {
				note15.setVisible(true);
			}
			else {
				note15.setVisible(false);
			}
			if (notes >= 16) {
				note16.setVisible(true);
			}
			else {
				note16.setVisible(false);
			}
			if (notes >= 17) {
				note17.setVisible(true);
			}
			else {
				note17.setVisible(false);
			}
			if (notes >= 18) {
				note18.setVisible(true);
			}
			else {
				note18.setVisible(false);
			}
			if (notes >= 19) {
				note19.setVisible(true);
			}
			else {
				note19.setVisible(false);
			}
			if (notes >= 20) {
				note20.setVisible(true);
			}
			else {
				note20.setVisible(false);
			}
		}
	}

	public void showNote(Note n) {
		isPaused = true;
		paragraph.setText(n.getInfo());
		paragraph.setVisible(true);
		updateParagraphPosition();
		letter.setVisible(true);
	}

	public void updateParagraphPosition() {
		paragraph.setPosition(main.ui.getWidth()/2-letter.getWidth()/2 + 50, main.ui.getHeight()/2);
	}

	public void inventory() {
		Sprite s2 = new Sprite(AssetHandler.manager.get("player/MAPUI/dialog.png", Texture.class));
		noteBackground = new Image(new SpriteDrawable(s2));
		noteBackground.setScaleY(1.62f);
		noteBackground.setScaleX(3.65f);
		noteBackground.setVisible(false);

		note1 = new Label("NOTE 1", AssetHandler.fontSize24);
		note1.setPosition(50, 1050);
		note1.setWidth(note1.getWidth() + note1.getWidth() + 400);
		note1.setAlignment(Align.center);
		note1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("HIT1");
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(0));
			}
		});
		note1.setVisible(false);

		noteTitle = new Label("NOTES COLLECTED", AssetHandler.fontSize32);
		noteTitle.setPosition(50, 1100);
		noteTitle.setWidth(note1.getWidth());
		noteTitle.setAlignment(Align.center);
		noteTitle.setVisible(false);

		note2 = new Label("NOTE 2", AssetHandler.fontSize24);
		note2.setPosition(50, 1050-note1.getHeight());
		note2.setWidth(note1.getWidth());
		note2.setAlignment(Align.center);
		note2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("HIT2");
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(1));
			}
		});
		note2.setVisible(false);


		note3 = new Label("Note 3", AssetHandler.fontSize24);
		note3.setPosition(50, 1050-(note1.getHeight()*2));
		note3.setWidth(note1.getWidth());
		note3.setAlignment(Align.center);
		note3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("HIT3");
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(2));
			}
		});
		note3.setVisible(false);

		note4 = new Label("Note 4", AssetHandler.fontSize24);
		note4.setPosition(50, 1050-(note1.getHeight()*3));
		note4.setWidth(note1.getWidth());
		note4.setAlignment(Align.center);
		note4.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("HIT4");
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(3));
			}
		});
		note4.setVisible(false);

		note5 = new Label("Note 5", AssetHandler.fontSize24);
		note5.setPosition(50, 1050-(note1.getHeight()*4));
		note5.setWidth(note1.getWidth());
		note5.setAlignment(Align.center);
		note5.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("HIT5");
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(4));
			}
		});
		note5.setVisible(false);

		note6 = new Label("Note 6", AssetHandler.fontSize24);
		note6.setPosition(50, 1050-(note1.getHeight()*5));
		note6.setWidth(note1.getWidth());
		note6.setAlignment(Align.center);
		note6.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(5));
			}
		});
		note6.setVisible(false);

		note7 = new Label("Note 7", AssetHandler.fontSize24);
		note7.setPosition(50, 1050-(note1.getHeight()*6));
		note7.setWidth(note1.getWidth());
		note7.setAlignment(Align.center);
		note7.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(6));
			}
		});
		note7.setVisible(false);

		note8 = new Label("Note 8", AssetHandler.fontSize24);
		note8.setPosition(50, 1050-(note1.getHeight()*7));
		note8.setWidth(note1.getWidth());
		note8.setAlignment(Align.center);
		note8.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(7));
			}
		});
		note8.setVisible(false);

		note9 = new Label("Note 9", AssetHandler.fontSize24);
		note9.setPosition(50, 1050-(note1.getHeight()*8));
		note9.setWidth(note1.getWidth());
		note9.setAlignment(Align.center);
		note9.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(8));
			}
		});
		note9.setVisible(false);

		note10 = new Label("Note 10", AssetHandler.fontSize24);
		note10.setPosition(50, 1050-(note1.getHeight()*9));
		note10.setWidth(note1.getWidth());
		note10.setAlignment(Align.center);
		note10.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(9));
			}
		});
		note10.setVisible(false);

		note11 = new Label("Note 11", AssetHandler.fontSize24);
		note11.setPosition(50, 1050-(note1.getHeight()*10));
		note11.setWidth(note1.getWidth());
		note11.setAlignment(Align.center);
		note11.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(10));
			}
		});
		note11.setVisible(false);

		note12 = new Label("Note 12", AssetHandler.fontSize24);
		note12.setPosition(50, 1050-(note1.getHeight()*11));
		note12.setWidth(note1.getWidth());
		note12.setAlignment(Align.center);
		note12.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(11));
			}
		});
		note12.setVisible(false);

		note13 = new Label("Note 13", AssetHandler.fontSize24);
		note13.setPosition(50, 1050-(note1.getHeight()*12));
		note13.setWidth(note1.getWidth());
		note13.setAlignment(Align.center);
		note13.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(12));
			}
		});
		note13.setVisible(false);

		note14 = new Label("Note 14", AssetHandler.fontSize24);
		note14.setPosition(50, 1050-(note1.getHeight()*13));
		note14.setWidth(note1.getWidth());
		note14.setAlignment(Align.center);
		note14.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(13));
			}
		});
		note14.setVisible(false);

		note15 = new Label("Note 15", AssetHandler.fontSize24);
		note15.setPosition(50, 1050-(note1.getHeight()*14));
		note15.setWidth(note1.getWidth());
		note15.setAlignment(Align.center);
		note15.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(14));
			}
		});
		note15.setVisible(false);

		note16 = new Label("Note 16", AssetHandler.fontSize24);
		note16.setPosition(50, 1050-(note1.getHeight()*15));
		note16.setWidth(note1.getWidth());
		note16.setAlignment(Align.center);
		note16.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(15));
			}
		});
		note16.setVisible(false);

		note17 = new Label("Note 17", AssetHandler.fontSize24);
		note17.setPosition(50, 1050-(note1.getHeight()*16));
		note17.setWidth(note1.getWidth());
		note17.setAlignment(Align.center);
		note17.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(16));
			}
		});
		note17.setVisible(false);

		note18 = new Label("Note 18", AssetHandler.fontSize24);
		note18.setPosition(50, 1050-(note1.getHeight()*17));
		note18.setWidth(note1.getWidth());
		note18.setAlignment(Align.center);
		note18.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(17));
			}
		});
		note18.setVisible(false);

		note19 = new Label("Note 19", AssetHandler.fontSize24);
		note19.setPosition(50, 1050-(note1.getHeight()*18));
		note19.setWidth(note1.getWidth());
		note19.setAlignment(Align.center);
		note19.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(18));
			}
		});
		note19.setVisible(false);

		note20 = new Label("Note 20", AssetHandler.fontSize24);
		note20.setPosition(50, 1050-(note1.getHeight()*19));
		note20.setWidth(note1.getWidth());
		note20.setAlignment(Align.center);
		note20.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showNote(PermanetPlayer.getPermanentPlayerInstance().getNotes().get(19));
			}
		});
		note20.setVisible(false);

		main.ui.addActor(noteBackground);
		main.ui.addActor(noteTitle);
		main.ui.addActor(note1);
		main.ui.addActor(note2);
		main.ui.addActor(note3);
		main.ui.addActor(note4);
		main.ui.addActor(note5);
		main.ui.addActor(note6);
		main.ui.addActor(note7);
		main.ui.addActor(note8);
		main.ui.addActor(note9);
		main.ui.addActor(note10);
		main.ui.addActor(note11);
		main.ui.addActor(note12);
		main.ui.addActor(note13);
		main.ui.addActor(note14);
		main.ui.addActor(note15);
		main.ui.addActor(note16);
		main.ui.addActor(note17);
		main.ui.addActor(note18);
		main.ui.addActor(note19);
		main.ui.addActor(note20);

		letter = new Image(new SpriteDrawable(new Sprite(AssetHandler.manager.get("pickups/letter/LETTER.png", Texture.class))));
		letter.setPosition(main.ui.getWidth()/2-letter.getWidth()/2, main.ui.getHeight()/2-letter.getHeight()/2);
		letter.setVisible(false);
		main.ui.addActor(letter);

		paragraph = new Label("", createLabelStyleWithBackground(Color.BLACK));
		paragraph.setWidth(letter.getWidth()-90);
		paragraph.setWrap(true);
		paragraph.setPosition(main.ui.getWidth()/2, main.ui.getHeight()/2);
		paragraph.setVisible(false);
		main.ui.addActor(paragraph);

	}

	public void inventoryVisible(boolean visible)  {
		noteBackground.setVisible(visible);
		noteTitle.setVisible(visible);
		note1.setVisible(visible);
		note2.setVisible(visible);
		note3.setVisible(visible);
		note4.setVisible(visible);
		note5.setVisible(visible);
		note6.setVisible(visible);
		note7.setVisible(visible);
		note8.setVisible(visible);
		note9.setVisible(visible);
		note10.setVisible(visible);
		note11.setVisible(visible);
		note12.setVisible(visible);
		note13.setVisible(visible);
		note14.setVisible(visible);
		note15.setVisible(visible);
		note16.setVisible(visible);
		note17.setVisible(visible);
		note18.setVisible(visible);
		note19.setVisible(visible);
		note20.setVisible(visible);
	}


	private Label.LabelStyle createLabelStyleWithBackground(Color color) {
		///core/assets/font/Pixel.ttf
		FileHandle fontFile = Gdx.files.internal("font/Pixel.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 60;
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = generator.generateFont(parameter);
		labelStyle.fontColor = color;
		return labelStyle;
	}
}
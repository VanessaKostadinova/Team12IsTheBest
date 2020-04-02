package com.mygdx.game;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.assets.AssetHandler;
import com.mygdx.camera.Camera;
import com.mygdx.extras.PermanetPlayer;
import com.mygdx.house.Torch;
import com.mygdx.renderable.*;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.mygdx.story.Note;
import com.mygdx.story.StoryHandler;

/**
 * Contains the information of each of the houses.
 * @author Inder, Vanessa, Max
 * @version 1.5
 */
public class HouseScreen implements Screen {

	/** The main class instance */
	private Main main;
	/** The node class instance */
	private Node node;
	/** The stateTime of house screen */
	private float stateTime;
	/** Used to count when to decrement the mask */
	private float secondCounter;
	/** List of sprites which are pickups */
	private List<Sprite> pickups;
	/** The camera for the screen */
	private Camera camera;
	/** The Camera for cameraUI */
	private Camera cameraUI;
	/** The background for the image */
	private Image letter;
	/** Image icon to leave house */
	private Image icon;
	/** The label for the paragraph for note */
	private Label paragraph;
	/** The houseinputhandler to handle the input and collision*/
	private HouseInputHandler handler;
	/** The instance of MapScreen to return to */
	private MapScreen mapScreen;
	/** The UI image */
	private Image ui;
	/** The current spray */
	private Image uiCurrentSpray;
	/** The label for amount of gold. */
	private Label goldLabel;
	/** The label for player sanity. */
	private Label sanityLabel;
	/** The label for number of masks  */
	private Label numberOfMasksLabel;
	/** The label for amount of cure  */
	private Label amountOfCureLabel;
	/** The label for amount of burn  */
	private Label amountOfBurnLabel;
	/** The texture to show the mask bar */
	private Texture maskBar;
	/** The image for mask ui */
	private Image bar;
	/** World entity for Box2D */
	private World world;
	/** RayHandler to handle the rays emitted by the torch */
	private RayHandler rayHandler;
	/** Darkness of the screen */
	private float darkness;
	/** The scale of the item */
	private float scaleItem;
	/** The pause window */
	private Window pause;
	/** The UI Skin */
	private Skin skin;
	/** The InputMultuplexer to combine different inputs */
	private InputMultiplexer input;
	/** One point light used for the player spray */
	private Light light;
	/** Set drawable for the fire  */
    private SpriteDrawable fire;
	/** Set drawable for the cure  */
	private SpriteDrawable cure;
	/** A instance of a bullet  */
	private Bullet bullet;
	/** Images for cutscenes */
	private Image overlayCutscene, dialogCutscene, speakerImage;
	/** Label to set person speaking */
	private Label personToSpeak;
	/** Label to set the description of text */
	private Label setDescriptionOfText;
	/** A number of list for each cutscene  */
	private List<String> currentCutsceneQuotes, currentCutscenePerson, currentCutsceneImage;
	/** The current cutscene sequence */
	private int cutsceneSequence;
	/** The amount of fake npc's */
	private ArrayList<NPC> fakeNPCs;
	/** Stores the number of initial number of masks */
	private int initialNumberOfMasks;
	/** Initial Amount of cure */
	private float amountOfCure;
	/** Initial Amount of flame */
	private float amountOfFlame;
	/** Texture for storyDoctor */
	private Sprite storyDoctor = null;
	/** The mask durablity at the start */
	private final float maskDurabiltyAtStart = Player.getInstance().getCurrentMaskDuration();

	/** The sanity level at the start */
	private final float sanityAtStart = PermanetPlayer.getPermanentPlayerInstance().getSanity();

	public HouseScreen(Main main, Node node, MapScreen mapScreen) {
			this.mapScreen = mapScreen;
			this.main = main;
			this.node = node;
			this.bullet = null;

			this.initialNumberOfMasks = PermanetPlayer.getPermanentPlayerInstance().getChosenItems()[0];
			this.amountOfCure = PermanetPlayer.getPermanentPlayerInstance().getChosenItems()[1];
			this.amountOfFlame = PermanetPlayer.getPermanentPlayerInstance().getChosenItems()[2];

			this.darkness = 0.2f;
			this.skin = AssetHandler.SKIN_UI;
			this.fakeNPCs = new ArrayList<>();
			cure = new SpriteDrawable(new Sprite(AssetHandler.MANAGER.get("house/UI/CureSpray.png", Texture.class)));
			fire = new SpriteDrawable(new Sprite(AssetHandler.MANAGER.get("house/UI/FireSpray.png", Texture.class)));

			currentCutsceneQuotes = new LinkedList<>();
			currentCutscenePerson = new LinkedList<>();
			currentCutsceneImage = new LinkedList<>();

			float w = Gdx.graphics.getWidth();
			scaleItem = w/1920;
			
			camera = new Camera(256, 1080f, 1920f);
			camera.getCamera().position.set(camera.getCamera().viewportWidth / 2f , camera.getCamera().viewportHeight / 2f, 0);
			
			cameraUI = new Camera(1920, 1080f, 1920f);
			cameraUI.getCamera().position.set(cameraUI.getCamera().viewportWidth / 2f , cameraUI.getCamera().viewportHeight / 2f, 0);

			Player.getInstance().getSprite().setX(camera.getViewport().getWorldWidth() / 2 - Player.getInstance().getSprite().getWidth() / 2);
			Player.getInstance().getSprite().setY(camera.getViewport().getWorldHeight() / 2 - Player.getInstance().getSprite().getHeight() / 2);
			//Player.getInstance().updateSprite(camera.getViewport().getWorldWidth() / 2 - Player.getInstance().getSprite().getWidth() / 2, camera.getViewport().getWorldHeight() / 2 - Player.getInstance().getSprite().getHeight() / 2);
			Player.getInstance().getSpray().getSprite().setPosition(Player.getInstance().getSprite().getX() - Player.getInstance().getSprite().getWidth() / 2 + 10f, Player.getInstance().getSprite().getY() + Player.getInstance().getSprite().getHeight());
			Player.getInstance().setRotation(90);

			setAllItemPickups();

			UIElements();

			this.world = new World(new Vector2(0,0), false);

			letter = new Image(new SpriteDrawable(new Sprite(AssetHandler.MANAGER.get("pickups/letter/LETTER.png", Texture.class))));
			letter.setPosition(main.ui.getWidth()/2-letter.getWidth()/2, main.ui.getHeight()/2-letter.getHeight()/2);
			letter.setVisible(false);
			main.ui.addActor(letter);

			paragraph = new Label("VOID", AssetHandler.FONT_SIZE_60_SUBTITLES_BLACK);
			paragraph.setWidth(letter.getWidth()-90);
			paragraph.setWrap(true);
			paragraph.setPosition(main.ui.getWidth()/2+50, main.ui.getHeight()/2);
			paragraph.setVisible(false);

			main.ui.addActor(paragraph);
			createInGameCutscene();
			pauseGame();
			handler = new HouseInputHandler(camera, node.getArray(), pause, node.getNPCs(), paragraph, letter, icon, world);
	        handler.setPaused(false);
	
			node.getHouse().createBodies(world);
			Player.getInstance().setBody(world);
			//p.setSprayBody(world);
			this.rayHandler = new RayHandler(world);
			this.rayHandler.setAmbientLight(darkness);
			this.rayHandler.setShadows(true);
			light = new PointLight(rayHandler, 200, Player.getInstance().getSpray().getColor(),50f, Player.getInstance().getSpray().getSprite().getX() + Player.getInstance().getSpray().getSprite().getWidth()/2,Player.getInstance().getSpray().getSprite().getY()+Player.getInstance().getSpray().getSprite().getHeight()/2);
			light.setSoftnessLength(2f);
			light.setContactFilter(Constants.PLAYER, Constants.PLAYER, Constants.PLAYER);
			
			Light player = new PointLight(rayHandler, 10, Color.BLACK,25f, Player.getInstance().getSpray().getSprite().getX() + Player.getInstance().getSpray().getSprite().getWidth()/2,Player.getInstance().getSpray().getSprite().getY()+Player.getInstance().getSpray().getSprite().getHeight()/2);
			player.setXray(true);
			player.setSoftnessLength(1f);
			player.attachToBody(Player.getInstance().getBody());
	        setTorchLights();
			spawnFakeNPC();

			//b2dr = new Box2DDebugRenderer();
			if(!StoryHandler.TutorialPart2) {
				startCreatingCutscene("cutscene/ingame/scripts/Scene4.csv");
				StoryHandler.TutorialPart2 = true;
			}
	        
			input = new InputMultiplexer();
			input.addProcessor(handler);
			input.addProcessor(main.ui);
	        Gdx.input.setInputProcessor(input);

			initialStoryHandler();
	        
		}

		/**
		 * Used to draw and create the cutscene items on stream.
		 */
		public void createInGameCutscene() {
			Sprite s = new Sprite(AssetHandler.MANAGER.get("cutscene/ingame/cutsceneOverlay.png", Texture.class));
			s.setAlpha(0.9f);
			overlayCutscene = new Image(new SpriteDrawable(s));
			overlayCutscene.setPosition(main.ui.getWidth()/2 - overlayCutscene.getWidth()/2 - 130, main.ui.getHeight()/2 - overlayCutscene.getHeight()/2 - 100);
			overlayCutscene.setScale(2f);
			overlayCutscene.setVisible(false);

			Sprite s3 = new Sprite(AssetHandler.MANAGER.get("cutscene/ingame/characterImages/templateCutsceneSpeaker.png", Texture.class));
			speakerImage = new Image(new SpriteDrawable(s3));
			speakerImage.setPosition(660, 430);
			speakerImage.setScale(2f);
			speakerImage.setVisible(false);

			Sprite s2 = new Sprite(AssetHandler.MANAGER.get("player/MAPUI/dialog.png", Texture.class));
			dialogCutscene = new Image(new SpriteDrawable(s2));
			dialogCutscene.setPosition(50, 50);
			dialogCutscene.setScaleY(0.5f);
			dialogCutscene.setScaleX(3.0666f);
			dialogCutscene.setVisible(false);

			personToSpeak = new Label("YOU:", AssetHandler.FONT_SIZE_CUT_SCENE_24);
			personToSpeak.setPosition(90, 350F);
			personToSpeak.setVisible(false);

			setDescriptionOfText = new Label("NULL", AssetHandler.FONT_SIZE_CUT_SCENE_24);
			setDescriptionOfText.setAlignment(Align.topLeft);
			setDescriptionOfText.setWidth(1710);
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
		 * Used to handle story throughout screen
		 */
		public void storyHandler() {
			if(Player.getInstance().getCurrentMaskDuration() < 10 && !StoryHandler.TutorialPart3) {
				startCreatingCutscene("cutscene/ingame/scripts/Scene5.csv");
				StoryHandler.TutorialPart3 = true;
			}
		}

		/**
		 * Used to check the initial story handler.
		 */
		public void initialStoryHandler() {
			if(PermanetPlayer.getPermanentPlayerInstance().getNotes().size() >= 12 && !StoryHandler.allNotesSequence) {
				storyDoctor = new Sprite(AssetHandler.MANAGER.get("cutscene/ingame/storyTextures/OtherDoctor.png", Texture.class));
				storyDoctor.setPosition(
						(camera.getViewport().getWorldWidth() / 2 - Player.getInstance().getSprite().getWidth() / 2) - 40,
						(camera.getViewport().getWorldWidth() / 2 - Player.getInstance().getSprite().getWidth() / 2) + 40);
				startCreatingCutscene("cutscene/ingame/scripts/Scene10.csv");
				StoryHandler.allNotesSequence =true;
			}

			if(StoryHandler.allNotesSequence && !StoryHandler.killedOtherGuy) {
				storyDoctor = new Sprite(AssetHandler.MANAGER.get("cutscene/ingame/storyTextures/OtherDoctor.png", Texture.class));
				storyDoctor.setPosition(
						(camera.getViewport().getWorldWidth() / 2 - Player.getInstance().getSprite().getWidth() / 2) - 40,
						(camera.getViewport().getWorldWidth() / 2 - Player.getInstance().getSprite().getWidth() / 2) + 40);
			}
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
			handler.setCutsceneActive(value);
		}

		/**
		 * Updates the text of script
		 * @param person Person's name.
		 * @param text What the person is saying.
		 */
		public void updateInGameCutscene(String person, String text, String URL) {
			this.personToSpeak.setText(person);
			this.setDescriptionOfText.setText(text);
			this.speakerImage.setDrawable(new TextureRegionDrawable(AssetHandler.MANAGER.get("cutscene/ingame/characterImages/" + URL, Texture.class)));
		}

		/**
		 * Intialising the new cutscene based of a text file.
		 * @param file The file of the .csv file for the cutscene.
		 */
		public void startCreatingCutscene(String file) {

			FileHandle n = Gdx.files.internal(file);

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
				currentCutsceneImage.add(data[0]);
				currentCutscenePerson.add(data[1]);
				currentCutsceneQuotes.add(data[2]);
			}
			updateInGameCutscene(true);
			updateInGameCutscene(currentCutscenePerson.get(cutsceneSequence), currentCutsceneQuotes.get(cutsceneSequence), currentCutsceneImage.get(cutsceneSequence));
		}

		/**
		 * Handles the switching of the cutscene.
		 */
		public void sequenceOfCutscene() {
			if(handler.getCutsceneActive()) {
				if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !handler.getPaused()) {
					cutsceneSequence++;
					if(cutsceneSequence == currentCutsceneImage.size()) {
						cutsceneSequence = 0;
						updateInGameCutscene(false);
					}
					else {
						updateInGameCutscene(currentCutscenePerson.get(cutsceneSequence), currentCutsceneQuotes.get(cutsceneSequence), currentCutsceneImage.get(cutsceneSequence));
					}
				}
			}
			else {
				currentCutsceneQuotes.clear();
				currentCutscenePerson.clear();
				currentCutsceneImage.clear();
			}
		}

		@Override
		public void show() {
			// TODO Auto-generated method stub
	
		}
	
		@Override
		public void render(float delta) {
			stateTime = stateTime + delta;
			secondCounter = secondCounter + delta;
			rayHandler.setCombinedMatrix(camera.getCamera());
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			
			//b2dr.render(world, camera.getCamera().combined);
			main.batch.begin();

			if(secondCounter > Player.getInstance().getMaskDurationSeconds()) {
				secondCounter -= Player.getInstance().getMaskDurationSeconds();
				reduceMask();
			}
			
	
			main.batch.setProjectionMatrix(camera.getCamera().combined);
			renderMap();
			drawNPC(main.batch);

			drawFakeNPC(main.batch);
			updateAllBullets();
			Player.getInstance().draw(main.batch);
			drawTorchs();
			drawAllItemPickups(main.batch);
			Player.getInstance().getSpray().getSprite().setRegion(Player.getInstance().getSpray().getAnimation().getKeyFrame(stateTime, true));
			Player.getInstance().getSpray().draw(main.batch);
			
			if(Player.getInstance().getSprayIndex() == 0) {
				uiCurrentSpray.setDrawable(cure);
			}
			if(Player.getInstance().getSprayIndex() == 1) {
				uiCurrentSpray.setDrawable(fire);
			}

			if(storyDoctor != null) {
				storyDoctor.draw(main.batch);
				if(Player.getInstance().getSpray().getIsActive()) {
					if(Player.getInstance().getSprayIndex() == 1) {
						if(storyDoctor.getBoundingRectangle().overlaps(Player.getInstance().getSpray().getSprite().getBoundingRectangle())) {
							StoryHandler.killedOtherGuy = true;
							StoryHandler.allNotesSequence = true;
							storyDoctor.setAlpha(0);
						}
					}
				}
			}



			handler.sprayWithVillagerCollision(node.getNPCs());

			if(Player.getInstance().getSprayIndex() == 0) {
				amountOfCure = handler.spray(amountOfCure);
				amountOfCureLabel.setText( (int) amountOfCure + "");
			}
			if(Player.getInstance().getSprayIndex() == 1) {
				amountOfFlame = handler.spray(amountOfFlame);
				amountOfBurnLabel.setText( (int) amountOfFlame + "");
			}

			updateSprayLight();
			handler.movement(Player.getInstance().getAnimation().getKeyFrame(stateTime, true), delta);
			
			main.batch.setProjectionMatrix(cameraUI.getCamera().combined);
			drawUI(main.batch);
	
	
	
			//b2dr.render(world, camera.getCamera().combined);
			rayHandler.updateAndRender();
			main.batch.end();
			main.ui.draw();
			sequenceOfCutscene();
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
				if(icon.isVisible()) {
					//p.writeToPlayerFile();
					dispose();
					Player.getInstance().setCoordinates(new Vector2(0, 0));
					Player.getInstance().getSprite().setX(0);
					Player.getInstance().getSprite().setY(0);
					main.ui.clear();
					PermanetPlayer.getPermanentPlayerInstance().reduceNumberOfMasks(PermanetPlayer.getPermanentPlayerInstance().getChosenItems()[0]);
					PermanetPlayer.getPermanentPlayerInstance().reduceCureSpray(PermanetPlayer.getPermanentPlayerInstance().getChosenItems()[1]);
					PermanetPlayer.getPermanentPlayerInstance().reduceBurnSpray(PermanetPlayer.getPermanentPlayerInstance().getChosenItems()[2]);
					Player.getInstance().resetMask();
					mapScreen.createUI();
					mapScreen.createInGameCutscene();
					mapScreen.inventory();
					mapScreen.decisionMaking();
					mapScreen.pauseGame();
					if(StoryHandler.TutorialPart2 && !StoryHandler.TutorialPart3) {
						StoryHandler.TutorialPart3 = true;
					}
					if(!StoryHandler.TutorialDone) {
						StoryHandler.didCureFirstHouse = true;
						for(NPC n : node.getNPCs()) {
							if(!n.getStatus().equals("Alive")) {
								StoryHandler.didCureFirstHouse = false;
							}
						}
					}
					handler.stopAllMusicAndDispose();
					main.setScreen(mapScreen);
				}
			}
			AI();
			storyHandler();
		}

		/**
		 * Used to reduce the mask if the scene allows the mask to be reduced.
		 */
		public void reduceMask() {
			if(!handler.getPaused() && !handler.getCutsceneActive()) {
				Player.getInstance().reduceMask();
			}
		}

		/**
		 * Updates the spray light when the spray is changed
		 */
		public void updateSprayLight() {
			light.setColor(Player.getInstance().getSpray().getColor());

			float angle =Player.getInstance().getSprite().getRotation() -90f;
			if(angle > 180) {
				angle =  360 - angle;
			}
			if(angle == 0) {
				angle = 360;
			}
			
			
			light.setPosition(Player.getInstance().getSpray().getSprite().getX()+Player.getInstance().getSprite().getWidth()/2,Player.getInstance().getSpray().getSprite().getY()+Player.getInstance().getSpray().getSprite().getHeight()/2);
			//light.setDirection(p.getSprite().getRotation());
			light.setActive(handler.getPressed() && !handler.getPaused() && !handler.getCutsceneActive() && Player.getInstance().getSpray().getIsActive());
		}


		/**
		 * Create all the UI Elements for the House Screen
		 */
		public void UIElements() {
			
			icon = new Image(new SpriteDrawable(new Sprite(AssetHandler.MANAGER.get("player/icon/ICON.png", Texture.class))));
			icon.setPosition(main.ui.getWidth()/2+Player.getInstance().getSprite().getWidth()+icon.getWidth(), main.ui.getHeight()/2+Player.getInstance().getSprite().getHeight()+icon.getHeight());
			icon.setVisible(false);
			main.ui.addActor(icon);
			
			uiCurrentSpray = new Image(new SpriteDrawable(new Sprite(AssetHandler.MANAGER.get("house/UI/CureSpray.png", Texture.class))));
			//ui.setDrawable(new SpriteDrawable(new Sprite(main.assets.manager.get("house/UI/MAPUI.png", Texture.class))));
			uiCurrentSpray.setPosition(10, main.ui.getHeight() - uiCurrentSpray.getHeight() - 10);		
			main.ui.addActor(uiCurrentSpray);
			
			ui = new Image(new SpriteDrawable(new Sprite(AssetHandler.MANAGER.get("house/UI/MAPUI.png", Texture.class))));
			//ui.setDrawable(new SpriteDrawable(new Sprite(main.assets.manager.get("house/UI/MAPUI.png", Texture.class))));
			ui.setPosition(10+uiCurrentSpray.getWidth(), main.ui.getHeight() - ui.getHeight() - 10);		
			main.ui.addActor(ui);
			

			goldLabel = new Label(Player.getInstance().getFood()+"", AssetHandler.FONT_SIZE_60_SUBTITLES_WHITE);
			goldLabel.setPosition(200+uiCurrentSpray.getWidth(), main.ui.getHeight()-100);
			goldLabel.setFontScale(0.6f);
			main.ui.addActor(goldLabel);

			sanityLabel = new Label(Player.getInstance().getSanityLabel(), AssetHandler.FONT_SIZE_60_SUBTITLES_WHITE);
			sanityLabel.setPosition(240+uiCurrentSpray.getWidth(), main.ui.getHeight()-230);
			sanityLabel.setFontScale(0.6f);
			main.ui.addActor(sanityLabel);

			numberOfMasksLabel = new Label(initialNumberOfMasks + "", AssetHandler.FONT_SIZE_60_SUBTITLES_WHITE);
			numberOfMasksLabel.setPosition(375+uiCurrentSpray.getWidth(), main.ui.getHeight()-295);
			numberOfMasksLabel.setFontScale(0.6f);
			main.ui.addActor(numberOfMasksLabel);

			amountOfBurnLabel = new Label((int) amountOfFlame + "", AssetHandler.FONT_SIZE_60_SUBTITLES_WHITE);
			amountOfBurnLabel.setPosition(375+uiCurrentSpray.getWidth(), main.ui.getHeight()-360);
			amountOfBurnLabel.setFontScale(0.6f);
			main.ui.addActor(amountOfBurnLabel);

			amountOfCureLabel = new Label((int) amountOfCure + "", AssetHandler.FONT_SIZE_60_SUBTITLES_WHITE);
			amountOfCureLabel.setPosition(375+uiCurrentSpray.getWidth(), main.ui.getHeight()-425);
			amountOfCureLabel.setFontScale(0.6f);
			main.ui.addActor(amountOfCureLabel);
			
			maskBar = AssetHandler.MANAGER.get("house/UI/BAR.png", Texture.class);
			bar = new Image(new SpriteDrawable(new Sprite(maskBar)));
			bar.setPosition(200+uiCurrentSpray.getWidth(), main.ui.getHeight()-125);
			bar.setWidth(250 * (Player.getInstance().getCurrentMaskDuration()/Player.getInstance().getInitialMaskDuration()));
			main.ui.addActor(bar);
			
		}


		/**
		 * Handling of the AI for each of NPC to allow for NPC to shoot Doctor.
		 */
		public void AI() {
			for(NPC n : node.getNPCs()) {
				if(n.getStatus().equals("Alive")) {

					float rotation = (float) MathUtils.radiansToDegrees * MathUtils.atan2(n.getSprite().getY() - Player.getInstance().getSprite().getY(), n.getSprite().getX()-Player.getInstance().getSprite().getX());
					rotation -= 90;
					if (rotation < 0) rotation += 360;
					n.getSprite().setRotation(rotation);

					Vector2 villager = new Vector2(n.getSprite().getX(), n.getSprite().getY());
					Vector2 player = new Vector2(Player.getInstance().getSprite().getX(), Player.getInstance().getSprite().getY());
					if(n.getAggressive() && player.dst(villager) < 100) {
						float dx = Player.getInstance().getSprite().getX() - n.getSprite().getX();
						float dy = Player.getInstance().getSprite().getY() - n.getSprite().getY();

						dx = dx/20f;
						dy = dy/20f;


						Bullet b = new Bullet(dx, dy, n.getSprite().getX()+16, n.getSprite().getY()+16, node.getArray() , rotation);
						if(bullet == null) {
							bullet = b;
						}
					}
				}
			}
		}

		/**
		 * Update the drawing of the bullet as it moves across the map.
		 */
		public void updateAllBullets() {
			if(bullet != null && !handler.getPaused() && !handler.getCutsceneActive()) {
				bullet.draw(main.batch);
				bullet.updateBullet();
				if(bullet.getSprite().getY() < 0 || bullet.getSprite().getX() < 0) {
					bullet = null;
				}
				else if(bullet.getSprite().getY() > node.getArray().length*32 || bullet.getSprite().getX() > node.getArray()[0].length*32 ) {
					bullet = null;
				}
				else if(bullet.hasCollided()) {
					bullet = null;
				}

			}

		}


		/**
		 * Update the paragraph position as the text is changed.
		 */
		public void updateParagraphPosition() {
			paragraph.setPosition(main.ui.getWidth()/2-letter.getWidth()/2 + 50, main.ui.getHeight()/2);
		}

		/**
		 * Draw the UI for house screen which are handled by the sprite batch.
		 * @param batch SpriteBatch of the current screen
		 */
		public void drawUI(SpriteBatch batch) {
			updateBar();
			goldLabel.setText(Player.getInstance().getFood()+"");
		}
	
		@Override
		public void resize(int width, int height) {
			// TODO Auto-generated method stub
			main.ui.getViewport().update(width, height);
			camera.getViewport().update(width, height);
			camera.getCamera().viewportHeight = 256*((float)height/(float) width);
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
			// TODO Auto-generated method stub
		}
		/**
		 * Render the map on to the house screen.
		 */
		private void renderMap() {
			int[][] workingArray = node.getHouse().getLevel();
			int yCoord = 0;
	
			for(int[] i : workingArray) {
				int xCoord = 0;
	
				for(int r : i) {
					main.batch.draw(node.getHouse().getTexture(r), xCoord, yCoord);
					xCoord += 32;
				}
				yCoord += 32;
			}
	
		}
		/**
		 * Draw all the torch lights.
		 */
		private void drawTorchs() {
			for(Torch t :node.getHouse().getTorches()) {
				t.draw(main.batch);
			}
		}
		/**
		 * Set all the torch lights.
		 */
		private void setTorchLights() {
			for(Torch t :node.getHouse().getTorches()) {
				Light l = new PointLight(rayHandler, 100, Color.ORANGE, 200f, t.getSprite().getX() + t.getSprite().getWidth()/2, t.getSprite().getY() + t.getSprite().getHeight()/2);
				l.setSoftnessLength(5f);
				
			}
		}

		/**
		 * Set all the visible item pickups.
		 */
		private void setAllItemPickups() {
			pickups = new ArrayList<>();
			for(Note note : node.getNotes()) {
				if(!note.getHasBeenSeen()) {
					Sprite s = new Sprite(AssetHandler.MANAGER.get("pickups/letter/PICKUP.png", Texture.class));
					s.setPosition(note.getX(), note.getY());
					pickups.add(s);
				}
			}
		}

		/**
		 * Draw all potential item picks ups. Rn just notes.
		 * @param batch SpriteBatch for this screen.
		 */
		private void drawAllItemPickups(SpriteBatch batch) {
			for(Sprite s : pickups) {
				s.draw(batch);
				
				if(Player.getInstance().getSprite().getBoundingRectangle().overlaps(s.getBoundingRectangle()) && s.getColor().a == 1) {
					//String message = node.getNotes().get(new Vector2(s.getX(), s.getY()));
					Note n = node.getNote(s.getX(), s.getY());
					if(n != null) {
						if (!n.getHasBeenSeen()) {
							handler.setPaused(true);
							paragraph.setText(n.getInfo());
							paragraph.setVisible(true);
							updateParagraphPosition();
							letter.setVisible(true);
							s.setAlpha(0);
							n.noteSeen();
							PermanetPlayer.getPermanentPlayerInstance().addNote(n);
						}
					}
				}
			}
		}

		/**
		 * Update the mask bar of the UI, to reduce and check if the player is dead.
		 */
		private void updateBar() {
			if((bar.getWidth() >= 1f)) {
				bar.setWidth(250 * (Player.getInstance().getCurrentMaskDuration()/Player.getInstance().getInitialMaskDuration()));
			}
			else {
				initialNumberOfMasks--;
				numberOfMasksLabel.setText(initialNumberOfMasks);
				if(initialNumberOfMasks <= 0) {
					bar.setWidth(0f);
					darkness = 0f;
					main.ui.clear();

					node.resetVillagers();

					//Player.getInstance().resetMask();
					//Player.getInstance().setMaskDurationSeconds(maskDurabiltyAtStart);
					PermanetPlayer.getPermanentPlayerInstance().setSanity(sanityAtStart);
					main.setScreen(new CheckPoint(main, node, mapScreen, maskDurabiltyAtStart));
				}
				bar.setWidth(250);
				Player.getInstance().resetMask();
			}
			
			
		}

		/**
		 * Draw all of the NPC's on the screen and update their bar's
		 * @param batch SpriteBatch of the screen
		 */
		private void drawNPC(SpriteBatch batch) {
			for(NPC villager : node.getNPCs()) {
				villager.getSprite().draw(batch);
				villager.update();
				if(villager.getHealth() >= 0) {
					batch.draw(villager.getBar().getTexture(), villager.getBar().getX(), villager.getBar().getY(), 32*(villager.getHealth()/100), villager.getBar().getHeight());
				}
				else {
					batch.draw(villager.getBar().getTexture(), villager.getBar().getX(), villager.getBar().getY(), 32*(villager.getHealth()/-100), villager.getBar().getHeight());
				}
				
				if(villager.isBurned()) {
					Player.getInstance().increaseSanity();
					sanityLabel.setText(Player.getInstance().getSanityLabel() +"");
				}
			}
		}

		/**
		 * Sets the labelstyle (Font, Color etc)
		 * @param color Color of the font
		 * @return A labelstyle of font 60
		 */

	    
	    /**
	     * Holds the window for the pause menu.
	     */
	    public void pauseGame() {

			float windowWidth = 200, windowHeight = 200;
			pause = new Window("", skin);
			pause.setMovable(false); //So the user can't move the window
			//final TextButton button1 = new TextButton("Resume", skin);

			final Label button1 = new Label("RESUME", AssetHandler.FONT_SIZE_24);
			button1.setFontScale((windowHeight/200), (windowHeight/200));
			button1.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					handler.togglePaused();
					pause.setVisible(false);
				}
			});


			Label button3 = new Label("EXIT", AssetHandler.FONT_SIZE_24);
			button3.setFontScale((windowHeight/200), (windowHeight/200) );
			button3.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					System.exit(0);
				}
			});

			pause.add(button1).row();
			pause.row();
			pause.add(button3).row();
			pause.row();
			pause.pack(); //Important! Correctly scales the window after adding new elements

			//Centre window on screen.
			pause.setBounds(((main.ui.getWidth() - windowWidth  ) / 2),
					(main.ui.getHeight() - windowHeight) / 2, windowWidth  , windowHeight );
			//Sets the menu as invisible
			pause.setVisible(false);

			pause.setSize(pause.getWidth(), pause.getHeight());
			main.ui.addActor(pause);
	    }


	/**
	 * Used to spawn fake npc's if sanity allows.
	 */
	private void spawnFakeNPC()
	{
		int x=0;
		if(Player.getInstance().getSanityLabel()=="VEXED") x=1;

		if(Player.getInstance().getSanityLabel()=="RISKY") x=2;

		if(Player.getInstance().getSanityLabel()=="INSANE") x=3;

		for(int i =0; i< x;i++)
		{
			Random rand = new Random();
			//Level height and width
			int width = node.getArray().length;
			int height = node.getArray()[0].length;

			NPC fake = new NPC(60+rand.nextInt(40),0,0, 1);
			fakeNPCs.add(fake);

			boolean isValidPosition  = false;
			//Check if npc is inside wall
			while(!isValidPosition)
			{
				//Player must be respawned
				fake.updateSprite(rand.nextInt(width)*32 - fake.getSprite().getX(), rand.nextInt(height)*32 - fake.getSprite().getY());
				if(handler.collision(fake.getSprite().getX()+32, fake.getSprite().getY()+32)) {
					isValidPosition = false;
				}
				else if(handler.collision(fake.getSprite().getX()+32, fake.getSprite().getY()+64)) {
					isValidPosition = false;
				}
				else if(handler.collision(fake.getSprite().getX()+64, fake.getSprite().getY()+64)) {
					isValidPosition = false;
				}
				else if(handler.collision(fake.getSprite().getX(), fake.getSprite().getY())) {
					isValidPosition = false;
				}
				else {
					isValidPosition = true;
				}
			}
		}

	}

	/**
	 * Draw a fake npc if there are fake npc's
	 * @param batch SpriteBatch to draw the npc's
	 */
	private void drawFakeNPC(SpriteBatch batch)
	{
		for(NPC fake : fakeNPCs) {
			fake.getSprite().draw(batch);
			if(fake.getHealth() >= 0) {
				batch.draw(fake.getBar().getTexture(), fake.getBar().getX(), fake.getBar().getY(), 32*(fake.getHealth()/100), fake.getBar().getHeight());
			}
			else {
				batch.draw(fake.getBar().getTexture(), fake.getBar().getX(), fake.getBar().getY(), 32*(fake.getHealth()/-100), fake.getBar().getHeight());
			}
		}
	}


	    
	    
	
}

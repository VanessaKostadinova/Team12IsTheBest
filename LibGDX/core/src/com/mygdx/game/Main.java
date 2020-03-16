package com.mygdx.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.mygdx.assets.AssetHandler;
import com.mygdx.assets.LoadingScreen;
import com.mygdx.camera.Camera;
import com.mygdx.extras.PermanetPlayer;
import com.mygdx.house.House;
import com.mygdx.house.Torch;
import com.mygdx.map.Map;
import com.mygdx.renderable.NPC;
import com.mygdx.renderable.Node;
import com.mygdx.renderable.Player;
import com.mygdx.shop.Item;
import com.mygdx.story.Note;
import com.mygdx.story.StoryHandler;

/**
 * The Main class, which initialises the main assets and values in the game,
 * @author Inder Panesar
 * @version 1.5
 */
public class Main extends Game implements Serializable {

	/**
	 * Serialization Unique Identification value.
	 */
	private static final long serialVersionUID = 1L;

	/** The sprite batch. */
	public SpriteBatch batch;

	/** The renderer for lines between houses */
	public ShapeRenderer shape;

	/** The stage for the ui. */
	public Stage ui;

	/** Initialises the assetHandler */
	public AssetHandler assets;

	/** The Main Camera for the UI */
	private Camera camera;

	/** A variable to store whether V-Sync is On Or Off*/
	public Boolean vsyncOn;

	/** A variable to store the kryo instance used to save the game!*/
	public Kryo kryo;

	/**
	 * Constructor of main.
	 * @param vsyncOn Used to set whether or not the window is being used.
	 */
	public Main(Boolean vsyncOn) {
		this.vsyncOn = vsyncOn;
	}


	/**
	 * Create's the multiple different batch's and viewpoints which will be used
	 * throughout the game.
	 */
    @Override
	public void create() {
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		//Set viewport as same as width
		camera = new Camera(1920f, 1080f, 1920f);
		camera.getCamera().position.set(
				camera.getCamera().viewportWidth / 2f ,
				camera.getCamera().viewportHeight / 2f, 0);
		ui = new Stage(camera.getViewport());

		//setScreen(new MapScreen(this));

		assets = new AssetHandler();

		kryo = new Kryo();
		//Every class needs to be registered before it can be saved.
		kryo.setRegistrationRequired(true);
		setUpSerializers();
		//Sets up the loading screen.
		setScreen(new LoadingScreen(this));
	}

	/**
	 * Creates a sets up the registering of classes for kryo as well as what information which will be
	 * stored within each class.
	 */
	public void setUpSerializers() {
		kryo.register(com.mygdx.game.MapScreen.class, new Serializer<MapScreen>() {
					@Override
					public void write(Kryo kryo, Output output, MapScreen object) {
						output.writeInt(PermanetPlayer.getPermanentPlayerInstance().getNumberOfMasks());
						output.writeFloat(PermanetPlayer.getPermanentPlayerInstance().getHealingFluid());
						output.writeFloat(PermanetPlayer.getPermanentPlayerInstance().getBurningFluid());
						output.writeFloat(PermanetPlayer.getPermanentPlayerInstance().getSanity());
						output.writeInt(PermanetPlayer.getPermanentPlayerInstance().getEnergy());

						output.writeInt(PermanetPlayer.getPermanentPlayerInstance().getNotes().size());
						for(Note note : PermanetPlayer.getPermanentPlayerInstance().getNotes()) {
							output.writeFloat(note.getX());
							output.writeFloat(note.getY());
							output.writeString(note.getInfo());
							output.writeBoolean(note.getHasBeenSeen());
						}

						output.writeInt(PermanetPlayer.getPermanentPlayerInstance().getItems().length);
						for(Item item : PermanetPlayer.getPermanentPlayerInstance().getItems()) {
							output.writeString(item.getName());
							output.writeString(item.getDescription());
							output.writeFloat(item.getIncreasingValue());
							output.writeInt(item.getOriginalCost());
							output.writeInt(item.getLevel());
						}

						output.writeFloat(Player.getInstance().getMaskDurationSeconds());
						output.writeInt(Player.getInstance().getNumberOfMasks());
						output.writeFloat(Player.getInstance().getFood());

						Map m = object.getMap();
						output.writeInt(m.getNodes().size());
						for(int i = 0;  i < m.getNodes().size(); i++) {
							Node n = m.getNodes().get(i);
							int[][] level = n.getHouse().getLevel();
							List<Torch> torches = n.getHouse().getTorches();
							List<Note> notes = n.getNotes();

							output.writeInt(level.length);
							output.writeInt(level[0].length);

							for(int y = 0; y < level.length; y++) {
								for(int x = 0; x < level[0].length; x++) {
									output.writeInt(level[y][x]);
								}
							}

							output.writeInt(torches.size());
							for(Torch torch : torches) {
								output.writeFloat(torch.getCoords().x);
								output.writeFloat(torch.getCoords().y);
								output.writeFloat(torch.getRotation());
							}


							output.writeInt(n.getHouse().getTextures().size());
							System.out.println(n.getHouse().getTextures().size());
							for(int x = 0; x < n.getHouse().getTextures().size(); x++) {
								StringBuilder s = new StringBuilder();
								s.append(n.getHouse().textureURL.get(x) +"");
								output.writeString(s.toString());
							}


							output.writeInt(n.getNotes().size());
							for(Note note : notes) {
								output.writeFloat(note.getX());
								output.writeFloat(note.getY());
								output.writeString(note.getInfo());
								output.writeBoolean(note.getHasBeenSeen());

							}

							output.writeInt(n.getResidents().size());
							for (int z = 0; z < n.getResidents().size(); z++) {
								NPC resident = n.getResidents().get(z);
								output.writeFloat(resident.getHealth());
								output.writeFloat(resident.getSprite().getX());
								output.writeFloat(resident.getSprite().getY());
								output.writeInt(resident.getVillagerType());
								output.writeFloat(resident.getRotation());
								output.writeBoolean(resident.foodGiven());
								System.out.println(resident.foodGiven());
							}
							output.writeString(n.getImageURL());
							output.writeFloat(n.getSprite().getX());
							output.writeFloat(n.getSprite().getY());

							output.writeBoolean(n.getLevel1());
							output.writeBoolean(n.getLevel2());
							output.writeBoolean(n.getLevel3());
							output.writeBoolean(n.getLevel4());

						}

						output.writeInt(object.getDay());


						output.writeBoolean(StoryHandler.introductionPart1);
						output.writeBoolean(StoryHandler.startedIntroPart2);
						output.writeBoolean(StoryHandler.introductionPart2);
						output.writeBoolean(StoryHandler.tutorialDecisionMade);
						output.writeBoolean(StoryHandler.TutorialPart1);
						output.writeBoolean(StoryHandler.TutorialPart2);
						output.writeBoolean(StoryHandler.TutorialPart3);
						output.writeBoolean(StoryHandler.TutorialDone);
						output.writeBoolean(StoryHandler.didCureFirstHouse);
						output.writeBoolean(StoryHandler.transitionEndOfDayTutorial);
						output.writeBoolean(StoryHandler.interactedWithSylvia);
						output.writeBoolean(StoryHandler.falseCure1);
						output.writeBoolean(StoryHandler.falseCure2);
						output.writeBoolean(StoryHandler.allNotesSequence);
						output.writeBoolean(StoryHandler.haveBeenReCured);
						output.writeBoolean(StoryHandler.oDNotesPlaced);
						output.writeBoolean(StoryHandler.decision2Created);
						output.writeBoolean(StoryHandler.decision2Made);
						output.writeBoolean(StoryHandler.toldVillagers);
						output.writeBoolean(StoryHandler.cutscene81Played);
						output.writeBoolean(StoryHandler.cutscene82Played);
						output.writeBoolean(StoryHandler.cutscene83Played);
						output.writeBoolean(StoryHandler.cutscene84Played);
						output.writeBoolean(StoryHandler.killedOtherGuy);
						output.writeInt(StoryHandler.decisionNumber);
					}

					@Override
					public MapScreen read(Kryo kryo, Input input, Class type) {

						int numberOfMask = input.readInt();
						float getHealingFluid = input.readFloat();
						float getBurningFluid = input.readFloat();
						float getSanity = input.readFloat();
						int getEnergy = input.readInt();

						PermanetPlayer.createPermanentPlayerInstance(numberOfMask, getHealingFluid, getBurningFluid);
						PermanetPlayer.getPermanentPlayerInstance().setSanity(getSanity);
						PermanetPlayer.getPermanentPlayerInstance().setEnergy(getEnergy);

						List<Note> notes = new ArrayList<>(10);
						int interval =input.readInt();
						for(int k = 0; k < interval; k++) {
							float x = input.readFloat();
							float y = input.readFloat();
							String info = input.readString();
							Boolean hasBeenSeen = input.readBoolean();
							notes.add(new Note(info, (int) x, (int) y, hasBeenSeen));
						}

						PermanetPlayer.getPermanentPlayerInstance().setNotes(notes);

						List<Item> items = new ArrayList<>(20);
						int interval2 =input.readInt();
						for(int k = 0; k < interval2; k++) {
							String itemName = input.readString();
							String description = input.readString();
							float increasingValue = input.readFloat();
							int cost = input.readInt();
							int level = input.readInt();
							items.add(new Item(itemName, description, increasingValue, cost, level));
						}
						Item[] itemsArray = new Item[items.size()];
						itemsArray = items.toArray(itemsArray);
						PermanetPlayer.getPermanentPlayerInstance().setItems(itemsArray);

						Player.init(0,0,0);

						Player.getInstance().setMaskDurationSeconds(input.readFloat());
						Player.getInstance().setNumberOfMasks(input.readInt());
						Player.getInstance().setFood(input.readFloat());

						int numberOfNodes = input.readInt();
						List<Node> nodes = new ArrayList<>(numberOfNodes);
						for(int i = 0; i < numberOfNodes; i++) {
							int height = input.readInt();
							int width = input.readInt();
							int[][] level = new int[height][width];
							for (int y = 0; y < height; y++) {
								for (int x = 0; x < width; x++) {
									level[y][x] = input.readInt();
								}
							}

							List<Torch> torches = new ArrayList<>();
							int nOfTorches = input.readInt();

							for (int q = 0; q < nOfTorches; q++) {
								float x = input.readFloat();
								float y = input.readFloat();
								float rotation = input.readFloat();
								torches.add(new Torch(x, y, rotation));

							}

							HashMap<Integer, Texture> textures = new HashMap<>();
							List<String> listOfTextures = new ArrayList<>();
							int nOfTextures = input.readInt();

							for (int j = 0; j < nOfTextures; j++) {
								String value = input.readString();
								textures.put(j, new Texture(Gdx.files.internal(value)));
								listOfTextures.add(value);
							}

							House house = new House(level, torches, textures, listOfTextures);

							List<Note> notesz = new ArrayList<>(10);
							int intervalz =input.readInt();
							for(int k = 0; k < intervalz; k++) {
								float x = input.readFloat();
								float y = input.readFloat();
								String info = input.readString();
								Boolean hasBeenSeen = input.readBoolean();
								notesz.add(new Note(info, (int) x, (int) y, hasBeenSeen));
							}


							ArrayList<NPC> residents = new ArrayList<>();
							int value = input.readInt();

							for (int b = 0; b < value; b++) {
								NPC n = new NPC(input.readFloat(), input.readFloat(), input.readFloat(), input.readInt());
								n.setRotation(input.readFloat());
								n.setFoodGiven(input.readBoolean());
								residents.add(n);
							}

							String s = input.readString();
							Node n = new Node(house, residents, notesz, s, input.readFloat(), input.readFloat());
							n.setLevel1(input.readBoolean());
							n.setLevel2(input.readBoolean());
							n.setLevel3(input.readBoolean());
							n.setLevel4(input.readBoolean());

							nodes.add(n);
						}
						Map m = new Map(nodes);
						Main mn = new Main(false);
						mn.create();
						int day =input.readInt();
						MapScreen screen = new MapScreen(mn, day, m);

						StoryHandler.introductionPart1 = input.readBoolean();
						StoryHandler.startedIntroPart2 = input.readBoolean();
						StoryHandler.introductionPart2 = input.readBoolean();
						StoryHandler.tutorialDecisionMade = input.readBoolean();
						StoryHandler.TutorialPart1 = input.readBoolean();
						StoryHandler.TutorialPart2 = input.readBoolean();
						StoryHandler.TutorialPart3 = input.readBoolean();
						StoryHandler.TutorialDone = input.readBoolean();
						StoryHandler.didCureFirstHouse = input.readBoolean();
						StoryHandler.transitionEndOfDayTutorial = input.readBoolean();
						StoryHandler.interactedWithSylvia = input.readBoolean();
						StoryHandler.falseCure1 = input.readBoolean();
						StoryHandler.falseCure2 = input.readBoolean();
						StoryHandler.allNotesSequence = input.readBoolean();
						StoryHandler.haveBeenReCured = input.readBoolean();
						StoryHandler.oDNotesPlaced = input.readBoolean();
						StoryHandler.decision2Created = input.readBoolean();
						StoryHandler.decision2Made = input.readBoolean();
						StoryHandler.toldVillagers = input.readBoolean();
						StoryHandler.cutscene81Played = input.readBoolean();
						StoryHandler.cutscene82Played = input.readBoolean();
						StoryHandler.cutscene83Played = input.readBoolean();
						StoryHandler.cutscene84Played = input.readBoolean();
						StoryHandler.killedOtherGuy = input.readBoolean();
						StoryHandler.decisionNumber = input.readInt();

						return screen;
					}
		});
	}


	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Game#render()
	 */
	@Override
	public void render() {
		super.render();
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Game#dispose()
	 */
	@Override
	public void dispose() {
		batch.dispose();
	}

	/**
	 * Returns the VSync method
	 * @return vsyncOn which tells you if the vsync for the game is on or off.
	 */
	public Boolean getVsync() {
		return vsyncOn;
	}


}

package com.mygdx.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.mygdx.assets.AssetHandler;
import com.mygdx.assets.LoadingScreen;
import com.mygdx.camera.Camera;
import com.mygdx.house.House;
import com.mygdx.house.Torch;
import com.mygdx.map.Map;
import com.mygdx.renderable.NPC;
import com.mygdx.renderable.Node;

/**
 * The Main class, which initialises the main assets and values in the game,
 * @author Inder Panesar
 * @version 1.5
 */
public final class Main extends Game implements Serializable {

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

	public Kryo kryo;

	public int interval = 0;

	private Boolean vsyncOn;

	public Main(Boolean vsyncOn) {
		this.vsyncOn = vsyncOn;
	}


	@Override
	public void create() {
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		camera = new Camera(2160f, 1080f, 1920f);
		camera.getCamera().position.set(
				camera.getCamera().viewportWidth / 2f ,
				camera.getCamera().viewportHeight / 2f, 0);
		ui = new Stage(camera.getViewport());

		//setScreen(new MapScreen(this));

		assets = new AssetHandler();
		//assets.load();
		//assets.manager.finishLoading();

		kryo = new Kryo();
		kryo.setRegistrationRequired(true);
		setUpSerializers();
		setScreen(new LoadingScreen(this));
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

	public void setUpSerializers() {

		kryo.register(com.mygdx.game.MapScreen.class, new Serializer<MapScreen>() {
			@Override
			public void write(Kryo kryo, Output output, MapScreen object) {
				System.out.println("hit");
				Map m = object.getMap();
				output.writeInt(m.getNodes().size());
				for(int i = 0;  i < m.getNodes().size(); i++) {
					Node n = m.getNodes().get(i);
					int[][] level = n.getHouse().getLevel();
					List<Torch> torches = n.getHouse().getTorches();

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
					for(Vector2 value : n.getNotes().keySet()) {
						output.writeFloat(value.x);
						output.writeFloat(value.y);
						output.writeString(n.getNotes().get(value));
					}

					for(String value : n.getNoteValidation().keySet()) {
						output.writeString(value);
						output.writeBoolean(n.getNoteValidation().get(value));
					}

					output.writeInt(n.getResidents().size());
					System.out.println(n.getResidents().size());
					for (int z = 0; z < n.getResidents().size(); z++) {
						NPC resident = n.getResidents().get(z);
						output.writeFloat(resident.getHealth());
						output.writeFloat(resident.getX());
						output.writeFloat(resident.getY());
						output.writeFloat(resident.getRotation());
						output.writeBoolean(resident.foodGiven());
						System.out.println(resident.foodGiven());
					}
					output.writeString(n.getImageURL());
					output.writeFloat(n.getX());
					output.writeFloat(n.getY());

					output.writeBoolean(n.getLevel1());
					output.writeBoolean(n.getLevel2());
					output.writeBoolean(n.getLevel3());
					output.writeBoolean(n.getLevel4());

				}

				output.writeFloat(object.getMapCamera().getdx());
				output.writeFloat(object.getMapCamera().getdy());
				output.writeInt(object.getDay());
				System.out.println("SAVED!");
			}

			@Override
			public MapScreen read(Kryo kryo, Input input, Class type) {
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

					HashMap<Vector2, String> notes = new HashMap<>();
					int interval =input.readInt();
					for (int k = 0; k < interval; k++) {
						Vector2 v = new Vector2(input.readFloat(), input.readFloat());
						String note = input.readString();
						notes.put(v, note);
						System.out.println(note);
					}

					HashMap<String, Boolean> notesValidation = new HashMap<>();
					for (int l = 0; l < interval; l++) {
						String note = input.readString();
						Boolean value = input.readBoolean();
						notesValidation.put(note, value);
					}

					ArrayList<NPC> residents = new ArrayList<>();
					int value = input.readInt();

					for (int b = 0; b < value; b++) {
						NPC n = new NPC(input.readFloat(), input.readFloat(), input.readFloat());
						n.setRotation(input.readFloat());
						n.setFoodGiven(input.readBoolean());
						residents.add(n);
					}

					String s = input.readString();
					Node n = new Node(house, residents, notes, notesValidation, s, input.readFloat(), input.readFloat());
					n.setLevel1(input.readBoolean());
					n.setLevel2(input.readBoolean());
					n.setLevel3(input.readBoolean());
					n.setLevel4(input.readBoolean());

					nodes.add(n);
				}
				Map m = new Map(nodes);
				Main mn = new Main(false);
				mn.create();
				MapScreen screen = new MapScreen(mn, input.readFloat(), input.readFloat(), input.readInt(), m);
				return screen;
			}
		});



	}


	public Boolean getVsync() {
		return vsyncOn;
	}

	public void setVsync(Boolean vsyncOn) {
		this.vsyncOn =  vsyncOn;
	}
}

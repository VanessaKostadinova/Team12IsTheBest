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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.assets.AssetHandler;
import com.mygdx.camera.Camera;
import com.mygdx.extras.PermanetPlayer;
import com.mygdx.house.Torch;
import com.mygdx.renderable.*;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.mygdx.story.Note;

import javax.xml.bind.ValidationException;

/**
 * Contains the information of each of the houses.
 * @author Inder, Vanessa, Max
 * @version 1.5
 */
public class HouseScreen implements Screen {
	
	private Main main;
	private Node node;

	private float stateTime;
	private float secondCounter;
	
	
	private List<Sprite> pickups;
	private Camera camera;
	private Camera cameraUI;

	private Image letter;
	private Image icon;
	private Label paragraph;
	
	private HouseInputHandler handler;
	private MapScreen mapScreen;
	private Image ui;
	private Image uiCurrentSpray;
	private Label goldLabel;
	private Label sanityLabel;
	private Texture maskBar;
	private Image bar;
	private World world;
	private RayHandler rayHandler;
	private float darkness;
	private float scaleItem;
	private Window pause;
	private Skin skin;
	private InputMultiplexer input;
	private Light light;
	private Image background;
    private SpriteDrawable fire;
    private SpriteDrawable cure;
    private Bullet bullet;

	private ArrayList<NPC> fakeNPCs;


	public HouseScreen(Main main, Node node, MapScreen mapScreen) {
			this.main = main;
			this.node = node;
			this.bullet = null;
			this.mapScreen = mapScreen;
			this.darkness = 0.2f;
			this.skin = new Skin(Gdx.files.internal("skin/terra-mother-ui.json"));
			this.fakeNPCs = new ArrayList<>();

			cure = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("house/UI/CureSpray.png"))));
			fire = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("house/UI/FireSpray.png"))));

			
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

			letter = new Image(new SpriteDrawable(new Sprite(AssetHandler.manager.get("pickups/letter/LETTER.png", Texture.class))));
			letter.setPosition(main.ui.getWidth()/2-letter.getWidth()/2, main.ui.getHeight()/2-letter.getHeight()/2);
			letter.setVisible(false);
			main.ui.addActor(letter);

			paragraph = new Label("VOID", createLabelStyleWithBackground(Color.BLACK));
			paragraph.setWidth(letter.getWidth()-90);
			paragraph.setWrap(true);
			paragraph.setPosition(main.ui.getWidth()/2+50, main.ui.getHeight()/2);
			paragraph.setVisible(false);

			main.ui.addActor(paragraph);

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

	        
			input = new InputMultiplexer();
			input.addProcessor(handler);
			input.addProcessor(main.ui);
	        Gdx.input.setInputProcessor(input);
	        
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
			
			
			handler.sprayWithVillagerCollision(node.getNPCs());
			handler.spray();
			updateSprayLight();
			handler.movement(Player.getInstance().getAnimation().getKeyFrame(stateTime, true), delta);
			
			main.batch.setProjectionMatrix(cameraUI.getCamera().combined);
			drawUI(main.batch);
	
	
	
			//b2dr.render(world, camera.getCamera().combined);
			rayHandler.updateAndRender();
			main.batch.end();
			main.ui.draw();
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
				if(icon.isVisible()) {
					//p.writeToPlayerFile();
					dispose();
					Player.getInstance().setCoordinates(new Vector2(0, 0));
					Player.getInstance().getSprite().setX(0);
					Player.getInstance().getSprite().setY(0);
					main.ui.clear();
					mapScreen.createUI();
					mapScreen.inventory();
					mapScreen.pauseGame();
					main.setScreen(mapScreen);
				}
			}
			AI();
			System.out.println("NOTES: " + node.getNotes().size());
		}
		
		public void reduceMask() {
			if(!handler.getPaused()) {
				Player.getInstance().reduceMask();
			}
		}
		
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
			light.setActive(handler.getPressed() && !handler.getPaused());
		}
		
		
		public void UIElements() {
			
			icon = new Image(new SpriteDrawable(new Sprite(AssetHandler.manager.get("player/icon/ICON.png", Texture.class))));
			icon.setPosition(main.ui.getWidth()/2+Player.getInstance().getSprite().getWidth()+icon.getWidth(), main.ui.getHeight()/2+Player.getInstance().getSprite().getHeight()+icon.getHeight());
			icon.setVisible(false);
			main.ui.addActor(icon);
			
			uiCurrentSpray = new Image(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("house/UI/CureSpray.png")))));
			//ui.setDrawable(new SpriteDrawable(new Sprite(main.assets.manager.get("house/UI/MAPUI.png", Texture.class))));
			uiCurrentSpray.setPosition(10, main.ui.getHeight() - uiCurrentSpray.getHeight() - 10);		
			main.ui.addActor(uiCurrentSpray);
			
			ui = new Image(new SpriteDrawable(new Sprite(AssetHandler.manager.get("house/UI/MAPUI.png", Texture.class))));
			//ui.setDrawable(new SpriteDrawable(new Sprite(main.assets.manager.get("house/UI/MAPUI.png", Texture.class))));
			ui.setPosition(10+uiCurrentSpray.getWidth(), main.ui.getHeight() - ui.getHeight() - 10);		
			main.ui.addActor(ui);
			

			goldLabel = new Label(Player.getInstance().getFood()+"", createLabelStyleWithBackground(Color.WHITE));
			goldLabel.setPosition(200+uiCurrentSpray.getWidth(), main.ui.getHeight()-100);
			goldLabel.setFontScale(0.6f);
			main.ui.addActor(goldLabel);

			sanityLabel = new Label(Player.getInstance().getSanityLabel(), createLabelStyleWithBackground(Color.WHITE));
			sanityLabel.setPosition(240+uiCurrentSpray.getWidth(), main.ui.getHeight()-230);
			sanityLabel.setFontScale(0.6f);
			main.ui.addActor(sanityLabel);
			
			maskBar = AssetHandler.manager.get("house/UI/BAR.png", Texture.class);
			bar = new Image(new SpriteDrawable(new Sprite(maskBar)));
			bar.setPosition(200+uiCurrentSpray.getWidth(), main.ui.getHeight()-125);
			bar.setWidth(250 * (Player.getInstance().getCurrentMaskDuration()/Player.getInstance().getInitialMaskDuration()));
			main.ui.addActor(bar);
			
		}


		public void AI() {
			for(NPC n : node.getNPCs()) {
				if(n.getStatus().equals("Alive")) {

					float rotation = (float) MathUtils.radiansToDegrees * MathUtils.atan2(n.getSprite().getY() - Player.getInstance().getSprite().getY(), n.getSprite().getX()-Player.getInstance().getSprite().getX());
					rotation -= 90;
					if (rotation < 0) rotation += 360;
					n.getSprite().setRotation(rotation);

					Vector2 villager = new Vector2(n.getSprite().getX(), n.getSprite().getY());
					Vector2 player = new Vector2(Player.getInstance().getSprite().getX(), Player.getInstance().getSprite().getY());
					System.out.println("VILLAGER: " + player.dst(villager));
					if(n.getAggressive() && player.dst(villager) < 100) {
						float dx = Player.getInstance().getSprite().getX() - n.getSprite().getX();
						float dy = Player.getInstance().getSprite().getY() - n.getSprite().getY();

						dx = dx/20f;
						dy = dy/20f;

						System.out.println(dx);
						System.out.println(dy);

						Bullet b = new Bullet(dx, dy, n.getSprite().getX()+16, n.getSprite().getY()+16, node.getArray() , rotation);
						if(bullet == null) {
							bullet = b;
						}
					}
				}
			}
		}

		public void updateAllBullets() {
			if(bullet != null && !handler.getPaused()) {
				System.out.println("HIT!");
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


		public void updateParagraphPosition() {
			paragraph.setPosition(main.ui.getWidth()/2-letter.getWidth()/2 + 50, main.ui.getHeight()/2);
		}
		
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
		
		private void drawTorchs() {
			for(Torch t :node.getHouse().getTorches()) {
				t.draw(main.batch);
			}
		}
		
		private void setTorchLights() {
			for(Torch t :node.getHouse().getTorches()) {
				Light l = new PointLight(rayHandler, 100, Color.ORANGE, 200f, t.getSprite().getX() + t.getSprite().getWidth()/2, t.getSprite().getY() + t.getSprite().getHeight()/2);
				l.setSoftnessLength(5f);
				
			}
		}
		
		private void setAllItemPickups() {
			pickups = new ArrayList<>();
			for(Note note : node.getNotes()) {
				if(!note.getHasBeenSeen()) {
					Sprite s = new Sprite(AssetHandler.manager.get("pickups/letter/PICKUP.png", Texture.class));
					s.setPosition(note.getX(), note.getY());
					pickups.add(s);
				}
			}
		}
		
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
		
		
		private void updateBar() {
			if((bar.getWidth() >= 1f)) {
				bar.setWidth(250 * (Player.getInstance().getCurrentMaskDuration()/Player.getInstance().getInitialMaskDuration()));
			}
			else {
				bar.setWidth(0f);
				darkness = 0f;
				main.ui.clear();
				
				node.resetVillagers();

				Player.getInstance().resetMask();
				
				main.setScreen(new CheckPoint(main, node, mapScreen));
			}
			
			
		}
		
		private void drawNPC(SpriteBatch batch) {
			for(NPC villager : node.getNPCs()) {
				villager.getSprite().draw(batch);
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
	        final Label button1 = new Label("RESUME", createLabelStyleWithBackground(Color.WHITE));
	        button1.setFontScale((windowHeight/200)*scaleItem, (windowHeight/200)*scaleItem );
	        button1.setFontScale(0.4f);
	        button1.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	                handler.togglePaused();
	                pause.setVisible(false);
	            }
	        });
	        Label button2 = new Label("EXIT", createLabelStyleWithBackground(Color.WHITE));
	        button2.setFontScale((windowHeight/200)*scaleItem, (windowHeight/200)*scaleItem );
	        button2.setFontScale(0.4f);
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
	        pause.setVisible(false);
	        
	        pause.setSize(pause.getWidth()*scaleItem, pause.getHeight()*scaleItem);
	        //Adds it to the UI Screen.
	        main.ui.addActor(pause);
	    }

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

			NPC fake = new NPC(60+rand.nextInt(40),0,0);
			fakeNPCs.add(fake);

			//Check if npc is inside wall
			while(handler.collision(fake.getSprite().getX(), fake.getSprite().getY()))
			{
				//Player must be respawned
				fake.updateSprite(rand.nextInt(width)*32 - fake.getSprite().getX(), rand.nextInt(height)*32 - fake.getSprite().getY());

			}
		}

	}
	private void drawFakeNPC(SpriteBatch batch)
	{
		System.out.println("NUMBER OF FAKE NPC'S: " + fakeNPCs.size());
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

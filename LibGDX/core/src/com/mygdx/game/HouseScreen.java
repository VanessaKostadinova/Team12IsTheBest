package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.camera.Camera;
import com.mygdx.house.Torch;
import com.mygdx.renderable.Constants;
import com.mygdx.renderable.NPC;
import com.mygdx.renderable.Node;
import com.mygdx.renderable.Player;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class HouseScreen implements Screen {
	
	private Main main;
	private Node node;
	private Player p;
	
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
	
	private Node initialNode;
	private Player initialPlayer;
	private float scaleItem;
	private Window pause;
	private Skin skin;
	
	private InputMultiplexer input;
	
	private static HouseScreen initialScreen;
	private Light light;
    private Box2DDebugRenderer b2dr;
    private float trackSpray;
    
    
    private SpriteDrawable fire;
    private SpriteDrawable cure;
    
    
	
		public HouseScreen(Main main, Node node, MapScreen mapScreen) {		
			this.main = main;
			this.node = node;
			this.mapScreen = mapScreen;
			this.darkness = 0.2f;
			this.skin = new Skin(Gdx.files.internal("skin/terra-mother-ui.json"));
	
			cure = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("house/UI/CureSpray.png"))));
			fire = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("house/UI/FireSpray.png"))));

			
			float w = Gdx.graphics.getWidth();
			scaleItem = w/1920;
			
			camera = new Camera(256, 1080f, 1920f);
			camera.getCamera().position.set(camera.getCamera().viewportWidth / 2f , camera.getCamera().viewportHeight / 2f, 0);
			
			cameraUI = new Camera(1920, 1080f, 1920f);
			cameraUI.getCamera().position.set(cameraUI.getCamera().viewportWidth / 2f , cameraUI.getCamera().viewportHeight / 2f, 0);
			
			p = readPlayer();
			p.updateSprite(camera.getViewport().getWorldWidth()/2-p.getSprite().getWidth()/2, camera.getViewport().getWorldHeight()/2-p.getSprite().getHeight()/2);
		    p.getSpray().getSprite().setPosition(p.getSprite().getX()-p.getSprite().getWidth()/2+10f, p.getSprite().getY()+p.getSprite().getHeight());
		    p.setRotation(90);
			setAllItemPickups();
			
			letter = new Image(new SpriteDrawable(new Sprite(main.assets.manager.get("pickups/letter/LETTER.png", Texture.class))));
			letter.setPosition(main.ui.getWidth()/2-letter.getWidth()/2, main.ui.getHeight()/2-letter.getHeight()/2);
			letter.setVisible(false);
			main.ui.addActor(letter);

			
			UIElements();
			
			this.world = new World(new Vector2(0,0), false);
			
			pauseGame();
			handler = new HouseInputHandler(p, camera, node.getArray(), pause, node.getNPCs(), paragraph, letter, icon, world);
	        handler.setPaused(false);
	
			node.getHouse().createBodies(world);
			p.setBody(world);
			p.setSprayBody(world);
			this.rayHandler = new RayHandler(world);
			this.rayHandler.setAmbientLight(darkness);
			this.rayHandler.setShadows(true);
			light = new PointLight(rayHandler, 200, p.getSpray().getColor(),50f, p.getSpray().getSprite().getX() + p.getSpray().getSprite().getWidth()/2,p.getSpray().getSprite().getY()+p.getSpray().getSprite().getHeight()/2);
			light.setSoftnessLength(2f);
			light.setContactFilter(Constants.PLAYER, Constants.PLAYER, Constants.PLAYER);
			
			Light player = new PointLight(rayHandler, 10, Color.BLACK,25f, p.getSpray().getSprite().getX() + p.getSpray().getSprite().getWidth()/2,p.getSpray().getSprite().getY()+p.getSpray().getSprite().getHeight()/2);
			player.setXray(true);
			player.setSoftnessLength(1f);
			player.attachToBody(p.getBody());
	        setTorchLights();
	        b2dr = new Box2DDebugRenderer();

	        
			input = new InputMultiplexer();
			input.addProcessor(handler);
			input.addProcessor(main.ui);
	        Gdx.input.setInputProcessor(input);
	        
		}
	
		
		public Player readPlayer() {
			FileHandle handle = Gdx.files.local("data/player.txt");
			String[] values= handle.readString().split(",");
			Player p = new Player(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6]), Float.parseFloat(values[7]));
			return p;
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
			
			if(secondCounter > 1) {
				secondCounter -= 1;
				reduceMask();
			}
			
	
			main.batch.setProjectionMatrix(camera.getCamera().combined);
			renderMap();
			drawNPC(main.batch);
			p.draw(main.batch);
			drawTorchs();
			drawAllItemPickups(main.batch);
			p.getSpray().getSprite().setRegion(p.getSpray().getAnimation().getKeyFrame(stateTime, true));
			p.getSpray().draw(main.batch);
			
			if(p.getSprayIndex() == 0) {
				uiCurrentSpray.setDrawable(cure);
			}
			if(p.getSprayIndex() == 1) {
				uiCurrentSpray.setDrawable(fire);
			}
			
			
			handler.sprayWithVillagerCollision(node.getNPCs());
			handler.spray();
			updateSprayLight();
			handler.movement(p.getAnimation().getKeyFrame(stateTime, true), delta);
			
			main.batch.setProjectionMatrix(cameraUI.getCamera().combined);
			drawUI(main.batch);
	
	
	
			//b2dr.render(world, camera.getCamera().combined);
			rayHandler.updateAndRender();
			
			main.batch.end();
			main.ui.draw();
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
				if(icon.isVisible()) {
					p.writeToPlayerFile();
					dispose();
					main.ui.clear();
					mapScreen.pauseGame();
					main.setScreen(mapScreen);
				}
			}
			
	
		}
		
		public void reduceMask() {
			if(!handler.getPaused()) {
				p.reduceMask();
			}
		}
		
		public void updateSprayLight() {
			light.setColor(p.getSpray().getColor());

			float angle =p.getSprite().getRotation() -90f;
			if(angle > 180) {
				angle =  360 - angle;
			}
			if(angle == 0) {
				angle = 360;
			}
			
			
			light.setPosition(p.getSpray().getSprite().getX()+p.getSprite().getWidth()/2,p.getSpray().getSprite().getY()+p.getSpray().getSprite().getHeight()/2);
			//light.setDirection(p.getSprite().getRotation());
			light.setActive(handler.getPressed() && !handler.getPaused());
		}
		
		
		public void UIElements() {
	
			paragraph = new Label("VOID", createLabelStyleWithBackground(Color.BLACK));
			paragraph.setWidth(letter.getWidth()-60);
			paragraph.setWrap(true);
			paragraph.setPosition(main.ui.getWidth()/2+50, main.ui.getHeight()/2);
			paragraph.setVisible(false);
			
			main.ui.addActor(paragraph);
			
			icon = new Image(new SpriteDrawable(new Sprite(main.assets.manager.get("player/icon/ICON.png", Texture.class))));
			icon.setPosition(main.ui.getWidth()/2+p.getSprite().getWidth()+icon.getWidth(), main.ui.getHeight()/2+p.getSprite().getHeight()+icon.getHeight());
			icon.setVisible(false);
			main.ui.addActor(icon);
			
			uiCurrentSpray = new Image(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("house/UI/CureSpray.png")))));
			//ui.setDrawable(new SpriteDrawable(new Sprite(main.assets.manager.get("house/UI/MAPUI.png", Texture.class))));
			uiCurrentSpray.setPosition(10, main.ui.getHeight() - uiCurrentSpray.getHeight() - 10);		
			main.ui.addActor(uiCurrentSpray);
			
			ui = new Image(new SpriteDrawable(new Sprite(main.assets.manager.get("house/UI/MAPUI.png", Texture.class))));
			//ui.setDrawable(new SpriteDrawable(new Sprite(main.assets.manager.get("house/UI/MAPUI.png", Texture.class))));
			ui.setPosition(10+uiCurrentSpray.getWidth(), main.ui.getHeight() - ui.getHeight() - 10);		
			main.ui.addActor(ui);
			

			goldLabel = new Label(p.getFood()+"", createLabelStyleWithBackground(Color.WHITE));
			goldLabel.setPosition(200+uiCurrentSpray.getWidth(), main.ui.getHeight()-100);
			goldLabel.setFontScale(0.6f);
			main.ui.addActor(goldLabel);
			
			sanityLabel = new Label(p.getSanityLabel(), createLabelStyleWithBackground(Color.WHITE));
			sanityLabel.setPosition(240+uiCurrentSpray.getWidth(), main.ui.getHeight()-165);
			sanityLabel.setFontScale(0.6f);
			main.ui.addActor(sanityLabel);
			
			maskBar = main.assets.manager.get("house/UI/BAR.png", Texture.class);
			bar = new Image(new SpriteDrawable(new Sprite(maskBar)));
			bar.setPosition(200+uiCurrentSpray.getWidth(), main.ui.getHeight()-190);
			bar.setWidth(250 * (p.getHealth()/p.getInitialMask()));
			main.ui.addActor(bar);
			
		}
		
		public void updateParagraphPosition() {
			paragraph.setPosition(main.ui.getWidth()/2-letter.getWidth()/2 + 50, main.ui.getHeight()/2);
		}
		
	
		private void updateBar() {
			if((bar.getWidth() >= 1f)) {
				bar.setWidth(250 * (p.getHealth()/p.getInitialMask()));
			}
			else {
				bar.setWidth(0f);
				darkness = 0f;
				main.ui.clear();
				
				node.resetVillagers();
				
				main.setScreen(new CheckPoint(main, node, mapScreen));
			}
			
			
		}
		
		public void drawUI(SpriteBatch batch) {
			updateBar();
			goldLabel.setText(p.getFood()+"");
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
				l.setContactFilter(Constants.PLAYER, Constants.PLAYER, Constants.PLAYER);
				l.setSoftnessLength(5f);
				
			}
		}
		
		private void setAllItemPickups() {
			pickups = new ArrayList<>();
			for(Vector2 vector : node.getNotes().keySet()) {
				Sprite s = new Sprite(main.assets.manager.get("pickups/letter/PICKUP.png", Texture.class));
				s.setPosition(vector.x, vector.y);
				pickups.add(s);
			}
		}
		
		private void drawAllItemPickups(SpriteBatch batch) {
			for(Sprite s : pickups) {
				s.draw(batch);
				
				if(p.getSprite().getBoundingRectangle().overlaps(s.getBoundingRectangle()) && s.getColor().a == 1) {
					handler.setPaused(true);
					paragraph.setText(node.getNotes().get(new Vector2(s.getX(), s.getY())));
					paragraph.setVisible(true);
					updateParagraphPosition();
					letter.setVisible(true);;
					s.setAlpha(0);
				}
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
					p.increaseSanity();		
					sanityLabel.setText(p.getSanityLabel()+"");
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
	    
	    
	
}

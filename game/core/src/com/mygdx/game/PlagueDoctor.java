package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class PlagueDoctor extends ApplicationAdapter {
	
	private static final int FRAME_COLS = 2, FRAME_ROWS = 1;
	float stateTime = 0f;
	SpriteBatch batch;
	Sprite sprite;
	InputHandler handler;
	TextureAtlas textureAtlas;
	Texture walksheet;
	Animation<TextureRegion> walkAnimation;	
	Pixmap pm;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer render;
	private OrthographicCamera camera;
	 
	//OrthographicCamera camera;
	

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationAdapter#create()
	 * -------------------------------------------------
	 * 
	 * Create, is called once when creating the objects.
	 * Called after Constructor but is called Straight after
	 * the constructor
	 * 
	 */
	@Override
	public void create () {
		/* Sprite Batch, is an optimatisation
		 * Behind the scenes, LibGDX is running in 3D
		 * So 2D graphics is parallel to the scene. (No Depth essentially)
		 * Sprite batch puts everything into one draw call.
		 * 
		 * Texas Instruments, came up the first chip which did Sprite Drawing.
		 */
		batch = new SpriteBatch();
		//Texture, is just pixels in memory. 

		
		map = new TmxMapLoader().load("TileMap/tilemap.tmx");	
		render = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		float aspectRatio = (float) Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
		//TiledMap map = new TmxMapLoader().load("tilemap.tmx");

		walksheet = new Texture(Gdx.files.internal("sprite.png"));
		
		TextureRegion[][] tmp = TextureRegion.split(walksheet, 
				walksheet.getWidth() / FRAME_COLS,
				walksheet.getHeight() / FRAME_ROWS);
		
		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		
		walkAnimation = new Animation<TextureRegion>(0.3f, walkFrames);
		
		textureAtlas = new TextureAtlas("sprite.txt");
		sprite = textureAtlas.createSprite("Left.png");
		/*sprite.setPosition(
				GAME_WORLD_WIDTH/2, 
				GAME_WORLD_HEIGHT/2);*/
		
		//sprite.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
		//sprite.setRotation(180f);
		
		// Scale:
		// sprite.setScale(1.5f); //50% bigger,
		// or each axis independentantly.
		// sprite.setScale(1.0f, 2.0f);
        sprite.setScale(5f);

        pm = new Pixmap(Gdx.files.internal("crosshair.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));

		/*camera = new OrthographicCamera(GAME_WORLD_HEIGHT * aspectRatio, GAME_WORLD_HEIGHT);
		camera.translate(camera.viewportWidth/2, camera.viewportHeight/2, 0);*/
	
		handler = new InputHandler(pm.getWidth(), pm.getHeight());
		Gdx.input.setInputProcessor(this.handler);
	}
	
	
	@Override
	public void resize(int width, int height) {
		camera.position.set(width/2, height/2, 0);
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		sprite.scale(0.5f);
		camera.update();
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationAdapter#render()
	 * -------------------------------------------------
	 * 
	 * Called every single frame the application runs.
	 * So changes should be placed here.
	 * 
	 * Once bath starts we draw things.
	 * Batch drawing is the heart of LibGDX
	 * 
	 * Colour is encoded in RGBA (Full Red, No Green, No Blue, Full Alpha)
	 * Alpha is how opaque something is (1 - not transparent > 0 - transparent)
	 * 
	 * The GDX.gl is there so if there is something which GDX is not able to do
	 * we are able to use OpenGl directly.
	 * 
	 * OpenGL tells Graphics how to draw things.
	 *  > Many versions of OpenGL
	 *  
	 * Low-Level API, so we can use it if needed, however is overly complex at times.
	 * 
	 * 0, 0 is weird depending on what we use. Box2D will start the world from the bottom left.
	 * However, images coordinates are shown in the centre.
	 *  > (CARTIESIAN PLANE) - X and Y axis graph
	 *  
	 *  We can give negative coordinates, for objects that will be off screen.
	 */
	@Override
	public void render () {
		//Check InputHandler for stuff on this.
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);

		handler.doctorMovement(sprite, walkAnimation.getKeyFrame(stateTime, true));
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		render.setView(camera);
		render.render();
		batch.begin();
		stateTime = stateTime + Gdx.graphics.getDeltaTime();
		
		//LibGDX is a 3D engine, so it will projection*view = combined
		
		//batch.setProjectionMatrix(camera.combined);
		//System.out.println("X: " + Gdx.input.getX());
		//System.out.println("Y: " + (Gdx.graphics.getHeight() - Gdx.input.getY()));
		Gdx.graphics.setTitle("Plague Doctor FPS:" + Gdx.graphics.getFramesPerSecond());
		
		/*
		 * Essentially  this is saying draw the sprite, at X and Y (which we defined in create).
		 * The origin of the image, which is the centre of the object.  (not the bottom left etc).
		 * The Sprite Height and Width
		 * The Scale of the Sprite in both X & Y --> To make the image bigger/smaller
		 * Then the rotation of the sprite itself.
		 */
		handler.setRotations(sprite);
		batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getWidth()/2, sprite.getHeight()/2, sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
		batch.end();
		

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		pm.dispose();
		textureAtlas.dispose();
		walksheet.dispose();
	}
}

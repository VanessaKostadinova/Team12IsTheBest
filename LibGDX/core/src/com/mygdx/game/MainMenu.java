package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryo.io.Input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.FileHandler;

/**
 * The Main Menu of the game. Is the second scene the player will see and the first interactive scene
 * that they will see.
 *
 * @author Inder, Hasan, Vanessa
 * @version 1.3
 */
public final class MainMenu implements Screen {

	Main main;
	Table t;
	Skin skin;
	float scaleItem;
	Image head;
	Music sound;
	MainMenu menu;

	public MainMenu(Main mainScreen) {
		/*
		 * Simple sound file for the User Interface, loaded using LibGDX Audio library.
		 */
		this.sound = Gdx.audio.newMusic(Gdx.files.internal("main_menu_assets/music.mp3"));
		this.sound.setLooping(true);
		/*
		 * This makes sure the UI fits within any size of screen
		 * the elements were made with 1920 pixels of width in mind.
		 */
		float width = Gdx.graphics.getWidth();
		scaleItem = width/1920;

		/*
		 * Skin required for some UI elements, not required so far here but will leave here just in case need.
		 */
		this.main = mainScreen;

		Image background = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/mainMenu.png")))));
		background.setPosition(0, 0);
		background.setSize( main.ui.getCamera().viewportWidth, main.ui.getCamera().viewportHeight);
		main.ui.addActor(background);

		/*
		 * Setting the image of the title of the game
		 * Scale it so it fits within the screen.
		 * Set the Position of the title
		 * 	> X: The GDX.graphics.getWidth - title.getWidth() this sets it at the top right of the screen. Width*Scale ensure that the image is adjusted correctly for scale.
		 * 	> Y: The GDX.graphics.getHeight - title.getHeight() this sets it at the top right of the screen. Height*Scale ensure that the image is adjusted correctly for scale.
		 * 	> + 20 : is used to act as the border to make sure it is not exactly up against the top of the window.
		 * Set the size of the Item to scale.
		 */
		Image title = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/doctormask_0002_PLAGUE-DOCTOR.png")))));
		title.setScaling(Scaling.fit);
		title.setPosition(20, main.ui.getHeight()-title.getHeight()-30);
		title.setSize(title.getWidth(), title.getHeight());

		/*
		 * Essentially the same as above however, adding the height of the title to Y and Border Spacing so the top of Subtitle isn't the bottom of title.
		 */
		Image subtitle = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/doctormask_0001_The-Price-of-our-Sins.png")))));
		subtitle.setScaling(Scaling.fit);
		subtitle.setPosition(20, title.getY()-subtitle.getHeight()-10);
		subtitle.setSize(subtitle.getWidth(), subtitle.getHeight());

		/*
		 * Again similar to before however, this Image contains a couple of Listeners
		 * So that when when Setting is clicked this screen is disposed.
		 * and the screen is set to a new class.
		 *
		 * Enter is used to check whether the mouse has entered the TextureRegion
		 * Exit is used to check whether the mouse has left the TextureRegion
		 *
		 * If it does enter, the image changes to inform the user that this has occurred.
		 * If it does leave, the image reverts to its original image to show to the user the mouse has left.
		 */

		final Image exit = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/_0000_EXIT.png")))));
		exit.setPosition(20,  exit.getHeight()*(1f));
		exit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				System.exit(0);
			}
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/_0000_EXIT_2.png"))));
				exit.setDrawable(t);
			}
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/_0000_EXIT.png"))));
				exit.setDrawable(t);
			}
		});

		final Image settings = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/doctormask_0004_SETTINGS.png")))));
		settings.setPosition(20,   settings.getHeight()+exit.getHeight() + 25);
		settings.setSize(settings.getWidth() * settings.getHeight()/exit.getHeight() , exit.getHeight());
		settings.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sound.pause();
				dispose();
				main.ui.clear();
				main.setScreen(new SettingsScreen(main, menu));
			}
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/doctormask_0003_SETTINGS.png"))));
				settings.setDrawable(t);
			}
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/doctormask_0004_SETTINGS.png"))));
				settings.setDrawable(t);
			}
		});

		final Image continueButton = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/doctormask_0008_CONTINUE.png")))));
		continueButton.setPosition(20,   continueButton.getHeight()+exit.getHeight()+continueButton.getHeight() + 35);
		continueButton.setSize(continueButton.getWidth() * continueButton.getHeight()/exit.getHeight() , exit.getHeight());
		continueButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sound.pause();
				dispose();
				main.ui.clear();
				MapScreen object2 = null;
				try {
					Input input = new Input(new FileInputStream("save.bin"));
					object2 = main.kryo.readObject(input, MapScreen.class);
					input.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				MapScreen screen = new MapScreen(main, object2.getDay(), object2.getMap());
				main.setScreen(screen);
			}
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/doctormask_0007_CONTINUE.png"))));
				continueButton.setDrawable(t);
			}
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/doctormask_0008_CONTINUE.png"))));
				continueButton.setDrawable(t);
			}
		});


		float playBorder = 65;

		try {
			Input input = new Input(new FileInputStream("save.bin"));
		}
		catch (FileNotFoundException e) {
			continueButton.setVisible(false);
			continueButton.setSize(0, 0);
			playBorder -= 20;
		}

		/*
		 * Essentially the same as above. Just to do with Play.
		 */
		final Image play = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/doctormask_0005_PLAY.png")))));
		play.setPosition(20,  settings.getHeight()+play.getHeight()+exit.getHeight()+continueButton.getHeight() + playBorder);
		play.setSize(play.getWidth() * play.getHeight()/exit.getHeight() , exit.getHeight());
		play.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sound.pause();
				dispose();
				main.ui.clear();
				main.setScreen(new Cutscene(main, "video/Cutscene 1.ogg", false));
			}
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/doctormask_0006_PLAY.png"))));
				play.setDrawable(t);
			}
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/doctormask_0005_PLAY.png"))));
				play.setDrawable(t);
			}
		});

		final Image logo = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main_menu_assets/Team12_Logo.png")))));
		logo.setScaling(Scaling.fit);
		logo.setSize(100, 100);
		logo.setPosition((1080*2)-200, exit.getHeight()*(1f));
		logo.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("http://plaguedoctor.xyz/");
			}
		});


		//Add all of the actors above to the stage.
		main.ui.addActor(title);
		main.ui.addActor(subtitle);
		main.ui.addActor(play);
		main.ui.addActor(settings);
		main.ui.addActor(exit);
		main.ui.addActor(logo);
		main.ui.addActor(continueButton);

		//Start playing the UI Soundtrack.
		sound.play();

		//Sets the input processor as Screen.ui as that is where the stage is contained.
		Gdx.input.setInputProcessor(main.ui);
		menu = this;

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		main.ui.act(Gdx.graphics.getDeltaTime());
		main.ui.draw();
		menu = this;
	}




	@Override
	public void resize(int width, int height) {
		main.ui.getViewport().update(width, height, true);

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
		main.ui.clear();
		sound.dispose();
	}

	public boolean if_exists ( String prefname )
	{
		Preferences tmprefs = Gdx.app.getPreferences ( prefname );

		Map tmpmap = tmprefs.get();

		if ( tmpmap.isEmpty() == true )
			return false;
		else
			return true;
	}


}
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.mygdx.camera.Camera;
import com.mygdx.extras.PermanetPlayer;
import com.mygdx.renderable.Node;
import com.mygdx.renderable.Player;

/**
 * Class used to generate a cut-scene and a lot of things to do.
 * Makes use of the gdx.video library which has been imported independently.
 * @author Inder Panesar
 * @version 2.0
 * @see com.badlogic.gdx.video;
 */
public class Cutscene implements Screen {

	/** Amount of time in state */
	float stateTime = 0f;
	/** The main class */
	Main main;
	/** If the game should leave, if it is a ending cutscene*/
	Boolean shouldLeave;
	/** VideoPlayer instance
	 * @see VideoPlayer
	 */
	VideoPlayer videoPlayer;

	/** Camera instance
	 * @see OrthographicCamera
	 */
	OrthographicCamera camera;
	/** File handle of the video */
	FileHandle videoFile;

	/**
	 * Constructor for the cutscene class.
	 * @param main The main class.
	 * @param file The cutscene that we want to play.
	 * @param shouldLeave Whether this a end game cutscene and should leave game.
	 */
	public Cutscene(Main main, String file, Boolean shouldLeave) {
		this.main = main;
		this.shouldLeave = shouldLeave;

		camera = new OrthographicCamera(1920, 1080);
		camera.position.set(1920 / 2, 1080 / 2, 0);
		Viewport viewport = new FitViewport(1920/2, 1080/2, camera);
		viewport.setScreenPosition(0, 0);

		videoPlayer = VideoPlayerCreator.createVideoPlayer(viewport);
		//Open gl to make the video creation more efficient.
		Gdx.gl.glEnable(GL20.GL_CULL_FACE);
		Gdx.gl.glCullFace(GL20.GL_BACK);

		try {
			videoFile = Gdx.files.internal(file);
			Gdx.app.log("LOADING", "Loading file : " + videoFile.file().getAbsolutePath());
			videoPlayer.play(videoFile);
		} catch (Exception e) {
			Gdx.app.log("ERROR", "Err: " + e);
			changeScreen();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		stateTime += delta;
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		main.ui.act();
		main.ui.draw();

		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			videoPlayer.stop();
			videoPlayer.dispose();
			this.changeScreen();
			this.camera.update();
		}

		/*if(delta > 23) {
			changeScreen();
		}*/

		videoPlayer.setOnCompletionListener(new VideoPlayer.CompletionListener() {
			@Override
			public void onCompletionListener(FileHandle file) {
				changeScreen();
			}
		});

		videoPlayer.render();
	}

	/**
	 * Change Screen is a method which is called to change the scene to the next scene
	 * or return to a previous scene.
	 */
	private void changeScreen() {
		Gdx.gl.glDisable(GL20.GL_CULL_FACE);
		if(shouldLeave) {
			Camera camera = new Camera(2160f, 1080f, 1920f);
			camera.getCamera().position.set(
					camera.getCamera().viewportWidth / 2f ,
					camera.getCamera().viewportHeight / 2f, 0);
			main.ui = new Stage(camera.getViewport());
			System.exit(0);
		}
		else {
			//main.ui.clear();
			main.setScreen(new MapScreen(main));
		}


	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		videoPlayer.dispose();
	}

}


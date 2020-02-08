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
import com.mygdx.renderable.Node;

public class Cutscene implements Screen {

	float stateTime = 0f;

	Main main;
	Node initialNode;
	MapScreen mapScreen;
	Boolean shouldLeave;
	VideoPlayer videoPlayer;
	OrthographicCamera camera;
	Stage temp;

	VideoPlayerCreator v;
	public Cutscene(Main main, String cutscene, Boolean shouldLeave) {
		this.main = main;
		this.shouldLeave = shouldLeave;


		camera = new OrthographicCamera(1920, 1080);
		camera.position.set(1920 / 2, 1080 / 2, 0);
		Viewport viewport = new FitViewport(1920/2, 1080/2, camera);
		viewport.setScreenPosition(0, 0);

		videoPlayer = VideoPlayerCreator.createVideoPlayer(viewport);
		System.out.println(videoPlayer);
		Gdx.gl.glEnable(GL20.GL_CULL_FACE);
		Gdx.gl.glCullFace(GL20.GL_BACK);




		try {
			FileHandle videoFile = Gdx.files.internal("video/ferrari.ogg");
			Gdx.app.log("LOADING", "Loading file : " + videoFile.file().getAbsolutePath());
			videoPlayer.play(videoFile);
		} catch (Exception e) {
			Gdx.app.log("ERROR", "Err: " + e);
		}










	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		/*f(	voiceOver.isPlaying()) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stateTime = stateTime + delta;

			main.ui.draw();

			if(stateTime > waitTime) {
				stateTime = stateTime - waitTime;
				changeScreen();
			}


			if(Gdx.input.isKeyJustPressed(Keys.ENTER) ) {
				totalTime.clear();
				this.changeScreen();
			}
		}
		else {
			voiceOver.play();
		}*/
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (videoPlayer.isBuffered()) {
			videoPlayer.render();
		}
		main.ui.act();
		main.ui.draw();

		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			videoPlayer.stop();
			videoPlayer.dispose();
			this.changeScreen();
			this.camera.update();
		}


		if(!videoPlayer.isPlaying()) {
			this.changeScreen();
		}
	}

	public void changeScreen() {
		Gdx.gl.glDisable(GL20.GL_CULL_FACE);
		if(shouldLeave) {
			//main.ui.clear();
			Camera camera = new Camera(2160f, 1080f, 1920f);
			camera.getCamera().position.set(
					camera.getCamera().viewportWidth / 2f ,
					camera.getCamera().viewportHeight / 2f, 0);
			main.ui = new Stage(camera.getViewport());
			main.setScreen(new MainMenu(main));
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
		//voiceOver.dispose();
		videoPlayer.dispose();
	}

}


package com.mygdx.game;

import java.util.LinkedList;
import java.util.Queue;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.assets.AssetHandler;
import com.mygdx.camera.Camera;
import com.mygdx.renderable.Node;

public class Cutscene implements Screen {

	float stateTime = 0f;

	Main main;
	Node initialNode;
	MapScreen mapScreen;

	Label l;

	Queue<Float> totalTime;
	Queue<String> subtitles;
	Queue<TextureRegionDrawable> background;

	Image backgroundImage;

	float waitTime;

	Boolean shouldLeave;

	Music voiceOver;

	public Cutscene(Main main, String cutscene, Boolean shouldLeave) {
		this.main = main;

		totalTime = new LinkedList<>();
		subtitles = new LinkedList<>();
		background = new LinkedList<>();


		this.shouldLeave = shouldLeave;

		System.out.println("HIT1");

		FileHandle handle = Gdx.files.internal(cutscene);
		String[] properties = handle.readString().split("\\r?\\n");
		for(String property : properties) {
			if(property.contains("#")) {
				String values[] = property.split("#");
				totalTime.add(Float.parseFloat(values[1]));
				subtitles.add(values[2]);
				background.add(new TextureRegionDrawable(new TextureRegion(AssetHandler.manager.get("cutscene/"+values[0], Texture.class))));
			}
			else {
				voiceOver = Gdx.audio.newMusic(Gdx.files.internal("cutscene/"+property));
			}
		}

		System.out.println("HIT2");

		backgroundImage = new Image(background.remove());
		backgroundImage.setWidth(main.ui.getWidth());
		backgroundImage.setHeight(main.ui.getHeight());
		main.ui.addActor(backgroundImage);

		l = new Label("VOID", AssetHandler.fontSize12Subtitles);
		l.setWidth(main.ui.getWidth()-50);
		l.setHeight(200);
		l.setWrap(true);
		l.setFontScale(2f);
		l.setAlignment(Align.center);
		l.setPosition(main.ui.getWidth()/2-l.getWidth()/2, l.getHeight()/10);
		main.ui.addActor(l);

		l.setText(subtitles.remove());
		waitTime = totalTime.remove();

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		if(	voiceOver.isPlaying()) {
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
		}
	}


	public void changeScreen() {
		if(totalTime.isEmpty()) {
			if(shouldLeave) {
				main.ui.clear();
				Camera camera = new Camera(2160f, 1080f, 1920f);
				camera.getCamera().position.set(
						camera.getCamera().viewportWidth / 2f ,
						camera.getCamera().viewportHeight / 2f, 0);
				main.ui = new Stage(camera.getViewport());
				voiceOver.stop();
				main.setScreen(new MainMenu(main));
			}
			else {
				main.ui.clear();
				voiceOver.stop();
				main.setScreen(new MapScreen(main));
			}
		}
		else {
			l.setText(subtitles.remove());
			l.setPosition(main.ui.getWidth()/2-l.getWidth()/2, l.getHeight()/10);
			backgroundImage.setDrawable(background.remove());
			waitTime = totalTime.remove();
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
		voiceOver.dispose();
	}

}


package com.mygdx.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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
		
		FileHandle handle = Gdx.files.internal(cutscene);
		String[] properties = handle.readString().split("\\r?\\n");
		for(String property : properties) {
			if(property.contains("#")) {
				String values[] = property.split("#");
				totalTime.add(Float.parseFloat(values[1]));
				subtitles.add(values[2]);
				background.add(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("cutscene/"+values[0])))));
			}
			else {
				voiceOver = Gdx.audio.newMusic(Gdx.files.internal("cutscene/"+property));
			}
		}
		
		backgroundImage = new Image(background.remove());
		backgroundImage.setWidth(main.ui.getWidth());
		backgroundImage.setHeight(main.ui.getHeight());
		main.ui.addActor(backgroundImage);
		
		l = new Label("VOID", createLabelStyleWithBackground(Color.WHITE));
		l.setWidth(main.ui.getWidth()-50);
		l.setHeight(300);
		l.setWrap(true);
		l.setFontScale(2f);
		l.setAlignment(Align.center);
		l.setPosition(main.ui.getWidth()/2-l.getWidth()/2, l.getHeight()/10);
		main.ui.addActor(l);
		
		l.setText(subtitles.remove());
		waitTime = totalTime.remove();
		
		voiceOver.play();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		stateTime = stateTime + delta;
	
		main.ui.draw();
		
		if(stateTime > waitTime) {
			stateTime = stateTime - waitTime;
			changeScreen();
		}
		
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			totalTime.clear();
			this.changeScreen();
		}
	}
	
	
	
	
    private LabelStyle createLabelStyleWithBackground(Color color) {
    	///core/assets/font/Pixel.ttf
    	FileHandle fontFile = Gdx.files.internal("font/prstartk.ttf");
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 24;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        labelStyle.fontColor = color;
        Sprite s = new Sprite(new Texture(Gdx.files.internal("misc/white.png")));
        s.setColor(Color.BLACK);
        s.setAlpha(0.75f);
        labelStyle.background = new SpriteDrawable(s);
        return labelStyle;
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


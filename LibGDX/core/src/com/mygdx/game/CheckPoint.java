package com.mygdx.game;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.renderable.Node;

public class CheckPoint implements Screen {
	
	float stateTime = 0f;
	
	Main main;
	Node initialNode;
	MapScreen mapScreen;
	Label dead;

	private LinkedList<Float> totalTime;

	private LinkedList<String> subtitles;

	private LinkedList<TextureRegionDrawable> background;

	private Image backgroundImage;
	private Label l;

	private float waitTime;
	
	public CheckPoint(Main main, Node initialNode, MapScreen mapScreen) {
		this.main = main;
		this.initialNode = initialNode;
		this.mapScreen = mapScreen;
		
		totalTime = new LinkedList<>();
		subtitles = new LinkedList<>();
		background = new LinkedList<>();
		
		FileHandle handle = Gdx.files.internal("cutscene/properties/cutscene2.txt");
		String[] properties = handle.readString().split("\\r?\\n");
		for(String property : properties) {
			if(property.contains(",")) {
				String values[] = property.split("#");
				System.out.println("cutscene/"+values[0]);
				totalTime.add(Float.parseFloat(values[1]));
				subtitles.add(values[2]);
				background.add(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("cutscene/"+values[0])))));
			}
		}
		
		dead = new Label("YOU'RE DEAD...", createLabelStyleWithoutBackground(Color.WHITE));
		dead.setPosition(main.ui.getWidth()/2-dead.getWidth()/2, main.ui.getHeight()/2-dead.getHeight()/2);
		main.ui.addActor(dead);

		
		backgroundImage = new Image(background.remove());
		backgroundImage.setWidth(main.ui.getWidth());
		backgroundImage.setHeight(main.ui.getHeight());
		backgroundImage.setVisible(false);
		main.ui.addActor(backgroundImage);
		
		l = new Label("VOID", createLabelStyleWithBackground(Color.WHITE));
		l.setWidth(main.ui.getWidth()-50);
		l.setHeight(300);
		l.setWrap(true);
		l.setFontScale(2f);
		l.setAlignment(Align.center);
		l.setPosition(main.ui.getWidth()/2-l.getWidth()/2, l.getHeight()/10);
		l.setVisible(false);
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		stateTime = stateTime + delta;
		
		

		main.ui.draw();
		
		
		if(stateTime > 3 && dead.isVisible()) {
			stateTime = 0;
			dead.setVisible(false);
			l.setVisible(true);
			backgroundImage.setVisible(true);
		}
		else {
			if(stateTime > waitTime) {
				stateTime = stateTime - waitTime;
				changeScreen();
			}
		}
	}
	
    private LabelStyle createLabelStyleWithoutBackground(Color color) {
    	///core/assets/font/Pixel.ttf
    	FileHandle fontFile = Gdx.files.internal("font/Pixel.ttf");
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 128;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        labelStyle.fontColor = color;
        return labelStyle;
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
			main.ui.clear();
			main.setScreen(new HouseScreen(main, initialNode, mapScreen));
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
		// TODO Auto-generated method stub
		
	}

}

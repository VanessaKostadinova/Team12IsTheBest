package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.mygdx.renderable.Node;

public class EndGame implements Screen {
	
	float stateTime = 0f;
	
	Main main;
	Node initialNode;
	MapScreen mapScreen;
	
	public EndGame(Main main) {
		this.main = main;
		Label l = new Label("GAME OVER", createLabelStyleWithBackground(Color.WHITE));
		l.setPosition(main.ui.getWidth()/2-l.getWidth()/2, main.ui.getHeight()/2-l.getHeight()/2);
		
		main.ui.addActor(l);
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
		
		
		if(stateTime > 5) {
			changeScreen();
		}
	}
	
    private LabelStyle createLabelStyleWithBackground(Color color) {
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
	
	public void changeScreen() {
		dispose();
		main.ui.clear();
		main.setScreen(new Cutscene(main, "cutscene/properties/cutscene2.txt", true));
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

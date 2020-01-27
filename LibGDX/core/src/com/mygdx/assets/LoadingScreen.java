package com.mygdx.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Main;
import com.mygdx.game.MainMenu;

public class LoadingScreen implements Screen {
	
	private Main main;
	private Sprite loading;
	private Label loaded;
	private Label loaded2;
	
	private int loadedAmount = 0;
	
	public LoadingScreen(Main main) {
		this.main = main;
		loading = new Sprite(new Texture(Gdx.files.internal("loading/loading.png")));
		loading.setPosition(Gdx.graphics.getWidth()/2-loading.getWidth()/2, Gdx.graphics.getHeight()/2-loading.getHeight()/2);
		
		loaded = new Label("0%", createLabelStyleWithBackground(Color.WHITE));
		loaded.setWidth(500);
		loaded.setHeight(75);
		loaded.setWrap(true);
		loaded.setFontScale(2f);
		loaded.setAlignment(Align.center);
		loaded.setPosition(main.ui.getWidth()/2-loaded.getWidth()/2, loaded.getHeight()/10);
		main.ui.addActor(loaded);
		
		loaded2 = new Label("0%", createLabelStyleWithBackground(Color.RED));
		loaded2.setWidth(450);
		loaded2.setHeight(50);
		loaded2.setWrap(true);
		loaded2.setFontScale(2f);
		loaded2.setAlignment(Align.center);
		loaded2.setPosition(loaded.getX()+25, loaded.getY()+11);
		loaded2.setWidth(0);
		main.ui.addActor(loaded2);
		
		main.assets.load();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		main.batch.begin();
			loading.draw(main.batch);
		main.batch.end();
		
		if (AssetHandler.manager.update()) {
			loaded2.setText("Loaded!");
			main.setScreen(new MainMenu(main));
		}
		else {
			loadedAmount = (int) (AssetHandler.manager.getProgress()*100);
			loaded2.setText(loadedAmount + "%");
			loaded2.setWidth(AssetHandler.manager.getProgress() * 450);
		}
		
		main.ui.draw();
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
	
    private LabelStyle createLabelStyleWithBackground(Color color) {
    	///core/assets/font/Pixel.ttf
    	FileHandle fontFile = Gdx.files.internal("font/prstartk.ttf");
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 12;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        if(color.equals(Color.RED)) {
        	labelStyle.fontColor = Color.BLACK;
        }
        else {
        	labelStyle.fontColor = color;
        }
        Sprite s = new Sprite(new Texture(Gdx.files.internal("misc/white.png")));
        s.setColor(color);
        s.setAlpha(0.75f);
        labelStyle.background = new SpriteDrawable(s);
        return labelStyle;
    }

}

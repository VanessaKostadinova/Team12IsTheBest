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

/**
 * Creating the loading screen for showing the amount of progress in loading the assets in assetManager.
 * @author Inder Panesar
 * @version 1.0
 */
public class LoadingScreen implements Screen {

	/** The main class */
	private Main main;

	/** the sprite for the loading texture */
	private Sprite loading;

	/** the outer bar of the loading bar */
	private Label outerBar;

	/** the inner bar (actually moving) of the loading bar*/
	private Label innerBar;

	/** The percentage which the AssetManager has currently loaded*/
	private int loadedAmount = 0;

	/**
	 * Used to create an instance of the Loading screen.
	 * @param main The main class (contains Scene2D, Kyro and Renderer)
	 */
	public LoadingScreen(Main main) {
		//Set up the scene.
		this.main = main;
		loading = new Sprite(new Texture(Gdx.files.internal("loading/loading.png")));
		loading.setPosition(Gdx.graphics.getWidth()/2-loading.getWidth()/2, Gdx.graphics.getHeight()/2-loading.getHeight()/2);

		outerBar = new Label("", createLabelStyleWithBackground(Color.WHITE));
		outerBar.setWidth(500);
		outerBar.setHeight(75);
		outerBar.setWrap(true);
		outerBar.setFontScale(2f);
		outerBar.setAlignment(Align.center);
		outerBar.setPosition(main.ui.getWidth()/2-outerBar.getWidth()/2, outerBar.getHeight()/10);
		main.ui.addActor(outerBar);

		innerBar = new Label("0%", createLabelStyleWithBackground(Color.RED));
		innerBar.setWidth(450);
		innerBar.setHeight(50);
		innerBar.setWrap(true);
		innerBar.setFontScale(2f);
		innerBar.setAlignment(Align.center);
		innerBar.setPosition(outerBar.getX()+25, outerBar.getY()+11);
		innerBar.setWidth(0);
		main.ui.addActor(innerBar);

		//Load the assets.
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

		//If Asset Manager not complete then get loaded percentage. Otherwise change screen.
		if (!AssetHandler.MANAGER.update()) {
			loadedAmount = (int) (AssetHandler.MANAGER.getProgress()*100);
			innerBar.setText(loadedAmount + "%");
			innerBar.setWidth(AssetHandler.MANAGER.getProgress() * 450);
		} else {
			innerBar.setText("Loaded!");
			main.setScreen(new MainMenu(main));
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

	/**
	 * Used to create a label with a background color and size 12 font text.
	 * @param color The background color of the label.
	 * @return The LabelStyle of the label. (setting/parameters of label).
	 */
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

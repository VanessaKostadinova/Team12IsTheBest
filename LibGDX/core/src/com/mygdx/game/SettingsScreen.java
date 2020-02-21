package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class SettingsScreen implements Screen {

    private Main main;
    private MainMenu menu;

    public SettingsScreen(final Main main, final MainMenu menu) {
        this.main = main;
        this.menu = menu;


        /*
         * This makes sure the UI fits within any size of screen
         * the elements were made with 1920 pixels of width in mind.
         */
        float width = Gdx.graphics.getWidth();





        /*
         * Setting the image of the title of the game
         * Scale it so it fits within the screen.
         * Set the Position of the title
         * 	> X: The GDX.graphics.getWidth - title.getWidth() this sets it at the top right of the screen. Width*Scale ensure that the image is adjusted correctly for scale.
         * 	> Y: The GDX.graphics.getHeight - title.getHeight() this sets it at the top right of the screen. Height*Scale ensure that the image is adjusted correctly for scale.
         * 	> + 20 : is used to act as the border to make sure it is not exactly up against the top of the window.
         * Set the size of the Item to scale.
         */
        Image title = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Settings.png")))));
        title.setScaling(Scaling.fit);
        title.setPosition(20, main.ui.getHeight()-title.getHeight() - 20);
        title.setSize(title.getWidth(), title.getHeight());


        Image aRatio = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/AspectRatio.png")))));
        aRatio.setScaling(Scaling.fit);
        aRatio.setPosition(20, title.getY() - aRatio.getHeight() - 150);
        aRatio.setSize(aRatio.getWidth(), aRatio.getHeight());

        Image aLeft = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Left.png")))));
        aLeft.setScaling(Scaling.fit);
        aLeft.setPosition(main.ui.getWidth()/2 + 100, aRatio.getY());
        aLeft.setSize(aLeft.getWidth(), aLeft.getHeight());

        Image aRight = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Right.png")))));
        aRight.setScaling(Scaling.fit);
        aRight.setPosition(main.ui.getWidth()/2 + 800, aRatio.getY());
        aRight.setSize(aRight.getWidth(), aRight.getHeight());

        Image fullscreen = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Fullscreen.png")))));
        fullscreen.setScaling(Scaling.fit);
        fullscreen.setPosition(20, aRatio.getY() - fullscreen.getHeight() - 50);
        fullscreen.setSize(fullscreen.getWidth(), fullscreen.getHeight());

        Image fBox = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Deactivated.png")))));
        fBox.setScaling(Scaling.fit);
        fBox.setPosition(main.ui.getWidth()/2 + 400, fullscreen.getY() - 20);
        fBox.setSize(fBox.getWidth(), fBox.getHeight());

        Image vsync = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/V-Sync.png")))));
        vsync.setScaling(Scaling.fit);
        vsync.setPosition(20, fullscreen.getY() - vsync.getHeight() - 50);
        vsync.setSize(vsync.getWidth(), vsync.getHeight());

        Image vBox = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Deactivated.png")))));
        vBox.setScaling(Scaling.fit);
        vBox.setPosition(main.ui.getWidth()/2 + 400, vsync.getY() - 20);
        vBox.setSize(vBox.getWidth(), vBox.getHeight());

        Image sResolution = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Screen Resolution.png")))));
        sResolution.setScaling(Scaling.fit);
        sResolution.setPosition(20, vsync.getY() - sResolution.getHeight() - 50);
        sResolution.setSize(sResolution.getWidth(), sResolution.getHeight());

        Image sLeft = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Left.png")))));
        sLeft.setScaling(Scaling.fit);
        sLeft.setPosition(main.ui.getWidth()/2 + 100, sResolution.getY());
        sLeft.setSize(sLeft.getWidth(), sLeft.getHeight());

        Image sRight = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Right.png")))));
        sRight.setScaling(Scaling.fit);
        sRight.setPosition(main.ui.getWidth()/2 + 800, sResolution.getY());
        sRight.setSize(sRight.getWidth(), sRight.getHeight());

        final Image exit = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Return.png")))));
        exit.setScaling(Scaling.fit);
        exit.setPosition(main.ui.getWidth() - exit.getWidth() - 40f, 40);
        exit.setSize(exit.getWidth(), exit.getHeight());
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.ui.clear();
                main.setScreen(new MainMenu(main));
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/ReturnHover.png"))));
                exit.setDrawable(t);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Return.png"))));
                exit.setDrawable(t);
            }
        });



        //Add all of the actors above to the stage.
        main.ui.addActor(title);
        main.ui.addActor(aRatio);
        main.ui.addActor(fullscreen);
        main.ui.addActor(vsync);
        main.ui.addActor(sResolution);
        main.ui.addActor(aLeft);
        main.ui.addActor(aRight);
        main.ui.addActor(fBox);
        main.ui.addActor(vBox);
        main.ui.addActor(sLeft);
        main.ui.addActor(sRight);
        main.ui.addActor(exit);



        //Start playing the UI Soundtrack.

        //Sets the input processor as Screen.ui as that is where the stage is contained.
        Gdx.input.setInputProcessor(main.ui);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.ui.act(Gdx.graphics.getDeltaTime());
        main.ui.draw();
    }

    @Override
    public void resize(int width, int height) {
        main.ui.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

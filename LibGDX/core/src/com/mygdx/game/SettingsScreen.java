package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.assets.AssetHandler;
import com.sun.org.apache.regexp.internal.RE;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SettingsScreen implements Screen {

    private Main main;
    private MainMenu menu;
    private String[] res;
    private String currentAspectRatio;
    private String currentResolution;
    private Boolean isFullscreen;
    private Boolean vsyncOn;
    private Map<String, Graphics.DisplayMode> resolutions;
    private int resolutionIndex;
    private int aspectRatioIndex;
    private Graphics.DisplayMode current;


    public SettingsScreen(final Main main, final MainMenu menu) {
        this.main = main;
        this.menu = menu;
        this.resolutionIndex = 0;
        this.aspectRatioIndex = 0;
        this.resolutions = new TreeMap<>();
        this.current = Gdx.graphics.getDisplayMode();


        Graphics.Monitor primary = Gdx.graphics.getPrimaryMonitor();
        Graphics.DisplayMode[] modes = Gdx.graphics.getDisplayModes(primary);

        for(Graphics.DisplayMode mode : modes) {
            if(changeModValid(mode)) {
                if (resolutions.get(mode.width + "x" + mode.height) == null) {
                    resolutions.put(mode.width + "x" + mode.height, mode);
                }
            }
        }

        res = new String[resolutions.size()];
        res = resolutions.keySet().toArray(res);

        StringBuilder builder = new StringBuilder();
        builder.append(Gdx.graphics.getWidth());
        builder.append("x");
        builder.append(Gdx.graphics.getHeight());
        this.currentResolution =  builder.toString();

        System.out.println("Current Resolution: " + currentResolution);
        System.out.println("Current Aspect Ratio: " + currentAspectRatio);



        for(int i = 0; i < res.length; i++) {
            if(res[i].equals(currentResolution)) {
                resolutionIndex = i;
            }
        }

        this.isFullscreen = Gdx.graphics.isFullscreen();
        this.vsyncOn = main.getVsync();


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



        Image fullscreen = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Fullscreen.png")))));
        fullscreen.setScaling(Scaling.fit);
        fullscreen.setPosition(20, title.getY() - fullscreen.getHeight() - 150);
        fullscreen.setSize(fullscreen.getWidth(), fullscreen.getHeight());

        final Image fBox = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Deactivated.png")))));

        if(isFullscreen) {
            TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/ActivatedHover.png"))));
            fBox.setDrawable(t);
        }

        fBox.setScaling(Scaling.fit);
        fBox.setPosition(main.ui.getWidth()/2 + 400, fullscreen.getY() - 20);
        fBox.setSize(fBox.getWidth(), fBox.getHeight());

        fBox.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isFullscreen = !(isFullscreen);
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(isFullscreen) {
                    TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Activated.png"))));
                    fBox.setDrawable(t);
                }
                else {
                    TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/DeactivatedHover.png"))));
                    fBox.setDrawable(t);
                }

            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(isFullscreen) {
                    TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/ActivatedHover.png"))));
                    fBox.setDrawable(t);
                }
                else {
                    TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Deactivated.png"))));
                    fBox.setDrawable(t);
                }

            }
        });

        final Image vsync = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/V-Sync.png")))));
        vsync.setScaling(Scaling.fit);
        vsync.setPosition(20, fullscreen.getY() - vsync.getHeight() - 50);
        vsync.setSize(vsync.getWidth(), vsync.getHeight());

        final Image vBox = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Deactivated.png")))));

        if(vsyncOn) {
            TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/ActivatedHover.png"))));
            vBox.setDrawable(t);
        }
        vBox.setScaling(Scaling.fit);
        vBox.setPosition(main.ui.getWidth()/2 + 400, vsync.getY() - 20);
        vBox.setSize(vBox.getWidth(), vBox.getHeight());
        vBox.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                vsyncOn = !(vsyncOn);
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(vsyncOn) {
                    TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Activated.png"))));
                    vBox.setDrawable(t);
                }
                else {
                    TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/DeactivatedHover.png"))));
                    vBox.setDrawable(t);
                }

            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(vsyncOn) {
                    TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/ActivatedHover.png"))));
                    vBox.setDrawable(t);
                }
                else {
                    TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Deactivated.png"))));
                    vBox.setDrawable(t);
                }

            }
        });

        Image sResolution = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Screen Resolution.png")))));
        sResolution.setScaling(Scaling.fit);
        sResolution.setPosition(20, vsync.getY() - sResolution.getHeight() - 50);
        sResolution.setSize(sResolution.getWidth(), sResolution.getHeight());

        final Label resolutionLabel = new Label(res[resolutionIndex], AssetHandler.fontSize48);
        resolutionLabel.setPosition(main.ui.getWidth()/2 + 200, sResolution.getY());
        resolutionLabel.setSize(500, vsync.getHeight());
        resolutionLabel.setAlignment(Align.center);

        final Image sLeft = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Left.png")))));
        sLeft.setScaling(Scaling.fit);
        sLeft.setPosition(main.ui.getWidth()/2 + 100, sResolution.getY());
        sLeft.setSize(sLeft.getWidth(), sLeft.getHeight());
        sLeft.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resolutionIndex = (resolutionIndex - 1) % res.length;
                if(resolutionIndex < 0) {
                    resolutionIndex = res.length + resolutionIndex;
                }
                resolutionLabel.setText(res[resolutionIndex]);
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/HoverLeft.png"))));
                sLeft.setDrawable(t);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Left.png"))));
                sLeft.setDrawable(t);
            }
        });


        final Image sRight = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Right.png")))));
        sRight.setScaling(Scaling.fit);
        sRight.setPosition(main.ui.getWidth()/2 + 800, sResolution.getY());
        sRight.setSize(sRight.getWidth(), sRight.getHeight());
        sRight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resolutionIndex = (resolutionIndex + 1) % res.length;
                resolutionLabel.setText(res[resolutionIndex]);
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/HoverRight.png"))));
                sRight.setDrawable(t);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Right.png"))));
                sRight.setDrawable(t);
            }
        });

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

        final Image apply = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Apply.png")))));
        apply.setScaling(Scaling.fit);
        apply.setPosition(0, 40);
        apply.setSize(exit.getWidth(), exit.getHeight());
        apply.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setVSync(vsyncOn);
                Gdx.graphics.setResizable(false);
                System.out.println("HIT");
                if(isFullscreen) {
                    System.out.println(res[resolutionIndex]);
                    Graphics.DisplayMode mode = resolutions.get(res[resolutionIndex]);
                    System.out.println(mode.height);
                    System.out.println(mode.bitsPerPixel);
                    System.out.println(mode.refreshRate);
                    System.out.println(mode.width);
                    Gdx.graphics.setFullscreenMode(mode);
                }
                else {
                    String[] arrOfStr = res[resolutionIndex].split("x", 2);
                    int width = Integer.parseInt(arrOfStr[0]);
                    int height = Integer.parseInt(arrOfStr[1]);
                    Gdx.graphics.setWindowedMode(width, height);
                }
                main.setScreen(new MainMenu(main));
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/ApplyHover.png"))));
                apply.setDrawable(t);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings/Apply.png"))));
                apply.setDrawable(t);
            }
        });







        //Add all of the actors above to the stage.
        main.ui.addActor(title);
        main.ui.addActor(fullscreen);
        main.ui.addActor(vsync);
        main.ui.addActor(sResolution);
        main.ui.addActor(fBox);
        main.ui.addActor(vBox);
        main.ui.addActor(sLeft);
        main.ui.addActor(sRight);
        main.ui.addActor(resolutionLabel);
        main.ui.addActor(exit);
        main.ui.addActor(apply);



        //Start playing the UI Soundtrack.

        //Sets the input processor as Screen.ui as that is where the stage is contained.
        Gdx.input.setInputProcessor(main.ui);
    }

    public Boolean changeModValid(Graphics.DisplayMode mode) {



        if(mode.refreshRate != current.refreshRate) {
            return false;
        }

        if(mode.bitsPerPixel != current.bitsPerPixel) {
            return false;
        }

        if(mode.height > current.height || mode.width > current.width) {
            return false;
        }

        return true;



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

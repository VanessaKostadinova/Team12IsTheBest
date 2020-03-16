package com.mygdx.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import java.io.File;

/**
 * Used to load assets into the game before playing the game, this reduces the load on a PC as well
 * the loading times as assets are loaded before making loading times from a few seconds to a less than a millisecond.
 *
 * @author Inder Panesar (Team 12)
 * @version 1.0
 */
public class AssetHandler {

    //Static so can be called from anywhere.
    public static AssetManager manager = new AssetManager();

    //All the assets we are loading as well as making them constants as they shouldn't change.
    private static final AssetDescriptor<Texture> house1 = new AssetDescriptor<Texture>("house/House1.gif", Texture.class);
    private static final AssetDescriptor<Texture> shop = new AssetDescriptor<Texture>("house/Shop.gif", Texture.class);
    private static final AssetDescriptor<Texture> background = new AssetDescriptor<Texture>("house/background.png", Texture.class);
    private static final AssetDescriptor<Texture> water = new AssetDescriptor<Texture>("house/water.png", Texture.class);
    private static final AssetDescriptor<Texture> mapTarget = new AssetDescriptor<Texture>("house/aim.png", Texture.class);
    private static final AssetDescriptor<Texture> enterHouse = new AssetDescriptor<Texture>("house/MAP_ENTERHOUSE.png", Texture.class);
    private static final AssetDescriptor<Texture> houseText = new AssetDescriptor<Texture>("house/MAP_HOUSE.png", Texture.class);
    private static final AssetDescriptor<Texture> inspectHouse = new AssetDescriptor<Texture>("house/MAP_INSPECT.png", Texture.class);
    private static final AssetDescriptor<Texture> shopText = new AssetDescriptor<Texture>("shop/SHOP.png", Texture.class);
    private static final AssetDescriptor<Texture> enterShop = new AssetDescriptor<Texture>("shop/ENTER_SHOP.png", Texture.class);
    private static final AssetDescriptor<Texture> baseMapPlayerUI = new AssetDescriptor<Texture>("player/MAPUI/BaseUI.png", Texture.class);
    private static final AssetDescriptor<Texture> currentDayPlayerUI = new AssetDescriptor<Texture>("player/MAPUI/DayLabel.png", Texture.class);
    private static final AssetDescriptor<Texture> healthBarPlayerUI = new AssetDescriptor<Texture>("player/MAPUI/HealthBar.png", Texture.class);
    private static final AssetDescriptor<Texture> nextLabelPlayerUI = new AssetDescriptor<Texture>("player/MAPUI/NextLabel.png", Texture.class);
    private static final AssetDescriptor<Texture> forwardPlayerUI = new AssetDescriptor<Texture>("player/MAPUI/ForwardButton.png", Texture.class);
    private static final AssetDescriptor<Texture> shopBuyUI = new AssetDescriptor<Texture>("shop/screen/BUY.png", Texture.class);
    private static final AssetDescriptor<Texture> shopBuyHoverUI = new AssetDescriptor<Texture>("shop/screen/BUYMOUSE.png", Texture.class);
    private static final AssetDescriptor<Texture> shopItemUI = new AssetDescriptor<Texture>("shop/screen/ITEM.png", Texture.class);
    private static final AssetDescriptor<Texture> shopItemHoverUI = new AssetDescriptor<Texture>("shop/screen/ITEMHOVER.png", Texture.class);
    private static final AssetDescriptor<Texture> shopLeaveUI = new AssetDescriptor<Texture>("shop/screen/LEAVE.png", Texture.class);
    private static final AssetDescriptor<Texture> shopLeaverHoverUI = new AssetDescriptor<Texture>("shop/screen/LEAVEMOUSE.png", Texture.class);
    private static final AssetDescriptor<Texture> shopTextUI = new AssetDescriptor<Texture>("shop/screen/SHOP.png", Texture.class);
    private static final AssetDescriptor<Texture> pickup = new AssetDescriptor<Texture>("pickups/letter/LETTER.png", Texture.class);
    private static final AssetDescriptor<Texture> letter = new AssetDescriptor<Texture>("pickups/letter/PICKUP.png", Texture.class);
    private static final AssetDescriptor<Texture> E = new AssetDescriptor<Texture>("player/icon/ICON.png", Texture.class);
    private static final AssetDescriptor<Texture> UI = new AssetDescriptor<Texture>("house/UI/MAPUI.png", Texture.class);
    private static final AssetDescriptor<Texture> BAR = new AssetDescriptor<Texture>("house/UI/BAR.png", Texture.class);
    private static final AssetDescriptor<Texture> HEALTHBAR = new AssetDescriptor<Texture>("house/UI/HEALTH.png", Texture.class);
    private static final AssetDescriptor<Texture> DialogPlayer = new AssetDescriptor<Texture>("player/MAPUI/dialog.png", Texture.class);


    private static final AssetDescriptor<Texture> npc1alive = new AssetDescriptor<Texture>("NPC/Alive1.gif", Texture.class);
    private static final AssetDescriptor<Texture> npc2alive = new AssetDescriptor<Texture>("NPC/Alive2.gif", Texture.class);
    private static final AssetDescriptor<Texture> npc3alive = new AssetDescriptor<Texture>("NPC/Alive3.gif", Texture.class);
    private static final AssetDescriptor<Texture> npc1sick = new AssetDescriptor<Texture>("NPC/Sick1.gif", Texture.class);
    private static final AssetDescriptor<Texture> npc2sick = new AssetDescriptor<Texture>("NPC/Sick2.gif", Texture.class);
    private static final AssetDescriptor<Texture> npc3sick = new AssetDescriptor<Texture>("NPC/Sick3.gif", Texture.class);
    private static final AssetDescriptor<Texture> npc1dead = new AssetDescriptor<Texture>("NPC/Dead1.gif", Texture.class);
    private static final AssetDescriptor<Texture> npc2dead = new AssetDescriptor<Texture>("NPC/Dead2.gif", Texture.class);
    private static final AssetDescriptor<Texture> npc3dead = new AssetDescriptor<Texture>("NPC/Dead3.gif", Texture.class);
    private static final AssetDescriptor<Texture> npc1burnt = new AssetDescriptor<Texture>("NPC/Dead1.gif", Texture.class);
    private static final AssetDescriptor<Texture> npc2burnt = new AssetDescriptor<Texture>("NPC/Dead2.gif", Texture.class);
    private static final AssetDescriptor<Texture> npc3burnt = new AssetDescriptor<Texture>("NPC/Dead3.gif", Texture.class);

    private static final AssetDescriptor<Texture> cure = new AssetDescriptor<Texture>("house/UI/CureSpray.png", Texture.class);
    private static final AssetDescriptor<Texture> fire = new AssetDescriptor<Texture>("house/UI/FireSpray.png", Texture.class);
    private static final AssetDescriptor<Texture> cutsceneOverlay = new AssetDescriptor<Texture>("cutscene/ingame/cutsceneOverlay.png", Texture.class);
    private static final AssetDescriptor<Texture> cutsceneSpeaker = new AssetDescriptor<Texture>("cutscene/ingame/characterImages/templateCutsceneSpeaker.png", Texture.class);
    private static final AssetDescriptor<Texture> otherDoctor = new AssetDescriptor<Texture>("cutscene/ingame/storyTextures/OtherDoctor.png", Texture.class);


    private static final AssetDescriptor<Texture> maskIcon = new AssetDescriptor<Texture>("icon/maskicon.png", Texture.class);
    private static final AssetDescriptor<Texture> cureIcon = new AssetDescriptor<Texture>("icon/cureicon.png", Texture.class);
    private static final AssetDescriptor<Texture> flameIcon = new AssetDescriptor<Texture>("icon/flameicon.png", Texture.class);
    private static final AssetDescriptor<Texture> enterChurch = new AssetDescriptor<Texture>("shop/church/ENTER_CHURCH.png", Texture.class);
    private static final AssetDescriptor<Texture> church = new AssetDescriptor<Texture>("shop/church/CHURCH.png", Texture.class);
    private static final AssetDescriptor<Texture> alivePercentage = new AssetDescriptor<Texture>("player/MAPUI/ALIVEPERCENTAGE.png", Texture.class);
    private static final AssetDescriptor<Texture> sickPercentage = new AssetDescriptor<Texture>("player/MAPUI/SICKPERCENTAGE.png", Texture.class);
    private static final AssetDescriptor<Texture> deadPercentage = new AssetDescriptor<Texture>("player/MAPUI/DEADPERCENTAGE.png", Texture.class);

    //Single instances of common font sizes and the UI skin used in the game.
    public static final Skin skinUI = new Skin(Gdx.files.internal("skin/terra-mother-ui.json"));
    public static final LabelStyle fontSize24 = createLabelStyleWithBackground();
    public static final LabelStyle fontSize48 = createLabelStyleWithBackground2();
    public static final LabelStyle fontSize12Subtitles = createLabelStyleWithBackground(Color.WHITE);
    public static final LabelStyle fontSize32 = createLabelStyleWithBackground(32);
    public static final LabelStyle fontSize15 = createLabelStyleWithBackground(15);
    public static final LabelStyle fontSize60SubtitlesBlack = createLabelStyleWithBackground(Color.BLACK, 2);
    public static final LabelStyle fontSize60SubtitlesWhite = createLabelStyleWithBackground(Color.WHITE, 2);
    public static final LabelStyle fontSize60SubtitlesCyan = createLabelStyleWithBackground(Color.CYAN, 2);

    /**
     * Load all the assets within the game.
     */
    public void load() {
    	manager.load(house1);
    	manager.load(shop);
    	manager.load(background);
    	manager.load(water);
    	manager.load(mapTarget);
    	manager.load(enterHouse);
    	manager.load(houseText);
    	manager.load(inspectHouse);
    	manager.load(shopText);
    	manager.load(enterShop);
    	manager.load(baseMapPlayerUI);
    	manager.load(currentDayPlayerUI);
    	manager.load(healthBarPlayerUI);
    	manager.load(nextLabelPlayerUI);
    	manager.load(forwardPlayerUI);
    	manager.load(DialogPlayer);
    	manager.load(shopBuyUI);
    	manager.load(shopBuyHoverUI);
    	manager.load(shopItemUI);
    	manager.load(shopItemHoverUI);
    	manager.load(shopLeaveUI);
    	manager.load(shopLeaverHoverUI);
    	manager.load(shopTextUI);
    	//manager.load(textureFont);
    	
    	manager.load(pickup);
    	manager.load(letter);

        manager.load(npc1alive);
        manager.load(npc2alive);
        manager.load(npc3alive);
        manager.load(npc1sick);
        manager.load(npc2sick);
        manager.load(npc3sick);
        manager.load(npc1dead);
        manager.load(npc2dead);
        manager.load(npc3dead);
        manager.load(npc1burnt);
        manager.load(npc2burnt);
        manager.load(npc3burnt);
    	
    	manager.load(E);
    	manager.load(UI);
    	manager.load(BAR);
    	manager.load(HEALTHBAR);

    	manager.load(cure);
    	manager.load(fire);
    	manager.load(cutsceneOverlay);
    	manager.load(cutsceneSpeaker);
    	manager.load(otherDoctor);

    	manager.load(maskIcon);
    	manager.load(flameIcon);
    	manager.load(cureIcon);
    	manager.load(enterChurch);
    	manager.load(church);
    	manager.load(alivePercentage);
    	manager.load(deadPercentage);
    	manager.load(sickPercentage);


    	manager.load("cutscene/1/Intro.mp3", Music.class);


    }


    /**
     * Used to create a label with no background and a font size of 24
     * @return LabelStyle which is the settings/looks of the label.
     */
    private static LabelStyle createLabelStyleWithBackground(int size) {

        ///core/assets/font/Pixel.ttf
        FileHandle fontFile = Gdx.files.internal("font/Pixel.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        labelStyle.fontColor = Color.WHITE;

        return labelStyle;
    }

    private static LabelStyle createLabelStyleWithBackground(Color color, int value) {
        ///core/assets/font/Pixel.ttf
        FileHandle fontFile = Gdx.files.internal("font/Pixel.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 60;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        labelStyle.fontColor = color;
        return labelStyle;
    }

    /**
     * Used to create a label with no background and a font size of 24
     * @return LabelStyle which is the settings/looks of the label.
     */
    private static LabelStyle createLabelStyleWithBackground() {

    	///core/assets/font/Pixel.ttf
    	FileHandle fontFile = Gdx.files.internal("font/Pixel.ttf");
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 24;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        labelStyle.fontColor = Color.WHITE;
		
        return labelStyle;
    }

    /**
     * Used to create a label with no background and a font size of 48
     * @return LabelStyle which is the settings/looks of the label
     */
    private static LabelStyle createLabelStyleWithBackground2() {

    	///core/assets/font/Pixel.ttf
    	FileHandle fontFile = Gdx.files.internal("font/Pixel.ttf");
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 48;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        labelStyle.fontColor = Color.WHITE;
		
        return labelStyle;
    }

    /**
     * Used to create a label with no background and a font size of 24
     * @param color is the background color.
     * @return LabelStyle which is the settings/looks of the label.
     */
    private static LabelStyle createLabelStyleWithBackground(Color color) {
    	///core/assets/font/Pixel.ttf
    	FileHandle fontFile = Gdx.files.internal("font/prstartk.ttf");
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 12;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        labelStyle.fontColor = color;
        Sprite s = new Sprite(new Texture(Gdx.files.internal("misc/white.png")));
        s.setColor(Color.BLACK);
        s.setAlpha(0.75f);
        labelStyle.background = new SpriteDrawable(s);
        return labelStyle;
    }

    /**
     * Dispose of the manager.
     */
    public void dispose() {
        manager.dispose();
    }
}
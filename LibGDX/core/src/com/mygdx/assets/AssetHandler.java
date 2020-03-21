package com.mygdx.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * Used to load assets into the game before playing the game, this reduces the load on a PC as well
 * the loading times as assets are loaded before making loading times from a few seconds to a less than a millisecond.
 *
 * @author Inder Panesar (Team 12)
 * @version 1.0
 */
public class AssetHandler {

    /** Manages all the assets which have been loaded */
    public static AssetManager MANAGER = new AssetManager();
    /** The house sprite - The house sprite is type 1 */
    private static final AssetDescriptor<Texture> HOUSE_1 = new AssetDescriptor<>("house/House1.gif", Texture.class);
    /** The Shop sprite */
    private static final AssetDescriptor<Texture> SHOP = new AssetDescriptor<>("house/Shop.gif", Texture.class);
    /** The Church Sprite */
    private static final AssetDescriptor<Texture> CHURCH_IMAGE = new AssetDescriptor<>("house/Church.png", Texture.class);
    /** The background for the main menu */
    private static final AssetDescriptor<Texture> BACKGROUND = new AssetDescriptor<>("house/background.png", Texture.class);
    /** The water texture */
    private static final AssetDescriptor<Texture> WATER = new AssetDescriptor<>("house/water.png", Texture.class);
    /** The aim recital for the map screen */
    private static final AssetDescriptor<Texture> MAP_TARGET = new AssetDescriptor<>("house/aim.png", Texture.class);
    /** The enter house asset when hovering over node*/
    private static final AssetDescriptor<Texture> ENTER_HOUSE = new AssetDescriptor<>("house/MAP_ENTERHOUSE.png", Texture.class);
    /** The House Text asset when hovering over node*/
    private static final AssetDescriptor<Texture> HOUSE_TEXT = new AssetDescriptor<>("house/MAP_HOUSE.png", Texture.class);
    /** The map inspect house asset when hovering over node*/
    private static final AssetDescriptor<Texture> INSPECT_HOUSE = new AssetDescriptor<>("house/MAP_INSPECT.png", Texture.class);
    /** The shop text asset when hovering over a shop */
    private static final AssetDescriptor<Texture> SHOP_TEXT = new AssetDescriptor<>("shop/SHOP.png", Texture.class);
    /** The enter shop asset when hovering over a shop */
    private static final AssetDescriptor<Texture> ENTER_SHOP = new AssetDescriptor<>("shop/ENTER_SHOP.png", Texture.class);
    /** Base map UI assets */
    private static final AssetDescriptor<Texture> BASE_MAP_PLAYER_UI = new AssetDescriptor<>("player/MAPUI/BaseUI.png", Texture.class);
    /** The Label for day label */
    private static final AssetDescriptor<Texture> CURRENT_DAY_PLAYER_UI = new AssetDescriptor<>("player/MAPUI/DayLabel.png", Texture.class);
    /** The health bar for player */
    private static final AssetDescriptor<Texture> HEALTH_BAR_PLAYER_UI = new AssetDescriptor<>("player/MAPUI/HealthBar.png", Texture.class);
    /** The next label for the player */
    private static final AssetDescriptor<Texture> NEXT_LABEL_PLAYER_UI = new AssetDescriptor<>("player/MAPUI/NextLabel.png", Texture.class);
    /** The texture to identify the forward button*/
    private static final AssetDescriptor<Texture> FORWARD_PLAYER_UI = new AssetDescriptor<>("player/MAPUI/ForwardButton.png", Texture.class);
    /** The texture - buy items in shop/church*/
    private static final AssetDescriptor<Texture> SHOP_BUY_UI = new AssetDescriptor<>("shop/screen/BUY.png", Texture.class);
    /** The texture - hover over buy items in shop/church*/
    private static final AssetDescriptor<Texture> SHOP_BUY_HOVER_UI = new AssetDescriptor<>("shop/screen/BUYMOUSE.png", Texture.class);
    /** The texture - when you click a item */
    private static final AssetDescriptor<Texture> SHOP_ITEM_UI = new AssetDescriptor<>("shop/screen/ITEM.png", Texture.class);
    /** The texture - when you hover of a item*/
    private static final AssetDescriptor<Texture> SHOP_ITEM_HOVER_UI = new AssetDescriptor<>("shop/screen/ITEMHOVER.png", Texture.class);
    /** The texture - when you click leave on shop/church */
    private static final AssetDescriptor<Texture> SHOP_LEAVE_UI = new AssetDescriptor<>("shop/screen/LEAVE.png", Texture.class);
    /** The texture - when you hover owerleave on shop/church */
    private static final AssetDescriptor<Texture> SHOP_LEAVER_HOVER_UI = new AssetDescriptor<>("shop/screen/LEAVEMOUSE.png", Texture.class);
    /** The texture - for the shop title */
    private static final AssetDescriptor<Texture> SHOP_TEXT_UI = new AssetDescriptor<>("shop/screen/SHOP.png", Texture.class);
    /** The texture - for the pickups in the gmae */
    private static final AssetDescriptor<Texture> PICKUP = new AssetDescriptor<>("pickups/letter/LETTER.png", Texture.class);
    /** The texture - the texture for the letter */
    private static final AssetDescriptor<Texture> LETTER = new AssetDescriptor<>("pickups/letter/PICKUP.png", Texture.class);
    /** The texture - for leaving the house */
    private static final AssetDescriptor<Texture> E = new AssetDescriptor<>("player/icon/ICON.png", Texture.class);
    /** The texture - for the map ui */
    private static final AssetDescriptor<Texture> UI = new AssetDescriptor<>("house/UI/MAPUI.png", Texture.class);
    /** The texture - a general bar used for mask*/
    private static final AssetDescriptor<Texture> BAR = new AssetDescriptor<>("house/UI/BAR.png", Texture.class);
    /** The texture - health bar */
    private static final AssetDescriptor<Texture> HEALTH_BAR = new AssetDescriptor<>("house/UI/HEALTH.png", Texture.class);
    /** The texture - dialog for the cutscenes */
    private static final AssetDescriptor<Texture> DIALOG_PLAYER = new AssetDescriptor<>("player/MAPUI/dialog.png", Texture.class);
    /** The texture - the villager alive texture -1  */
    private static final AssetDescriptor<Texture> NPC_1_ALIVE = new AssetDescriptor<Texture>("NPC/Alive1.gif", Texture.class);
    /** The texture - the villager alive texture -2  */
    private static final AssetDescriptor<Texture> NPC_2_ALIVE = new AssetDescriptor<Texture>("NPC/Alive2.gif", Texture.class);
    /** The texture - the villager alive texture -3  */
    private static final AssetDescriptor<Texture> NPC_3_ALIVE = new AssetDescriptor<Texture>("NPC/Alive3.gif", Texture.class);
    /** The texture - the villager sick texture -1  */
    private static final AssetDescriptor<Texture> NPC_1_SICK = new AssetDescriptor<Texture>("NPC/Sick1.gif", Texture.class);
    /** The texture - the villager sick texture -2  */
    private static final AssetDescriptor<Texture> NPC_2_SICK = new AssetDescriptor<Texture>("NPC/Sick2.gif", Texture.class);
    /** The texture - the villager sick texture -3  */
    private static final AssetDescriptor<Texture> NPC_3_SICK = new AssetDescriptor<Texture>("NPC/Sick3.gif", Texture.class);
    /** The texture - the villager dead texture -1  */
    private static final AssetDescriptor<Texture> NPC_1_DEAD = new AssetDescriptor<Texture>("NPC/Dead1.gif", Texture.class);
    /** The texture - the villager dead texture -2  */
    private static final AssetDescriptor<Texture> NPC_2_DEAD = new AssetDescriptor<Texture>("NPC/Dead2.gif", Texture.class);
    /** The texture - the villager dead texture -3  */
    private static final AssetDescriptor<Texture> NPC_3_DEAD = new AssetDescriptor<Texture>("NPC/Dead3.gif", Texture.class);
    /** The texture - the villager burnt texture -1  */
    private static final AssetDescriptor<Texture> NPC_1_BURNT = new AssetDescriptor<Texture>("NPC/Burnt1.gif", Texture.class);
    /** The texture - the villager burnt texture -2  */
    private static final AssetDescriptor<Texture> NPC_2_BURNT = new AssetDescriptor<Texture>("NPC/Burnt2.gif", Texture.class);
    /** The texture - the villager burnt texture -3  */
    private static final AssetDescriptor<Texture> NPC_3_BURNT = new AssetDescriptor<Texture>("NPC/Burnt3.gif", Texture.class);
    /** The texture for the cure spray  */
    private static final AssetDescriptor<Texture> CURE = new AssetDescriptor<Texture>("house/UI/CureSpray.png", Texture.class);
    /** The texture for the fire spray */
    private static final AssetDescriptor<Texture> FIRE = new AssetDescriptor<Texture>("house/UI/FireSpray.png", Texture.class);
    /** The texture for cutscene overlays to darken the screen  */
    private static final AssetDescriptor<Texture> CUTSCENE_OVERLAY = new AssetDescriptor<Texture>("cutscene/ingame/cutsceneOverlay.png", Texture.class);
    /** The texture for the cutscene speaker  */
    private static final AssetDescriptor<Texture> CUTSCENE_SPEAKER = new AssetDescriptor<Texture>("cutscene/ingame/characterImages/templateCutsceneSpeaker.png", Texture.class);
    /** The texture for the other doctor  */
    private static final AssetDescriptor<Texture> OTHER_DOCTOR = new AssetDescriptor<Texture>("cutscene/ingame/storyTextures/OtherDoctor.png", Texture.class);
    /** The texture for the mask icon in inventory selection  */
    private static final AssetDescriptor<Texture> MASK_ICON = new AssetDescriptor<Texture>("icon/maskicon.png", Texture.class);
    /** The texture for the cure icon in inventory selection */
    private static final AssetDescriptor<Texture> CURE_ICON = new AssetDescriptor<Texture>("icon/cureicon.png", Texture.class);
    /** The texture for the flame icon in inventory selection  */
    private static final AssetDescriptor<Texture> FLAME_ICON = new AssetDescriptor<Texture>("icon/flameicon.png", Texture.class);
    /** The texture for enter church  */
    private static final AssetDescriptor<Texture> ENTER_CHURCH = new AssetDescriptor<Texture>("shop/church/ENTER_CHURCH.png", Texture.class);
    /** The texture for church title */
    private static final AssetDescriptor<Texture> CHURCH = new AssetDescriptor<Texture>("shop/church/CHURCH.png", Texture.class);
    /** The texture for number of alive UI on map screen  */
    private static final AssetDescriptor<Texture> ALIVE_PERCENTAGE = new AssetDescriptor<Texture>("player/MAPUI/ALIVEPERCENTAGE.png", Texture.class);
    /** The texture for number of sick UI on map screen  */
    private static final AssetDescriptor<Texture> SICK_PERCENTAGE = new AssetDescriptor<Texture>("player/MAPUI/SICKPERCENTAGE.png", Texture.class);
    /** The texture for number of dead UI on map screen  */
    private static final AssetDescriptor<Texture> DEAD_PERCENTAGE = new AssetDescriptor<Texture>("player/MAPUI/DEADPERCENTAGE.png", Texture.class);
    /** The texture for the spray image  */
    private static final AssetDescriptor<Texture> SPRAY_IMAGE = new AssetDescriptor<Texture>("house/UI/CureSpray.png", Texture.class);

    /** Single instances of common font sizes and the UI skin used in the game. */
    public static final Skin SKIN_UI = new Skin(Gdx.files.internal("skin/terra-mother-ui.json"));
    /** A static instance of the cutscene font */
    public static final FileHandle CUTSCENE_FONT = Gdx.files.internal("font/prstartk.ttf");
    /** A static instance of the normal font  */
    public static final FileHandle NORMAL_FONT = Gdx.files.internal("font/Pixel.ttf");
    /** A static instance of label style of font size 24 and color white  */
    public static final LabelStyle FONT_SIZE_24 = createLabelStyleWithoutBackground(Color.WHITE, 24);
    /** A static instance of label style of font size 48 and color white  */
    public static final LabelStyle FONT_SIZE_48 = createLabelStyleWithoutBackground(Color.WHITE, 48);
    /** A static instance of label style of font size 32 and color white  */
    public static final LabelStyle FONT_SIZE_32 = createLabelStyleWithoutBackground(Color.WHITE, 32);
    /** A static instance of label style of font size 24 and color white specifically for cutscenes  */
    public static final LabelStyle FONT_SIZE_CUT_SCENE_24 = createLabelStyleWithoutBackgroundCutscene(24);
    /** A static instance of label style of font size 15 and color white  */
    public static final LabelStyle FONT_SIZE_15 = createLabelStyleWithoutBackground(Color.WHITE, 15);
    /** A static instance of label style of font size 60 and color black  */
    public static final LabelStyle FONT_SIZE_60_SUBTITLES_BLACK = createLabelStyleWithoutBackground(Color.BLACK, 60);
    /** A static instance of label style of font size 60 and color white  */
    public static final LabelStyle FONT_SIZE_60_SUBTITLES_WHITE = createLabelStyleWithoutBackground(Color.WHITE, 60);
    /** A static instance of label style of font size 60 and color cyan  */
    public static final LabelStyle FONT_SIZE_60_SUBTITLES_CYAN = createLabelStyleWithoutBackground(Color.CYAN, 60);
    /** A static instance of label style of font size 128 and color white  */
    public static final LabelStyle FONT_SIZE_128_WHITE = createLabelStyleWithoutBackground(Color.WHITE, 128);
    /** A static instance of file handle which holds the error message for not enough energy  */
    public static final FileHandle NOT_ENOUGH_ENERGY_ERROR = Gdx.files.internal("error/notenoughenergy.csv");


    /**
     * Load all the assets within the game.
     */
    public void load() {
    	MANAGER.load(HOUSE_1);
    	MANAGER.load(SHOP);
    	MANAGER.load(BACKGROUND);
    	MANAGER.load(WATER);
    	MANAGER.load(MAP_TARGET);
    	MANAGER.load(ENTER_HOUSE);
    	MANAGER.load(HOUSE_TEXT);
    	MANAGER.load(INSPECT_HOUSE);
    	MANAGER.load(SHOP_TEXT);
    	MANAGER.load(ENTER_SHOP);
    	MANAGER.load(BASE_MAP_PLAYER_UI);
    	MANAGER.load(CURRENT_DAY_PLAYER_UI);
    	MANAGER.load(HEALTH_BAR_PLAYER_UI);
    	MANAGER.load(NEXT_LABEL_PLAYER_UI);
    	MANAGER.load(FORWARD_PLAYER_UI);
    	MANAGER.load(DIALOG_PLAYER);
    	MANAGER.load(SHOP_BUY_UI);
    	MANAGER.load(SHOP_BUY_HOVER_UI);
    	MANAGER.load(SHOP_ITEM_UI);
    	MANAGER.load(SHOP_ITEM_HOVER_UI);
    	MANAGER.load(SHOP_LEAVE_UI);
    	MANAGER.load(SHOP_LEAVER_HOVER_UI);
    	MANAGER.load(SHOP_TEXT_UI);
    	MANAGER.load(PICKUP);
    	MANAGER.load(LETTER);
        MANAGER.load(NPC_1_ALIVE);
        MANAGER.load(NPC_2_ALIVE);
        MANAGER.load(NPC_3_ALIVE);
        MANAGER.load(NPC_1_SICK);
        MANAGER.load(NPC_2_SICK);
        MANAGER.load(NPC_3_SICK);
        MANAGER.load(NPC_1_DEAD);
        MANAGER.load(NPC_2_DEAD);
        MANAGER.load(NPC_3_DEAD);
        MANAGER.load(NPC_1_BURNT);
        MANAGER.load(NPC_2_BURNT);
        MANAGER.load(NPC_3_BURNT);
    	MANAGER.load(E);
    	MANAGER.load(UI);
    	MANAGER.load(BAR);
    	MANAGER.load(HEALTH_BAR);
    	MANAGER.load(CURE);
    	MANAGER.load(FIRE);
    	MANAGER.load(CUTSCENE_OVERLAY);
    	MANAGER.load(CUTSCENE_SPEAKER);
    	MANAGER.load(OTHER_DOCTOR);
    	MANAGER.load(MASK_ICON);
    	MANAGER.load(FLAME_ICON);
    	MANAGER.load(CURE_ICON);
    	MANAGER.load(ENTER_CHURCH);
    	MANAGER.load(CHURCH);
    	MANAGER.load(ALIVE_PERCENTAGE);
    	MANAGER.load(DEAD_PERCENTAGE);
    	MANAGER.load(SICK_PERCENTAGE);
    	MANAGER.load(CHURCH_IMAGE);
    	MANAGER.load(SPRAY_IMAGE);
    	MANAGER.load("cutscene/1/Intro.mp3", Music.class);
    }

    /**
     * Used to create a label with no background for cutscenes
     * @param size the size of the font.
     * @return LabelStyle which is the settings/looks of the label.
     */
    private static LabelStyle createLabelStyleWithoutBackgroundCutscene(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(CUTSCENE_FONT);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        labelStyle.fontColor = Color.WHITE;
        return labelStyle;
    }

    /**
     * Used to create a label with no background for anything but cutscenes.
     * @param color the color of the font.
     * @param size the size of the font.
     * @return LabelStyle which is the settings/looks of the label.
     */
    private static LabelStyle createLabelStyleWithoutBackground(Color color, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(NORMAL_FONT);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        labelStyle.fontColor = color;
        return labelStyle;
    }


    /**
     * Dispose of the manager.
     */
    public void dispose() {
        MANAGER.dispose();
    }
}
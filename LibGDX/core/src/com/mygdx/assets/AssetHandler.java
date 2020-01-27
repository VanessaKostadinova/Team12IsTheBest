package com.mygdx.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class AssetHandler {
    public static AssetManager manager = new AssetManager();

    //private static final AssetDescriptor<Texture> someTexture = new AssetDescriptor<Texture>("images/sometexture.png", Texture.class);
    //private static final AssetDescriptor<TextureAtlas> uiAtlas = new AssetDescriptor<TextureAtlas>("ui/uiskin.pack", TextureAtlas.class);
    //private static final AssetDescriptor<Skin> uiSkin = new AssetDescriptor<Skin>("ui/uiskin.json", Skin.class, new SkinLoader.SkinParameter("ui/uiskin.pack"));
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

    
    private static final AssetDescriptor<Texture> scene1Part1 = new AssetDescriptor<Texture>("cutscene/1/1.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part2 = new AssetDescriptor<Texture>("cutscene/1/2.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part3 = new AssetDescriptor<Texture>("cutscene/1/3.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part4 = new AssetDescriptor<Texture>("cutscene/1/4.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part5 = new AssetDescriptor<Texture>("cutscene/1/5.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part6 = new AssetDescriptor<Texture>("cutscene/1/6.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part7 = new AssetDescriptor<Texture>("cutscene/1/7.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part8 = new AssetDescriptor<Texture>("cutscene/1/8.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part9 = new AssetDescriptor<Texture>("cutscene/1/9.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part10 = new AssetDescriptor<Texture>("cutscene/1/10.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part11 = new AssetDescriptor<Texture>("cutscene/1/11.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part12 = new AssetDescriptor<Texture>("cutscene/1/12.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part13 = new AssetDescriptor<Texture>("cutscene/1/13.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part14 = new AssetDescriptor<Texture>("cutscene/1/14.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part15 = new AssetDescriptor<Texture>("cutscene/1/15.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part16 = new AssetDescriptor<Texture>("cutscene/1/30.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part17 = new AssetDescriptor<Texture>("cutscene/1/31.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part18 = new AssetDescriptor<Texture>("cutscene/1/32.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part19 = new AssetDescriptor<Texture>("cutscene/1/33.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part20 = new AssetDescriptor<Texture>("cutscene/1/34.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part21 = new AssetDescriptor<Texture>("cutscene/1/35.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part22 = new AssetDescriptor<Texture>("cutscene/1/36.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part23 = new AssetDescriptor<Texture>("cutscene/1/37.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part24 = new AssetDescriptor<Texture>("cutscene/1/38.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part25 = new AssetDescriptor<Texture>("cutscene/1/39.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part26 = new AssetDescriptor<Texture>("cutscene/1/40.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part27 = new AssetDescriptor<Texture>("cutscene/1/41.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part28 = new AssetDescriptor<Texture>("cutscene/1/42.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part29 = new AssetDescriptor<Texture>("cutscene/1/43.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part30 = new AssetDescriptor<Texture>("cutscene/1/44.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part31 = new AssetDescriptor<Texture>("cutscene/1/20.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part32 = new AssetDescriptor<Texture>("cutscene/1/21.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part33 = new AssetDescriptor<Texture>("cutscene/1/22.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part34 = new AssetDescriptor<Texture>("cutscene/1/23.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part35 = new AssetDescriptor<Texture>("cutscene/1/24.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part36 = new AssetDescriptor<Texture>("cutscene/1/25.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part37 = new AssetDescriptor<Texture>("cutscene/1/26.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part38 = new AssetDescriptor<Texture>("cutscene/1/27.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part39 = new AssetDescriptor<Texture>("cutscene/1/45.png", Texture.class);
    private static final AssetDescriptor<Texture> scene1Part40 = new AssetDescriptor<Texture>("cutscene/1/46.png", Texture.class);








   // FreetypeFontLoader.FreeTypeFontLoaderParameter params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();

    //params.fontFileName = "fonts/coastershadow.ttf";
    //params.fontParameters.size = 30;
    
   // private static final AssetDescriptor<BitmapFont> textureFont = new AssetDescriptor<BitmapFont>("font/Pixel.tff", BitmapFont.class, params);

    
    //"skin/terra-mother-ui.json"

    
    
    
    public void load() {
        //manager.load(someTexture);
        //manager.load(uiAtlas);
        //manager.load(uiSkin);
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
    	
    	manager.load(E);
    	manager.load(UI);
    	manager.load(BAR);
    	manager.load(HEALTHBAR);
    	
    	
    	manager.load(scene1Part1);
    	manager.load(scene1Part2);
    	manager.load(scene1Part3);
    	manager.load(scene1Part4);
    	manager.load(scene1Part5);
    	manager.load(scene1Part6);
    	manager.load(scene1Part7);
    	manager.load(scene1Part8);
    	manager.load(scene1Part9);
    	manager.load(scene1Part10);
    	manager.load(scene1Part11);
    	manager.load(scene1Part12);
    	manager.load(scene1Part13);
    	manager.load(scene1Part14);
    	manager.load(scene1Part15);
    	manager.load(scene1Part16);
    	manager.load(scene1Part17);
    	manager.load(scene1Part18);
    	manager.load(scene1Part19);
    	manager.load(scene1Part20);
    	manager.load(scene1Part21);
    	manager.load(scene1Part22);
    	manager.load(scene1Part23);
    	manager.load(scene1Part24);
    	manager.load(scene1Part25);
    	manager.load(scene1Part26);
    	manager.load(scene1Part27);
    	manager.load(scene1Part28);
    	manager.load(scene1Part29);
    	manager.load(scene1Part30);
    	manager.load(scene1Part31);
    	manager.load(scene1Part32);
    	manager.load(scene1Part33);
    	manager.load(scene1Part34);
    	manager.load(scene1Part35);
    	manager.load(scene1Part36);
    	manager.load(scene1Part37);
    	manager.load(scene1Part38);
    	manager.load(scene1Part39);
    	manager.load(scene1Part40);

    	manager.load("cutscene/1/Intro.mp3", Music.class);
			
    	


    }

    public void dispose() {
        manager.dispose();
    }
}
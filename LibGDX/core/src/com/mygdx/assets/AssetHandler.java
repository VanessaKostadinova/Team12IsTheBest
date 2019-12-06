package com.mygdx.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

public class AssetHandler {
    public AssetManager manager = new AssetManager();

    //public static final AssetDescriptor<Texture> someTexture = new AssetDescriptor<Texture>("images/sometexture.png", Texture.class);
    //public static final AssetDescriptor<TextureAtlas> uiAtlas = new AssetDescriptor<TextureAtlas>("ui/uiskin.pack", TextureAtlas.class);
    //public static final AssetDescriptor<Skin> uiSkin = new AssetDescriptor<Skin>("ui/uiskin.json", Skin.class, new SkinLoader.SkinParameter("ui/uiskin.pack"));
    public static final AssetDescriptor<Texture> house1 = new AssetDescriptor<Texture>("house/House1.gif", Texture.class);
    public static final AssetDescriptor<Texture> shop = new AssetDescriptor<Texture>("house/Shop.gif", Texture.class);
    public static final AssetDescriptor<Texture> background = new AssetDescriptor<Texture>("house/background.png", Texture.class);
    public static final AssetDescriptor<Texture> water = new AssetDescriptor<Texture>("house/water.png", Texture.class);
    public static final AssetDescriptor<Texture> mapTarget = new AssetDescriptor<Texture>("house/aim.png", Texture.class);
    
    public static final AssetDescriptor<Texture> enterHouse = new AssetDescriptor<Texture>("house/MAP_ENTERHOUSE.png", Texture.class);
    public static final AssetDescriptor<Texture> houseText = new AssetDescriptor<Texture>("house/MAP_HOUSE.png", Texture.class);
    public static final AssetDescriptor<Texture> inspectHouse = new AssetDescriptor<Texture>("house/MAP_INSPECT.png", Texture.class);
    
    public static final AssetDescriptor<Texture> shopText = new AssetDescriptor<Texture>("shop/SHOP.png", Texture.class);
    public static final AssetDescriptor<Texture> enterShop = new AssetDescriptor<Texture>("shop/ENTER_SHOP.png", Texture.class);
    
    public static final AssetDescriptor<Texture> baseMapPlayerUI = new AssetDescriptor<Texture>("player/MAPUI/BaseUI.png", Texture.class);
    public static final AssetDescriptor<Texture> currentDayPlayerUI = new AssetDescriptor<Texture>("player/MAPUI/DayLabel.png", Texture.class);
    public static final AssetDescriptor<Texture> healthBarPlayerUI = new AssetDescriptor<Texture>("player/MAPUI/HealthBar.png", Texture.class);
    public static final AssetDescriptor<Texture> nextLabelPlayerUI = new AssetDescriptor<Texture>("player/MAPUI/NextLabel.png", Texture.class);
    public static final AssetDescriptor<Texture> forwardPlayerUI = new AssetDescriptor<Texture>("player/MAPUI/ForwardButton.png", Texture.class);

    public static final AssetDescriptor<Texture> shopBuyUI = new AssetDescriptor<Texture>("shop/screen/BUY.png", Texture.class);
    public static final AssetDescriptor<Texture> shopBuyHoverUI = new AssetDescriptor<Texture>("shop/screen/BUYMOUSE.png", Texture.class);
    public static final AssetDescriptor<Texture> shopItemUI = new AssetDescriptor<Texture>("shop/screen/ITEM.png", Texture.class);
    public static final AssetDescriptor<Texture> shopItemHoverUI = new AssetDescriptor<Texture>("shop/screen/ITEMHOVER.png", Texture.class);
    public static final AssetDescriptor<Texture> shopLeaveUI = new AssetDescriptor<Texture>("shop/screen/LEAVE.png", Texture.class);
    public static final AssetDescriptor<Texture> shopLeaverHoverUI = new AssetDescriptor<Texture>("shop/screen/LEAVEMOUSE.png", Texture.class);
    public static final AssetDescriptor<Texture> shopTextUI = new AssetDescriptor<Texture>("shop/screen/SHOP.png", Texture.class);
    
    public static final AssetDescriptor<Texture> pickup = new AssetDescriptor<Texture>("pickups/letter/LETTER.png", Texture.class);
    public static final AssetDescriptor<Texture> letter = new AssetDescriptor<Texture>("pickups/letter/PICKUP.png", Texture.class);

    public static final AssetDescriptor<Texture> E = new AssetDescriptor<Texture>("player/icon/ICON.png", Texture.class);

    public static final AssetDescriptor<Texture> UI = new AssetDescriptor<Texture>("house/UI/MAPUI.png", Texture.class);
    public static final AssetDescriptor<Texture> BAR = new AssetDescriptor<Texture>("house/UI/BAR.png", Texture.class);
    
    public static final AssetDescriptor<Texture> HEALTHBAR = new AssetDescriptor<Texture>("house/UI/HEALTH.png", Texture.class);
    public static final AssetDescriptor<Music> MAINMENUSOUND = new AssetDescriptor<Music>("main_menu_assets/music.mp3", Music.class);


   // FreetypeFontLoader.FreeTypeFontLoaderParameter params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();

    //params.fontFileName = "fonts/coastershadow.ttf";
    //params.fontParameters.size = 30;
    
   // public static final AssetDescriptor<BitmapFont> textureFont = new AssetDescriptor<BitmapFont>("font/Pixel.tff", BitmapFont.class, params);

    
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
    	manager.load(MAINMENUSOUND);
			
    	


    }

    public void dispose() {
        manager.dispose();
    }
}
package com.mygdx.house;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.assets.AssetHandler;
import com.mygdx.renderable.Renderable;

/**
 * The torch class, holds torch specific information.
 * @author Inder Panesar
 * @version 1.0
 */
public class Torch extends Renderable {
	
	public Torch(float x, float y, float rotation) {
		super.setSprite(AssetHandler.MANAGER.get("levels/TORCH.gif", Texture.class), x, y);
		super.setRotation(rotation);
	}

}

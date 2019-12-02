package com.mygdx.house;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.renderable.Renderable;

public class Torch extends Renderable {
	
	public Torch(float x, float y, float rotation) {
		super.setSprite(new Texture(Gdx.files.internal("levels/TORCH.gif")), x, y);
		super.setRotation(rotation);
	}

}

package com.mygdx.shop;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.extras.PermanetPlayer;
import com.mygdx.renderable.Renderable;

public class Shop extends Renderable {

    public final float FLUID_PER_PURCHASE = 10f;
    public final int MASK_PER_PURCHASE = 1;
    public final int COST_PER_MASK = 10;
    public final int COST_PER_FLUID = 20;


    public Shop(Texture textureOfHouse, float x, float y) {
        super.setSprite(textureOfHouse, x, y);
    }

}

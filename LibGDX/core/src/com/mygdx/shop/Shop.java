package com.mygdx.shop;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.renderable.Renderable;

/**
 * Contains information about the shop and also holds
 * the sprite of the shop for map-screen.
 */
public class Shop extends Renderable {

    /** Constant value of how much fluid you get per purchase */
    public final float FLUID_PER_PURCHASE = 10f;

    /** Constant value of how many mask you get per purchase */
    public final int MASK_PER_PURCHASE = 1;

    /** Constant value of the cost of mask */
    public final int COST_PER_MASK = 10;

    /** Constant value of the cost of fluid */
    public final int COST_PER_FLUID = 20;

    /**
     * Constructor for the shop
     * @param textureOfHouse The texture of the shop.
     * @param x The x coordinate of the shop.
     * @param y The y coordinate of the shop.
     */
    public Shop(Texture textureOfHouse, float x, float y) {
        super.setSprite(textureOfHouse, x, y);
    }

}

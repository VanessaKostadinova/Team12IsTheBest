package com.mygdx.renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.mygdx.extras.PermanetPlayer;

/**
 * Bullet class to allow NPC's to attack the doctor.
 * @author  Inder
 */
public class Bullet extends Renderable {

    /** holds velocity of npc's bullet in x axis */
    float dx;
    /** holds velocity of npc's bullet in y axis */
    float dy;
    /** holds velocity the integer array of the map */
    int map[][];

    /**
     * Used to construct a bullet
     * @param dx the velocity in x axis
     * @param dy the velocity in y axis
     * @param intialX the initial x position
     * @param intialY the initial y position
     * @param map an array which show tile map
     * @param rotation shows the rotation of the player
     */
    public Bullet(float dx, float dy, float intialX, float intialY, int map[][], float rotation) {
        super();
        this.dx = dx;
        this.dy = dy;
        super.setSprite(new Texture(Gdx.files.internal("NPC/bullet/Bullet8x8.png")), intialX, intialY);
        setRotation(rotation);
    }

    /**
     * Update the bullet direction
     */
    public void updateBullet() {
        float x = sprite.getX() + dx;
        float y = sprite.getY() + dy;
        sprite.setX(x);
        sprite.setY(y);
    }

    /**
     * Check if the bullet has collided with player
     * @return
     */
    public Boolean hasCollided() {
        return Player.getInstance().getSprite().getBoundingRectangle().overlaps(sprite.getBoundingRectangle());
    }

}

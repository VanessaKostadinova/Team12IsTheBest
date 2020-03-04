package com.mygdx.renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.mygdx.extras.PermanetPlayer;

public class Bullet extends Renderable {

    float dx;
    float dy;
    int map[][];

    public Bullet(float dx, float dy, float intialX, float intialY, int map[][], float rotation) {
        super();
        this.dx = dx;
        this.dy = dy;
        super.setSprite(new Texture(Gdx.files.internal("NPC/bullet/Bullet8x8.png")), intialX, intialY);
        setRotation(rotation);
    }


    public void updateBullet() {
        float x = sprite.getX() + dx;
        float y = sprite.getY() + dy;
        sprite.setX(x);
        sprite.setY(y);
    }

    public Boolean hasCollided() {
        return Player.getInstance().getSprite().getBoundingRectangle().overlaps(sprite.getBoundingRectangle());
    }

}

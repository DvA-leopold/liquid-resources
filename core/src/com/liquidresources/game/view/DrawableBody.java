package com.liquidresources.game.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public interface DrawableBody {
    void draw(final Batch batch, Vector2 position, float delta);
    BodyDef getBodyDef();
    FixtureDef getFixtureDef();
}
package com.liquidresources.game.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public interface DrawableBody {
    void draw(final Batch batch, final Vector2 position, float delta);
    Vector2 getSize();
}

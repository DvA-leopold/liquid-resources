package com.liquidresources.game.view.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public interface Animator {
    void draw(final Batch batch, Vector2 position, float delta);
    void resetAnimation(boolean isStoped);

    float getX();

    float getY();

    float getWidth();

    float getHeight();
}

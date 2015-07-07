package com.liquidresources.game.view.animation;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Animator {
    void create();
    void draw(final Batch batch, float x, float y, float width, float height);
    void resetAnimation(boolean isStoped);
}

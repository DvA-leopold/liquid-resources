package com.liquidresources.game.view.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

public interface Animator {
    void create(Animation.PlayMode animationPlayMode);

    void draw(final Batch batch);
    void resetAnimation(boolean isStoped);

    float getX();

    float getY();

    float getWidth();

    float getHeight();
}

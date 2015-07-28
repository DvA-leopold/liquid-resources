package com.liquidresources.game.model;

import com.badlogic.gdx.physics.box2d.Body;

public interface Updatable {
    void update(final Body body);
}

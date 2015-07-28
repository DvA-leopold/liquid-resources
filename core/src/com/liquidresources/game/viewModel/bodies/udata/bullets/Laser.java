package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.model.Updatable;
import com.liquidresources.game.view.DrawableBody;

public class Laser implements DrawableBody, Updatable {
    @Override
    public void draw(Batch batch, Vector2 position, float delta) {

    }

    @Override
    public BodyDef getBodyDef() {
        return null;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return null;
    }

    @Override
    public void update(Body body) {

    }
}

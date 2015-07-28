package com.liquidresources.game.viewModel.bodies.udata.buildings;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.model.Updatable;
import com.liquidresources.game.view.DrawableBody;

public class BaseShieldView implements DrawableBody, Updatable {
    public BaseShieldView() {

    }

    @Override
    public void draw(Batch batch, Vector2 position, float delta) {

    }

    @Override
    public void update(Body body) {
        //TODO update for collisions and response
    }

    @Override
    public BodyDef getBodyDef() {
        return null;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return null;
    }
}

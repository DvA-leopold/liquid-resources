package com.liquidresources.game.view.drawable.buildings;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.view.drawable.DrawableBody;

public class BaseShieldView implements DrawableBody {
    public BaseShieldView() {

    }

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
    public void dispose() {

    }
}

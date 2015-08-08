package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

public abstract class Bullet implements UniversalBody {
    public Bullet(final Vector2 defaultPosition, final Vector2 bulletSize, BodyDef.BodyType bodyType) {
        isActive = true;
        initBodyDefAndFixture(defaultPosition, bulletSize, bodyType);
    }

    @Override
    public BodyDef getBodyDef() {
        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    protected abstract void initBodyDefAndFixture(
            final Vector2 defaultPosition,
            final Vector2 bulletSize,
            BodyDef.BodyType bodyType
    );


    protected boolean isActive;

    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
}

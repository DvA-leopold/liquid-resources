package com.liquidresources.game.viewModel.bodies.udata.buildings;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

public abstract class Building implements UniversalBody {
    public Building(final Vector2 defaultPosition, final Vector2 buildingSize) {
        isActive = false;
        initBodyDefAndFixture(defaultPosition, buildingSize);
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public BodyDef getBodyDef() {
        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    protected abstract void initBodyDefAndFixture(
            final Vector2 defaultPosition,
            final Vector2 buildingSize
    );


    protected boolean isActive;

    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
}

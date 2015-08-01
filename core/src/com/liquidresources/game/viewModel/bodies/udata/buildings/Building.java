package com.liquidresources.game.viewModel.bodies.udata.buildings;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

public abstract class Building implements UniversalBody {
    public Building(final Vector2 defaultPosition, final Vector2 buildingSize, BodyDef.BodyType bodyType) {
        isDestroyed = false;
        initBodyDefAndFixture(defaultPosition, buildingSize, bodyType);
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
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
            final Vector2 bulletSize,
            BodyDef.BodyType bodyType
    );


    private boolean isDestroyed;

    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

}

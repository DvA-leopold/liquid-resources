package com.liquidresources.game.viewModel.bodies.udata.ships;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

public abstract class Ship implements UniversalBody {
    public Ship(final Vector2 defaultPosition, final Vector2 shipSize, int health) {
        this.health = health;
        isActive = false;

        initBodyDefAndFixture(defaultPosition, shipSize);
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

    /**
     * this method must initialize <code>bodyDef</code>, <code>polygonShape</code> and
     * <code>fixtureDef</code>
     */
    protected abstract void initBodyDefAndFixture(
            final Vector2 defaultPosition,
            final Vector2 shipSize
    );


    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;

    protected int health;

    private boolean isActive;
}

package com.liquidresources.game.viewModel.bodies.udata.ships;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.model.game.world.Updatable;
import com.liquidresources.game.view.DrawableBody;

public abstract class Ship implements DrawableBody, Updatable {
    public Ship(final Vector2 defaultPosition, final Vector2 shipSize, int health) {
        this.defaultPosition = defaultPosition;
        this.shipSize = shipSize;
        this.health = health;

        initBodyDefAndFixture();
    }

    @Override
    public BodyDef getBodyDef() {
        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public abstract int doDamage();

    /**
     * this method must initialize <code>bodyDef</code>, <code>polygonShape</code> and
     * <code>fixtureDef</code>
     */
    protected abstract void initBodyDefAndFixture();


    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;

    protected int health;

    final protected Vector2 defaultPosition;
    final protected Vector2 shipSize;
}

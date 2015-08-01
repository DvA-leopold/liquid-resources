package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

public abstract class Bullet implements UniversalBody {
    public Bullet(final Vector2 defaultPosition, final Vector2 bulletSize, BodyDef.BodyType bodyType) {
        isDestroyed = false;
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
    public boolean isDestroyed() {
        return isDestroyed;
    }

    protected abstract void initBodyDefAndFixture(
            final Vector2 defaultPosition,
            final Vector2 bulletSize,
            BodyDef.BodyType bodyType
    );


    protected boolean isDestroyed;

    protected Sprite bulletSprite;

    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
}

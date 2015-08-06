package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.model.BodyType;
import com.liquidresources.game.model.UpdatableBody;
import com.liquidresources.game.view.DrawableBody;

public class Laser extends Bullet {
    public Laser(final Vector2 defaultPosition, final Vector2 bulletSize, BodyDef.BodyType bodyType) {
        super(defaultPosition, bulletSize, bodyType);
    }

    @Override
    protected void initBodyDefAndFixture(Vector2 defaultPosition, Vector2 bulletSize, BodyDef.BodyType bodyType) {

    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {

    }

    @Override
    public Vector2 getSize() {
        return null;
    }

    @Override
    public void update(final Body body, float delta) {

    }

    @Override
    public void beginCollisionContact(final Body bodyA) {

    }

    @Override
    public BodyType getBodyType() {
        return BodyType.LASER;
    }
}

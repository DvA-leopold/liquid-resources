package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.liquidresources.game.model.BodyType;
import com.liquidresources.game.model.game.world.base.RelationTypes;

public class Bomb extends Bullet {
    public Bomb(final Vector2 defaultPosition,
                final Vector2 bulletSize,
                final BodyDef.BodyType bodyType,
                final RelationTypes parentRelation) {
        super(defaultPosition, bulletSize, bodyType, parentRelation);
    }

    @Override
    protected void initBodyDefAndFixture(final Vector2 defaultPosition,
                                         final Vector2 bulletSize,
                                         final BodyDef.BodyType bodyType) {

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
        return BodyType.BOMB;
    }
}

package com.liquidresources.game.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.model.game.world.base.RelationTypes;

public interface UpdatableBody {
    void update(final Body body, float delta);
    void beginCollisionContact(final Body bodyA);

    BodyType getBodyType();

    BodyDef getBodyDef();
    FixtureDef getFixtureDef();

    boolean isActive();

    RelationTypes getRelation();
}

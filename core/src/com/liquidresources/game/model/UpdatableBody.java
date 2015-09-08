package com.liquidresources.game.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.types.BodyTypes;

public interface UpdatableBody {
    void update(final Body body, float delta);
    void beginCollisionContact(final Body bodyA);

    BodyTypes getBodyType();

    BodyDef getBodyDef();
    FixtureDef getFixtureDef();

    boolean isActive();

    RelationTypes getRelation();
}

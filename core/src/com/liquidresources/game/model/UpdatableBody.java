package com.liquidresources.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;

public interface UpdatableBody {
    void update(final Body body, float delta);
    void beginCollisionContact(final Body bodyA, final BodyFactoryWrapper bodyFactoryWrapper);

    void setBody(final Body body);


    BodyTypes getBodyType();
    Vector2 getPosition();
    RelationTypes getRelation();
    BodyDef getBodyDef();
    FixtureDef getFixtureDef();
}

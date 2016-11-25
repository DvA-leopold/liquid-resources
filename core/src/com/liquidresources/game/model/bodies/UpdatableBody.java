package com.liquidresources.game.model.bodies;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;


public interface UpdatableBody {
    BodyTypes getBodyType();
    RelationTypes getRelation();
    void collisionContact(Body collidedWithBody);
}

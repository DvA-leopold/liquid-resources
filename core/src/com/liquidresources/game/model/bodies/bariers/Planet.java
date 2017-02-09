package com.liquidresources.game.model.bodies.bariers;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.bodies.UpdatableBody;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;


final public class Planet extends UpdatableBody {
    public Planet(RelationTypes relationType) {
        super(relationType, Integer.MAX_VALUE);
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.GROUND;
    }

    @Override
    public void collisionContact(Body collidedWithBody) {

    }

    public void act(float delta) { }
}

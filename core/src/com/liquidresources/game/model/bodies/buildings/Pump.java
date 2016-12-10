package com.liquidresources.game.model.bodies.buildings;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.bodies.UpdatableBody;


abstract public class Pump extends UpdatableBody {
    public Pump(RelationTypes relationTypes, BodyTypes bodyType) {
        super(relationTypes, 100);
        this.bodyType = bodyType;
    }

    @Override
    public void dispose() {

    }

    @Override
    public BodyTypes getBodyType() {
        return bodyType;
    }

    @Override
    public void collisionContact(Body collidedWithBody) {

    }


    final private BodyTypes bodyType;
}

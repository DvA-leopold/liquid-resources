package com.liquidresources.game.model.bodies.buildings;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.bodies.UpdatableBody;

final public class PowerFactory extends UpdatableBody {
    public PowerFactory(final RelationTypes relationType) {
        super(relationType, 100);
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.SHIP_FACTORY;
    }

    @Override
    public void collisionContact(Body collidedWithBody) {

    }

    @Override
    public void act(float delta) { }

    @Override
    public void dispose() {

    }
}

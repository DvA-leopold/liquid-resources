package com.liquidresources.game.model.bodies.bullets;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.bodies.UpdatableBodyImpl;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;


final public class Meteor extends UpdatableBodyImpl {
    public Meteor(RelationTypes relationType) {
        super(relationType, 1);
    }

    @Override
    public void act(float delta) { }

    @Override
    public void dispose() {
        entityInitializer.getEngine().removeEntity(entity);
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.METEOR;
    }

    @Override
    public void collisionContact(Body collidedWithBody) {
        if (((UpdatableBodyImpl) collidedWithBody.getUserData()).getBodyType() != BodyTypes.METEOR) {
            takeDamage(1);
        }
    }
}

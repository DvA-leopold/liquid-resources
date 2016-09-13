package com.liquidresources.game.model.bodies.bullets;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.bodies.UpdatableBodyImpl;

final public class Missile extends UpdatableBodyImpl {
    public Missile(final RelationTypes parentRelation) {
        super(parentRelation, 1);
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.MISSILE;
    }

    @Override
    public void collisionContact(Body collidedEnemyBody) {

    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void dispose() { }
}

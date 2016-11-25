package com.liquidresources.game.model.bodies.bullets;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.bodies.UpdatableBodyImpl;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;


final public class Bullet extends UpdatableBodyImpl {
    public Bullet(final RelationTypes parentRelation) {
        super(parentRelation, 1);
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.BULLET;
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

package com.liquidresources.game.model.bodies.bullets;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.bodies.UpdatableBody;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;


final public class Bullet extends UpdatableBody {
    public Bullet(final RelationTypes parentRelation) {
        super(parentRelation, 1);
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.BULLET;
    }

    @Override
    public void collisionContact(Body collidedEnemyBody) {
        UpdatableBody collidedUpdatableBody = (UpdatableBody) collidedEnemyBody.getUserData();
        if (collidedUpdatableBody.getRelation() != this.getRelation()) {
            takeDamage(1);
        }
    }

    public void act(float delta) {
        if (!isInitialized) {
            return;
        }

//        if (targetVector == null) {
//            UpdatableBody hunterUpdatableBody = entityInitializer.getTargetBody(RelationTypes.ENEMY);
//            if (hunterUpdatableBody != null) {
//                setHunterUpdatableBody(hunterUpdatableBody);
//                targetVector = new Vector2(hunterUpdatableBody.getPosition());
//                targetVector.sub(getPosition().add(MathUtils.random(-1.5f, 0f), 0));
//            } else {
//                return;
//            }
//        }

        physicsBodyComponent.body.setTransform(getPosition(), vectorToAngle(physicsBodyComponent.body.getLinearVelocity()));
        physicsBodyComponent.body.applyForceToCenter(targetVector, true);
    }

    @Override
    public void dispose() {
        entityInitializer.destroyEntity(this);
    }


    private Vector2 targetVector;
}

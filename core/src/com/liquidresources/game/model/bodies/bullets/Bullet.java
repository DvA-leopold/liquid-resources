package com.liquidresources.game.model.bodies.bullets;

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

    }

    @Override
    public void act(float delta) {
        if (!isInitialized) {
            return;
        }

        if (targetVector == null) {
            Vector2 targetBodyPos = entityInitializer.getTargetBody(RelationTypes.ENEMY).getPosition();
            if (targetBodyPos != null) {
                targetVector = new Vector2(targetBodyPos);
                targetVector.sub(this.getPosition());
            } else {
                return;
            }
        }

        physicsBodyComponent.body.applyForceToCenter(targetVector, true);
    }

    @Override
    public void dispose() {
        entityInitializer.destroyEntity(this);
    }


    private Vector2 targetVector;
}

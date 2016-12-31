package com.liquidresources.game.model.bodies.bullets;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.bodies.UpdatableBody;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;


final public class Meteor extends UpdatableBody {
    public Meteor(RelationTypes relationType) {
        super(relationType, 10);
    }

    @Override
    public void act(float delta) { }

    @Override
    public void dispose() {
        entityInitializer.destroyEntity(this);
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.METEOR;
    }

    @Override
    public void collisionContact(Body collidedEnemyBody) {
        UpdatableBody collidedUpdatableBody = (UpdatableBody) collidedEnemyBody.getUserData();
        if (equals(collidedUpdatableBody.getHunterUpdatableBody())) {
            switch (collidedUpdatableBody.getBodyType()) {
                case MISSILE:
                    takeDamage(health);
                    break;
                case BULLET:
                    takeDamage(2);
                    break;
            }
            return;
        }

        if (getRelation() != collidedUpdatableBody.getRelation()) {
            switch (collidedUpdatableBody.getBodyType()) {
                case BULLET:
                case MISSILE:
                    break;
                default:
                    takeDamage(health);
                    break;
            }
        }
    }
}

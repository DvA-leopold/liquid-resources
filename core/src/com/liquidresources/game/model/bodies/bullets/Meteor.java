package com.liquidresources.game.model.bodies.bullets;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.bodies.UpdatableBody;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;


final public class Meteor extends UpdatableBody {
    public Meteor(RelationTypes relationType) {
        super(relationType, 1);
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
        if (this.equals(collidedUpdatableBody.getHunterUpdatableBody()) ||
                (this.getRelation() != collidedUpdatableBody.getRelation() && this.getBodyType() != BodyTypes.MISSILE)) {
            takeDamage(1);
        }
    }
}

package com.liquidresources.game.model.bodies.buildings;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.bodies.UpdatableBody;


final public class Pump extends UpdatableBody {
    public Pump(RelationTypes relationTypes, BodyTypes bodyType) {
        super(relationTypes, 50);
        this.bodyType = bodyType;
    }

    public void act(float delta) {
        if (!isInitialized) {
            return;
        }

        if (health > 0) {
            if (bodyType == BodyTypes.OIL_PUMP) {
                ((Capital) entityInitializerSystem.getBaseSceneElement("capital")).changeOil(1);
            } else {
                ((Capital) entityInitializerSystem.getBaseSceneElement("capital")).changeWater(1);
            }
        } else if (body.isActive()) {
            body.setActive(false);
        }
    }

    @Override
    public BodyTypes getBodyType() {
        return bodyType;
    }

    @Override
    public void collisionContact(final Body collidedWithBody) {
        UpdatableBody collidedUpdatableBody = (UpdatableBody) collidedWithBody.getUserData();
        if (collidedUpdatableBody.getRelation() == RelationTypes.ENEMY) {
            switch (collidedUpdatableBody.getBodyType()) {
                case METEOR:
                    takeDamage(25);
                    break;
                default:
                    break;
            }
        }
    }


    final private BodyTypes bodyType;
}

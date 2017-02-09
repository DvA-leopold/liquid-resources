package com.liquidresources.game.model.bodies.buildings;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.bodies.bullets.Bullet;
import com.liquidresources.game.model.bodies.bullets.Missile;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.bodies.UpdatableBody;


final public class Capital extends UpdatableBody {
    public Capital(final RelationTypes relationType) {
        super(relationType, 100);
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.CAPITAL;
    }

    @Override
    public void collisionContact(Body collidedWithBody) {
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

    public void act(float delta) {
        if (!isInitialized) {
            return;
        }

        if (health <= 0 && body.isActive()) {
            body.setActive(false);
        }
    }

    public boolean changeOil(int oilBarrels) {
        if (this.oilBarrels + oilBarrels >= 0) {
            this.oilBarrels += oilBarrels;
            return true;
        } else {
            return false;
        }
    }

    public boolean changeWater(int waterBarrels) {
        if (this.waterBarrels + waterBarrels >= 0) {
            this.waterBarrels += waterBarrels;
            return true;
        } else {
            return false;
        }
    }

    public long getOilBarrels() {
        return oilBarrels;
    }

    public long getWaterBarrels() {
        return waterBarrels;
    }

    public void fireMissile() {
        if (entityInitializerSystem.hasTargetBodies(RelationTypes.ENEMY, BodyTypes.METEOR) && changeWater(-10)) {
            entityInitializerSystem.createEntityFromLibrary("missile", new Missile(this.getRelation()), 10, 10);
        } else {
            System.out.println("no water or target entities");
        }
    }

    public void fireBullet() {
        if (entityInitializerSystem.hasTargetBodies(RelationTypes.ENEMY, BodyTypes.METEOR) && changeWater(-4)) {
            entityInitializerSystem.createEntityFromLibrary("bullet", new Bullet(this.getRelation()), 10, 10);
        } else {
            System.out.println("no water or target entities");
        }
    }


    private long oilBarrels, waterBarrels;
}

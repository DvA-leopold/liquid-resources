package com.liquidresources.game.model.bodies.buildings;

import com.badlogic.gdx.Gdx;
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
            if (collidedUpdatableBody.getBodyType() == BodyTypes.METEOR) {
                takeDamage(25);
            }
        }
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void dispose() { }

    public boolean changeOil(int oilBarrels) {
        if (this.oilBarrels + oilBarrels >= 0) {
            this.oilBarrels += oilBarrels;
            return true;
        }

        return false;
    }

    public boolean changeWater(int waterBarrels) {
        if (this.waterBarrels + waterBarrels >= 0) {
            this.waterBarrels += waterBarrels;
            return true;
        }

        return false;
    }

    public long getOilBarrels() {
        return oilBarrels;
    }

    public long getWaterBarrels() {
        return waterBarrels;
    }

    public void fireMissile() {
        if (changeWater(-10)) {
            entityInitializer.createEntityFromLibrary("missile", new Missile(getRelation()), 10, 10);
            return;
        }
        Gdx.app.log(this.getClass().getCanonicalName(), "no water or target entities");
    }

    public void fireBullet() {
        if (changeWater(-4)) {
            entityInitializer.createEntityFromLibrary("bullet", new Bullet(getRelation()), 10, 10);
            return;
        }
        Gdx.app.log(this.getClass().getCanonicalName(), "no water or target entities");
    }


    private long oilBarrels, waterBarrels;
}

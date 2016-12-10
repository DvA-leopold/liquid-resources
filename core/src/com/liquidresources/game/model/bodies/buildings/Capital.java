package com.liquidresources.game.model.bodies.buildings;

import com.badlogic.gdx.physics.box2d.Body;
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

    }

    @Override
    public void act(float delta) { }

    @Override
    public void dispose() { }

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
        if (entityInitializer.hasTargetBodies(RelationTypes.ENEMY, BodyTypes.METEOR) && changeWater(-10)) {
            entityInitializer.createEntityFromLibrary("missile", new Missile(this.getRelation()), 10, 10);
        } else {
            System.out.println("no water or target entities");
        }
    }


    private long oilBarrels, waterBarrels;
}

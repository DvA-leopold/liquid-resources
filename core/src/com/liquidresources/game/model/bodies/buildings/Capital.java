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
    public void act(float delta) {
        Pump waterPump = ((Pump) entityInitializer.getBaseSceneElement("pump_1"));
        Pump oilPump = ((Pump) entityInitializer.getBaseSceneElement("pump_2"));
        Pump oilPump2 = ((Pump) entityInitializer.getBaseSceneElement("pump_3"));
        update(oilPump.getResources() + oilPump2.getResources(), waterPump.getResources());
    }

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

    private boolean changeWater(int waterBarrels) {
        if (this.waterBarrels + waterBarrels >= 0) {
            this.waterBarrels += waterBarrels;
            return true;
        } else {
            return false;
        }
    }

    private boolean update(int oilBarrels, short waterBarrels) {
        this.oilBarrels = oilBarrels + oilBarrels < Long.MAX_VALUE
                ? this.oilBarrels + oilBarrels
                : Long.MAX_VALUE - 1;

        this.waterBarrels = this.waterBarrels + waterBarrels < Long.MAX_VALUE
                ? this.waterBarrels + waterBarrels
                : Long.MAX_VALUE - 1;
        return true;
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

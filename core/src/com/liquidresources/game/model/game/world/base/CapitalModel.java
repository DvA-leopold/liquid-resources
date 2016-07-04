package com.liquidresources.game.model.game.world.base;

import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.bullets.Missile;

import static com.liquidresources.game.model.common.utils.UConverter.m2p;

public class CapitalModel {
    public CapitalModel(final Vector2 missileSpawnPos,
                        final BodyFactoryWrapper bodyFactoryWrapper) {
        this.bodyFactoryWrapper = bodyFactoryWrapper;
        oilBarrels = 0;
        waterBarrels = 0;
        shieldStatus = false;

        this.missileSpawnPos = missileSpawnPos;
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

    /**
     * this capital is a storage for all useful resources and convert resources from one to another
     * update method just add obtained resources from different factories
     * and control ION shield activity status
     * @param oilBarrels   passed from the oil factory class
     * @param waterBarrels passed from the water factory class*/
    public boolean update(int oilBarrels, short waterBarrels) {
        this.oilBarrels = oilBarrels + oilBarrels < Long.MAX_VALUE
                ? this.oilBarrels + oilBarrels
                : Long.MAX_VALUE - 1;

        this.waterBarrels = this.waterBarrels + waterBarrels < Long.MAX_VALUE
                ? this.waterBarrels + waterBarrels
                : Long.MAX_VALUE - 1;

        if (shieldStatus) {
            if (this.waterBarrels <= 0) {
                shieldStatus = false;
                return false;
            } else {
                this.waterBarrels -= 1;
            }
        }
        return true;
    }

    public void missileLaunch(RelationTypes relationType) {
        Vector2 spawnPosition = new Vector2(missileSpawnPos.x, missileSpawnPos.y + m2p(20));
        bodyFactoryWrapper.createBody(new Missile(spawnPosition, relationType));
    }

    public void switchIonShield() {
        shieldStatus = !shieldStatus;
    }

    public long getOilBarrels() {
        return oilBarrels;
    }

    public long getWaterBarrels() {
        return waterBarrels;
    }

    public boolean getShieldStatus() {
        return shieldStatus;
    }


    final private BodyFactoryWrapper bodyFactoryWrapper;

    final private Vector2 missileSpawnPos;
    private long oilBarrels, waterBarrels;

    private boolean shieldStatus;
}

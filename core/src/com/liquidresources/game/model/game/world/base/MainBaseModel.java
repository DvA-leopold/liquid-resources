package com.liquidresources.game.model.game.world.base;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.viewModel.bodies.udata.bullets.Rocket;

public class MainBaseModel {
    public MainBaseModel(final Vector2 position, final Vector2 rocketSize) {
        oilBarrels = 0;
        waterBarrels = 0;
        shieldStatus = true;

        this.position = position;
        this.rocketSize = rocketSize;
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
     * this mainAI is a storage for all useful resources and convert resources from one to another
     * update method just add obtained resources from different factories
     * @param oilBarrels   passed from the oil factory class
     * @param waterBarrels passed from the water factory class*/
    public void update(int oilBarrels, short waterBarrels) {
        this.oilBarrels = oilBarrels + oilBarrels < Long.MAX_VALUE
                ? this.oilBarrels + oilBarrels
                : Long.MAX_VALUE - 1;

        this.waterBarrels = this.waterBarrels + waterBarrels < Long.MAX_VALUE
                ? this.waterBarrels + waterBarrels
                : Long.MAX_VALUE - 1;
    }

    public EventListener fireRocketLaunch(final BodyFactoryWrapper bodyFactoryWrapper) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (changeOil(-20)) {
                    bodyFactoryWrapper.createBody(
                            new Rocket(new Vector2(position.x, position.y + rocketSize.y + 5), rocketSize, RelationTypes.ALLY),
                            false
                    );
                }
            }
        };
    }

    public long getOilBarrels() {
        return oilBarrels;
    }

    public long getWaterBarrels() {
        return waterBarrels;
    }


    final private Vector2 position;
    final private Vector2 rocketSize;

    private long oilBarrels, waterBarrels;
    private boolean shieldStatus;
}

package com.liquidresources.game.model.game.world.base;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.viewModel.bodies.udata.bullets.Rocket;
import com.liquidresources.game.viewModel.screens.game.buttons.GameScreenWidgetsGroup;

import java.util.Observable;

public class AMainBaseModel {
    static {
        aOilBarrels = 0;
        aWaterBarrels = 0;
        aShieldStatus = false;
    }

    public AMainBaseModel(final Vector2 position, final Vector2 rocketSize) {
        this.position = position;
        this.rocketSize = rocketSize;
    }

    public static boolean changeOil(int oilBarrels) {
        if (AMainBaseModel.aOilBarrels + oilBarrels >= 0) {
            AMainBaseModel.aOilBarrels += oilBarrels;
            return true;
        } else {
            return false;
        }
    }

    public static boolean changeWater(int waterBarrels) {
        if (AMainBaseModel.aWaterBarrels + waterBarrels >= 0) {
            AMainBaseModel.aWaterBarrels += waterBarrels;
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
        AMainBaseModel.aOilBarrels = oilBarrels + oilBarrels < Long.MAX_VALUE
                ? AMainBaseModel.aOilBarrels + oilBarrels
                : Long.MAX_VALUE - 1;

        AMainBaseModel.aWaterBarrels = AMainBaseModel.aWaterBarrels + waterBarrels < Long.MAX_VALUE
                ? AMainBaseModel.aWaterBarrels + waterBarrels
                : Long.MAX_VALUE - 1;

        if (aShieldStatus && AMainBaseModel.aOilBarrels > 0) {
            AMainBaseModel.aOilBarrels -= 1;
        } else {
            aShieldStatus = false;
            // TODO сброс кнопки если ресурсы закончились.
//            GameScreenWidgetsGroup.setIONChecked(false);
        }
    }

    public EventListener switchIONShield() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!aShieldStatus && aOilBarrels > 0) {
                    aShieldStatus = true;
                } else if (aShieldStatus) {
                    aShieldStatus = false;
                }
            }
        };
    }

    public EventListener fireRocketLaunch(final BodyFactoryWrapper bodyFactoryWrapper) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (changeOil(-20)) {
                    bodyFactoryWrapper.createBody(
                            new Rocket(new Vector2(position.x, position.y + rocketSize.y + 5), rocketSize, RelationTypes.ALLY), false
                    );
                }
            }
        };
    }

    public static void dropAllData() {
        aOilBarrels = aWaterBarrels = 0;
    }

    public static long getOilBarrels() {
        return aOilBarrels;
    }

    public static long getWaterBarrels() {
        return aWaterBarrels;
    }

    public static boolean getShieldStatus() {
        return aShieldStatus;
    }


    final private Vector2 position;
    final private Vector2 rocketSize;

    private static long aOilBarrels, aWaterBarrels;
    private static boolean aShieldStatus;
}

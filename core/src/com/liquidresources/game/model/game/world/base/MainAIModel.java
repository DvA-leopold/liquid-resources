package com.liquidresources.game.model.game.world.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.viewModel.bodies.udata.bullets.Rocket;
import com.liquidresources.game.viewModel.screens.game.buttons.GameScreenWidgetsGroup;

public class MainAIModel {
    static {
        //TODO возможно лучше сделать нестатический вариант
        oilBarrels = 0;
        waterBarrels = 0;
        shieldOnOffStatus = false;
    }

    public MainAIModel(final Vector2 position, final Vector2 rocketSize) {
        this.position = position;
        this.rocketSize = rocketSize;
    }

    public static boolean changeOil(int oilBarrels) {
        if (MainAIModel.oilBarrels + oilBarrels >= 0) {
            MainAIModel.oilBarrels += oilBarrels;
            return true;
        } else {
            return false;
        }
    }

    public static boolean changeWater(int waterBarrels) {
        if (MainAIModel.waterBarrels + waterBarrels >= 0) {
            MainAIModel.waterBarrels += waterBarrels;
            return true;
        } else {
            return false;
        }
    }

    /**
     * this mainAI is a storage for all useful resources and convert resources from one to another
     * update method just add obtained resources from different factories
     *  @param oilBarrels   passed from the oil factory class
     * @param waterBarrels passed from the water factory class*/
    public static void update(int oilBarrels, short waterBarrels) {
        MainAIModel.oilBarrels = oilBarrels + oilBarrels < Long.MAX_VALUE
                ? MainAIModel.oilBarrels + oilBarrels
                : Long.MAX_VALUE - 1;

        MainAIModel.waterBarrels = MainAIModel.waterBarrels + waterBarrels < Long.MAX_VALUE
                ? MainAIModel.waterBarrels + waterBarrels
                : Long.MAX_VALUE - 1;

        if (shieldOnOffStatus && MainAIModel.oilBarrels > 0) {
            MainAIModel.oilBarrels -= 1;
        } else {
            shieldOnOffStatus = false;
            GameScreenWidgetsGroup.setIONChecked(false);
        }
    }

    public EventListener switchIONShield() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!shieldOnOffStatus && oilBarrels > 0) {
                    shieldOnOffStatus = true;
                } else if (shieldOnOffStatus) {
                    shieldOnOffStatus = false;
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
                            new Rocket(new Vector2(position.x, position.y + rocketSize.y + 5), rocketSize), false
                    );
                }
            }
        };
    }

    public static void dropAllData() {
        oilBarrels = waterBarrels = 0;
    }

    public static long getOilBarrels() {
        return oilBarrels;
    }

    public static long getWaterBarrels() {
        return waterBarrels;
    }

    public static boolean getShieldStatus() {
         return shieldOnOffStatus;
    }


    final private Vector2 position;
    final private Vector2 rocketSize;
    private static long oilBarrels, waterBarrels;
    private static boolean shieldOnOffStatus;
}

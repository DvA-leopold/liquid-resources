package com.liquidresources.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.liquidresources.game.model.game.world.base.MainAI;
import com.liquidresources.game.model.game.world.factories.ShipFactory;
import com.liquidresources.game.model.game.world.pumps.OilPump;
import com.liquidresources.game.model.game.world.pumps.Pump;
import com.liquidresources.game.model.game.world.pumps.WaterPump;
import com.liquidresources.game.viewModel.GameStates;
import com.liquidresources.game.viewModel.screens.game.GameScreen;
import com.liquidresources.game.viewModel.screens.game.buttons.GameScreenWidgetsGroup;

public class GameWorldModel {
    public GameWorldModel(
            final BodyFactoryWrapper bodyFactoryWrapper,
            final Vector2 shipFactoryPosition,
            final Vector2 mainAIPosition) {
        this.bodyFactoryWrapper = bodyFactoryWrapper;

        shipFactory = new ShipFactory(
                shipFactoryPosition,
                new Vector2(Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.05f),
                100, 30);

        oilPump1 = new OilPump(0.04f);
        oilPump2 = new OilPump(0.04f);
        waterPump = new WaterPump(0.09f);
        mainAI = new MainAI(
                mainAIPosition,
                new Vector2(Gdx.graphics.getWidth() * 0.01f, Gdx.graphics.getHeight() * 0.05f)
        );

        worldState = GameStates.GAME_PREPARING;
    }

    public void update(float delta) {
        switch (worldState) {
            case GAME_PREPARING:
                break;
            case GAME_RUNNING:
                MainAI.update(
                        oilPump1.getResources(delta) + oilPump2.getResources(delta),
                        waterPump.getResources(delta)
                );
                for (Body body : bodyFactoryWrapper.getDynamicObjects()) {
                    ((UpdatableBody) body.getUserData()).update(body, delta);
                }
                bodyFactoryWrapper.updateWorld();
                break;
            case GAME_PAUSED:
                break;
            case GAME_EXIT:
                break;
            case GAME_OVER:
                break;
        }
    }

    public void pause() {
        if (worldState != GameStates.GAME_PREPARING) {
            changeWorldState(GameStates.GAME_PREPARING);
        }
    }

    public EventListener getShipFactoryListeners(ShipFactory.ShipType shipType) {
        return shipFactory.getShipButtonListener(bodyFactoryWrapper, shipType);
    }

    public EventListener getMainAIListener() {
        return mainAI.switchIONShield();
    }

    public EventListener getRocketFireEventListener() {
        return mainAI.fireRocketLaunch(bodyFactoryWrapper);
    }

    public static GameStates getWorldState() {
        return worldState;
    }

    public static void changeWorldState(GameStates newWorldState) {
        worldState = newWorldState;
        switch (newWorldState) {
            case GAME_PREPARING:
                GameScreenWidgetsGroup.setButtonVisible(false);
                break;
            case GAME_RUNNING:
                GameScreenWidgetsGroup.setButtonVisible(true);
                break;
            case GAME_EXIT:
                MainAI.dropAllData();
                break;
        }
    }


    private static GameStates worldState;

    final private BodyFactoryWrapper bodyFactoryWrapper;

    private MainAI mainAI;
    private Pump oilPump1, oilPump2;
    private Pump waterPump;
    private ShipFactory shipFactory;
}

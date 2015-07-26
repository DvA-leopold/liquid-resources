package com.liquidresources.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.liquidresources.game.model.game.world.base.MainAI;
import com.liquidresources.game.model.game.world.factories.ShipFactory;
import com.liquidresources.game.model.game.world.pumps.OilPump;
import com.liquidresources.game.model.game.world.pumps.Pump;
import com.liquidresources.game.model.game.world.pumps.WaterPump;
import com.liquidresources.game.viewModel.GameStates;
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
        if (worldState != GameStates.GAME_PREPARING && worldState != GameStates.GAME_PAUSED) {
            changeWorldState(GameStates.GAME_PREPARING);
        }
    }

    public EventListener getShipFactoryListeners(ShipFactory.ShipType shipType) {
        switch (shipType) {
            case BOMBER:
                return shipFactory.bomberButtonListener(bodyFactoryWrapper);
            case FIGHTER:
                return shipFactory.fighterButtonListener(bodyFactoryWrapper);
            default:
                System.err.println("no such ship");
                return null;
        }
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
        }
    }


    private static GameStates worldState = GameStates.GAME_PREPARING;

    final private BodyFactoryWrapper bodyFactoryWrapper;

    private MainAI mainAI;
    private Pump oilPump1, oilPump2;
    private Pump waterPump;
    private ShipFactory shipFactory;
}

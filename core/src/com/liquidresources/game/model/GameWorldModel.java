package com.liquidresources.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.liquidresources.game.model.game.world.base.MainBaseModel;
import com.liquidresources.game.model.game.world.base.RelationTypes;
import com.liquidresources.game.model.game.world.factories.ShipFactory;
import com.liquidresources.game.model.game.world.pumps.OilPump;
import com.liquidresources.game.model.game.world.pumps.Pump;
import com.liquidresources.game.model.game.world.pumps.WaterPump;
import com.liquidresources.game.viewModel.GameStates;

import java.util.Observable;

public class GameWorldModel extends Observable {
    public GameWorldModel(
            final BodyFactoryWrapper bodyFactoryWrapper,
            final Vector2 aMainBasePosition,
            final Vector2 aShipFactoryPosition,
            final Vector2 eMainBasePosition,
            final Vector2 eShipFactoryPosition) {

        this.bodyFactoryWrapper = bodyFactoryWrapper;

        final Vector2 rocketSize = new Vector2(Gdx.graphics.getWidth() * 0.004f, Gdx.graphics.getHeight() * 0.02f);
        mainBaseModel = new MainBaseModel(aMainBasePosition, rocketSize);
//        eMainBaseModel = new EMainBaseModel(eMainBasePosition, rocketSize);

        final Vector2 shipSize = new Vector2(Gdx.graphics.getWidth() * 0.02f, Gdx.graphics.getHeight() * 0.02f);
        aShipFactory = new ShipFactory(aShipFactoryPosition, shipSize, 100, 30, RelationTypes.ALLY);
        eShipFactory = new ShipFactory(eShipFactoryPosition, shipSize, 100, 30, RelationTypes.ENEMY);

        oilPump1 = new OilPump(0.04f);
        oilPump2 = new OilPump(0.04f);
        waterPump = new WaterPump(0.09f);

        worldState = GameStates.GAME_PREPARING;
    }

    public void update(float delta) {
        switch (worldState) {
            case GAME_PREPARING:
                updatePreparingState(delta);
                break;
            case GAME_RUNNING:
                updateRunningState(delta);
                break;
            case GAME_PAUSED:
                break;
            case GAME_EXIT:
                break;
            case GAME_OVER:
                break;
        }
    }

    public void updatePreparingState(float delta) {
        if (Gdx.input.justTouched()) {
            changeWorldState(GameStates.GAME_RUNNING);
        }
    }

    public void updateRunningState(float delta) {
        mainBaseModel.update(
                oilPump1.getResources(delta) + oilPump2.getResources(delta),
                waterPump.getResources(delta)
        );
        for (Body body : bodyFactoryWrapper.getDynamicBodies()) {
            ((UpdatableBody) body.getUserData()).update(body, delta);
        }
        bodyFactoryWrapper.updateWorld();
    }

    public void pause() {
        if (worldState != GameStates.GAME_PREPARING) {
            changeWorldState(GameStates.GAME_PREPARING);
        }
    }

    public EventListener getShipFactoryListeners(ShipFactory.ShipType shipType) {
        return aShipFactory.getShipButtonListener(bodyFactoryWrapper, mainBaseModel, shipType);
    }

    public EventListener getRocketFireEventListener() {
        return mainBaseModel.fireRocketLaunch(bodyFactoryWrapper);
    }

    public MainBaseModel getMainBaseModel() {
        return mainBaseModel;
    }

    public void changeWorldState(GameStates newWorldState) {
        worldState = newWorldState;
        setChanged();
        notifyObservers(worldState);
    }


    private GameStates worldState;

    final private BodyFactoryWrapper bodyFactoryWrapper;

    final private MainBaseModel mainBaseModel;

    final private Pump oilPump1, oilPump2;
    final private Pump waterPump;
    final private ShipFactory aShipFactory, eShipFactory;
}

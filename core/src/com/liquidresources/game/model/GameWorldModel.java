package com.liquidresources.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.game.world.base.CapitalModel;
import com.liquidresources.game.model.game.world.pumps.OilPump;
import com.liquidresources.game.model.game.world.pumps.Pump;
import com.liquidresources.game.model.game.world.pumps.WaterPump;
import com.liquidresources.game.viewModel.GameStates;

import java.util.Observable;

public class GameWorldModel extends Observable {
    public GameWorldModel(final BodyFactoryWrapper bodyFactoryWrapper, final CapitalModel capitalModel) {
        this.bodyFactoryWrapper = bodyFactoryWrapper;
        this.capitalModel = capitalModel;

        oilPump = new OilPump(0.01f);
        waterPump = new WaterPump(0.09f);

        worldState = GameStates.GAME_PREPARING;
    }

    public void update(float delta) {
        switch (worldState) {
            case GAME_PREPARING:
                updatePreparingState();
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

    private void updatePreparingState() {
        if (Gdx.input.justTouched()) {
            changeWorldState(GameStates.GAME_RUNNING);
        }
    }

    private void updateRunningState(float delta) {
        if (!capitalModel.update(oilPump.getResources(delta), waterPump.getResources(delta))) {
            setChanged();
            notifyObservers(capitalModel.getShieldStatus());
        }

//        for (Body ship : bodyFactoryWrapper.getShipsBodies()) {
//            ((UpdatableBody) ship.getUserData()).update(ship, delta);
//        }

        for (Body bullet : bodyFactoryWrapper.getDynamicBodies()) {
            ((UpdatableBody) bullet.getUserData()).update(bullet, delta);
        }
        bodyFactoryWrapper.updateWorld();
    }

    public void pause() {
        if (worldState != GameStates.GAME_PREPARING) {
            changeWorldState(GameStates.GAME_PREPARING);
        }
    }

    public void changeWorldState(GameStates newWorldState) {
        worldState = newWorldState;
        setChanged();
        notifyObservers(worldState);
    }


    private GameStates worldState;

    final private CapitalModel capitalModel;
    final private Pump oilPump;
    final private Pump waterPump;

    final private BodyFactoryWrapper bodyFactoryWrapper;
}

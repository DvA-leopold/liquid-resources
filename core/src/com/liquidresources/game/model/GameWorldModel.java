package com.liquidresources.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.model.game.world.base.CapitalModel;
import com.liquidresources.game.model.game.world.pumps.OilPump;
import com.liquidresources.game.model.game.world.pumps.Pump;
import com.liquidresources.game.model.game.world.pumps.WaterPump;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.types.ShipTypes;
import com.liquidresources.game.view.UConverter;
import com.liquidresources.game.viewModel.GameStates;
import com.liquidresources.game.viewModel.bodies.udata.ships.Bomber;
import com.liquidresources.game.viewModel.bodies.udata.ships.Fighter;

import java.util.Observable;

public class GameWorldModel extends Observable {
    public GameWorldModel(
            final BodyFactoryWrapper bodyFactoryWrapper,
            final Vector2 aMainBasePosition,
            final Vector2 aShipsCreationPosition,
            final Vector2 eMainBasePosition,
            final Vector2 eShipFactoryPosition) {

        this.bodyFactoryWrapper = bodyFactoryWrapper;

        final Vector2 rocketSize = UConverter.M2P(Gdx.graphics.getWidth() * 0.004f, Gdx.graphics.getHeight() * 0.02f);
        capitalModel = new CapitalModel(aMainBasePosition, rocketSize);

        shipSize = UConverter.M2P(Gdx.graphics.getWidth() * 0.04f, Gdx.graphics.getHeight() * 0.04f);
        shipCreationPosition = aShipsCreationPosition;
        bombersDefaultHealth = 100;
        fighterDefaultHealth = 20;

        oilPump1 = new OilPump(0.04f);
        oilPump2 = new OilPump(0.04f);
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

    public void updatePreparingState() {
        if (Gdx.input.justTouched()) {
            changeWorldState(GameStates.GAME_RUNNING);
        }
    }

    public void updateRunningState(float delta) {
        if (!capitalModel.update(oilPump1.getResources(delta) + oilPump2.getResources(delta),
                waterPump.getResources(delta))) {
            setChanged();
            notifyObservers(capitalModel.getShieldStatus());
        }

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

    public EventListener getShipsCreationEventListener(ShipTypes shipType,
                                                       final RelationTypes relationType) throws TypeNotPresentException {
        switch (shipType) {
            case BOMBER:
                return new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(capitalModel.changeOil(-122)) {
                            bodyFactoryWrapper.createBody(
                                    new Bomber(shipCreationPosition, shipSize, bombersDefaultHealth, relationType), false
                            );
                        }
                    }
                };
            case FIGHTER:
                return new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(capitalModel.changeOil(-55)) {
                            bodyFactoryWrapper.createBody(
                                    new Fighter(shipCreationPosition, shipSize, fighterDefaultHealth, relationType), false
                            );
                        }
                    }
                };
            default:
                throw new TypeNotPresentException("default type not present ", new Throwable());
        }
    }

    public EventListener getRocketFireEventListener() {
        return capitalModel.missileLaunch(bodyFactoryWrapper);
    }

    public EventListener getIONShieldListener() {
        return capitalModel.getIONShieldListener();
    }

    public void changeWorldState(GameStates newWorldState) {
        worldState = newWorldState;
        setChanged();
        notifyObservers(worldState);
    }

    public long getOil() {
        return capitalModel.getOilBarrels();
    }

    public long getWater() {
        return capitalModel.getWaterBarrels();
    }


    private GameStates worldState;

    final private BodyFactoryWrapper bodyFactoryWrapper;

    final private CapitalModel capitalModel;
    final private Pump oilPump1, oilPump2;

    final private Pump waterPump;
    final Vector2 shipCreationPosition, shipSize;

    int fighterDefaultHealth, bombersDefaultHealth;
}
